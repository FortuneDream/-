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
    private ConvertPost post;//一个评论属于一个帖子,一对多
    private String content;//谱子
    private String title;
    private int checkNum;//查看此乐谱的用户的数量;

    public int getCheckNum() {
        return checkNum;
    }

    public void setCheckNum(int checkNum) {
        this.checkNum = checkNum;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public ConvertComment() {
        this.checkNum = 0;
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


}
