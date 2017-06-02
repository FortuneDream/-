package com.example.q.pocketmusic.module.common;

/**
 * Created by 鹏君 on 2017/1/28.
 */

public interface IBaseView extends IBase{
    void showLoading(boolean isShow);

    void finish();

    int setContentResource();

    void initView();
}
