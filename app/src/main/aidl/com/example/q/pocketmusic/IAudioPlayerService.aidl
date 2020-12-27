// IAudioPlayerService.aidl
package com.example.q.pocketmusic;

// Declare any non-default typeNames here with import statements

interface IAudioPlayerService {
        //
        void notifyChange(String notify);

        //
        boolean isPlaying();

        //
        void openAudio(int position);

        //
        void play();

        //
        void pause();

        //
        String getAudioName();

        //
        int getDuration();


        int getCurrentPosition();


        void seekTo(int position);


        void preSong();


        void nextSong();


        void stop();
}
