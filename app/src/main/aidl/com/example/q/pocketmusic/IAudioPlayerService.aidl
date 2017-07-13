// IAudioPlayerService.aidl
package com.example.q.pocketmusic;

// Declare any non-default typeNames here with import statements

interface IAudioPlayerService {
        //发广播
        void notifyChange(String notify);

        //是否播放中
        boolean isPlaying();

        //打开音频
        void openAudio(int position);

        //播放音频
        void play();

        //暂停音频
        void pause();

        //得到歌曲名称
        String getAudioName();

        //得到总时长
        int getDuration();

        //得到当前播放位置
        int getCurrentPosition();

        //定位到音频的播放位置
        void seekTo(int position);

        //上一首
        void preSong();

        //下一首
        void nextSong();

        //停止播放
        void stop();
}
