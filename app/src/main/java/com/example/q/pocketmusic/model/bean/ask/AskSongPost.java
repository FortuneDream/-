package com.example.q.pocketmusic.model.bean.ask;

import com.example.q.pocketmusic.model.bean.MyUser;

import cn.bmob.v3.BmobObject;

/**
 * Created by 鹏君 on 2016/11/15.
 */

public class AskSongPost extends BmobObject {
    private MyUser user;//帖子的发布者，一对一
    private String title;//帖子标题
    private int type;//所求曲谱类型
    private String content;//帖子内容
    private Integer commentNum;//回复数量
    private int index;//先按照指数，再创建时间逆序


    public AskSongPost() {
    }

    public AskSongPost(MyUser user, String title, int type, String content) {
        this.user = user;
        this.title = title;
        this.content = content;
        this.type = type;
        this.commentNum = 0;
        this.index = 0;
    }

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public MyUser getUser() {
        return user;
    }

    public void setUser(MyUser user) {
        this.user = user;
    }

    public Integer getCommentNum() {
        return commentNum;
    }

    public void setCommentNum(Integer commentNum) {
        this.commentNum = commentNum;
    }

}

