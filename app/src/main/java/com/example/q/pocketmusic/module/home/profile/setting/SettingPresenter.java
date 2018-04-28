package com.example.q.pocketmusic.module.home.profile.setting;

import android.content.ContextWrapper;
import android.content.Intent;

import com.example.q.pocketmusic.data.bean.MyUser;
import com.example.q.pocketmusic.module.common.BasePresenter;

import com.example.q.pocketmusic.module.common.IBaseView;
import com.example.q.pocketmusic.util.common.ToastUtil;
import com.example.q.pocketmusic.util.common.update.UpdateUtils;

import cn.bmob.v3.listener.BmobUpdateListener;
import cn.bmob.v3.update.BmobUpdateAgent;
import cn.bmob.v3.update.UpdateResponse;
import cn.bmob.v3.update.UpdateStatus;

/**
 * Created by 鹏君 on 2016/11/14.
 */

public class SettingPresenter extends BasePresenter<SettingPresenter.IView> {


    public SettingPresenter(IView activity) {
       super(activity);
    }

    public void checkUpdate() {
        BmobUpdateAgent.setUpdateOnlyWifi(false);
        BmobUpdateAgent.setUpdateListener(new BmobUpdateListener() {
            @Override
            public void onUpdateReturned(int i, UpdateResponse updateResponse) {
                if (i == UpdateStatus.Yes) {
                    ToastUtil.showToast("有新版哦~");
                } else {
                    ToastUtil.showToast("当前已是最新版哦~");
                }
            }
        });
        BmobUpdateAgent.forceUpdate(mContext);

    }


    public void logOut() {
        MyUser.logOut();
        android.os.Process.killProcess(android.os.Process.myPid());
        ContextWrapper wrapper = ((ContextWrapper) mContext);
        Intent i = wrapper.getBaseContext().getPackageManager()
                .getLaunchIntentForPackage(wrapper.getBaseContext().getPackageName());
        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        mContext.startActivity(i);//重启app
    }

    public void appUpdate() {
        UpdateUtils.getInstance().update(mContext);
    }


    public interface IView extends IBaseView {

    }
}
