package com.example.q.pocketmusic.model.bean;

/**
 * Created by 鹏君 on 2017/2/20.
 */

public class DownloadInfo {
    private String info;
    private boolean isStart;



    public DownloadInfo(String info, boolean isStart) {
        this.info = info;
        this.isStart = isStart;
    }

    public DownloadInfo() {
    }

    public String getInfo() {
        return info;
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
