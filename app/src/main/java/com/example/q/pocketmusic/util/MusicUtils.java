package com.example.q.pocketmusic.util;

import android.content.Context;
import android.media.AudioManager;
import android.media.SoundPool;
import android.support.annotation.IdRes;

import com.example.q.pocketmusic.R;
import com.example.q.pocketmusic.model.bean.convert.Sound;
import com.example.q.pocketmusic.util.common.LogUtils;
import com.example.q.pocketmusic.util.common.ToastUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by 鹏君 on 2017/4/17.
 */

public class MusicUtils {
    private Context context;
    int[] mMusicRaws = {R.raw.e3,R.raw.g3, R.raw.a3, R.raw.b3, R.raw.c4, R.raw.d4, R.raw.e4, R.raw.f4, R.raw.g4, R.raw.a4, R.raw.b4, R.raw.c5, R.raw.d5, R.raw.e5, R.raw.f5, R.raw.g5};
    int[] mMusicButtonIds = {R.id.e3,R.id.g3, R.id.a3, R.id.b3, R.id.c4, R.id.d4, R.id.e4, R.id.f4, R.id.g4, R.id.a4, R.id.b4, R.id.c5, R.id.d5, R.id.e5, R.id.f5, R.id.g5};
    private String[] mMusicNoteStrs = {"[3]","[5]", "[6]", "[7]", "1", "2", "3", "4", "5", "6", "7", "(1)", "(2)", "(3)", "(4)", "(5)"};
    private List<Sound> sounds;
    private SoundPool soundPool;
    private Boolean isComplete;
    public static final int DELETE_ID = -1;
    public static final int BO_LANG_ID = -2;
    public static final int ENTER_ID = -3;
    public static final int BLANK_ID = -4;

    public static final String BO_LANG = "~";
    public static final String ENTER = "\n";
    public static final String BLANK = " ";


    public MusicUtils(Context context) {
        sounds = new ArrayList<>();
        soundPool = new SoundPool(mMusicRaws.length, AudioManager.STREAM_MUSIC, 0);//同时支持两个键
        for (int i = 0; i < mMusicRaws.length; i++) {
            int temp = soundPool.load(context, mMusicRaws[i], 0);
//            LogUtils.e("sound temp:"+temp);
//            LogUtils.e("sound id:"+mMusicButtonIds[i]);
            Sound sound = new Sound(mMusicButtonIds[i], temp, mMusicNoteStrs[i]);
            sounds.add(sound);
        }
        soundPool.setOnLoadCompleteListener(new SoundPool.OnLoadCompleteListener() {
            @Override
            public void onLoadComplete(SoundPool soundPool, int sampleId, int status) {
                isComplete = true;
            }
        });
    }


    //播放
    public void soundPlay(Sound sound) {
        if (isComplete) {
            int note = sound.getNote();
            if (note == -1) {
                return;
            }
            soundPool.play(note, 1.0f, 1.0f, 1, 0, 1.0f);
        } else {
            ToastUtil.showToast("正在加载音频~请稍后");
        }
    }

    //通过resId 取出音效  sol
    public int getSoundNote(@IdRes int id) {
        for (int i = 0; i < sounds.size(); i++) {
            if (sounds.get(i).getResId() == id) {
                return sounds.get(i).getNote();
            }
        }
        return -1;
    }

    //通过resId 取出字符  [5]
    public String getSoundStr(@IdRes int id) {
        for (int i = 0; i < sounds.size(); i++) {
            if (sounds.get(i).getResId() == id) {
                return sounds.get(i).getNumber();
            }
        }
        return "-1";
    }

    @Override
    protected void finalize() throws Throwable {
        super.finalize();
        soundPool.release();
        soundPool = null;
    }

}
