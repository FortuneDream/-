package com.example.q.pocketmusic.module.home.seek.ask.comment;

import com.example.q.pocketmusic.callback.ToastQueryListener;
import com.example.q.pocketmusic.model.bean.ask.AskSongComment;
import com.example.q.pocketmusic.model.bean.ask.AskSongPic;
import com.example.q.pocketmusic.model.bean.ask.AskSongPost;
import com.example.q.pocketmusic.util.BmobUtil;

import java.util.ArrayList;
import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.datatype.BmobPointer;

/**
 * Created by 鹏君 on 2017/4/22.
 */

public class AskSongCommentModel {
    private BmobUtil bmobUtil;
    private List<String> picUrls;

    public AskSongCommentModel() {
        bmobUtil = new BmobUtil();
        picUrls = new ArrayList<>();
    }

    public void getInitCommentList(AskSongPost post, ToastQueryListener<AskSongComment> listener) {
        bmobUtil.getInitListWithEqual(AskSongComment.class, "user,post.user", "post", new BmobPointer(post), listener);
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
