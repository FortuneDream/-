package com.example.q.pocketmusic.callback;

import com.example.q.pocketmusic.config.BmobConstant;
import com.example.q.pocketmusic.config.CommonString;
import com.example.q.pocketmusic.model.bean.MyUser;
import com.example.q.pocketmusic.module.common.IBaseView;
import com.example.q.pocketmusic.util.CheckUserUtil;
import com.example.q.pocketmusic.util.common.LogUtils;
import com.example.q.pocketmusic.util.common.ToastUtil;

import cn.bmob.v3.BmobUser;
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
            //addUserActive();
        } else {
            onFail(t, e);
        }
    }

    //添加活跃度
    private void addUserActive() {
        MyUser user = BmobUser.getCurrentUser(MyUser.class);
        if (user != null) {
            user.increment(BmobConstant.BMOB_ACTIVE_NUM);
            user.update(new ToastUpdateListener() {
                @Override
                public void onSuccess() {
                    LogUtils.e("活跃度+1");
                }
            });
        }
    }

    public void onFail(T t, BmobException e) {
        if (baseView != null) {
            baseView.showLoading(false);
        }
        ToastUtil.showToast(CommonString.STR_ERROR_INFO + e.getMessage());
        e.printStackTrace();
        //        CrashHandler handler=CrashHandler.getInstance();
//        handler.uncaughtException(Thread.currentThread(),e);
    }


}
