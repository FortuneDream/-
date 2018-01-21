package com.example.q.pocketmusic.module.common;

import android.content.Intent;

import com.example.q.pocketmusic.config.Constant;
import com.example.q.pocketmusic.data.bean.MyUser;
import com.example.q.pocketmusic.util.UserUtil;
import com.example.q.pocketmusic.util.common.ToastUtil;

/**
 * Created by 鹏君 on 2017/1/26.
 */

public abstract class AuthFragment<V, T extends BasePresenter<V>> extends BaseFragment<V, T> {
    public static final String RESULT_USER = "result";


    @Override
    public void initView() {
        if (!UserUtil.checkLocalUser(this)){
            ToastUtil.showToast("请登录");
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == Constant.REQUEST_LOGIN) {//请求登录
            UserUtil.onActivityResult(resultCode,data);
        }
    }


}
