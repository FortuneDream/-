package com.example.q.pocketmusic.module.home.profile.contribution;

import android.content.Context;

import com.example.q.pocketmusic.callback.IBaseList;
import com.example.q.pocketmusic.callback.IBaseView;
import com.example.q.pocketmusic.callback.ToastQueryListener;
import com.example.q.pocketmusic.model.bean.MyUser;
import com.example.q.pocketmusic.util.MyToast;
import com.jude.easyrecyclerview.adapter.RecyclerArrayAdapter;

import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;

/**
 * Created by 81256 on 2017/3/13.
 */

public class ContributionPresenter {
    private Context context;
    private IView activity;


    public ContributionPresenter(Context context, IView activity) {
        this.context = context;
        this.activity = activity;
    }

    public void init() {
        BmobQuery<MyUser> query = new BmobQuery<>();
        query.setLimit(20);//前十个
        query.order("-contribution");
        query.findObjects(new ToastQueryListener<MyUser>(context, activity) {
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
