package com.example.q.pocketmusic.callback;

import android.app.Application;

import com.example.q.pocketmusic.R;
import com.example.q.pocketmusic.config.MyApplication;
import com.example.q.pocketmusic.data.event.LoadingDialogEvent;
import com.example.q.pocketmusic.module.common.IBaseView;
import com.example.q.pocketmusic.util.common.ToastUtil;

import org.greenrobot.eventbus.EventBus;

import java.util.List;

import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;

/**
 * Created by 鹏君 on 2017/1/28.
 */
//封装查询，失败后会消除loadingView，且弹出Toast和错误信息
public abstract class ToastQueryListener<T> extends FindListener<T> {

    protected ToastQueryListener(){}

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
        EventBus.getDefault().post(new LoadingDialogEvent(false));
        ToastUtil.showToast( MyApplication.context.getResources().getString(R.string.send_error) + e.getMessage());
        e.printStackTrace();
    }


}
