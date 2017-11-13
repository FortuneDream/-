package com.example.q.pocketmusic.data.bean.local;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

/**
 * Created by 鹏君 on 2016/9/19.
 */
@DatabaseTable(tableName = "tb_img")
public class Img {
    @DatabaseField(generatedId = true)
    private int imgId;
    @DatabaseField
    private String url;
    @DatabaseField(foreign = true, foreignAutoRefresh = true)
    private LocalSong localSong;

    public Img() {
    }

    @Override
    public String toString() {
        return "Img{" +
                "imgId=" + imgId +
                ", url='" + url + '\'' +
                ", localSong=" + localSong +
                '}';
    }

    public int getImgId() {
        return imgId;
    }

    public void setImgId(int imgId) {
        this.imgId = imgId;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public LocalSong getLocalSong() {
        return localSong;
    }

    public void setLocalSong(LocalSong localSong) {
        this.localSong = localSong;
    }
}
