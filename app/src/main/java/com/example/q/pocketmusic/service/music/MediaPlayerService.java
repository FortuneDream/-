package com.example.q.pocketmusic.service.music;

import android.app.Service;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.IBinder;
import android.os.RemoteException;
import android.support.annotation.Nullable;

import com.dell.fortune.tools.toast.ToastUtil;
import com.example.q.pocketmusic.IAudioPlayerService;
import com.example.q.pocketmusic.data.bean.local.RecordAudio;
import com.example.q.pocketmusic.data.db.RecordAudioDao;

import java.io.IOException;
import java.util.List;

/**
 * Created by 鹏君 on 2016/11/1.
 */

public class MediaPlayerService extends Service {
    public static final String RECEIVER_ACTION = "Receiver_action";//用不同的intent
    public static final String NOTIFY = "notify";
    public static final String PROGRESS = "progress";
    public static final String COMPLETE = "complete";

    private MediaPlayer player;
    private List<RecordAudio> list;
    private RecordAudioDao recordAudioDao;
    private RecordAudio currentAudio;
    private int currentPosition;

    @Override
    public void onCreate() {
        super.onCreate();
        recordAudioDao = new RecordAudioDao(this);
        list = recordAudioDao.queryForAll();
        //Log.e("TAG","Service onCreate");
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        return super.onStartCommand(intent, flags, startId);
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        //Log.e("TAG","onBind()");
        return new MyBinder();
    }


    //开启播放器
    private void openAudio(int position) throws IOException {
        currentAudio = list.get(position);
        currentPosition = position;
        //Log.e("TAG","openAudio初始化");
        if (player != null) {
            player.reset();
            player.release();
            player = null;
        }
        player = new MediaPlayer();
        //设置监听
        setListener();
        //设置数据源并准备
        player.setDataSource(currentAudio.getPath());
        player.prepareAsync();
    }

    private void setListener() {
        //播放开始
        player.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mp) {
                play();
                //Log.e("TAG","play");
                notifyChange(PROGRESS);//发送准备完成的广播
            }
        });
        //播放完成
        player.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {
                //Log.e("TAG","complete");
                notifyChange(COMPLETE);
            }
        });
        //播放错误
        player.setOnErrorListener(new MediaPlayer.OnErrorListener() {
            @Override
            public boolean onError(MediaPlayer mp, int what, int extra) {
                ToastUtil.showToast("播放出错了");
                return false;
            }
        });
    }

    private String getAudioName() {
        if (currentAudio != null) {
            return currentAudio.getName();
        }
        return "";
    }

    private void pause() {
        if (player != null) {
            player.pause();
        }
    }

    private void play() {
        if (player != null) {
            player.start();
        }
    }

    //获得总时长
    private int getDuration() {
        if (player != null) {
            return player.getDuration();
        }
        return 0;
    }

    //得到当前位置
    public int getCurrentPosition() {
        if (player != null) {
            return player.getCurrentPosition();
        }
        return 0;
    }


    //定位到播放位置
    private void seekTo(int position) {
        if (player != null) {
            player.seekTo(position);
        }
    }

    private boolean isPlaying() {
        return player != null && player.isPlaying();
    }

    private void preSong() {
        if (player != null) {
            currentPosition--;
            //倒回到最后一个
            if (currentPosition == -1) {
                currentPosition = list.size() - 1;
            }
            try {
                openAudio(currentPosition);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void nextSong() {
        if (player != null) {
            currentPosition++;
            //防止溢出
            currentPosition = currentPosition % list.size();
            try {
                openAudio(currentPosition);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    //停止播放
    private void stop() {
        if (player != null) {
            player.reset();
            player.release();
            player = null;
        }
    }


    //发广播
    private void notifyChange(String notify) {
        Intent intent = new Intent();
        intent.setAction(RECEIVER_ACTION);
        intent.putExtra(NOTIFY, notify);
        sendBroadcast(intent);
    }

    private class MyBinder extends IAudioPlayerService.Stub {
        MediaPlayerService service = MediaPlayerService.this;

        @Override
        public void notifyChange(String notify) throws RemoteException {
            service.notifyChange(notify);
        }

        @Override
        public boolean isPlaying() throws RemoteException {
            return service.isPlaying();
        }

        @Override
        public void openAudio(int position) throws RemoteException {
            try {
                service.openAudio(position);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        @Override
        public void play() throws RemoteException {
            service.play();
        }

        @Override
        public void pause() throws RemoteException {
            service.pause();
        }

        @Override
        public String getAudioName() throws RemoteException {
            return service.getAudioName();
        }

        @Override
        public int getDuration() throws RemoteException {
            return service.getDuration();
        }

        @Override
        public int getCurrentPosition() throws RemoteException {
            return service.getCurrentPosition();
        }

        @Override
        public void seekTo(int position) throws RemoteException {
            service.seekTo(position);
        }

        @Override
        public void preSong() throws RemoteException {
            service.preSong();
        }

        @Override
        public void nextSong() throws RemoteException {
            service.nextSong();
        }

        @Override
        public void stop() throws RemoteException {
            service.stop();
        }
    }


}
