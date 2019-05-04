package com.example.q.pocketmusic.module.home.net.type.community;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import java.util.List;

/**
 * Created by 81256 on 2017/10/6.
 */

public class CommunityPagerAdapter extends FragmentPagerAdapter {
    private List<CharSequence> tabs;
    private List<Fragment> fragmentList;

    public CommunityPagerAdapter(FragmentManager fm, List<Fragment> list, List<CharSequence> tabs) {
        super(fm);
        this.fragmentList = list;
        this.tabs = tabs;
    }

    @Override
    public Fragment getItem(int position) {
        return fragmentList.get(position);
    }

    @Override
    public int getCount() {
        return fragmentList.size();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return tabs.get(position);
    }
}
