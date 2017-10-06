package com.example.q.pocketmusic.module.home.profile.convert;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;

import com.example.q.pocketmusic.R;
import com.example.q.pocketmusic.module.common.BasePresenter;
import com.example.q.pocketmusic.module.common.IBaseView;
import com.example.q.pocketmusic.module.home.profile.convert.post.ProfileConvertPostFragment;
import com.example.q.pocketmusic.module.home.profile.convert.temporary.ProfileTemporaryConvertFragment;

import java.util.ArrayList;
import java.util.List;


public class ProfileConvertActivityPresenter extends BasePresenter<ProfileConvertActivityPresenter.IView> {
    private IView activity;
    private static final int FLAG_SELECT_TEMPORARY_CONVERT = 1001;
    private static final int FLAG_SELECT_POST_CONVERT = 1002;
    private int FLAG;
    private Fragment totalFragment;
    private FragmentManager fm;
    private List<Fragment> fragments;
    private ProfileConvertPostFragment profileConvertPostFragment;
    private ProfileTemporaryConvertFragment profileTemporaryConvertFragment;

    public ProfileConvertActivityPresenter(IView activity) {
        attachView(activity);
        this.activity = getIViewRef();
    }

    public void initFragment() {
        fragments = new ArrayList<>();
        profileConvertPostFragment = new ProfileConvertPostFragment();
        profileTemporaryConvertFragment = new ProfileTemporaryConvertFragment();
        fragments.add(profileConvertPostFragment);
        fragments.add(profileTemporaryConvertFragment);
    }

    public void clickPost() {
        if (FLAG != FLAG_SELECT_POST_CONVERT) {
            FLAG = FLAG_SELECT_POST_CONVERT;
            showFragment(fragments.get(0));
            activity.onSelectPost();
        }
    }

    //转谱
    public void clickTemporary() {
        if (FLAG != FLAG_SELECT_TEMPORARY_CONVERT) {
            FLAG = FLAG_SELECT_TEMPORARY_CONVERT;
            showFragment(fragments.get(1));
            activity.onSelectTemporary();
        }
    }

    private void showFragment(Fragment fragment) {
        if (!fragment.isAdded()) {
            if (totalFragment == null) {
                fm.beginTransaction().add(R.id.profile_convert_content, fragment, fragment.getClass().getName()).commit();
            } else {
                fm.beginTransaction().hide(totalFragment).add(R.id.profile_convert_content, fragment, fragment.getClass().getName()).commit();
            }
        } else {
            fm.beginTransaction().hide(totalFragment).show(fragment).commit();
        }
        totalFragment = fragment;
    }

    public void getList() {

    }

    public void setFragmentManager(FragmentManager supportFragmentManager) {
        this.fm = supportFragmentManager;
    }

    interface IView extends IBaseView {

        void onSelectPost();

        void onSelectTemporary();
    }
}
