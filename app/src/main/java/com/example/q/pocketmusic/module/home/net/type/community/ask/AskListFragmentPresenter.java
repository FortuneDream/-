package com.example.q.pocketmusic.module.home.net.type.community.ask;

import android.content.Intent;

import com.example.q.pocketmusic.callback.ToastQueryListener;
import com.example.q.pocketmusic.config.BmobConstant;
import com.example.q.pocketmusic.data.bean.ask.AskSongPost;
import com.example.q.pocketmusic.module.common.BasePresenter;
import com.example.q.pocketmusic.module.common.IBaseView;
import com.example.q.pocketmusic.module.home.net.type.community.ask.comment.AskSongCommentActivity;

import java.util.List;

import cn.bmob.v3.BmobQuery;

/**
 * Created by 鹏君 on 2017/1/26.
 */

public class AskListFragmentPresenter extends BasePresenter<AskListFragmentPresenter.IView> {
    private IView fragment;
    private int mPage;
    private int typeId;

    public AskListFragmentPresenter(IView fragment) {
        attachView(fragment);
        this.fragment = getIViewRef();
    }

    //得到帖子列表
    public void getPostList(final boolean isRefreshing) {
        mPage++;
        if (isRefreshing) {
            mPage = 0;
        }
        BmobQuery<AskSongPost> query = new BmobQuery<>();
        query.order("-index," + BmobConstant.BMOB_CREATE_AT);
        query.addWhereEqualTo(BmobConstant.BMOB_INSTRUMENT, typeId);
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

    public void enterCommentActivity(AskSongPost askSongPost) {
        Intent intent = new Intent(fragment.getCurrentContext(), AskSongCommentActivity.class);
        intent.putExtra(AskSongCommentActivity.PARAM_POST, askSongPost);
        intent.putExtra(AskSongCommentActivity.PARAM_IS_FROM_USER, false);
        fragment.getCurrentContext().startActivity(intent);

    }

    public void setmPage(int mPage) {
        this.mPage = mPage;
    }

    public void setType(int typeId) {
        this.typeId = typeId;
    }


    public interface IView extends IBaseView {
        void setPostList(boolean isRefreshing, List<AskSongPost> list);

    }
}
