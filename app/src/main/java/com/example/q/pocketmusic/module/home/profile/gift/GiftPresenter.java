package com.example.q.pocketmusic.module.home.profile.gift;

import com.dell.fortune.tools.toast.ToastUtil;
import com.example.q.pocketmusic.R;
import com.example.q.pocketmusic.callback.ToastQueryListener;
import com.example.q.pocketmusic.callback.ToastUpdateListener;
import com.example.q.pocketmusic.data.bean.bmob.Gift;
import com.example.q.pocketmusic.data.model.GiftModel;
import com.example.q.pocketmusic.module.common.BasePresenter;
import com.example.q.pocketmusic.module.common.IBaseView;
import com.example.q.pocketmusic.util.UserUtil;

import java.util.ArrayList;
import java.util.List;

import cn.bmob.v3.BmobBatch;
import cn.bmob.v3.BmobObject;
import cn.bmob.v3.datatype.BatchResult;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.QueryListListener;

/**
 * Created by 81256 on 2017/9/6.
 */

public class GiftPresenter extends BasePresenter<GiftPresenter.IView> {
    private GiftModel model;
    private int mPage;

    public GiftPresenter(IView activity) {
        super(activity);
        model = new GiftModel();
        this.mPage = 0;
    }


    public void getGiftList(final boolean isRefreshing) {
        mPage++;
        if (isRefreshing) {
            mPage = 0;
        }
        model.getGiftList(UserUtil.user, mPage, new ToastQueryListener<Gift>() {
            @Override
            public void onSuccess(List<Gift> list) {
                mView.setGiftList(list, isRefreshing);
            }
        });

    }


    //收礼物，批量更新
    public void receivedAllCoin(List<Gift> allData) {
        mView.showLoading(true);
        int sum = 0;
        List<BmobObject> gifts = new ArrayList<>();
        for (Gift gift : allData) {
            sum += gift.getCoin();
            gift.setGet(true);
            gifts.add(gift);
        }
        final int finalSum = sum;
        new BmobBatch().updateBatch(gifts).doBatch(new QueryListListener<BatchResult>() {
            @Override
            public void done(List<BatchResult> list, BmobException e) {
                mView.showLoading(false);
                if (e == null) {
                    UserUtil.increment(finalSum, new ToastUpdateListener() {
                        @Override
                        public void onSuccess() {
                            ToastUtil.showToast(mView.getResString(R.string.add_coin) + finalSum);
                            mView.onRefresh();
                        }
                    });
                }
            }
        });

    }


    public interface IView extends IBaseView {

        void setGiftList(List<Gift> list, boolean isRefreshing);

        void onRefresh();
    }
}
