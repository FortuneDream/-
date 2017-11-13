package com.example.q.pocketmusic.data.bean;

import cn.bmob.v3.BmobObject;

/**
 * Created by 鹏君 on 2017/7/8.
 * （￣m￣）
 */

public class MoneySupport extends BmobObject {
    private MyUser user;
    private String content;
    private double money;


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

    public double getMoney() {
        return money;
    }

    public void setMoney(double money) {
        this.money = money;
    }
}
