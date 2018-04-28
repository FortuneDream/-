package com.example.q.pocketmusic.module.home.profile.user.login;

import android.app.Activity;
import android.content.Intent;
import android.text.TextUtils;

import com.example.q.pocketmusic.R;
import com.example.q.pocketmusic.callback.ToastSaveListener;
import com.example.q.pocketmusic.config.constant.Constant;
import com.example.q.pocketmusic.data.bean.MyUser;
import com.example.q.pocketmusic.module.common.BasePresenter;
import com.example.q.pocketmusic.module.common.IBaseView;
import com.example.q.pocketmusic.module.home.profile.user.register.RegisterActivity;
import com.example.q.pocketmusic.util.common.ToastUtil;

import cn.bmob.v3.exception.BmobException;

/**
 * Created by 鹏君 on 2016/11/14.
 */

public class LoginPresenter extends BasePresenter<LoginPresenter.IView> {


    public LoginPresenter(IView activity) {
        super(activity);
    }

    //登录
    public void login(final String account, String password) {
        if (TextUtils.isEmpty(account) || TextUtils.isEmpty(password)) {
            ToastUtil.showToast(mView.getResString(R.string.complete_info));
            return;
        }
        mView.showLoading(true);
        final MyUser user = new MyUser();
        user.setUsername(account);
        user.setPassword(password);
        user.login(new ToastSaveListener<MyUser>() {

            @Override
            public void onSuccess(MyUser user) {
                ToastUtil.showToast("欢迎尊贵的VIP！ ");
                mView.loginToResult(Constant.SUCCESS, user);
                mView.finish();//关闭登录界面
            }

            @Override
            public void onFail(MyUser user, BmobException e) {
                super.onFail(user, e);
                mView.loginToResult(Constant.FAIL, null);
            }
        });

    }

    //跳转到RegistererActivity，如果注册成功就不finish到此页
    public void enterRegisterActivity() {
        Intent intent = new Intent(mContext, RegisterActivity.class);
        ((Activity) mContext).startActivityForResult(intent, Constant.REQUEST_REGISTER);
    }


    public interface IView extends IBaseView {
        void finish();

        void loginToResult(Integer success, MyUser myUser);
    }
}
