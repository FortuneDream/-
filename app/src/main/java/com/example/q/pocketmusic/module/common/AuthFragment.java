package com.example.q.pocketmusic.module.common;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;

import com.example.q.pocketmusic.config.Constant;
import com.example.q.pocketmusic.model.bean.MyUser;
import com.example.q.pocketmusic.util.CheckUserUtil;

import cn.bmob.v3.BmobUser;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FetchUserInfoListener;

/**
 * Created by 鹏君 on 2017/1/26.
 */

public abstract class AuthFragment<V, T extends BasePresenter<V>> extends BaseFragment<V, T> {
    public static MyUser user;
    public static final String RESULT_USER = "result";

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        user = CheckUserUtil.checkLocalUser(this);
        if (user != null) {
            BmobUser.fetchUserInfo(new FetchUserInfoListener<BmobUser>() {
                @Override
                public void done(BmobUser bmobUser, BmobException e) {
                    if (e != null) {
                        user = (MyUser) bmobUser;
                    }
                }
            });
        }
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
