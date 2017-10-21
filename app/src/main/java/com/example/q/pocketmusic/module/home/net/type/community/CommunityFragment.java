package com.example.q.pocketmusic.module.home.net.type.community;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.q.pocketmusic.R;
import com.example.q.pocketmusic.module.common.BaseFragment;
import com.example.q.pocketmusic.module.home.net.type.SongTypeActivity;
import com.example.q.pocketmusic.view.widget.view.TopTabView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by 鹏君 on 2017/5/16.
 */

public class CommunityFragment extends BaseFragment<CommunityPresenter.IView, CommunityPresenter> implements CommunityPresenter.IView {

    @BindView(R.id.tab_layout)
    TabLayout tabLayout;
    @BindView(R.id.view_pager)
    ViewPager viewPager;
    private int typeId;
    private CommunityPagerAdapter adapter;


    @Override
    protected CommunityPresenter createPresenter() {
        return new CommunityPresenter(this);
    }


    @Override
    public void finish() {
        getActivity().finish();
    }

    @Override
    public int setContentResource() {
        return R.layout.fragment_community;
    }

    @Override
    public void initView() {
        List<CharSequence> list = new ArrayList<>();
        list.add("动态");
        list.add("分享");
        list.add("求谱");
        adapter = new CommunityPagerAdapter(getChildFragmentManager(), presenter.initFragment(typeId), list);
        viewPager.setAdapter(adapter);
        tabLayout.setupWithViewPager(viewPager);
    }



    public static CommunityFragment getInstance(int typeId) {
        CommunityFragment fragment = new CommunityFragment();
        Bundle args = new Bundle();
        args.putInt(SongTypeActivity.PARAM_POSITION, typeId);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.typeId = getArguments().getInt(SongTypeActivity.PARAM_POSITION);
    }

}
