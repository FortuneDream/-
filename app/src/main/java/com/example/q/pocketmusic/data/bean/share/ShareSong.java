package com.example.q.pocketmusic.data.bean.share;

import com.example.q.pocketmusic.data.bean.MyUser;

import cn.bmob.v3.BmobObject;
import cn.bmob.v3.datatype.BmobRelation;

/**
 * Created by 鹏君 on 2017/1/16.
 */
//分享乐谱，用于替换UploadSong
public class ShareSong extends BmobObject {
    private MyUser user;//上传者
    private String name;//歌曲名字
    private String content;//内容，介绍信息
    private BmobRelation agrees;//点赞的多个用户，多对多
    private int agreeNum;//分享数量
    private int downloadNum;//下载量
    private int collectionNum;//收藏量
    private int instrument;//所属月

    public ShareSong() {

    }

    public int getCollectionNum() {
        return collectionNum;
    }

    public void setCollectionNum(int collectionNum) {
        this.collectionNum = collectionNum;
    }

    public int getInstrument() {
        return instrument;
    }

    public void setInstrument(int instrument) {
        this.instrument = instrument;
    }

    public ShareSong(MyUser user, String name, String content) {
        this.user = user;
        this.name = name;
        this.content = content;
        this.agreeNum = 0;
        this.downloadNum = 0;
        this.instrument = 0;
        this.collectionNum=0;
    }

    public BmobRelation getAgrees() {
        return agrees;
    }

    public void setAgrees(BmobRelation agrees) {
        this.agrees = agrees;
    }

    public int getAgreeNum() {
        return agreeNum;
    }

    public void setAgreeNum(int agreeNum) {
        this.agreeNum = agreeNum;
    }

    public int getDownloadNum() {
        return downloadNum;
    }

    public void setDownloadNum(int downloadNum) {
        this.downloadNum = downloadNum;
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
