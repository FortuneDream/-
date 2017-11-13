package com.example.q.pocketmusic.module.home.profile.interest;

import android.content.Intent;

import com.example.q.pocketmusic.callback.ToastQueryListener;
import com.example.q.pocketmusic.data.bean.MyUser;
import com.example.q.pocketmusic.data.model.UserInterestModel;
import com.example.q.pocketmusic.module.common.BasePresenter;
import com.example.q.pocketmusic.module.common.IBaseView;
import com.example.q.pocketmusic.module.home.profile.user.other.OtherProfileActivity;

import java.util.List;


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

    public void enterOtherActivity(MyUser other) {
        Intent intent = new Intent(activity.getCurrentContext(), OtherProfileActivity.class);
        intent.putExtra(OtherProfileActivity.PARAM_USER, other);
        activity.getCurrentContext().startActivity(intent);
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
    }
}
