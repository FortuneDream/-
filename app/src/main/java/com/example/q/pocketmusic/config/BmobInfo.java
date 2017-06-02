package com.example.q.pocketmusic.config;

import cn.bmob.v3.BmobObject;

/**
 * Created by 鹏君 on 2017/4/2.
 */

public class BmobInfo extends BmobObject{
    private int type;
    private String content;

    public BmobInfo(int type, String content) {
        this.type = type;
        this.content = content;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
