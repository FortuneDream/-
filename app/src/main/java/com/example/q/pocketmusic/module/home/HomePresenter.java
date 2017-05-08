package com.example.q.pocketmusic.module.home;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;

import com.example.q.pocketmusic.R;
import com.example.q.pocketmusic.module.common.BasePresenter;
import com.example.q.pocketmusic.module.common.IBaseView;
import com.example.q.pocketmusic.module.home.ask.list.HomeAskListFragment;
import com.example.q.pocketmusic.module.home.local.HomeLocalFragment;
import com.example.q.pocketmusic.module.home.net.HomeNetFragment;
import com.example.q.pocketmusic.module.home.profile.HomeProfileFragment;

import java.util.ArrayList;
import java.util.List;

import cn.bmob.v3.listener.BmobUpdateListener;
import cn.bmob.v3.update.BmobUpdateAgent;
import cn.bmob.v3.update.UpdateResponse;

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
        this.activity=getIViewRef();
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

    public void checkVersion() {
        BmobUpdateAgent.setUpdateOnlyWifi(false);//在任意情况下都会提示
        BmobUpdateAgent.update(activity.getCurrentContext());//更新
        BmobUpdateAgent.setUpdateListener(new BmobUpdateListener() {
            @Override
            public void onUpdateReturned(int i, UpdateResponse updateResponse) {
//                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
//                    MyToast.showToast(context, "在目前暂时不支持Android N 7.0 的自动更新，请到应用商店中下载");
//                }
            }
        });//更新监听
    }

    public void setFragmentManager(FragmentManager fragmentManager) {
        this.fm=fragmentManager;
    }


    public interface IView extends IBaseView {

        void onSelectLocal();

        void onSelectNet();

        void onSelectAsk();

        void onSelectProfile();
    }
}
