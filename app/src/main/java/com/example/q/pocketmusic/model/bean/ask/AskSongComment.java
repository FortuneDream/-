package com.example.q.pocketmusic.model.bean.ask;

import com.example.q.pocketmusic.model.bean.MyUser;

import cn.bmob.v3.BmobObject;
import cn.bmob.v3.datatype.BmobRelation;

/**
 * Created by 鹏君 on 2017/1/11.
 */

public class AskSongComment extends BmobObject {
    private MyUser user;//评论的用户，Pointer类型，一对一
    private AskSongPost post;//一个评论属于一个帖子,一对多
    private BmobRelation agrees;//点赞的多个用户，多对多
    private Integer agreeNum;//点赞用户的数量
    private String content;//评论内容
    private Boolean hasPic;//是否有图片

    public Integer getAgreeNum() {
        return agreeNum;
    }

    public void setAgreeNum(Integer agreeNum) {
        this.agreeNum = agreeNum;
    }

    public BmobRelation getAgrees() {
        return agrees;
    }

    public void setAgrees(BmobRelation agrees) {
        this.agrees = agrees;
    }




    public AskSongComment() {
    }

    public AskSongComment(MyUser user, AskSongPost post, String content, Boolean hasPic) {
        this.user = user;
        this.post = post;
        this.content = content;
        this.hasPic = hasPic;
        this.agreeNum = 0;//默认为0
    }

    public Boolean getHasPic() {
        return hasPic;
    }

    public void setHasPic(Boolean hasPic) {
        this.hasPic = hasPic;
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

    public AskSongPost getPost() {
        return post;
    }

    public void setPost(AskSongPost post) {
        this.post = post;
    }
}
