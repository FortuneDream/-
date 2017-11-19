package com.example.q.pocketmusic.data.bean;

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
    private String signature;//个人签名
    private String nickName;//昵称
    private String headImg;//头像
    private String lastSignInDate;//最后一次签到时间
    private int contribution;//贡献值
    private BmobRelation collections;//某个用户收藏的所有曲谱
    private BmobRelation interests;//关注
    private String versionFlag;//版本

    public BmobRelation getInterests() {
        return interests;
    }

    public void setInterests(BmobRelation interests) {
        this.interests = interests;
    }


    public String getLastSignInDate() {
        return lastSignInDate;
    }

    public void setLastSignInDate(String lastSignInDate) {
        this.lastSignInDate = lastSignInDate;
    }


    public MyUser() {
        this.signature = "这个人没有签名哦~";
        this.nickName = "匿名";
        this.headImg = Constant.COMMON_HEAD_IV_URL;
        this.contribution = Constant.ADD_CONTRIBUTION_INIT;
    }


    public void setVersion(String version) {
        this.versionFlag = version;
    }

    public String getSignature() {
        return signature;
    }

    public void setSignature(String signature) {
        this.signature = signature;
    }

    public String getHeadImg() {
        return headImg;
    }

    public void setHeadImg(String headImg) {
        this.headImg = headImg;
    }

    public int getContribution() {
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

}
