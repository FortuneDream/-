package com.example.q.pocketmusic.module.home.profile.contribution;

import android.content.Context;

import com.example.q.pocketmusic.module.common.BasePresenter;
import com.example.q.pocketmusic.module.common.IBaseList;
import com.example.q.pocketmusic.callback.ToastQueryListener;
import com.example.q.pocketmusic.model.bean.MyUser;

import java.util.List;

import cn.bmob.v3.BmobQuery;

/**
 * Created by 81256 on 2017/3/13.
 */

public class ContributionPresenter extends BasePresenter<ContributionPresenter.IView> {
    private IView activity;


    public ContributionPresenter(IView activity) {
        attachView(activity);
        this.activity=getIViewRef();
    }

    //贡献前十个
    public void init() {
        BmobQuery<MyUser> query = new BmobQuery<>();
        query.setLimit(10);
        query.order("-contribution");
        query.findObjects(new ToastQueryListener<MyUser>(activity) {
            @Override
            public void onSuccess(List<MyUser> list) {
                activity.setListResult(list);
            }
        });
    }

    interface IView extends IBaseList {

        void setListResult(List<MyUser> list);
    }
}
