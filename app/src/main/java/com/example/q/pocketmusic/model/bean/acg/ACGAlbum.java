package com.example.q.pocketmusic.model.bean.acg;

import cn.bmob.v3.BmobObject;

/**
 * Created by 鹏君 on 2017/7/26.
 * （￣m￣）
 */

public class ACGAlbum extends BmobObject {
    private String backgroundUrl;//封面URL
    private String albumName;//专辑名字
    private int songNum;//含有曲谱数

    public String getBackgroundUrl() {
        return backgroundUrl;
    }

    public void setBackgroundUrl(String backgroundUrl) {
        this.backgroundUrl = backgroundUrl;
    }

    public String getAlbumName() {
        return albumName;
    }

    public void setAlbumName(String albumName) {
        this.albumName = albumName;
    }

    public int getSongNum() {
        return songNum;
    }

    public void setSongNum(int songNum) {
        this.songNum = songNum;
    }
}
