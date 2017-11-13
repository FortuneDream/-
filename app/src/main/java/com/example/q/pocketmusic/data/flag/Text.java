package com.example.q.pocketmusic.data.flag;

/**
 * Created by 鹏君 on 2016/11/22.
 */
//标记类，标记热门搜索的顶部Text
public class Text {
    private int mResource;//右边的图片资源
    private String name;

    public Text(int mResource, String name) {
        this.mResource = mResource;
        this.name = name;
    }

    public int getmResource() {
        return mResource;
    }

    public void setmResource(int mResource) {
        this.mResource = mResource;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
