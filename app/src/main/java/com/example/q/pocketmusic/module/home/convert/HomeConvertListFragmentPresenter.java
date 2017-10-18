package com.example.q.pocketmusic.module.home.convert;

import android.content.Intent;

import com.example.q.pocketmusic.callback.ToastQueryListener;
import com.example.q.pocketmusic.model.bean.convert.ConvertPost;
import com.example.q.pocketmusic.module.common.BasePresenter;
import com.example.q.pocketmusic.module.common.IBaseView;
import com.example.q.pocketmusic.module.home.convert.comment.ConvertCommentActivity;
import com.example.q.pocketmusic.module.home.convert.publish.PublishConvertActivity;

import java.util.List;


public class HomeConvertListFragmentPresenter extends BasePresenter<HomeConvertListFragmentPresenter.IView> {
    private IView fragment;
    private int mPage;
    private HomeConvertListModel homeConvertListModel;

    public HomeConvertListFragmentPresenter(IView fragment) {
        attachView(fragment);
        this.fragment = getIViewRef();
        homeConvertListModel = new HomeConvertListModel();
    }

    public void setPage(int page) {
        this.mPage = page;
    }

    public void getConvertPostList(final boolean isRefreshing) {
        if (isRefreshing) {
            mPage = 0;
        }
        homeConvertListModel.getList(mPage, new ToastQueryListener<ConvertPost>() {
            @Override
            public void onSuccess(List<ConvertPost> list) {
                fragment.setConvertList(isRefreshing, list);
            }
        });
    }

    public void getMoreConvertList() {
        mPage++;
        getConvertPostList(false);
    }

    public void enterConvertComment(ConvertPost item) {
        Intent intent = new Intent(fragment.getCurrentContext(), ConvertCommentActivity.class);
        intent.putExtra(ConvertCommentActivity.PARAM_POST, item);
        fragment.getCurrentContext().startActivity(intent);
    }

    public void enterSearchMainActivity() {
        fragment.getCurrentContext().startActivity(new Intent(fragment.getCurrentContext(), SearchMainActivity.class));
    }

    public void enterPublishConvertActivity() {
        fragment.getCurrentContext().startActivity(new Intent(fragment.getCurrentContext(), PublishConvertActivity.class));
    }

    interface IView extends IBaseView {

        void setConvertList(boolean isRefreshing, List<ConvertPost> list);
    }
}
