package com.example.q.pocketmusic.model.bean.collection;

import cn.bmob.v3.BmobObject;
import cn.bmob.v3.datatype.BmobRelation;

/**
 * Created by 鹏君 on 2017/1/26.
 */
//用户收藏的
public class CollectionSong extends BmobObject {
    private String name;//收藏曲谱的名字
    private String content;//收藏曲谱的描述
    private BmobRelation users;//收藏了这首曲谱的所有用户
    private Integer isFrom;//来自

    public CollectionSong() {
    }

    public Integer getIsFrom() {
        return isFrom;
    }

    public void setIsFrom(Integer isFrom) {
        this.isFrom = isFrom;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public BmobRelation getUsers() {
        return users;
    }

    public void setUsers(BmobRelation users) {
        this.users = users;
    }
}
