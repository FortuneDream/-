package com.example.q.pocketmusic.module.home.profile.post;

import android.content.Context;
import android.content.Intent;

import com.example.q.pocketmusic.module.common.IBaseList;
import com.example.q.pocketmusic.callback.ToastQueryListener;
import com.example.q.pocketmusic.model.bean.MyUser;
import com.example.q.pocketmusic.model.bean.ask.AskSongPost;
import com.example.q.pocketmusic.module.common.BasePresenter;
import com.example.q.pocketmusic.module.home.ask.comment.AskSongCommentActivity;

import java.util.List;

/**
 * Created by 鹏君 on 2017/5/4.
 */

public class UserPostPresenter extends BasePresenter {
    private IView activity;
    private MyUser user;
    private UserPostModel model;


    public UserPostPresenter(IView activity, MyUser user) {
        this.activity = activity;
        this.user = user;
        model = new UserPostModel();
    }

    public void getUserPostList() {
        model.getInitPostList(user, new ToastQueryListener<AskSongPost>(activity) {
            @Override
            public void onSuccess(List<AskSongPost> list) {
                activity.setInitPostList(list);
            }
        });

    }

    public void enterPostInfo(AskSongPost item) {
        Intent intent = new Intent(activity.getCurrentContext(), AskSongCommentActivity.class);
        intent.putExtra(AskSongCommentActivity.PARAM_POST, item);
        activity.getCurrentContext().startActivity(intent);
    }

    public interface IView extends IBaseList {

        void setInitPostList(List<AskSongPost> list);
    }
}
