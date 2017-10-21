package com.example.q.pocketmusic.model.bean;

import cn.bmob.v3.BmobObject;
import cn.bmob.v3.BmobQuery;

/**
 * Created by 81256 on 2017/10/18.
 */
//圈子动态
public class CommunityState extends BmobObject {
    private MyUser user;//所属用户
    private int communityType;//圈子类型
    private int stateType;//动态类型
    private boolean isHide;//是否隐藏
    private String content;//内容

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public CommunityState() {
        communityType = 0;
        stateType = 0;
        isHide = false;
    }

    public boolean isHide() {
        return isHide;
    }

    public void setHide(boolean hide) {
        isHide = hide;
    }

    public MyUser getUser() {
        return user;
    }

    public void setUser(MyUser user) {
        this.user = user;
    }

    public int getCommunityType() {
        return communityType;
    }

    public void setCommunityType(int communityType) {
        this.communityType = communityType;
    }

    public int getStateType() {
        return stateType;
    }

    public void setStateType(int stateType) {
        this.stateType = stateType;
    }
}
