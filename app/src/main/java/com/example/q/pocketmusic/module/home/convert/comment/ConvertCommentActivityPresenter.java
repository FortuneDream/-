package com.example.q.pocketmusic.module.home.convert.comment;

import android.content.Context;
import android.content.Intent;

import com.example.q.pocketmusic.callback.ToastQueryListener;
import com.example.q.pocketmusic.config.Constant;
import com.example.q.pocketmusic.model.bean.ConvertObject;
import com.example.q.pocketmusic.model.bean.convert.ConvertComment;
import com.example.q.pocketmusic.model.bean.convert.ConvertPost;
import com.example.q.pocketmusic.model.bean.convert.ConvertPostPic;
import com.example.q.pocketmusic.module.common.BasePresenter;
import com.example.q.pocketmusic.module.common.IBaseView;
import com.example.q.pocketmusic.module.home.convert.comment.convert.ConvertActivity;

import java.util.ArrayList;
import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.datatype.BmobPointer;


public class ConvertCommentActivityPresenter extends BasePresenter<ConvertCommentActivityPresenter.IView> {
    private IView activity;
    private int mPage;

    public ConvertPost getPost() {
        return post;
    }

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
                activity.setCommentList(isRefreshing, list);
            }
        });

    }

    public void setPost(ConvertPost convertPost) {
        this.post = convertPost;
    }

    public void enterConvertActivity(ConvertObject convertObject) {
        Intent intent = new Intent(activity.getCurrentContext(), ConvertActivity.class);
        intent.putExtra(ConvertActivity.PARAM_CONVERT_OBJECT, convertObject);
        activity.getCurrentContext().startActivity(intent);
    }

    public void getConvertObject() {
        activity.showLoading(true);
        BmobQuery<ConvertPostPic> query = new BmobQuery<>();
        query.addWhereEqualTo("post", new BmobPointer(post));
        query.findObjects(new ToastQueryListener<ConvertPostPic>(activity) {
            @Override
            public void onSuccess(List<ConvertPostPic> list) {
                activity.showLoading(false);
                List<String> ivs = new ArrayList<String>();
                for (int i = 0; i < list.size(); i++) {
                    ivs.add(list.get(i).getUrl());
                }
                ConvertObject object = new ConvertObject(post.getTitle(), ivs, Constant.NET);
                enterConvertActivity(object);
            }
        });
    }


    interface IView extends IBaseView {

        void setCommentList(boolean isRefreshing, List<ConvertComment> list);
    }
}
