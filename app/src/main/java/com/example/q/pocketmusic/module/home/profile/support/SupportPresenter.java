package com.example.q.pocketmusic.module.home.profile.support;

import android.content.Intent;

import com.example.q.pocketmusic.callback.ToastQueryListener;
import com.example.q.pocketmusic.data.bean.MoneySupport;
import com.example.q.pocketmusic.data.model.SupportModel;
import com.example.q.pocketmusic.module.common.BasePresenter;
import com.example.q.pocketmusic.module.common.IBaseView;

import java.util.List;


/**
 * Created by 鹏君 on 2017/7/8.
 * （￣m￣）
 */

public class SupportPresenter extends BasePresenter<SupportPresenter.IView> {
    private int mPage;
    private SupportModel model;

    public SupportPresenter(IView activity) {
        super(activity);
        model = new SupportModel();
        this.mPage = 0;
    }


    public void getSupportMoneyList(final boolean isRefreshing) {
        mPage++;
        if (isRefreshing) {
            mPage = 0;
        }
        model.getList(mPage, new ToastQueryListener<MoneySupport>() {
            @Override
            public void onSuccess(List<MoneySupport> list) {
                mView.setMoneyList(isRefreshing, list);
            }
        });
    }

    public void enterDimensionActivity(int which) {
        Intent intent = new Intent(mContext, DimensionActivity.class);
        intent.putExtra(DimensionActivity.PARAM_TYPE, which);
        mContext.startActivity(intent);
    }


    public interface IView extends IBaseView {


        void setMoneyList(boolean isRefreshing, List<MoneySupport> list);
    }
}
