package com.example.q.pocketmusic.module.common;

import android.content.Intent;

import com.example.q.pocketmusic.config.Constant;
import com.example.q.pocketmusic.model.bean.MyUser;
import com.example.q.pocketmusic.util.CheckUserUtil;

import cn.bmob.v3.BmobUser;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FetchUserInfoListener;

/**
 * Created by 鹏君 on 2017/1/13.
 */
//用于验证的Activity
public abstract class AuthActivity<V, T extends BasePresenter<V>> extends BaseActivity<V, T> {
    public static MyUser user;
    public static int a;
    public static final String RESULT_USER = "result_user";//返回的User

    public abstract void initUserView();

    @Override
    public void initView() {
        user = CheckUserUtil.checkLocalUser(this);    //基类跳转,检测是否本地是否已经保存了，如果没有保存就跳转到登录界面，保存了就把this.user=user.之后可以直接用user来使用，每次需要使用到用户系统的时候都需要check一下
        if (user != null) {
            BmobUser.fetchUserInfo(new FetchUserInfoListener<BmobUser>() {
                @Override
                public void done(BmobUser bmobUser, BmobException e) {
                    if (e!=null){
                        user = (MyUser) bmobUser;
                    }
                }
            });
            initUserView();
        }
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == Constant.REQUEST_LOGIN) {//请求登录
            if (resultCode == Constant.SUCCESS) {
                user = (MyUser) data.getSerializableExtra(RESULT_USER);//成功登录并复制
            } else if (resultCode == Constant.FAIL) {
                user = null;//登录失败
            }
            finish();//把登录跳转前的界面(即将进入的页面)finish掉
        }
    }
}
