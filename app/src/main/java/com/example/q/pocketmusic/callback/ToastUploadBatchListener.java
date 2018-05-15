package com.example.q.pocketmusic.callback;

import com.dell.fortune.tools.toast.ToastUtil;
import com.example.q.pocketmusic.R;
import com.example.q.pocketmusic.config.MyApplication;
import com.example.q.pocketmusic.data.event.LoadingDialogEvent;

import org.greenrobot.eventbus.EventBus;

import java.util.List;

import cn.bmob.v3.datatype.BmobFile;
import cn.bmob.v3.listener.UploadBatchListener;

/**
 * Created by 鹏君 on 2017/1/31.
 */

public class ToastUploadBatchListener implements UploadBatchListener {



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
        EventBus.getDefault().post(new LoadingDialogEvent(false));
        ToastUtil.showToast( MyApplication.context.getResources().getString(R.string.send_error) +  "第" + i + "张图片上传错误:" + s);
    }


}
