package com.example.q.pocketmusic.callback;

import com.example.q.pocketmusic.config.CommonString;
import com.example.q.pocketmusic.module.common.IBaseView;
import com.example.q.pocketmusic.util.common.ToastUtil;

import java.util.List;

import cn.bmob.v3.datatype.BmobFile;
import cn.bmob.v3.listener.UploadBatchListener;

/**
 * Created by 鹏君 on 2017/1/31.
 */

public class ToastUploadBatchListener implements UploadBatchListener {

    private IBaseView baseView;

    public ToastUploadBatchListener(IBaseView baseView) {
        this.baseView = baseView;
    }

    public ToastUploadBatchListener() {
    }

    @Override
    public void onSuccess(List<BmobFile> list, List<String> list1) {

    }

    @Override
    public void onProgress(int i, int i1, int i2, int i3) {

    }

    @Override
    public void onError(int i, String s) {
        if (baseView != null) {
            baseView.showLoading(false);
            ToastUtil.showToast( CommonString.STR_ERROR_INFO + "第" + i + "张图片上传错误:" + s);
        }
        //        CrashHandler handler=CrashHandler.getInstance();
//        handler.uncaughtException(Thread.currentThread(),e);
    }


}
