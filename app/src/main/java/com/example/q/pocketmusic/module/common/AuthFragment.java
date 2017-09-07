package com.example.q.pocketmusic.module.common;

import android.app.Activity;
import android.content.Intent;

import com.example.q.pocketmusic.config.Constant;
import com.example.q.pocketmusic.model.bean.MyUser;
import com.example.q.pocketmusic.module.home.HomeActivity;
import com.example.q.pocketmusic.util.CheckUserUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by 鹏君 on 2017/1/26.
 */

public abstract class AuthFragment<V, T extends BasePresenter<V>> extends BaseFragment<V, T> {
    public static MyUser user;
    public static final String RESULT_USER = "result";

    @Override
    public void initView() {
        user = CheckUserUtil.checkLocalUser(this);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == Constant.REQUEST_LOGIN) {//请求登录
            if (resultCode == Constant.SUCCESS) {
                user = (MyUser) data.getSerializableExtra(RESULT_USER);//成功登录并复制
            } else if (resultCode == Constant.FAIL) {
                user = null;//登录失败
            }
        }
    }


}
