package com.example.q.pocketmusic.module.home.profile.user.other;

import android.content.Context;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.example.q.pocketmusic.module.home.profile.user.other.collection.OtherCollectionFragment;
import com.example.q.pocketmusic.module.home.profile.user.other.share.OtherShareFragment;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by 鹏君 on 2017/7/24.
 * （￣m￣）
 */

public class OtherAdapter extends FragmentPagerAdapter {
    private String[] titles = new String[]{"Ta的收藏", "Ta的分享"};
    private Context context;
    private Fragment otherCollectionFragment;
    private Fragment otherShareFragment;
    private List<Fragment> list;

    public OtherAdapter(FragmentManager fm) {
        super(fm);
        otherCollectionFragment = new OtherCollectionFragment();
        otherShareFragment = new OtherShareFragment();
        list = new ArrayList<>();
        list.add(otherCollectionFragment);
        list.add(otherShareFragment);
    }

    @Override
    public Fragment getItem(int position) {
        return list.get(position);
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return titles[position];
    }
}
