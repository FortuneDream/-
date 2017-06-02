package com.example.q.pocketmusic.util;

import android.content.Context;
import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;

import com.example.q.pocketmusic.callback.ToastQueryListener;
import com.example.q.pocketmusic.config.Constant;
import com.example.q.pocketmusic.model.bean.MyUser;
import com.example.q.pocketmusic.module.common.IBaseView;
import com.example.q.pocketmusic.module.user.login.LoginActivity;

import java.util.List;

import cn.bmob.v3.BmobQuery;

/**
 * Created by 鹏君 on 2017/1/26.
 */

public class CheckUserUtil {

    public interface UserContributionListener {
        void onSuccess(Integer contribution);
    }

    public static MyUser checkLocalUser(Fragment fragment) {
        MyUser user = MyUser.getCurrentUser(MyUser.class);
        if (user == null) {
            Intent intent = new Intent(fragment.getContext(), LoginActivity.class);
            fragment.startActivityForResult(intent, Constant.REQUEST_LOGIN);
        }
        return user;
    }

    public static MyUser checkLocalUser(FragmentActivity activity) {
        MyUser user = MyUser.getCurrentUser(MyUser.class);
        if (user == null) {
            Intent intent = new Intent(activity, LoginActivity.class);
            activity.startActivityForResult(intent, Constant.REQUEST_LOGIN);
        }
        return user;
    }


    public static boolean checkUserContribution(FragmentActivity activity, Integer needContribution) {
        MyUser user = checkLocalUser(activity);
        return user.getContribution() >= needContribution;
    }


    public static void getUserContribution(Fragment activity, Context context, IBaseView fragment, final UserContributionListener listener) {
        MyUser user = checkLocalUser(activity);
        if (user == null) {
            return;
        }
        BmobQuery<MyUser> query = new BmobQuery<>();
        query.addWhereEqualTo("username", user.getUsername());
        query.findObjects(new ToastQueryListener<MyUser>(fragment) {
            @Override
            public void onSuccess(List<MyUser> list) {
                MyUser nowUser = list.get(0);
                listener.onSuccess(nowUser.getContribution());
            }
        });

    }
}
