package com.example.q.pocketmusic.module.home.net.type.community;

import android.support.v4.app.Fragment;

import com.example.q.pocketmusic.module.common.BasePresenter;
import com.example.q.pocketmusic.module.common.IBaseView;
import com.example.q.pocketmusic.module.home.net.type.community.ask.AskListFragment;
import com.example.q.pocketmusic.module.home.net.type.community.share.ShareListFragment;
import com.example.q.pocketmusic.module.home.net.type.community.state.CommunityStateFragment;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by 鹏君 on 2017/5/16.
 */

public class CommunityPresenter extends BasePresenter<CommunityPresenter.IView> {
    private IView fragment;

    public CommunityPresenter(IView fragment) {
        attachView(fragment);
        this.fragment = getIViewRef();
    }

    public List<Fragment> initFragment(int typeId) {
        List<Fragment> fragments = new ArrayList<>();
        AskListFragment askListFragment = AskListFragment.getInstance(typeId);
        ShareListFragment shareListFragment = ShareListFragment.getInstance(typeId);
        CommunityStateFragment communityStateFragment = CommunityStateFragment.getInstance(typeId);
        fragments.add(communityStateFragment);
        fragments.add(shareListFragment);
        fragments.add(askListFragment);
        return fragments;
    }


    interface IView extends IBaseView {
    }
}
