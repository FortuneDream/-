package com.example.q.pocketmusic.module.home.profile.post;

import android.content.Intent;

import com.example.q.pocketmusic.callback.ToastQueryListener;
import com.example.q.pocketmusic.model.bean.MyUser;
import com.example.q.pocketmusic.model.bean.ask.AskSongComment;
import com.example.q.pocketmusic.model.bean.ask.AskSongPost;
import com.example.q.pocketmusic.module.common.BasePresenter;
import com.example.q.pocketmusic.module.common.IBaseView;
import com.example.q.pocketmusic.module.home.seek.ask.comment.AskSongCommentActivity;

import java.util.List;

/**
 * Created by 鹏君 on 2017/5/4.
 */

public class UserPostPresenter extends BasePresenter<UserPostPresenter.IView> {
    private IView activity;
    private MyUser user;
    private UserPostModel model;


    public UserPostPresenter(IView activity) {
        attachView(activity);
        this.activity = getIViewRef();
        model = new UserPostModel();
    }

    public void setUser(MyUser user) {
        this.user = user;
    }

    public void getUserPostList(final boolean isRefreshing) {
        model.getInitPostList(user, new ToastQueryListener<AskSongPost>() {
            @Override
            public void onSuccess(List<AskSongPost> list) {
                if (!isRefreshing) {
                    activity.setInitPostList(list);
                } else {
                    activity.setInitPostListWithRefreshing(list);
                }

            }
        });

    }

    public void enterPostInfo(AskSongPost item) {
        Intent intent = new Intent(activity.getCurrentContext(), AskSongCommentActivity.class);
        intent.putExtra(AskSongCommentActivity.PARAM_POST, item);
        intent.putExtra(AskSongCommentActivity.PARAM_IS_FROM_USER, true);
        activity.getCurrentContext().startActivity(intent);
    }

    public interface IView extends IBaseView {

        void setInitPostList(List<AskSongPost> list);

        void setInitPostListWithRefreshing(List<AskSongPost> list);
    }
}
