package com.example.q.pocketmusic.module.home.profile.post;

import com.example.q.pocketmusic.callback.ToastQueryListener;
import com.example.q.pocketmusic.model.bean.MyUser;
import com.example.q.pocketmusic.model.bean.ask.AskSongPost;
import com.example.q.pocketmusic.util.BmobUtil;

import java.util.ArrayList;
import java.util.List;

import cn.bmob.v3.datatype.BmobPointer;

/**
 * Created by 鹏君 on 2017/5/4.
 */

public class UserPostModel {
    private BmobUtil bmobUtil;

    public UserPostModel() {
        bmobUtil = new BmobUtil();
    }

    public void getInitPostList(MyUser user, ToastQueryListener<AskSongPost> listener) {
        bmobUtil.getInitListWithEqual(AskSongPost.class, "user", "user", new BmobPointer(user), listener);
    }
}
