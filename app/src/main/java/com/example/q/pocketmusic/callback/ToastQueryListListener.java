package com.example.q.pocketmusic.callback;

import com.dell.fortune.tools.toast.ToastUtil;
import com.example.q.pocketmusic.R;
import com.example.q.pocketmusic.config.MyApplication;
import com.example.q.pocketmusic.data.event.LoadingDialogEvent;

import org.greenrobot.eventbus.EventBus;

import java.util.List;

import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.QueryListListener;

/**
 * Created by 鹏君 on 2017/1/28.
 */

public abstract class ToastQueryListListener<BatchResult> extends QueryListListener<BatchResult> {

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
        EventBus.getDefault().post(new LoadingDialogEvent(false));
        ToastUtil.showToast( MyApplication.context.getResources().getString(R.string.send_error) + e.getMessage());
        e.printStackTrace();
    }
}
