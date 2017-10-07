package com.example.q.pocketmusic.model.bean.convert;

import com.example.q.pocketmusic.model.bean.MyUser;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

import java.io.Serializable;

import cn.bmob.v3.BmobObject;

/**
 * Created by 81256 on 2017/10/3.
 */
//暂存
public class ConvertSong extends BmobObject {
    private String name;
    private String content;
    private MyUser user;//属于某个人

    public ConvertSong() {
    }

    public MyUser getUser() {
        return user;
    }

    public void setUser(MyUser user) {
        this.user = user;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

}
