package com.example.q.pocketmusic.module.home.profile.support;

import com.example.q.pocketmusic.module.common.BasePresenter;
import com.example.q.pocketmusic.module.common.IBaseView;

/**
 * Created by 鹏君 on 2017/7/8.
 * （￣m￣）
 */

public class SupportPresenter extends BasePresenter<SupportPresenter.IView> {
    private IView activity;

    public SupportPresenter(IView activity) {
        attachView(activity);
        this.activity = getIViewRef();
    }

    public interface IView extends IBaseView {

    }
}
