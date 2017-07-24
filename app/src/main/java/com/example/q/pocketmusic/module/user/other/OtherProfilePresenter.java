package com.example.q.pocketmusic.module.user.other;

import com.example.q.pocketmusic.module.common.BasePresenter;
import com.example.q.pocketmusic.module.common.IBaseView;

/**
 * Created by 鹏君 on 2017/7/24.
 * （￣m￣）
 */

public class OtherProfilePresenter extends BasePresenter<OtherProfilePresenter.IView> {
    private IView activity;

    public OtherProfilePresenter(IView activity) {
        attachView(activity);
        this.activity = getIViewRef();
    }

    interface IView extends IBaseView {

    }
}
