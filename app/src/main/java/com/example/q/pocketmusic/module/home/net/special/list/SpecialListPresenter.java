package com.example.q.pocketmusic.module.home.net.special.list;

import com.example.q.pocketmusic.module.common.BasePresenter;
import com.example.q.pocketmusic.module.common.IBaseView;

/**
 * Created by 鹏君 on 2017/8/24.
 * （￣m￣）
 */

public class SpecialListPresenter extends BasePresenter<SpecialListPresenter.IView> {
    private IView activity;

    public SpecialListPresenter(IView activity) {
        attachView(activity);
        this.activity = getIViewRef();
    }

    interface IView extends IBaseView {

    }
}
