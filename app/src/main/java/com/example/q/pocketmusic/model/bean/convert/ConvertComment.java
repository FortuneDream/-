package com.example.q.pocketmusic.model.bean.convert;

import com.example.q.pocketmusic.model.bean.MyUser;
import com.example.q.pocketmusic.model.bean.ask.AskSongPost;

import cn.bmob.v3.BmobObject;
import cn.bmob.v3.datatype.BmobRelation;

/**
 * Created by 81256 on 2017/10/1.
 */

public class ConvertComment extends BmobObject {
    private MyUser user;//评论的用户，Pointer类型，一对一
    private BmobRelation consume;//购买了此转谱贴的用户
    private ConvertPost post;//一个评论属于一个帖子,一对多
    private String content;//谱子

    public ConvertComment() {
    }

    public MyUser getUser() {
        return user;
    }

    public void setUser(MyUser user) {
        this.user = user;
    }

    public ConvertPost getPost() {
        return post;
    }

    public void setPost(ConvertPost post) {
        this.post = post;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public BmobRelation getRelation() {
        return consume;
    }

    public void setRelation(BmobRelation relation) {
        this.consume = relation;
    }
}
