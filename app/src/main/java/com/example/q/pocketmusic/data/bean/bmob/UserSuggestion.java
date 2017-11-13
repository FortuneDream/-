package com.example.q.pocketmusic.data.bean.bmob;

import com.example.q.pocketmusic.data.bean.MyUser;

import cn.bmob.v3.BmobObject;

/**
 * Created by 鹏君 on 2017/1/21.
 */
//用户邮箱
public class UserSuggestion extends BmobObject {
    private MyUser user;
    private String suggestion;
    private String reply;

    public UserSuggestion(MyUser user) {
        this.user = user;
    }

    public MyUser getUser() {
        return user;
    }

    public void setUser(MyUser user) {
        this.user = user;
    }

    public String getSuggestion() {
        return suggestion;
    }

    public void setSuggestion(String suggestion) {
        this.suggestion = suggestion;
    }

    public String getReply() {
        return reply;
    }

    public void setReply(String reply) {
        this.reply = reply;
    }
}
