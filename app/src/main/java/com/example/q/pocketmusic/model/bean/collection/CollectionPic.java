package com.example.q.pocketmusic.model.bean.collection;

import cn.bmob.v3.BmobObject;

/**
 * Created by 鹏君 on 2017/1/26.
 */
//收藏的图片
public class CollectionPic extends BmobObject {
    private CollectionSong collectionSong;
    private String url;

    public CollectionSong getCollectionSong() {
        return collectionSong;
    }

    public void setCollectionSong(CollectionSong collectionSong) {
        this.collectionSong = collectionSong;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
