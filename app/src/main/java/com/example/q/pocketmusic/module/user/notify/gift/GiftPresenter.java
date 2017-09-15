package com.example.q.pocketmusic.module.user.notify.gift;

import com.example.q.pocketmusic.callback.ToastQueryListener;
import com.example.q.pocketmusic.callback.ToastUpdateListener;
import com.example.q.pocketmusic.config.BmobConstant;
import com.example.q.pocketmusic.config.CommonString;
import com.example.q.pocketmusic.config.Constant;
import com.example.q.pocketmusic.model.bean.MyUser;
import com.example.q.pocketmusic.model.bean.bmob.Gift;
import com.example.q.pocketmusic.module.common.BasePresenter;
import com.example.q.pocketmusic.module.common.IBaseView;
import com.example.q.pocketmusic.util.UserUtil;
import com.example.q.pocketmusic.util.common.LogUtils;
import com.example.q.pocketmusic.util.common.ToastUtil;

import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.UpdateListener;

/**
 * Created by 81256 on 2017/9/6.
 */

public class GiftPresenter extends BasePresenter<GiftPresenter.IView> {
    private IView activity;
    private GiftModel model;
    private int mPage;

    public GiftPresenter(IView activity) {
        attachView(activity);
        this.activity = getIViewRef();
        model = new GiftModel();
    }


    public void getGiftList(final boolean isRefreshing) {
        if (isRefreshing) {
            mPage = 0;
        }
        model.getGiftList(UserUtil.user, mPage, new ToastQueryListener<Gift>() {
            @Override
            public void onSuccess(List<Gift> list) {
                activity.setGiftList(list, isRefreshing);
            }
        });

    }

    public void setPage(int page) {
        this.mPage = page;
    }

    public void getMoreGiftList() {
        mPage++;
        model.getGiftList(UserUtil.user, mPage, new ToastQueryListener<Gift>() {
            @Override
            public void onSuccess(List<Gift> list) {
                activity.setGiftList(list, false);
            }
        });
    }


    public void addCoin(final Gift gift) {
        gift.setGet(true);
        gift.update(new ToastUpdateListener() {
            @Override
            public void onSuccess() {
                LogUtils.i(TAG,"gift.getCoin:"+gift.getCoin());
                UserUtil.user.increment(BmobConstant.BMOB_COIN, gift.getCoin());
                UserUtil.user.update(new ToastUpdateListener() {
                    @Override
                    public void onSuccess() {
                        LogUtils.e(TAG,"user.getContribution:"+UserUtil.user.getContribution());
                        ToastUtil.showToast(CommonString.ADD_COIN_BASE + gift.getCoin());
                    }
                });
            }
        });
    }


    public interface IView extends IBaseView {

        void setGiftList(List<Gift> list, boolean isRefreshing);
    }
}
