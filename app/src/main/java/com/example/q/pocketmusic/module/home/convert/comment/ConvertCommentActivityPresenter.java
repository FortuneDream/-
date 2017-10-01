package com.example.q.pocketmusic.module.home.convert.comment;

import android.content.Context;

import com.example.q.pocketmusic.callback.ToastQueryListener;
import com.example.q.pocketmusic.model.bean.convert.ConvertComment;
import com.example.q.pocketmusic.model.bean.convert.ConvertPost;
import com.example.q.pocketmusic.module.common.BasePresenter;
import com.example.q.pocketmusic.module.common.IBaseView;

import java.util.List;


public class ConvertCommentActivityPresenter extends BasePresenter<ConvertCommentActivityPresenter.IView> {
    private IView activity;
    private int mPage;
    private ConvertCommentModel convertCommentModel;
    private ConvertPost post;

    public ConvertCommentActivityPresenter(IView activity) {
        attachView(activity);
        this.activity = getIViewRef();
        this.mPage = 0;
        convertCommentModel = new ConvertCommentModel();
    }

    public void getMoreCommentList() {
        mPage++;
        getCommentList(false);
    }

    public void getCommentList(final boolean isRefreshing) {
        if (isRefreshing) {
            mPage = 0;
        }
        convertCommentModel.getList(post, mPage, new ToastQueryListener<ConvertComment>() {
            @Override
            public void onSuccess(List<ConvertComment> list) {
                    activity.setCommentList(isRefreshing,list);
            }
        });

    }

    public void setPost(ConvertPost convertPost) {
        this.post = convertPost;
    }

    interface IView extends IBaseView {

        void setCommentList(boolean isRefreshing, List<ConvertComment> list);
    }
}
