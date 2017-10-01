package com.example.q.pocketmusic.model.bean.convert;

/**
 * Created by 81256 on 2017/9/30.
 */

public class Sound {
    private int resId;//资源的id                R.id.a3
    private String number;//音符                [5]
    private int note;//加载出来的音效           sol


    public Sound(int resId, String number, int note) {
        this.resId = resId;
        this.number = number;
        this.note = note;
    }

    public Sound() {
    }

    public int getResId() {
        return resId;
    }

    public void setResId(int resId) {
        this.resId = resId;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public int getNote() {
        return note;
    }

    public void setNote(int note) {
        this.note = note;
    }
}
