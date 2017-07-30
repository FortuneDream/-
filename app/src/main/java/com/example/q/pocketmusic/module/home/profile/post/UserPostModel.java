package com.example.q.pocketmusic.module.home.profile.post;

import com.example.q.pocketmusic.callback.ToastQueryListener;
import com.example.q.pocketmusic.config.BmobConstant;
import com.example.q.pocketmusic.model.bean.MyUser;
import com.example.q.pocketmusic.model.bean.ask.AskSongPost;
import com.example.q.pocketmusic.module.common.BaseModel;


import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.datatype.BmobPointer;

/**
 * Created by 鹏君 on 2017/5/4.
 */

public class UserPostModel extends BaseModel {

    public void getUserPostList(MyUser user, int page, ToastQueryListener<AskSongPost> listener) {
        BmobQuery<AskSongPost> queryComment = new BmobQuery<>();
        initDefaultListQuery(queryComment,page);
        queryComment.addWhereEqualTo(BmobConstant.BMOB_USER, new BmobPointer(user));
        queryComment.include(BmobConstant.BMOB_USER);
        queryComment.findObjects(listener);
    }
}
