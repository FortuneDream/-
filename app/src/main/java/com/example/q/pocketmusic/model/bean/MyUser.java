package com.example.q.pocketmusic.model.bean;

import com.example.q.pocketmusic.config.Constant;

import cn.bmob.v3.BmobUser;
import cn.bmob.v3.datatype.BmobRelation;

/**
 * Created by 鹏君 on 2017/1/10.
 */
//用户类，内置
// username: 用户的用户名，使用邮箱，验证正则
//password: 用户的密码。
public class MyUser extends BmobUser {
    private String nickName;//昵称
    private String instrument;//乐器
    private String headImg;//头像
    private String lastSignInDate;//最后一次签到时间
    private Integer contribution;//贡献值
    private BmobRelation collections;//某个用户收藏的所有曲谱

    public String getLastSignInDate() {
        return lastSignInDate;
    }

    public void setLastSignInDate(String lastSignInDate) {
        this.lastSignInDate = lastSignInDate;
    }

    public MyUser() {
        this.nickName = "匿名";
        this.instrument = "无";
        this.headImg = Constant.COMMON_HEAD_IV_URL;
        this.contribution = Constant.ADD_CONTRIBUTION_INIT;
    }

    public String getHeadImg() {
        return headImg;
    }

    public void setHeadImg(String headImg) {
        this.headImg = headImg;
    }

    public Integer getContribution() {
        return contribution;
    }

    public void setContribution(Integer contribution) {
        this.contribution = contribution;
    }

    public BmobRelation getCollections() {
        return collections;
    }

    public void setCollections(BmobRelation collections) {
        this.collections = collections;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public String getInstrument() {
        return instrument;
    }

    public void setInstrument(String instrument) {
        this.instrument = instrument;
    }
}
