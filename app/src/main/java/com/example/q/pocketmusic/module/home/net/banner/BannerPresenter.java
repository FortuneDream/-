package com.example.q.pocketmusic.module.home.net.banner;

import android.content.Context;

import com.example.q.pocketmusic.module.common.IBaseList;

/**
 * Created by 鹏君 on 2017/4/23.
 */

public class BannerPresenter {
    private IView activity;

    public BannerPresenter(IView activity) {
        this.activity = activity;
    }

    public interface IView extends IBaseList {

    }
}
