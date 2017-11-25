package com.example.q.pocketmusic.module.home.profile.interest;

import android.content.Intent;

import com.example.q.pocketmusic.callback.ToastQueryListener;
import com.example.q.pocketmusic.callback.ToastUpdateListener;
import com.example.q.pocketmusic.data.bean.MyUser;
import com.example.q.pocketmusic.data.model.UserInterestModel;
import com.example.q.pocketmusic.module.common.BasePresenter;
import com.example.q.pocketmusic.module.common.IBaseView;
import com.example.q.pocketmusic.module.home.profile.user.other.OtherProfileActivity;
import com.example.q.pocketmusic.util.UserUtil;
import com.example.q.pocketmusic.util.common.ToastUtil;

import java.util.List;

import cn.bmob.v3.datatype.BmobRelation;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.UpdateListener;


public class UserInterestPresenter extends BasePresenter<UserInterestPresenter.IView> {
    private IView activity;
    private int mPage;
    private UserInterestModel model;

    public UserInterestPresenter(IView activity) {
        attachView(activity);
        this.activity = getIViewRef();
        this.mPage=0;
        model=new UserInterestModel();
    }

    //取消关注
    public void cancelInterest(MyUser other) {
        BmobRelation relation=new BmobRelation();
        relation.remove(other);
        UserUtil.user.setInterests(relation);
        UserUtil.user.update(new ToastUpdateListener() {
            @Override
            public void onSuccess() {
                ToastUtil.showToast("已取消关注");
                activity.onRefresh();
            }
        });
    }

    public void getList(final boolean isRefreshing) {
        mPage++;
        if (isRefreshing){
            mPage=0;
        }
        model.getList(mPage, new ToastQueryListener<MyUser>() {
            @Override
            public void onSuccess(List<MyUser> list) {
                activity.setList(list,isRefreshing);
            }
        });

    }

    interface IView extends IBaseView {

        void setList(List<MyUser> list, boolean isRefreshing);

        void onRefresh();
    }
}
