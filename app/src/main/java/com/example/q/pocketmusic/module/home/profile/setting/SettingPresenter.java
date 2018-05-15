package com.example.q.pocketmusic.module.home.profile.setting;

import android.content.ContextWrapper;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;

import com.dell.fortune.tools.toast.ToastUtil;
import com.dell.fortune.tools.update.UpdateBuilder;
import com.example.q.pocketmusic.R;
import com.example.q.pocketmusic.callback.ToastQueryListener;
import com.example.q.pocketmusic.data.bean.MyUser;
import com.example.q.pocketmusic.module.common.BasePresenter;

import com.example.q.pocketmusic.module.common.IBaseView;

import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.listener.BmobUpdateListener;
import cn.bmob.v3.update.AppVersion;
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
        try {
            final PackageInfo pi = mContext.getPackageManager().getPackageInfo(mContext.getPackageName(), PackageManager.GET_ACTIVITIES);
            BmobQuery<AppVersion> query = new BmobQuery<>();
            query.addWhereGreaterThan("version_i", pi.versionCode);
            query.findObjects(new ToastQueryListener<AppVersion>() {
                @Override
                public void onSuccess(List<AppVersion> list) {
                    if (list.size() >= 1) {
                        AppVersion appVersion = list.get(list.size() - 1);
                        String versionContent = appVersion.getUpdate_log();
                        String versionCodeStr = appVersion.getVersion();
                        String url = appVersion.getPath().getUrl();
                        boolean isForce = appVersion.getIsforce();
                        UpdateBuilder updateBuilder = new UpdateBuilder(mContext);
                        updateBuilder.setVersionCodeStr(versionCodeStr)
                                .setVersionContent(versionContent)
                                .setUrl(url)
                                .setIsForce(isForce)
                                .update();
                    }
                }
            });
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
    }


    public interface IView extends IBaseView {

    }
}
