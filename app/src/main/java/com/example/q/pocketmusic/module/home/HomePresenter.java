package com.example.q.pocketmusic.module.home;

import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;

import com.example.q.pocketmusic.R;
import com.example.q.pocketmusic.module.common.BasePresenter;
import com.example.q.pocketmusic.module.common.IBaseView;
import com.example.q.pocketmusic.module.home.ask.list.HomeAskListFragment;
import com.example.q.pocketmusic.module.home.local.HomeLocalFragment;
import com.example.q.pocketmusic.module.home.net.HomeNetFragment;
import com.example.q.pocketmusic.module.home.profile.HomeProfileFragment;
import com.example.q.pocketmusic.util.MyToast;

import java.util.ArrayList;
import java.util.List;

import cn.bmob.v3.listener.BmobDialogButtonListener;
import cn.bmob.v3.listener.BmobUpdateListener;
import cn.bmob.v3.update.BmobUpdateAgent;
import cn.bmob.v3.update.UpdateResponse;
import cn.bmob.v3.update.UpdateStatus;

/**
 * Created by Cloud on 2016/11/22.
 */

public class HomePresenter extends BasePresenter<HomePresenter.IView> {
    private IView activity;
    private List<Fragment> fragments;
    private FragmentManager fm;
    private Fragment totalFragment;
    private static int FLAG_SELECT_LOCAL = 1001;
    private static int FLAG_SELECT_NET = 1002;
    private static int FLAG_SELECT_ASK = 1003;
    private static int FLAG_SELECT_PROFILE = 1004;
    private HomeLocalFragment homeLocalFragment;
    private HomeNetFragment homeNetFragment;
    private HomeAskListFragment homeAskListFragment;
    private HomeProfileFragment homeProfileFragment;
    private int FLAG;//标记当前Fragment


    public HomePresenter(IView activity) {
        attachView(activity);
        this.activity = getIViewRef();
        initFragment();
    }

    private void initFragment() {
        fragments = new ArrayList<>();
        homeLocalFragment = new HomeLocalFragment();
        homeNetFragment = new HomeNetFragment();
        homeAskListFragment = new HomeAskListFragment();
        homeProfileFragment = new HomeProfileFragment();
        fragments.add(homeLocalFragment);
        fragments.add(homeNetFragment);
        fragments.add(homeAskListFragment);
        fragments.add(homeProfileFragment);
    }

    //点击本地
    public void clickLocal() {
        if (FLAG != FLAG_SELECT_LOCAL) {
            FLAG = FLAG_SELECT_LOCAL;
            showFragment(fragments.get(0));
            activity.onSelectLocal();
        }
    }


    public void clickNet() {
        if (FLAG != FLAG_SELECT_NET) {
            FLAG = FLAG_SELECT_NET;
            showFragment(fragments.get(1));
            activity.onSelectNet();
        }
    }

    public void clickAsk() {
        if (FLAG != FLAG_SELECT_ASK) {
            FLAG = FLAG_SELECT_ASK;
            showFragment(fragments.get(2));
            activity.onSelectAsk();
        }
    }

    public void clickProfile() {
        if (FLAG != FLAG_SELECT_PROFILE) {
            FLAG = FLAG_SELECT_PROFILE;
            showFragment(fragments.get(3));
            activity.onSelectProfile();
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
        BmobUpdateAgent.setUpdateOnlyWifi(false);//在任意情况下都会提示
        BmobUpdateAgent.setDialogListener(new BmobDialogButtonListener() {
            @Override
            public void onClick(int i) {
                if (i == UpdateStatus.Update) {
                    toastIgnoreAndroidN();
                    enterAppStore();
                }
            }
        });
        BmobUpdateAgent.setUpdateListener(new BmobUpdateListener() {
            @Override
            public void onUpdateReturned(int i, UpdateResponse updateResponse) {
                if (i == UpdateStatus.Yes) {//版本有更新
                    toastIgnoreAndroidN();
                } else if (i == UpdateStatus.ErrorSizeFormat) {
                    MyToast.showToast(activity.getCurrentContext(), "稍等片刻~正在准备更新中~");
                } else if (i == UpdateStatus.TimeOut) {
                    MyToast.showToast(activity.getCurrentContext(), "查询出错或查询超时");
                }
            }
        });//更新监听
        BmobUpdateAgent.update(activity.getCurrentContext());//更新
    }

    //进入AppStore
    private void enterAppStore() {
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setData(Uri.parse("market://details?id=" + activity.getCurrentContext().getPackageName()));
        if (intent.resolveActivity(activity.getCurrentContext().getPackageManager()) != null) { //可以接收
            activity.getCurrentContext().startActivity(intent);
        } else {
            MyToast.showToast(activity.getCurrentContext(), "没有找到应用市场~");
        }
    }

    //忽略
    private void toastIgnoreAndroidN() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {//7.0 不更新
            MyToast.showToast(activity.getCurrentContext(), "在目前暂时不支持Android N 7.0 的自动更新，请到应用商店中下载");
        }
    }

    public void setFragmentManager(FragmentManager fragmentManager) {
        this.fm = fragmentManager;
    }


    public interface IView extends IBaseView {

        void onSelectLocal();

        void onSelectNet();

        void onSelectAsk();

        void onSelectProfile();
    }
}
