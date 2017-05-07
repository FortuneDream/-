package com.example.q.pocketmusic.module.user.register;

import android.app.Activity;
import android.content.Context;
import android.text.TextUtils;

import com.example.q.pocketmusic.module.common.IBaseList;
import com.example.q.pocketmusic.config.CommonString;
import com.example.q.pocketmusic.config.Constant;
import com.example.q.pocketmusic.model.bean.MyUser;
import com.example.q.pocketmusic.callback.ToastSaveListener;
import com.example.q.pocketmusic.module.common.BasePresenter;
import com.example.q.pocketmusic.util.MyToast;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by Cloud on 2016/11/14.
 */

public class RegisterPresenter extends BasePresenter {
    private IView activity;
    private Context context;

    public RegisterPresenter(IView activity, Context context) {
        this.activity = activity;
        this.context = context;
    }

    public Boolean checkAccount(String email) {
        Pattern pattern = Pattern.compile(Constant.CHECK_EMAIL_REGEX);
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    }

    public void register(String account, String password, String confirmPassword, String nickName) {
        Boolean isConfirm = checkAccount(account);//邮箱验证账号
        if (TextUtils.isEmpty(account) || TextUtils.isEmpty(password) || TextUtils.isEmpty(nickName) || TextUtils.isEmpty(confirmPassword)) {
            MyToast.showToast(context, CommonString.STR_COMPLETE_INFO);
        } else if (!isConfirm) {
            MyToast.showToast(context, "邮箱格式错误~");
        } else if (!confirmPassword.equals(password)){
            MyToast.showToast(context,"两次输入的密码要相同哦~");
        }else {
            activity.showLoading(true);
            final MyUser user = new MyUser();
            user.setUsername(account);
            user.setPassword(password);
            user.setEmail(account);//账号作为邮箱,打开邮箱验证
            user.setNickName(nickName);
            user.signUp(new ToastSaveListener<MyUser>(context,activity) {

                @Override
                public void onSuccess(MyUser user) {
                    activity.showLoading(false);
                    MyToast.showToast(context, "注册成功，\\(^o^)/~");
                    ((Activity) context).setResult(Constant.SUCCESS);
                    activity.finish();
                }
            });
        }

    }

    public interface IView extends IBaseList{
        void finish();

        void showLoading(boolean isShow);
    }
}
