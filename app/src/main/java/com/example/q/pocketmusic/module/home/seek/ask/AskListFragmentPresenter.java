package com.example.q.pocketmusic.module.home.seek.ask;

import android.content.Intent;

import com.example.q.pocketmusic.callback.ToastQueryListener;
import com.example.q.pocketmusic.config.BmobConstant;
import com.example.q.pocketmusic.model.bean.ask.AskSongPost;
import com.example.q.pocketmusic.module.common.BasePresenter;
import com.example.q.pocketmusic.module.common.IBaseView;
import com.example.q.pocketmusic.module.home.seek.ask.comment.AskSongCommentActivity;
import com.example.q.pocketmusic.util.BmobUtil;

import java.util.List;

import cn.bmob.v3.BmobQuery;

/**
 * Created by 鹏君 on 2017/1/26.
 */

public class AskListFragmentPresenter extends BasePresenter<AskListFragmentPresenter.IView> {
    private IView fragment;
    private BmobUtil bmobUtil;
    private int mPage;

    public AskListFragmentPresenter(IView fragment) {
        attachView(fragment);
        this.fragment = getIViewRef();
        bmobUtil = new BmobUtil();

    }

    //得到帖子列表
    public void getPostList(final boolean isRefreshing) {
        if (isRefreshing) {
            mPage = 0;
        }
        BmobQuery<AskSongPost> query = new BmobQuery<>();
        query.order("-index," + BmobConstant.BMOB_CREATE_AT);
        query.setLimit(10);
        query.setSkip(10 * mPage);
        query.include(BmobConstant.BMOB_USER);
        query.findObjects(new ToastQueryListener<AskSongPost>() {
            @Override
            public void onSuccess(List<AskSongPost> list) {
                fragment.setPostList(isRefreshing, list);
            }
        });
    }

    //加载更多
    public void getMore() {
        mPage++;
        getPostList(false);
    }

    public void enterCommentActivity(AskSongPost askSongPost) {
        Intent intent = new Intent(fragment.getCurrentContext(), AskSongCommentActivity.class);
        intent.putExtra(AskSongCommentActivity.PARAM_POST, askSongPost);
        intent.putExtra(AskSongCommentActivity.PARAM_IS_FROM_USER, false);
        fragment.getCurrentContext().startActivity(intent);

    }

    public void setmPage(int mPage) {
        this.mPage = mPage;
    }


    public interface IView extends IBaseView {
        void setPostList(boolean isRefreshing, List<AskSongPost> list);

    }
}
