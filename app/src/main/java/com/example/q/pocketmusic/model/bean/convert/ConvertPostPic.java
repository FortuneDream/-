package com.example.q.pocketmusic.model.bean.convert;

import cn.bmob.v3.BmobObject;

/**
 * Created by 81256 on 2017/10/3.
 */

public class ConvertPostPic  extends BmobObject{
    private ConvertPost post;
    private String url;

    public ConvertPostPic() {
    }

    public ConvertPost getPost() {
        return post;
    }

    public void setPost(ConvertPost post) {
        this.post = post;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
