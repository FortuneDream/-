package com.example.q.pocketmusic.callback;

import com.example.q.pocketmusic.R;
import com.example.q.pocketmusic.config.CommonString;
import com.example.q.pocketmusic.module.common.IBaseView;
import com.example.q.pocketmusic.util.common.ToastUtil;

import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.SaveListener;

/**
 * Created by 鹏君 on 2017/1/28.
 */
//封装添加，失败后会消除loadingView，且弹出Toast和错误信息
public abstract class ToastSaveListener<T> extends SaveListener<T> {
    private IBaseView baseView;

    public abstract void onSuccess(T t);

    public ToastSaveListener(IBaseView baseView) {
        this.baseView = baseView;
    }

    public ToastSaveListener() {
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
        if (baseView != null) {
            baseView.showLoading(false);
            ToastUtil.showToast( baseView.getResString(R.string.send_error) + e.getMessage());
        }
        e.printStackTrace();

    }


}
