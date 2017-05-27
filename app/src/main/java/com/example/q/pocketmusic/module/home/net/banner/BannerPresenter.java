package com.example.q.pocketmusic.module.home.net.banner;

import com.example.q.pocketmusic.module.common.BasePresenter;
import com.example.q.pocketmusic.module.common.IBaseView;

/**
 * Created by 鹏君 on 2017/4/23.
 */

public class BannerPresenter extends BasePresenter<BannerPresenter.IView> {
    private IView activity;

    public BannerPresenter(IView activity) {
        attachView(activity);
        this.activity = getIViewRef();
    }

    public interface IView extends IBaseView {

    }
}
