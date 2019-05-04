package com.example.q.pocketmusic.module.home.search;


import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import java.util.List;

/**
 * Created by 鹏君 on 2017/4/14.
 */
//ViewPager Adapter
public class HomeSearchAdapter extends FragmentPagerAdapter {
    private List<String> tabAttrList;
    private List<Fragment> fragments;


    public HomeSearchAdapter(FragmentManager fm, List<String> toolbarAttrList, List<Fragment> fragments) {
        super(fm);
        this.tabAttrList = toolbarAttrList;
        this.fragments = fragments;
    }


    @Override
    public Fragment getItem(int position) {
        return fragments.get(position);
    }

    @Override
    public int getCount() {
        return tabAttrList.size();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return  tabAttrList.get(position);
    }
}
