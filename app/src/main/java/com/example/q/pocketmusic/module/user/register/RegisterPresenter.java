package com.example.q.pocketmusic.module.user.register;

import android.app.Activity;
import android.text.TextUtils;

import com.example.q.pocketmusic.callback.ToastSaveListener;
import com.example.q.pocketmusic.config.CommonString;
import com.example.q.pocketmusic.config.Constant;
import com.example.q.pocketmusic.model.bean.MyUser;
import com.example.q.pocketmusic.module.common.BasePresenter;
import com.example.q.pocketmusic.module.common.IBaseView;
import com.example.q.pocketmusic.util.ToastUtil;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by 鹏君 on 2016/11/14.
 */

public class RegisterPresenter extends BasePresenter<RegisterPresenter.IView> {
    private IView activity;

    public RegisterPresenter(IView activity) {
        attachView(activity);
        this.activity=getIViewRef();
    }

    public Boolean checkAccount(String email) {
        Pattern pattern = Pattern.compile(Constant.CHECK_EMAIL_REGEX);
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    }

    public void register(String account, String password, String confirmPassword, String nickName) {
        Boolean isConfirm = checkAccount(account);//邮箱验证账号
        if (TextUtils.isEmpty(account) || TextUtils.isEmpty(password) || TextUtils.isEmpty(nickName) || TextUtils.isEmpty(confirmPassword)) {
            ToastUtil.showToast(CommonString.STR_COMPLETE_INFO);
        } else if (!isConfirm) {
            ToastUtil.showToast( "邮箱格式错误~");
        } else if (!confirmPassword.equals(password)) {
            ToastUtil.showToast( "两次输入的密码要相同哦~");
        } else {
            activity.showLoading(true);
            final MyUser user = new MyUser();
            user.setUsername(account);
            user.setPassword(password);
            user.setEmail(account);//账号作为邮箱,打开邮箱验证
            user.setNickName(nickName);
            user.signUp(new ToastSaveListener<MyUser>(activity) {
                @Override
                public void onSuccess(MyUser user) {
                    activity.showLoading(false);
                    ToastUtil.showToast( "注册成功，\\(^o^)/~");
                    ((Activity) activity.getCurrentContext()).setResult(Constant.SUCCESS);
                    activity.finish();
                }
            });
        }
    }

    public interface IView extends IBaseView {
        void finish();

        void showLoading(boolean isShow);
    }
}
