package com.example.q.pocketmusic.module.home.net.type;


import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;

import com.example.q.pocketmusic.R;
import com.example.q.pocketmusic.module.common.BasePresenter;
import com.example.q.pocketmusic.module.common.IBaseView;
import com.example.q.pocketmusic.module.home.local.HomeLocalFragment;
import com.example.q.pocketmusic.module.home.net.HomeNetFragment;
import com.example.q.pocketmusic.module.home.net.type.community.CommunityFragment;
import com.example.q.pocketmusic.module.home.net.type.hot.HotListFragment;
import com.example.q.pocketmusic.module.home.net.type.study.StudyActivity;
import com.example.q.pocketmusic.module.home.profile.HomeProfileFragment;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by 鹏君 on 2016/8/29.
 */
public class SongTypeActivityPresenter extends BasePresenter<SongTypeActivityPresenter.IView> {
    private IView activity;
    private static int FLAG_SELECT_HOT_LIST = 1001;
    private static int FLAG_SELECT_COMMUNITY = 1002;
    private HotListFragment hotListFragment;
    private CommunityFragment communityFragment;
    private FragmentManager fm;
    private Fragment totalFragment;
    private int FLAG;//标记当前Fragment
    private List<Fragment> fragments = new ArrayList<>();

    public SongTypeActivityPresenter(IView activity) {
        attachView(activity);
        this.activity = getIViewRef();
    }

    //进入学习版块
    public void enterStudyActivity(Integer typeId) {
        Intent intent = new Intent(activity.getCurrentContext(), StudyActivity.class);
        intent.putExtra(StudyActivity.PARAM_TYPE, typeId);
        activity.getCurrentContext().startActivity(intent);
    }

    public void initFragment(int typeId) {
        fragments = new ArrayList<>();
        hotListFragment = HotListFragment.getInstance(typeId);
        communityFragment = CommunityFragment.getInstance(typeId);
        fragments.add(hotListFragment);
        fragments.add(communityFragment);
    }

    //热门
    public void clickHotList() {
        if (FLAG != FLAG_SELECT_HOT_LIST) {
            FLAG = FLAG_SELECT_HOT_LIST;
            showFragment(fragments.get(0));
            activity.onSelectHotList();
        }
    }

    //圈子
    public void clickCommunity() {
        if (FLAG != FLAG_SELECT_COMMUNITY) {
            FLAG = FLAG_SELECT_COMMUNITY;
            showFragment(fragments.get(1));
            activity.onSelectCommunity();
        }
    }


    private void showFragment(Fragment fragment) {
        if (!fragment.isAdded()) {
            if (totalFragment == null) {
                fm.beginTransaction().add(R.id.type_content, fragment, fragment.getClass().getName()).commit();
            } else {
                fm.beginTransaction().hide(totalFragment).add(R.id.type_content, fragment, fragment.getClass().getName()).commit();
            }
        } else {
            fm.beginTransaction().hide(totalFragment).show(fragment).commit();
        }
        totalFragment = fragment;
    }

    public void setFragmentManager(FragmentManager supportFragmentManager) {
        this.fm = supportFragmentManager;
    }


    public interface IView extends IBaseView {

        void onSelectHotList();

        void onSelectCommunity();
    }


}
