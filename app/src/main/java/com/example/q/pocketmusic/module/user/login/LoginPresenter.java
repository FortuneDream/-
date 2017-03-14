package com.example.q.pocketmusic.module.user.login;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;

import com.example.q.pocketmusic.config.CommonString;
import com.example.q.pocketmusic.config.Constant;
import com.example.q.pocketmusic.model.bean.MyUser;
import com.example.q.pocketmusic.callback.IBaseView;
import com.example.q.pocketmusic.callback.ToastSaveListener;
import com.example.q.pocketmusic.module.common.BasePresenter;
import com.example.q.pocketmusic.module.user.register.RegisterActivity;
import com.example.q.pocketmusic.util.MyToast;

import cn.bmob.v3.exception.BmobException;

/**
 * Created by Cloud on 2016/11/14.
 */

public class LoginPresenter extends BasePresenter{
    private IView activity;
    private Context context;

    public LoginPresenter(IView activity, Context context) {
        this.activity = activity;
        this.context = context;
    }

    //登录
    public void login(final String account, String password) {
        if (TextUtils.isEmpty(account) || TextUtils.isEmpty(password)) {
            MyToast.showToast(context, CommonString.STR_COMPLETE_INFO);
            return;
        }
        activity.showLoading(true);
        final MyUser user = new MyUser();
        user.setUsername(account);
        user.setPassword(password);
        user.login(new ToastSaveListener<MyUser>(context,activity) {

            @Override
            public void onSuccess(MyUser user) {
                MyToast.showToast(context, "欢迎尊贵的VIP！ ");
                activity.loginToResult(Constant.SUCCESS, user);
                activity.finish();
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
        Intent intent = new Intent(context, RegisterActivity.class);
        ((Activity) context).startActivityForResult(intent, Constant.REQUEST_REGISTER);
    }

    public interface IView extends IBaseView{
        void finish();

        void loginToResult(Integer success, MyUser myUser);
    }
}
