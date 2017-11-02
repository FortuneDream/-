package com.example.q.pocketmusic.module.home.profile.user.login;

import android.app.Activity;
import android.content.Intent;
import android.text.TextUtils;

import com.example.q.pocketmusic.callback.ToastSaveListener;
import com.example.q.pocketmusic.config.CommonString;
import com.example.q.pocketmusic.config.Constant;
import com.example.q.pocketmusic.model.bean.MyUser;
import com.example.q.pocketmusic.module.common.BasePresenter;
import com.example.q.pocketmusic.module.common.IBaseView;
import com.example.q.pocketmusic.module.home.profile.user.register.RegisterActivity;
import com.example.q.pocketmusic.util.common.ToastUtil;

import cn.bmob.v3.exception.BmobException;

/**
 * Created by 鹏君 on 2016/11/14.
 */

public class LoginPresenter extends BasePresenter<LoginPresenter.IView> {
    private IView activity;

    public LoginPresenter(IView activity) {
        attachView(activity);
        this.activity = getIViewRef();
    }

    //登录
    public void login(final String account, String password) {
        if (TextUtils.isEmpty(account) || TextUtils.isEmpty(password)) {
            ToastUtil.showToast(CommonString.STR_COMPLETE_INFO);
            return;
        }
        activity.showLoading(true);
        final MyUser user = new MyUser();
        user.setUsername(account);
        user.setPassword(password);
        user.login(new ToastSaveListener<MyUser>(activity) {

            @Override
            public void onSuccess(MyUser user) {
                ToastUtil.showToast("欢迎尊贵的VIP！ ");
                activity.loginToResult(Constant.SUCCESS, user);
                activity.finish();//关闭登录界面
            }

            @Override
            public void onFail(MyUser user, BmobException e) {
                super.onFail(user, e);
                activity.loginToResult(Constant.FAIL, null);
            }
        });

    }

    //跳转到RegistererActivity，如果注册成功就不finish到此页
    public void enterRegisterActivity() {
        Intent intent = new Intent(activity.getCurrentContext(), RegisterActivity.class);
        ((Activity) activity.getCurrentContext()).startActivityForResult(intent, Constant.REQUEST_REGISTER);
    }




    public interface IView extends IBaseView {
        void finish();

        void loginToResult(Integer success, MyUser myUser);
    }
}
