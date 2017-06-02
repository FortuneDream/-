package com.example.q.pocketmusic.module.search;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.List;

/**
 * Created by 鹏君 on 2017/4/14.
 */
//ViewPager Adapter
public class SearchMainAdapter  extends FragmentPagerAdapter {
    private List<String> tabAttrList;
    private List<Fragment> fragments;
    private Context context;


    public SearchMainAdapter(Context context, FragmentManager fm, List<String> toolbarAttrList, List<Fragment> fragments) {
        super(fm);
        this.tabAttrList = toolbarAttrList;
        this.fragments = fragments;
        this.context = context;
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
