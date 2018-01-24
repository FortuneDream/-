package com.example.q.pocketmusic.data.event;

/**
 * Created by 81256 on 2018/1/24.
 */

public class LoadingDialogEvent {
    private boolean isShow;

    public LoadingDialogEvent(boolean isShow) {
        this.isShow = isShow;
    }

    public boolean isShow() {
        return isShow;
    }

    public void setShow(boolean show) {
        isShow = show;
    }
}
