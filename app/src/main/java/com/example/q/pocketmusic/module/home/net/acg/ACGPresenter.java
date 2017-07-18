package com.example.q.pocketmusic.module.home.net.acg;

import com.example.q.pocketmusic.module.common.BasePresenter;
import com.example.q.pocketmusic.module.common.IBaseView;

/**
 * Created by 鹏君 on 2017/7/18.
 * （￣m￣）
 */

public class ACGPresenter extends BasePresenter<ACGPresenter.IView> {
    private IView activity;

    public ACGPresenter(IView activity) {
        attachView(activity);
        this.activity = getIViewRef();
    }

    interface IView extends IBaseView {
    }
}
