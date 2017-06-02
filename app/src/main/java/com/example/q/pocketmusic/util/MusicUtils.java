package com.example.q.pocketmusic.util;

import android.content.Context;
import android.media.AudioManager;
import android.media.SoundPool;

import com.example.q.pocketmusic.R;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by 鹏君 on 2017/4/17.
 */

public class MusicUtils {
    int[] mMusicRaws = {R.raw.di_do1, R.raw.di_re2, R.raw.di_mi3, R.raw.di_fa4, R.raw.di_sol5, R.raw.di_la6, R.raw.di_si7};
    int[] mMusicButtonIds = {R.id.do_1, R.id.re_2, R.id.mi_3, R.id.fa_4, R.id.sol_5, R.id.la_6, R.id.xi_7};
    String[] mMusicNoteStrs = {"1", "2", "3", "4", "5", "6", "7"};
    private SoundPool soundPool;
    private Map<Integer, Integer> soundPoolMap;//音效池
    private Map<Integer, String> soundNoteMap;//对应的音符池
    private Boolean isComplete;
    private Context context;

    public MusicUtils(Context context) {
        this.context = context;
        soundPool = new SoundPool(mMusicRaws.length, AudioManager.STREAM_MUSIC, 0);//同时支持两个键
        soundPoolMap = new HashMap<>();
        soundNoteMap = new HashMap<>();
        for (int i = 0; i < mMusicRaws.length; i++) {
            soundPoolMap.put(mMusicButtonIds[i], soundPool.load(context, mMusicRaws[i], 0));
            soundNoteMap.put(mMusicButtonIds[i], mMusicNoteStrs[i]);
        }
        soundPool.setOnLoadCompleteListener(new SoundPool.OnLoadCompleteListener() {
            @Override
            public void onLoadComplete(SoundPool soundPool, int sampleId, int status) {
                isComplete = true;
            }
        });
    }

    //no 从0开始  0表示di_do_1
    public void soundPlay(int no) {
        if (isComplete) {
            soundPool.play(soundPoolMap.get(no), 1.0f, 1.0f, 1, 0, 1.0f);
        } else {
            MyToast.showToast(context, "正在加载音频~请稍后");
        }
    }

    public int soundOver() {
        return soundPool.play(soundPoolMap.get(1), 1.0f, 1.0f, 1, 0, 1.0f);
    }

    @Override
    protected void finalize() throws Throwable {
        super.finalize();
        soundPool.release();
        soundPool = null;
    }

    public String getNote(int id) {
        return soundNoteMap.get(id);
    }
}
