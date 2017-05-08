package com.example.q.pocketmusic.callback;

import android.content.Context;
import android.content.Intent;

import com.example.q.pocketmusic.config.CommonString;
import com.example.q.pocketmusic.model.bean.MyUser;
import com.example.q.pocketmusic.module.common.IBaseList;
import com.example.q.pocketmusic.util.MyToast;

import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.UpdateListener;

/**
 * Created by Cloud on 2017/1/28.
 */
//封装更新，失败后会消除loadingView，且弹出Toast和错误信息
public abstract class ToastUpdateListener extends UpdateListener {
    private IBaseList baseList;


    public abstract void onSuccess();

    public ToastUpdateListener(IBaseList baseList) {
        this.baseList = baseList;
    }

    @Override
    final public void done(BmobException e) {
        if (e == null) {
            onSuccess();
        } else {
            onFail(e);
        }
    }

    public void onFail(BmobException e) {
        baseList.showLoading(false);
        baseList.showRefreshing(false);
        MyToast.showToast(baseList.getCurrentContext(), CommonString.STR_ERROR_INFO + e.getMessage());
        e.printStackTrace();
        if (e.getErrorCode() == 206) {//在其他地方已经登录
            MyUser.logOut();
            Intent intent = new Intent("pocket.music.home.activity");//重启app,但是这样还是无法阻止用户利用这个漏洞去无限下载曲谱Orz
            intent.addCategory("android.intent.category.DEFAULT");
            baseList.getCurrentContext().startActivity(intent);
        }
    }
}
