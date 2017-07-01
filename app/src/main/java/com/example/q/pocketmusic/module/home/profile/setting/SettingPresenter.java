package com.example.q.pocketmusic.module.home.profile.setting;

import android.content.ContextWrapper;
import android.content.Intent;

import com.example.q.pocketmusic.model.bean.MyUser;
import com.example.q.pocketmusic.module.common.BasePresenter;

import com.example.q.pocketmusic.module.common.IBaseView;
import com.example.q.pocketmusic.util.ToastUtil;

import cn.bmob.v3.listener.BmobUpdateListener;
import cn.bmob.v3.update.BmobUpdateAgent;
import cn.bmob.v3.update.UpdateResponse;
import cn.bmob.v3.update.UpdateStatus;

/**
 * Created by 鹏君 on 2016/11/14.
 */

public class SettingPresenter extends BasePresenter<SettingPresenter.IView> {
    private IView activity;

    public SettingPresenter(IView activity) {
        attachView(activity);
        this.activity=getIViewRef();
    }

    public void checkUpdate(final Boolean showToast) {
        BmobUpdateAgent.setUpdateListener(new BmobUpdateListener() {
            @Override
            public void onUpdateReturned(int i, UpdateResponse updateResponse) {
                if (i == UpdateStatus.Yes) {
                    activity.setCheckUpdateResult(true, showToast);
                } else if (i == UpdateStatus.No) {
                    activity.setCheckUpdateResult(false, showToast);
                }
            }
        });
        BmobUpdateAgent.forceUpdate(activity.getCurrentContext());

    }


    public void logOut() {
        MyUser.logOut();
        android.os.Process.killProcess(android.os.Process.myPid());
        ContextWrapper wrapper = ((ContextWrapper) activity.getCurrentContext());
        Intent i = wrapper.getBaseContext().getPackageManager()
                .getLaunchIntentForPackage(wrapper.getBaseContext().getPackageName());
        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        activity.getCurrentContext().startActivity(i);//重启app
    }



    //分享apk
    public void shareApp() {
        Intent intent = new Intent("android.intent.action.SEND");
        intent.addCategory("android.intent.category.DEFAULT");
        intent.setType("text/plain");
        intent.putExtra(Intent.EXTRA_TEXT, "推荐一款app:" + "<口袋乐谱>" + "---官网地址：" + "http://pocketmusic.bmob.site/");
        if (intent.resolveActivity(activity.getCurrentContext().getPackageManager()) != null) {
            activity.getCurrentContext().startActivity(intent);
        } else {
            ToastUtil.showToast( "你的手机不支持分享~");
        }
    }


    public interface IView extends IBaseView {

        void setCheckUpdateResult(boolean hasUpdate, boolean showToast);
    }
}
