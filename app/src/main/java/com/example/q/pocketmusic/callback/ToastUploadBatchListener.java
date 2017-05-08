package com.example.q.pocketmusic.callback;

import android.content.Context;

import com.example.q.pocketmusic.config.CommonString;
import com.example.q.pocketmusic.module.common.IBaseList;
import com.example.q.pocketmusic.util.MyToast;

import java.util.List;

import cn.bmob.v3.datatype.BmobFile;
import cn.bmob.v3.listener.UploadBatchListener;

/**
 * Created by Cloud on 2017/1/31.
 */

public class ToastUploadBatchListener implements UploadBatchListener {

    private IBaseList baseList;

    public ToastUploadBatchListener(IBaseList baseList) {
        this.baseList = baseList;
    }


    @Override
    public void onSuccess(List<BmobFile> list, List<String> list1) {

    }

    @Override
    public void onProgress(int i, int i1, int i2, int i3) {

    }

    @Override
    public void onError(int i, String s) {
        baseList.showLoading(false);
        baseList.showRefreshing(false);
        MyToast.showToast(baseList.getCurrentContext(), CommonString.STR_ERROR_INFO + "第" + i + "张图片上传错误:" + s);
        //        CrashHandler handler=CrashHandler.getInstance();
//        handler.uncaughtException(Thread.currentThread(),e);
    }


}
