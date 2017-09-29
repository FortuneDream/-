package com.example.q.pocketmusic.util;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.util.Log;

import com.example.q.pocketmusic.callback.ToastQueryListener;
import com.example.q.pocketmusic.callback.ToastUpdateListener;
import com.example.q.pocketmusic.config.Constant;
import com.example.q.pocketmusic.model.bean.MyUser;
import com.example.q.pocketmusic.module.common.IBaseView;
import com.example.q.pocketmusic.module.user.login.LoginActivity;
import com.example.q.pocketmusic.util.common.LogUtils;
import com.example.q.pocketmusic.util.common.SharedPrefsUtil;

import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FetchUserInfoListener;
import cn.bmob.v3.listener.FindListener;

/**
 * Created by 鹏君 on 2017/1/26.
 */

public class UserUtil {
    public static MyUser user;

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
        if (checkLocalUser(activity)) {
            return UserUtil.user.getContribution() >= needContribution;
        } else {
            return false;
        }
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

}
