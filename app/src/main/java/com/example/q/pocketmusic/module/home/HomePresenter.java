package com.example.q.pocketmusic.module.home;

import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;

import com.example.q.pocketmusic.R;
import com.example.q.pocketmusic.module.common.BasePresenter;
import com.example.q.pocketmusic.module.common.IBaseView;
import com.example.q.pocketmusic.module.home.convert.HomeConvertListFragment;
import com.example.q.pocketmusic.module.home.profile.support.SupportActivity;
import com.example.q.pocketmusic.module.home.local.HomeLocalFragment;
import com.example.q.pocketmusic.module.home.net.HomeNetFragment;
import com.example.q.pocketmusic.module.home.profile.HomeProfileFragment;
import com.example.q.pocketmusic.util.common.SharedPrefsUtil;
import com.example.q.pocketmusic.util.common.update.UpdateUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by 鹏君 on 2016/11/22.
 */

public class HomePresenter extends BasePresenter<HomePresenter.IView> {
    private IView activity;
    private List<Fragment> fragments;
    private FragmentManager fm;
    private Fragment totalFragment;
    private static int FLAG_SELECT_NET = 1001;
    private static int FLAG_SELECT_CONVERT = 1002;
    private static int FLAG_SELECT_LOCAL = 1003;
    private static int FLAG_SELECT_PROFILE = 1004;
    private HomeNetFragment homeNetFragment;//1
    private HomeConvertListFragment convertFragment;//2
    private HomeLocalFragment homeLocalFragment;//3
    private HomeProfileFragment homeProfileFragment;//4
    private int FLAG;//标记当前Fragment


    public HomePresenter(IView activity) {
        attachView(activity);
        this.activity = getIViewRef();
        initFragment();
    }

    private void initFragment() {
        fragments = new ArrayList<>();
        homeNetFragment = new HomeNetFragment();
        convertFragment = new HomeConvertListFragment();
        homeLocalFragment = new HomeLocalFragment();
        homeProfileFragment = new HomeProfileFragment();
        fragments.add(homeNetFragment);
        fragments.add(convertFragment);
        fragments.add(homeLocalFragment);
        fragments.add(homeProfileFragment);
    }

    //网络
    public void clickNet() {
        if (FLAG != FLAG_SELECT_NET) {
            FLAG = FLAG_SELECT_NET;
            showFragment(fragments.get(0));
            activity.onSelectNet();
        }
    }


    //转谱
    public void clickConvert() {
        if (FLAG != FLAG_SELECT_CONVERT) {
            FLAG = FLAG_SELECT_CONVERT;
            showFragment(fragments.get(1));
            activity.onSelectConvert();
        }
    }

    //本地
    public void clickLocal() {
        if (FLAG != FLAG_SELECT_LOCAL) {
            FLAG = FLAG_SELECT_LOCAL;
            showFragment(fragments.get(2));
            activity.onSelectLocal();
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
        UpdateUtils.getInstance().update(activity.getCurrentContext());
    }


    public void setFragmentManager(FragmentManager fragmentManager) {
        this.fm = fragmentManager;
    }

    public void enterSupportActivity() {
        activity.getCurrentContext().startActivity(new Intent(activity.getCurrentContext(), SupportActivity.class));
    }

    //弹出支持开发者的字段
    public void checkAlertSupportDialog() {
        long ago = SharedPrefsUtil.getLong("support_date", 1503071043854L);
        long now = System.currentTimeMillis();
        if (now - ago >= 36 * 1000 * 60 * 60) {
            SharedPrefsUtil.putLong("support_date", now);
            activity.alertSupportDialog();
        }
    }


    public interface IView extends IBaseView {

        void onSelectLocal();

        void onSelectNet();

        void onSelectConvert();

        void onSelectProfile();

        void alertSupportDialog();
    }
}
