package com.example.q.pocketmusic.callback;

import android.content.Intent;

import com.dell.fortune.tools.toast.ToastUtil;
import com.example.q.pocketmusic.R;
import com.example.q.pocketmusic.config.MyApplication;
import com.example.q.pocketmusic.data.bean.MyUser;
import com.example.q.pocketmusic.data.event.LoadingDialogEvent;
import com.example.q.pocketmusic.module.common.IBaseView;


import org.greenrobot.eventbus.EventBus;

import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.UpdateListener;

/**
 * Created by 鹏君 on 2017/1/28.
 */
//封装更新，失败后会消除loadingView，且弹出Toast和错误信息
public abstract class ToastUpdateListener extends UpdateListener {
    private IBaseView baseView;

    public abstract void onSuccess();

    public ToastUpdateListener(IBaseView baseView) {
        this.baseView = baseView;
    }

    public ToastUpdateListener() {

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
        EventBus.getDefault().post(new LoadingDialogEvent(false));
        ToastUtil.showToast( MyApplication.context.getResources().getString(R.string.send_error) + e.getMessage());
        e.printStackTrace();
        if (e.getErrorCode() == 206) {//在其他地方已经登录
            MyUser.logOut();
            Intent intent = new Intent("pocket.music.home.activity");//重启app,但是这样还是无法阻止用户利用这个漏洞去无限下载曲谱Orz
            intent.addCategory("android.intent.category.DEFAULT");
            if (baseView != null) {
                baseView.getCurrentContext().startActivity(intent);
            }
        }
    }
}
