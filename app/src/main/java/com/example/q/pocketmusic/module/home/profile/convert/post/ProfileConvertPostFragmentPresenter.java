package com.example.q.pocketmusic.module.home.profile.convert.post;

import android.content.Context;
import android.content.Intent;

import com.example.q.pocketmusic.callback.ToastQueryListener;
import com.example.q.pocketmusic.config.BmobConstant;
import com.example.q.pocketmusic.model.bean.convert.ConvertComment;
import com.example.q.pocketmusic.module.common.BasePresenter;
import com.example.q.pocketmusic.module.common.IBaseView;
import com.example.q.pocketmusic.module.home.convert.comment.ConvertCommentActivity;
import com.example.q.pocketmusic.util.UserUtil;
import com.example.q.pocketmusic.util.common.LogUtils;

import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.datatype.BmobPointer;

public class ProfileConvertPostFragmentPresenter extends BasePresenter<ProfileConvertPostFragmentPresenter.IView> {
    private IView fragment;
    private int mPage;
    private ProfileConvertPostModel profileConvertPostModel;

    public ProfileConvertPostFragmentPresenter(IView fragment) {
        attachView(fragment);
        this.fragment = getIViewRef();
        profileConvertPostModel = new ProfileConvertPostModel();
    }

    public void getInitCommentList(final boolean isRefreshing) {
        if (isRefreshing) {
            mPage = 0;
        }
        profileConvertPostModel.getList(mPage, new ToastQueryListener<ConvertComment>() {
            @Override
            public void onSuccess(List<ConvertComment> list) {
                LogUtils.e(TAG, String.valueOf(list.size()));
                fragment.setList(list, isRefreshing);
            }
        });
    }

    public void getMoreCommentList(final boolean isRefreshing) {
        mPage++;
        profileConvertPostModel.getList(mPage, new ToastQueryListener<ConvertComment>() {
            @Override
            public void onSuccess(List<ConvertComment> list) {
                fragment.setList(list, isRefreshing);
            }
        });
    }

    public void enterConvertPostActivity(ConvertComment item) {
        Intent intent = new Intent(fragment.getCurrentContext(), ConvertCommentActivity.class);
        intent.putExtra(ConvertCommentActivity.PARAM_POST, item.getPost());
        fragment.getCurrentContext().startActivity(intent);
    }

    interface IView extends IBaseView {

        void setList(List<ConvertComment> list, boolean isRefreshing);
    }
}
