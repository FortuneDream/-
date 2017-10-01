package com.example.q.pocketmusic.model.bean.convert;

import com.example.q.pocketmusic.model.bean.MyUser;

import cn.bmob.v3.BmobObject;

/**
 * Created by 81256 on 2017/9/30.
 */

public class ConvertPost extends BmobObject {
    private MyUser user;//帖子的发布者，一对一
    private String title;//帖子标题
    private String content;//帖子内容
    private int commentNum;//回复数量
    private int coin;//赏金


    public ConvertPost() {
        this.commentNum = 0;
        this.coin = 30;
    }

    public MyUser getUser() {
        return user;
    }

    public void setUser(MyUser user) {
        this.user = user;
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

    public int getCommentNum() {
        return commentNum;
    }

    public void setCommentNum(int commentNum) {
        this.commentNum = commentNum;
    }


    public int getCoin() {
        return coin;
    }

    public void setCoin(int coin) {
        this.coin = coin;
    }
}