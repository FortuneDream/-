package com.example.q.pocketmusic.module.home.profile.setting.help;

import com.example.q.pocketmusic.module.common.BasePresenter;

/**
 * Created by 鹏君 on 2017/5/8.
 */

public class HelpPresenter extends BasePresenter<HelpPresenter.IView> {
    private IView activity;

    public HelpPresenter() {
        activity = getIViewRef();
    }

    interface IView {

    }
}
