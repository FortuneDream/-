package com.example.q.pocketmusic.module.home.net.banner;

import android.content.Context;

import com.example.q.pocketmusic.module.common.IBaseList;

/**
 * Created by 鹏君 on 2017/4/23.
 */

public class BannerPresenter {
    private Context context;
    private IView activity;

    public BannerPresenter(Context context, IView activity) {
        this.context = context;
        this.activity = activity;
    }

    public interface IView extends IBaseList {

    }
}
