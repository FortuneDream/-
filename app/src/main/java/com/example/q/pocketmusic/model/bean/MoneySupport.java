package com.example.q.pocketmusic.model.bean;

import cn.bmob.v3.BmobObject;

/**
 * Created by 鹏君 on 2017/7/8.
 * （￣m￣）
 */

public class MoneySupport extends BmobObject {
    private MyUser user;
    private String content;
    private String money;


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

    public String getMoney() {
        return money;
    }

    public void setMoney(String money) {
        this.money = money;
    }
}
