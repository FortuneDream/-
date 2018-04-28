package com.example.q.pocketmusic.module.home.local.localrecord;

import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.ServiceConnection;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.RemoteException;

import com.example.q.pocketmusic.IAudioPlayerService;
import com.example.q.pocketmusic.data.bean.local.RecordAudio;
import com.example.q.pocketmusic.data.db.RecordAudioDao;
import com.example.q.pocketmusic.data.model.LocalModel;
import com.example.q.pocketmusic.module.common.BasePresenter;
import com.example.q.pocketmusic.module.common.IBaseView;
import com.example.q.pocketmusic.service.music.MediaPlayerService;
import com.example.q.pocketmusic.util.common.FileUtils;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import io.reactivex.functions.Consumer;
import io.reactivex.functions.Predicate;
import rx.functions.Action1;

/**
 * Created by 鹏君 on 2016/10/23.
 */

public class LocalRecordFragmentPresenter extends BasePresenter<LocalRecordFragmentPresenter.IView> {
    private static final int PROGRESS = 0;
    private RecordAudioDao recordAudioDao;
    private LocalModel localModel;

    public LocalRecordFragmentPresenter(IView fragment) {
        super(fragment);
        recordAudioDao = new RecordAudioDao(fragment.getAppContext());
        localModel = new LocalModel(fragment.getAppContext());
    }

    //同步录音
    public void synchronizedRecord() {
        //先遍历文件夹的录音，添加到数据库（不重复添加），然后再从数据库取出来
        localModel.synchronizeRecordAudio()
                .filter(new Predicate<List<RecordAudio>>() {
                    @Override
                    public boolean test(List<RecordAudio> recordAudios) throws Exception {
                        return mView != null;
                    }
                })
                .subscribe(new Consumer<List<RecordAudio>>() {
                    @Override
                    public void accept(List<RecordAudio> recordAudios) throws Exception {
                        mView.setList(recordAudios);
                    }
                });
    }

    //长按删除记录
    public void deleteRecord(RecordAudio item) {
        //删除文件
        FileUtils.deleteFile(new File(item.getPath()));
        //数据库删除
        recordAudioDao.delete(item);
    }


    /**
     * 以下代码均是关于音乐播放功能
     */
    private MediaReceiver mMediaReceiver;
    private IAudioPlayerService mService;//播放器接口
    private int position;//当前播放曲谱位置
    private boolean isDestroy;//是否退出
    private SimpleDateFormat durationFormat = new SimpleDateFormat("mm:ss", Locale.CHINA);
    private ServiceConnection connection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            mService = IAudioPlayerService.Stub.asInterface(service);
            if (mService != null) {
                try {
                    //开始播放
                    mService.openAudio(position);
                } catch (RemoteException e) {
                    e.printStackTrace();
                }
            }
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            //关闭音乐
            try {
                mService.stop();
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }
    };
    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case PROGRESS:
                    //递归发送信息,关闭之后isDestroy=true，之后不再更新界面
                    if (!isDestroy) {
                        mHandler.sendEmptyMessageDelayed(PROGRESS, 1000);
                    }
                    int currentPosition;
                    try {
                        currentPosition = mService.getCurrentPosition();
                        String time = durationFormat.format(new Date(mService.getCurrentPosition())) + "/" + durationFormat.format(new Date(mService.getDuration()));
                        mView.updateProgress(currentPosition, time);
                    } catch (RemoteException e) {
                        e.printStackTrace();
                    }
            }
        }
    };


    //收到播放开始的消息之后,接受者用于初始化界面
    private class MediaReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            try {
                String notify = intent.getStringExtra(MediaPlayerService.NOTIFY);//得到不同的notify;
                if (notify.equals(MediaPlayerService.PROGRESS)) {//进度条
                    String name = mService.getAudioName();
                    String time = durationFormat.format(new Date(mService.getCurrentPosition())) + "/" + durationFormat.format(new Date(mService.getDuration()));
                    int duration = mService.getDuration();
                    mView.setViewStatus(name, time, duration);
                    isDestroy = false;
                    mHandler.sendEmptyMessage(PROGRESS);
                } else if (notify.equals(MediaPlayerService.COMPLETE)) {//结束
                    isDestroy = true;
                    mView.setPlayOrPauseImage(true);
                    mService.openAudio(position);//初始化
//                    mService.pause();//这里不能直接暂停，因为是异步播放的

                }
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }
    }


    //注册广播接受者
    public void registerReceiver() {
        IntentFilter filter = new IntentFilter(MediaPlayerService.RECEIVER_ACTION);
        mMediaReceiver = new MediaReceiver();
        mContext.registerReceiver(mMediaReceiver, filter);
        //Log.e("TAG", "registerReceiver()");
    }

    //绑定服务,设定位置
    public void bindService(int position) {
        this.position = position;
        Intent intent = new Intent(mContext, MediaPlayerService.class);
        mContext.bindService(intent, connection, Context.BIND_AUTO_CREATE);
        //Log.e("TAG", "bindService");
    }


    public void seekTo(int progress) {
        try {
            mService.seekTo(progress);
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    public boolean playOrPause() {
        try {
            if (mService.isPlaying()) {
                mService.pause();
                return false;
            } else {
                mService.play();
                return true;
            }
        } catch (RemoteException e) {
            e.printStackTrace();
        }
        return false;
    }

    //解绑服务,并且关闭音乐
    public void closeMedia() {
        isDestroy = true;
        try {
            mService.stop();
        } catch (RemoteException e) {
            e.printStackTrace();
        }
        mContext.unbindService(connection);
        mContext.unregisterReceiver(mMediaReceiver);
        mMediaReceiver = null;
    }


    public interface IView extends IBaseView {
        void setList(List<RecordAudio> list);

        void setViewStatus(String name, String time, int duration);

        void updateProgress(int currentPosition, String time);

        void setPlayOrPauseImage(boolean status);

    }
}
