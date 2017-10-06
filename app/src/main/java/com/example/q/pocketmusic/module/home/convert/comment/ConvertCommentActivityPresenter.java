package com.example.q.pocketmusic.module.home.convert.comment;

import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.util.Log;

import com.example.q.pocketmusic.callback.ToastQueryListener;
import com.example.q.pocketmusic.callback.ToastSaveListener;
import com.example.q.pocketmusic.callback.ToastUpdateListener;
import com.example.q.pocketmusic.config.Constant;
import com.example.q.pocketmusic.model.bean.ConvertObject;
import com.example.q.pocketmusic.model.bean.MyUser;
import com.example.q.pocketmusic.model.bean.convert.ConvertComment;
import com.example.q.pocketmusic.model.bean.convert.ConvertPost;
import com.example.q.pocketmusic.model.bean.convert.ConvertPostPic;
import com.example.q.pocketmusic.module.common.BasePresenter;
import com.example.q.pocketmusic.module.common.IBaseView;
import com.example.q.pocketmusic.module.home.convert.comment.convert.ConvertActivity;
import com.example.q.pocketmusic.util.UserUtil;
import com.example.q.pocketmusic.util.common.ToastUtil;

import java.util.ArrayList;
import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.datatype.BmobPointer;
import cn.bmob.v3.datatype.BmobRelation;


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
                Log.e(TAG, "list.size:" + list.size());
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
        intent.putExtra(ConvertActivity.PARAM_CONVERT_POST, post);
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

    public void checkUserHasConsumeCoin(final ConvertComment item) {
        BmobQuery<MyUser> query = new BmobQuery<>();
        query.addWhereRelatedTo("consume", new BmobPointer(item));
        query.findObjects(new ToastQueryListener<MyUser>() {
            @Override
            public void onSuccess(List<MyUser> list) {
                boolean isConsume = false;
                for (MyUser other : list) {
                    if (other.getObjectId().equals(UserUtil.user.getObjectId())) {
                        //已经点赞
                        isConsume = true;
                        break;
                    }
                }

                if (isConsume) {
                    activity.alertCompleteConvertSongDialog(item);
                } else {
                    activity.alertCoinDialog(item, post.getCoin());
                }

            }
        });
    }

    //消费硬币，添加关联
    public void performConsume(final ConvertComment item, final int coin) {
        activity.showLoading(true);
        ToastUtil.showToast("稍等片刻");
        //已经购买的用户
        BmobRelation relation1 = new BmobRelation();
        relation1.add(UserUtil.user);
        item.setRelation(relation1);
        item.save(new ToastSaveListener<String>(activity) {
            @Override
            public void onSuccess(String s) {
                UserUtil.increment(-coin, new ToastUpdateListener(activity) {
                    @Override
                    public void onSuccess() {
                        activity.showLoading(false);
                        activity.alertCompleteConvertSongDialog(item);
                    }
                });
            }
        });

    }


    interface IView extends IBaseView {

        void setCommentList(boolean isRefreshing, List<ConvertComment> list);

        void alertCompleteConvertSongDialog(ConvertComment item);

        void alertCoinDialog(ConvertComment item, int coin);
    }
}
