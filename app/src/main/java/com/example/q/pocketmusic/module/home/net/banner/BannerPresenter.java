package com.example.q.pocketmusic.module.home.net.banner;

import android.content.Context;

import com.example.q.pocketmusic.module.common.BasePresenter;
import com.example.q.pocketmusic.module.common.IBaseList;

/**
 * Created by 鹏君 on 2017/4/23.
 */

public class BannerPresenter extends BasePresenter<BannerPresenter.IView> {
    private IView activity;

    public BannerPresenter() {
        activity = getIViewRef();
    }

    public interface IView extends IBaseList {

    }
}
