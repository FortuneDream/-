package com.example.q.pocketmusic.module.home.profile.contribution;

import com.example.q.pocketmusic.callback.ToastQueryListener;
import com.example.q.pocketmusic.data.bean.MyUser;
import com.example.q.pocketmusic.data.model.CoinRankModel;
import com.example.q.pocketmusic.module.common.BasePresenter;
import com.example.q.pocketmusic.module.common.IBaseView;

import java.util.List;

/**
 * Created by 鹏君 on 2017/3/13.
 */

public class CoinRankPresenter extends BasePresenter<CoinRankPresenter.IView> {
    private CoinRankModel coinRankModel;

    public CoinRankPresenter(IView activity) {
        super(activity);
        coinRankModel = new CoinRankModel();
    }

    //贡献前20个
    public void getCoinRankList() {
        coinRankModel.getCoinRankList(new ToastQueryListener<MyUser>() {
            @Override
            public void onSuccess(List<MyUser> list) {
                mView.setCoinRankList(list);
            }
        });
    }

    interface IView extends IBaseView {

        void setCoinRankList(List<MyUser> list);
    }
}
