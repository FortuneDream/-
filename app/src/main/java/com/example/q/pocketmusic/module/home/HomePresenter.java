package com.example.q.pocketmusic.module.home;

import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.os.Environment;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;

import com.dell.fortune.tools.LogUtils;
import com.dell.fortune.tools.SharedPrefsUtil;
import com.dell.fortune.tools.update.UpdateBuilder;
import com.dell.fortune.tools.update.UpdateConfiguration;
import com.example.q.pocketmusic.R;
import com.example.q.pocketmusic.callback.ToastQueryListener;
import com.example.q.pocketmusic.config.constant.Constant;
import com.example.q.pocketmusic.module.common.BasePresenter;
import com.example.q.pocketmusic.module.common.IBaseView;
import com.example.q.pocketmusic.module.home.local.HomeLocalFragment;
import com.example.q.pocketmusic.module.home.net.HomeNetFragment;
import com.example.q.pocketmusic.module.home.profile.HomeProfileFragment;
import com.example.q.pocketmusic.module.home.search.HomeSearchFragment;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.update.AppVersion;

/**
 * Created by 鹏君 on 2016/11/22.
 */

public class HomePresenter extends BasePresenter<HomePresenter.IView> {


    public interface TabIndex {
        int NET_INDEX = 0;
        int SEARCH_INDEX = 1;
        int LOCAL_INDEX = 2;
        int PROFILE_INDEX = 3;
        int INIT_INDEX = -1;//初始值
    }

    private List<Fragment> fragments;
    private FragmentManager fm;
    private Fragment totalFragment;
    private HomeNetFragment homeNetFragment;//1
    private HomeSearchFragment homeSearchFragment;//2
    private HomeLocalFragment homeLocalFragment;//3
    private HomeProfileFragment homeProfileFragment;//4
    private int mCurIndex = TabIndex.INIT_INDEX;//标记当前Fragment
    private HashMap<String, Fragment> map;//改用HashMap来取得fragment


    public HomePresenter(IView activity) {
        super(activity);
        initFragment();
    }

    private void initFragment() {
        fragments = new ArrayList<>();
        homeNetFragment = new HomeNetFragment();
        homeSearchFragment = new HomeSearchFragment();
        homeLocalFragment = new HomeLocalFragment();
        homeProfileFragment = new HomeProfileFragment();
        fragments.add(TabIndex.NET_INDEX, homeNetFragment);
        fragments.add(TabIndex.SEARCH_INDEX, homeSearchFragment);
        fragments.add(TabIndex.LOCAL_INDEX, homeLocalFragment);
        fragments.add(TabIndex.PROFILE_INDEX, homeProfileFragment);
    }

    public void clickBottomTab(int index) {
        if (mCurIndex != index) {
            showFragment(fragments.get(index));
            mView.onSelectTabResult(mCurIndex, index);
            mCurIndex = index;
        }
    }

    private void showFragment(Fragment fragment) {
        if (!fragment.isAdded()) {
            if (totalFragment == null) {
                fm.beginTransaction().add(R.id.home_content, fragment, fragment.getClass().getName()).commit();
            } else {
                fm.beginTransaction().hide(totalFragment).add(R.id.home_content, fragment, fragment.getClass().getName()).commit();
            }
        } else {
            fm.beginTransaction().hide(totalFragment).show(fragment).commit();
        }
        totalFragment = fragment;
    }

    //检查版本更新
    public void checkVersion() {
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
                        updateBuilder.setConfiguration(new UpdateConfiguration(versionCodeStr, versionContent, url, isForce, Environment.getExternalStorageDirectory() + "", R.mipmap.ic_launcher));
                        updateBuilder.update();
                    }
                }
            });
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
    }


    public void setFragmentManager(FragmentManager fragmentManager) {
        this.fm = fragmentManager;
    }


    //弹出支持开发者的字段，最多只显示三次
    public void checkAlertSupportDialog() {
        long ago = SharedPrefsUtil.getLong(Constant.SUPPORT_DATE, 1503071043854L);
        int time = SharedPrefsUtil.getInt(Constant.SUPPORT_TIME, 0);
        long now = System.currentTimeMillis();
        if (now - ago >= 36 * 1000 * 60 * 60 && time <= 3) {
            SharedPrefsUtil.putLong(Constant.SUPPORT_DATE, now);
            time++;
            SharedPrefsUtil.putInt(Constant.SUPPORT_TIME, time);
            mView.alertSupportDialog();
        }
    }

    //与把签名放到C++防重打包
    public void checkSignature(PackageManager pm) {
        if (!isApkDebug()) {//release版本
            try {
                PackageInfo packageInfo = pm.getPackageInfo("com.example.q.pocketmusic", PackageManager.GET_SIGNATURES);
                StringBuilder sb = new StringBuilder();
                for (Signature signature : packageInfo.signatures) {
                    sb.append(signature.toCharsString());
                }
                //通过JNI调用把签名放到C++层
                if (!mView.getResString(R.string.signature).equals(sb.toString())) {
                    LogUtils.e("你使用的是盗版应用（可能会盗取您的个人信息）,请加入QQ群后下载正版");
                }
            } catch (PackageManager.NameNotFoundException e) {
                e.printStackTrace();
            }
        }
    }

    private boolean isApkDebug() {
        try {
            ApplicationInfo info = mContext.getApplicationInfo();
            return (info.flags & ApplicationInfo.FLAG_DEBUGGABLE) != 0;
        } catch (Exception e) {
            return false;
        }
    }


    public interface IView extends IBaseView {


        void alertSupportDialog();

        void onSelectTabResult(int oldIndex, int index);
    }
}
