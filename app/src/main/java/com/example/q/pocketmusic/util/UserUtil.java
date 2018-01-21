package com.example.q.pocketmusic.util;

import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;

import com.example.q.pocketmusic.callback.ToastUpdateListener;
import com.example.q.pocketmusic.config.Constant;
import com.example.q.pocketmusic.data.bean.MyUser;
import com.example.q.pocketmusic.module.home.profile.user.login.LoginActivity;
import com.example.q.pocketmusic.util.common.LogUtils;
import com.example.q.pocketmusic.util.common.SharedPrefsUtil;

/**
 * Created by 鹏君 on 2017/1/26.
 */

public class UserUtil {
    public static MyUser user;
    public static final String RESULT_USER = "result_user";//返回的User

    public static boolean checkLocalUser(Fragment fragment) {
        MyUser user = MyUser.getCurrentUser(MyUser.class);
        if (user == null) {
            Intent intent = new Intent(fragment.getContext(), LoginActivity.class);
            fragment.startActivityForResult(intent, Constant.REQUEST_LOGIN);
            return false;
        } else {
            UserUtil.user = user;
            LogUtils.e(SharedPrefsUtil.getString("user", ""));
            return true;
        }
    }

    public static boolean checkLocalUser(FragmentActivity activity) {
        MyUser user = MyUser.getCurrentUser(MyUser.class);
        if (user == null) {
            Intent intent = new Intent(activity, LoginActivity.class);
            activity.startActivityForResult(intent, Constant.REQUEST_LOGIN);
            return false;
        } else {
            UserUtil.user = user;
            LogUtils.e(SharedPrefsUtil.getString("user", ""));
            return true;
        }
    }


    public static boolean checkUserContribution(FragmentActivity activity, Integer needContribution) {
        return checkLocalUser(activity) && UserUtil.user.getContribution() >= needContribution;
    }

    public static void increment(MyUser user, int number, ToastUpdateListener listener) {
        int now = user.getContribution();
        now = now + number;
        user.setContribution(now);
        user.update(listener);
    }

    public static void increment(int number, ToastUpdateListener listener) {
        int now = user.getContribution();
        now = now + number;
        user.setContribution(now);
        user.update(listener);
    }

    public static void onActivityResult(int resultCode,Intent data){
        if (resultCode == Constant.SUCCESS) {
            UserUtil.user = (MyUser) data.getSerializableExtra(RESULT_USER);//成功登录并复制
        } else if (resultCode == Constant.FAIL) {
            UserUtil.user = null;//登录失败
        }
    }

}
