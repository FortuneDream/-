package com.example.q.pocketmusic.module.home.profile.post;

import android.content.Intent;

import com.example.q.pocketmusic.callback.ToastQueryListener;
import com.example.q.pocketmusic.data.bean.ask.AskSongPost;
import com.example.q.pocketmusic.data.model.UserPostModel;
import com.example.q.pocketmusic.module.common.BasePresenter;
import com.example.q.pocketmusic.module.common.IBaseView;
import com.example.q.pocketmusic.module.home.net.type.community.ask.comment.AskSongCommentActivity;

import java.util.List;

/**
 * Created by 鹏君 on 2017/5/4.
 */

public class UserPostPresenter extends BasePresenter<UserPostPresenter.IView> {
    private UserPostModel model;
    private int mPage;


    public UserPostPresenter(IView activity) {
        super(activity);
        model = new UserPostModel();
    }

    public void getUserPostList(final boolean isRefreshing) {
        mPage++;
        if (isRefreshing) {
            mPage = 0;
        }
        model.getUserPostList(mPage, new ToastQueryListener<AskSongPost>() {
            @Override
            public void onSuccess(List<AskSongPost> list) {
                mView.setUserPostList(isRefreshing, list);
            }
        });

    }

    public void enterPostInfo(AskSongPost item) {
        Intent intent = new Intent(mContext, AskSongCommentActivity.class);
        intent.putExtra(AskSongCommentActivity.PARAM_POST, item);
        intent.putExtra(AskSongCommentActivity.PARAM_IS_FROM_USER, true);
        mContext.startActivity(intent);
    }

    public interface IView extends IBaseView {

        void setUserPostList(boolean isRefreshing, List<AskSongPost> list);
    }
}
