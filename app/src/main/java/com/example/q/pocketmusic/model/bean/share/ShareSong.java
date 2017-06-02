package com.example.q.pocketmusic.model.bean.share;

import com.example.q.pocketmusic.model.bean.MyUser;

import cn.bmob.v3.BmobObject;

/**
 * Created by 鹏君 on 2017/1/16.
 */
//分享乐谱，用于替换UploadSong
public class ShareSong extends BmobObject {
    private MyUser user;//上传者
    private String name;//歌曲名字
    private String content;//内容，介绍信息


    public ShareSong(MyUser user, String name, String content) {
        this.user = user;
        this.name = name;
        this.content = content;
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
