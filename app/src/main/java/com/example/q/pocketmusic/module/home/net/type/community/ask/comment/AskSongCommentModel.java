package com.example.q.pocketmusic.module.home.net.type.community.ask.comment;

import com.example.q.pocketmusic.callback.ToastQueryListener;
import com.example.q.pocketmusic.config.BmobConstant;
import com.example.q.pocketmusic.data.bean.ask.AskSongComment;
import com.example.q.pocketmusic.data.bean.ask.AskSongPic;
import com.example.q.pocketmusic.data.bean.ask.AskSongPost;
import com.example.q.pocketmusic.module.common.BaseModel;

import java.util.ArrayList;
import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.datatype.BmobPointer;

/**
 * Created by 鹏君 on 2017/4/22.
 */

public class AskSongCommentModel extends BaseModel {
    private List<String> picUrls;

    public AskSongCommentModel() {
        picUrls = new ArrayList<>();
    }


    public void getUserCommentList(AskSongPost post, int page, ToastQueryListener<AskSongComment> listener) {
        BmobQuery<AskSongComment> queryComment = new BmobQuery<>();
        queryComment.order("-agreeNum," + BmobConstant.BMOB_CREATE_AT);
        queryComment.setLimit(DEFAULT_LIMIT);
        queryComment.setSkip(DEFAULT_LIMIT * page);
        queryComment.addWhereEqualTo("post", new BmobPointer(post));
        queryComment.include("user,post.user");
        queryComment.findObjects(listener);
    }

    public void getPicList(AskSongComment askSongComment, ToastQueryListener<AskSongPic> listener) {
        BmobQuery<AskSongPic> query = new BmobQuery<>();
        query.addWhereEqualTo("comment", new BmobPointer(askSongComment));
        query.findObjects(listener);
    }

    public List<String> getPicUrls() {
        return picUrls;
    }

}
