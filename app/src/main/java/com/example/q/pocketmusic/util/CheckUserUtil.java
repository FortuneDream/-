package com.example.q.pocketmusic.util;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.view.View;

import com.example.q.pocketmusic.callback.IBaseView;
import com.example.q.pocketmusic.callback.ToastQueryListener;
import com.example.q.pocketmusic.config.Constant;
import com.example.q.pocketmusic.model.bean.MyUser;
import com.example.q.pocketmusic.module.common.AuthFragment;
import com.example.q.pocketmusic.module.user.login.LoginActivity;

import org.json.JSONObject;

import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.BmobRealTimeData;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.listener.ValueEventListener;

/**
 * Created by Cloud on 2017/1/26.
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
        if (user.getContribution() >= needContribution) {
            return true;
        } else {
            return false;
        }
    }


    public static void getUserContribution(Fragment activity, Context context, IBaseView fragment, final UserContributionListener listener) {
        MyUser user = checkLocalUser(activity);
        if (user == null) {
            return;
        }
        BmobQuery<MyUser> query = new BmobQuery<>();
        query.addWhereEqualTo("username", user.getUsername());
        query.findObjects(new ToastQueryListener<MyUser>(context, fragment) {
            @Override
            public void onSuccess(List<MyUser> list) {
                MyUser nowUser = list.get(0);
                listener.onSuccess(nowUser.getContribution());
            }
        });

    }
}
