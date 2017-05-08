package com.example.q.pocketmusic.callback;

import android.content.Context;

import com.example.q.pocketmusic.config.CommonString;
import com.example.q.pocketmusic.module.common.IBaseList;
import com.example.q.pocketmusic.util.MyToast;

import java.util.List;

import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.helper.NotificationCompat;
import cn.bmob.v3.listener.FindListener;

/**
 * Created by Cloud on 2017/1/28.
 */
//封装查询，失败后会消除loadingView，且弹出Toast和错误信息
public abstract class ToastQueryListener<T> extends FindListener<T> {
    private IBaseList baseList;

    protected ToastQueryListener(IBaseList baseList) {
        this.baseList = baseList;

    }

    public abstract void onSuccess(List<T> list);

    @Override
    final public void done(List<T> list, BmobException e) {
        if (e == null) {
            onSuccess(list);
        } else {
            onFail(e);
        }
    }

    public void onFail(BmobException e) {
        baseList.showLoading(false);
        baseList.showRefreshing(false);
        MyToast.showToast(baseList.getCurrentContext(), CommonString.STR_ERROR_INFO + e.getMessage());
        e.printStackTrace();
        //        CrashHandler handler=CrashHandler.getInstance();
//        handler.uncaughtException(Thread.currentThread(),e);
    }


}
