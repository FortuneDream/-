package com.example.q.pocketmusic.module.common;

import android.content.Intent;

import com.example.q.pocketmusic.config.Constant;
import com.example.q.pocketmusic.model.bean.MyUser;
import com.example.q.pocketmusic.util.UserUtil;
import com.example.q.pocketmusic.util.common.LogUtils;

/**
 * Created by 鹏君 on 2017/1/13.
 */
//用于验证的Activity
public abstract class AuthActivity<V, T extends BasePresenter<V>> extends BaseActivity<V, T> {
    public static int a;
    public static final String RESULT_USER = "result_user";//返回的User

    public abstract void initUserView();

    @Override
    public void initView() {
        UserUtil.checkLocalUser(this);
        if (UserUtil.user != null) {
            LogUtils.i("user.getContribution:" + String.valueOf(UserUtil.user.getContribution()));
            initUserView();
        }
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == Constant.REQUEST_LOGIN) {//请求登录
            if (resultCode == Constant.SUCCESS) {
                UserUtil.user = (MyUser) data.getSerializableExtra(RESULT_USER);//成功登录并复制
            } else if (resultCode == Constant.FAIL) {
                UserUtil.user = null;//登录失败
            }
            finish();//把登录跳转前的界面(即将进入的页面)finish掉
        }
    }
}
