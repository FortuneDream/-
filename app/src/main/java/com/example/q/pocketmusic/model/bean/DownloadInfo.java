package com.example.q.pocketmusic.model.bean;

/**
 * Created by Cloud on 2017/2/20.
 */

public class DownloadInfo {
    private String info;
    private boolean isStart;



    public String getInfo() {
        return info;
    }

    public DownloadInfo(String info, boolean isStart) {
        this.info = info;
        this.isStart = isStart;
    }

    public DownloadInfo() {
    }

    public void setInfo(String info) {
        this.info = info;
    }

    public boolean isStart() {
        return isStart;
    }

    public void setStart(boolean start) {
        isStart = start;
    }
}
