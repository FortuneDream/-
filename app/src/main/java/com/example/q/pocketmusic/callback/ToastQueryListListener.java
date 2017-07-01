package com.example.q.pocketmusic.callback;

import com.example.q.pocketmusic.config.CommonString;
import com.example.q.pocketmusic.module.common.IBaseView;
import com.example.q.pocketmusic.util.ToastUtil;

import java.util.List;

import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.QueryListListener;

/**
 * Created by 鹏君 on 2017/1/28.
 */

public abstract class ToastQueryListListener<BatchResult> extends QueryListListener<BatchResult> {
    private IBaseView baseView;

    public ToastQueryListListener(IBaseView baseView) {
        this.baseView = baseView;
    }

    public ToastQueryListListener(){}

    public abstract void onSuccess(List<BatchResult> list);

    @Override
    final public void done(List<BatchResult> list, BmobException e) {
        if (e == null) {
            onSuccess(list);
        } else {

            onFail(e);
        }
    }

    public void onFail(BmobException e) {
        if (baseView!=null){
            baseView.showLoading(false);
            ToastUtil.showToast(CommonString.STR_ERROR_INFO + e.getMessage());
        }
        e.printStackTrace();
        //        CrashHandler handler=CrashHandler.getInstance();
//        handler.uncaughtException(Thread.currentThread(),e);
    }
}
