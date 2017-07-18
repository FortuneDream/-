package com.example.q.pocketmusic.model.bean;

import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;

/**
 * Created by 鹏君 on 2017/2/5.
 */
//Intent传递
public class SongObject implements Serializable {
    private Song song;
    private int from;
    private int showMenu;
    private int loadingWay;

    public SongObject(Song song, int from, int showMenu, int loadingWay) {
        this.song = song;
        this.from = from;
        this.showMenu = showMenu;
        this.loadingWay = loadingWay;
    }


    public Song getSong() {
        return song;
    }

    public void setSong(Song song) {
        this.song = song;
    }

    public int getFrom() {
        return from;
    }

    public void setFrom(int from) {
        this.from = from;
    }

    public int getShowMenu() {
        return showMenu;
    }

    public void setShowMenu(int showMenu) {
        this.showMenu = showMenu;
    }

    public int getLoadingWay() {
        return loadingWay;
    }

    public void setLoadingWay(int loadingWay) {
        this.loadingWay = loadingWay;
    }
}
