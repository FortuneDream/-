package com.example.q.pocketmusic.data.bean.bmob;

import com.example.q.pocketmusic.data.bean.MyUser;

import cn.bmob.v3.BmobObject;

/**
 * Created by 81256 on 2017/9/6.
 */

public class Gift extends BmobObject {
    private MyUser user;
    private String content;
    private int coin;
    private boolean isGet;

    public Gift() {

    }

    public MyUser getUser() {
        return user;
    }

    public void setUser(MyUser user) {
        this.user = user;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public int getCoin() {
        return coin;
    }

    public void setCoin(int coin) {
        this.coin = coin;
    }

    public boolean isGet() {
        return isGet;
    }

    public void setGet(boolean get) {
        isGet = get;
    }
}
