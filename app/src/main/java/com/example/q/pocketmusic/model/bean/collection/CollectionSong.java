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
    private int isFrom;//来自

    public CollectionSong() {
    }

    public int  getIsFrom() {
        return isFrom;
    }

    public void setIsFrom(int isFrom) {
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
}
