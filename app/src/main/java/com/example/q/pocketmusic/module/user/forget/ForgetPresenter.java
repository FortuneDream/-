package com.example.q.pocketmusic.module.user.forget;

import android.text.TextUtils;

import com.example.q.pocketmusic.config.BmobInfo;
import com.example.q.pocketmusic.config.Constant;
import com.example.q.pocketmusic.model.bean.MyUser;
import com.example.q.pocketmusic.module.common.BasePresenter;
import com.example.q.pocketmusic.module.common.IBaseView;
import com.example.q.pocketmusic.util.MyToast;

import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.SaveListener;

/**
 * Created by 鹏君 on 2017/5/8.
 */

public class ForgetPresenter extends BasePresenter<ForgetPresenter.IView> {
    private IView activity;

    public ForgetPresenter(IView activity) {
        attachView(activity);
        this.activity = getIViewRef();
    }

    public void checkInfo(final String account, String nickName, final String newPassword, String confirmPassword) {
        if (TextUtils.isEmpty(account) || TextUtils.isEmpty(nickName) || TextUtils.isEmpty(newPassword) || TextUtils.isEmpty(confirmPassword)) {
            MyToast.showToast(activity.getCurrentContext(), "请输入完整信息");
            return;
        }

        if (!newPassword.equals(confirmPassword)) {
            MyToast.showToast(activity.getCurrentContext(), "两次密码输入不同");
            return;
        }
        activity.showLoading(true);
        BmobQuery<MyUser> bmobQuery = new BmobQuery<>();
        bmobQuery.addWhereEqualTo("username", account);
        bmobQuery.addWhereEqualTo("nickName", nickName);
        bmobQuery.findObjects(new FindListener<MyUser>() {
            @Override
            public void done(List<MyUser> list, BmobException e) {
                if (e == null) {

                    if (list.size() != 1) {
                        activity.showLoading(false);
                        MyToast.showToast(activity.getCurrentContext(), "没有找到该用户");
                        return;
                    }

                    BmobInfo bmobInfo = new BmobInfo(Constant.BMOB_INFO_RESET_PASSWORD, account + ":" + newPassword);
                    bmobInfo.save(new SaveListener<String>() {
                        @Override
                        public void done(String s, BmobException e) {
                            activity.showLoading(false);
                            if (e == null) {
                                MyToast.showToast(activity.getCurrentContext(), "已收到修改密码请求，请稍等");
                                activity.finish();
                            } else {
                                MyToast.showToast(activity.getCurrentContext(), e.getMessage());
                            }
                        }
                    });
                } else {
                    activity.showLoading(false);
                    MyToast.showToast(activity.getCurrentContext(), e.getMessage());
                }
            }
        });
    }

    interface IView extends IBaseView {

    }
}
