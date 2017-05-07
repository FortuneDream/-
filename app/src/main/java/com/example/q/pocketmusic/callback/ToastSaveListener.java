package com.example.q.pocketmusic.callback;

import android.content.Context;

import com.example.q.pocketmusic.config.CommonString;
import com.example.q.pocketmusic.module.common.IBaseList;
import com.example.q.pocketmusic.util.MyToast;

import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.SaveListener;

/**
 * Created by Cloud on 2017/1/28.
 */
//封装添加，失败后会消除loadingView，且弹出Toast和错误信息
public abstract class ToastSaveListener<T> extends SaveListener<T> {
    private Context context;
    private IBaseList baseList;

    public abstract void onSuccess(T t);

    public ToastSaveListener(Context context, IBaseList baseList) {
        this.context = context;
        this.baseList = baseList;
    }

    //不允许重写
    @Override
    final public void done(T t, BmobException e) {
        if (e == null) {
            onSuccess(t);
        } else {
            onFail(t, e);
        }
    }

    public void onFail(T t, BmobException e) {
        baseList.showLoading(false);
        baseList.showRefreshing(false);
        MyToast.showToast(context, CommonString.STR_ERROR_INFO + e.getMessage());
        e.printStackTrace();
        //        CrashHandler handler=CrashHandler.getInstance();
//        handler.uncaughtException(Thread.currentThread(),e);
    }




}
