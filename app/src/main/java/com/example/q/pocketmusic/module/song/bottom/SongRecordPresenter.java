package com.example.q.pocketmusic.module.song.bottom;

import android.Manifest;
import android.content.Intent;
import android.media.MediaPlayer;
import android.media.MediaRecorder;
import android.net.Uri;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.provider.Settings;

import com.example.q.pocketmusic.config.constant.Constant;
import com.example.q.pocketmusic.config.constant.IntentConstant;
import com.example.q.pocketmusic.data.bean.Song;
import com.example.q.pocketmusic.data.bean.SongObject;
import com.example.q.pocketmusic.data.bean.local.RecordAudio;
import com.example.q.pocketmusic.data.db.RecordAudioDao;
import com.example.q.pocketmusic.module.common.BaseActivity;
import com.example.q.pocketmusic.module.common.BasePresenter;
import com.example.q.pocketmusic.module.common.IBaseView;
import com.example.q.pocketmusic.module.song.SongActivity;
import com.example.q.pocketmusic.util.common.FileUtils;

import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

import pub.devrel.easypermissions.EasyPermissions;

/**
 * Created by 鹏君 on 2017/5/31.
 */

public class SongRecordPresenter extends BasePresenter<SongRecordPresenter.IView> {
    //显示文字状态
    private RECORD_STATUS status = RECORD_STATUS.STOP;
    private Song song;
    private Intent intent;


    public void setIntent(Intent intent) {
        this.intent = intent;
    }

    public Intent getIntent() {
        return intent;
    }

    public void init() {
        //初始化song
        song = ((SongObject) intent.getSerializableExtra(IntentConstant.EXTRA_SONG_ACTIVITY_SONG_OBJECT)).getSong();
    }

    public enum RECORD_STATUS {
        PLAY, STOP
    }

    private RecordAudioDao recordAudioDao;
    //录音文件夹
    private final static String RECORD_DIR = Environment.getExternalStorageDirectory().getAbsolutePath() + "/" + Constant.RECORD_FILE + "/";
    //暂存文件夹
    private final static String TEMP_DIR = Environment.getExternalStorageDirectory().getAbsolutePath() + "/";
    //暂存文件名
    private final static String TEMP_NAME = "temp";
    //播放器和录音器
    private MediaRecorder mRecorder = new MediaRecorder();
    private MediaPlayer mPlayer = new MediaPlayer();
    //录音时间标志
    private static final int ADD_TIME = 0;
    //录音时间
    private int mRecordTime;
    //定时任务
    private Timer mRecordTimer;
    private final int REQUEST_RECORD_AUDIO = 1001;
    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case ADD_TIME:
                    mRecordTime++;
                    mView.changedTimeTv(String.valueOf(mRecordTime));
                    break;
            }
        }
    };

    public SongRecordPresenter(IView fragment) {
       super(fragment);
        recordAudioDao = new RecordAudioDao(fragment.getCurrentContext());
        File file = new File(RECORD_DIR);
        if (!file.exists()) {
            file.mkdirs();
        }
    }


    //录音
    public void record() {
        //请求权限,有可能出错
        String[] perms = {Manifest.permission.RECORD_AUDIO};
        if (!EasyPermissions.hasPermissions(mContext, perms)) {
            EasyPermissions.requestPermissions((BaseActivity) mContext, "录音权限", REQUEST_RECORD_AUDIO, perms);
            return;
        }

        mView.setBtnStatus(status);
        //开始录音
        if (status == RECORD_STATUS.STOP) {
            //设置按钮文字
            status = RECORD_STATUS.PLAY;
            //初始化
            mRecorder.setAudioSource(MediaRecorder.AudioSource.MIC);
            mRecorder.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP);
            //暂存文件夹和文件名,存在就先删除源文件
            File file = new File(TEMP_DIR + TEMP_NAME + ".3gp");
            if (file.exists()) {
                file.delete();
            }
            mRecorder.setOutputFile(TEMP_DIR + TEMP_NAME + ".3gp");
            mRecorder.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB);
            mRecordTime = 0;
            try {
                mRecorder.prepare();
                mRecorder.start();
                TimerTask task = new TimerTask() {
                    @Override
                    public void run() {
                        mHandler.sendEmptyMessage(ADD_TIME);
                    }
                };
                mRecordTimer = new Timer();
                mRecordTimer.schedule(task, 1000, 1000);

            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            //停止录音
            status = RECORD_STATUS.STOP;
            mRecorder.stop();
            mHandler.removeMessages(ADD_TIME);
            mRecordTimer.cancel();
            mRecordTime = 0;
            mView.changedTimeTv(String.valueOf(mRecordTime));
            //加入弹出dialog
            mView.showAddDialog(song.getName());
        }
    }


    //s:歌曲名=editText的输入
    public void saveRecordAudio(final String s) {
        try {
            //得到时长
            mPlayer.reset();
            mPlayer.setDataSource(TEMP_DIR + TEMP_NAME + ".3gp");//设置为暂时的路径
            mPlayer.prepareAsync();
            mPlayer.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                @Override
                public void onPrepared(MediaPlayer mp) {
                    int duration = mp.getDuration();
                    //保存到数据库
                    RecordAudio recordAudio = new RecordAudio();
                    recordAudio.setName(s);
                    recordAudio.setDuration(duration);//存入的是毫秒
                    recordAudio.setDate(dateFormat.format(new Date()));//以存入的时间不同来区别不同
                    recordAudio.setPath(RECORD_DIR + s + ".3gp");
                    boolean isSucceed = recordAudioDao.add(recordAudio);
                    mView.setAddResult(isSucceed);//返回结果
                    //只有当没有重名的时候才移动文件
                    //将tempRecord移动到指定文件夹
                    if (isSucceed) {
                        FileUtils.copyFile(TEMP_DIR + TEMP_NAME + ".3gp", RECORD_DIR + s + ".3gp");
                    }
                }
            });
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void onStop() {
        //暂停线程,取消Timer
        mHandler.removeMessages(ADD_TIME);
        if (mRecordTimer != null) {
            mRecordTimer.cancel();
        }
        //重置mRecorder
        if (mRecorder != null) {
            mRecorder.reset();
        }
        //重置mPlayer
        if (mPlayer != null) {
            mPlayer.reset();
        }

        //初始化
        status = RECORD_STATUS.STOP;
        mView.setBtnStatus(RECORD_STATUS.PLAY);
        mView.changedTimeTv(String.valueOf(0));
    }

    //释放资源
    public void release() {
        onStop();
        mPlayer.release();
        mRecorder.release();
    }


    interface IView extends IBaseView {

        void changedTimeTv(String s);

        void setAddResult(boolean isSucceed);

        void setBtnStatus(RECORD_STATUS play);

        void showAddDialog(String name);
    }
}
