package com.example.q.pocketmusic.model.bean.special;

import cn.bmob.v3.BmobObject;

/**
 * Created by 鹏君 on 2017/8/24.
 * （￣m￣）
 */

public class SpecialColumnPic extends BmobObject {
    private SpecialColumnSong song;
    private String url;

    public SpecialColumnSong getSong() {
        return song;
    }

    public void setSong(SpecialColumnSong song) {
        this.song = song;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
