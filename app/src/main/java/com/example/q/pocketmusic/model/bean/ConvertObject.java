package com.example.q.pocketmusic.model.bean;

import java.io.Serializable;
import java.util.List;

/**
 * Created by 81256 on 2017/10/4.
 */

public class ConvertObject implements Serializable {
    private String title;//求谱名字
    private List<String> ivUrl;//曲谱集合
    private int loadingWay;//本地，网络

    public ConvertObject(String title, List<String> ivUrl, int loadingWay) {
        this.title = title;
        this.ivUrl = ivUrl;
        this.loadingWay = loadingWay;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public List<String> getIvUrl() {
        return ivUrl;
    }

    public void setIvUrl(List<String> ivUrl) {
        this.ivUrl = ivUrl;
    }

    public int getLoadingWay() {
        return loadingWay;
    }

    public void setLoadingWay(int loadingWay) {
        this.loadingWay = loadingWay;
    }
}
