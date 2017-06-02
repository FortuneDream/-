package com.example.q.pocketmusic.model.bean.ask;

import cn.bmob.v3.BmobObject;

/**
 * Created by 鹏君 on 2017/1/11.
 */

public class AskSongPic extends BmobObject {
    private AskSongComment comment;
    private String url;

    public AskSongPic(AskSongComment comment, String url) {
        this.comment = comment;
        this.url = url;
    }

    public AskSongComment getComment() {
        return comment;
    }

    public void setComment(AskSongComment comment) {
        this.comment = comment;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
