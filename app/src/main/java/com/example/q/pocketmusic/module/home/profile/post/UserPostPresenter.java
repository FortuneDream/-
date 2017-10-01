package com.example.q.pocketmusic.module.home.profile.post;

import android.content.Intent;

import com.example.q.pocketmusic.callback.ToastQueryListener;
import com.example.q.pocketmusic.model.bean.ask.AskSongPost;
import com.example.q.pocketmusic.module.common.BasePresenter;
import com.example.q.pocketmusic.module.common.IBaseView;
import com.example.q.pocketmusic.module.home.net.type.community.ask.comment.AskSongCommentActivity;
import com.example.q.pocketmusic.util.UserUtil;

import java.util.List;

/**
 * Created by 鹏君 on 2017/5/4.
 */

public class UserPostPresenter extends BasePresenter<UserPostPresenter.IView> {
    private IView activity;
    private UserPostModel model;
    private int mPage;


    public UserPostPresenter(IView activity) {
        attachView(activity);
        this.activity = getIViewRef();
        model = new UserPostModel();
    }

    public void getUserPostList(final boolean isRefreshing) {
        if (isRefreshing) {
            mPage = 0;
        }
        model.getUserPostList(UserUtil.user, mPage, new ToastQueryListener<AskSongPost>() {
            @Override
            public void onSuccess(List<AskSongPost> list) {
                activity.setUserPostList(isRefreshing, list);
            }
        });

    }

    public void enterPostInfo(AskSongPost item) {
        Intent intent = new Intent(activity.getCurrentContext(), AskSongCommentActivity.class);
        intent.putExtra(AskSongCommentActivity.PARAM_POST, item);
        intent.putExtra(AskSongCommentActivity.PARAM_IS_FROM_USER, true);
        activity.getCurrentContext().startActivity(intent);
    }

    public void getMoreList() {
        mPage++;
        model.getUserPostList(UserUtil.user, mPage, new ToastQueryListener<AskSongPost>() {
            @Override
            public void onSuccess(List<AskSongPost> list) {
                activity.setUserPostList(false,list);
            }
        });
    }

    public interface IView extends IBaseView {

        void setUserPostList(boolean isRefreshing, List<AskSongPost> list);
    }
}
