package com.example.q.pocketmusic.module.home.local;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.RelativeLayout;

import com.example.q.pocketmusic.R;
import com.example.q.pocketmusic.module.common.BaseFragment;
import com.example.q.pocketmusic.module.home.local.localrecord.LocalRecordFragment;
import com.example.q.pocketmusic.module.home.local.localsong.LocalSongFragment;
import com.example.q.pocketmusic.module.lead.LeadSongActivity;
import com.github.clans.fab.FloatingActionButton;
import com.github.clans.fab.FloatingActionMenu;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import butterknife.Unbinder;


/**
 * Created by YQ on 2016/8/28.
 */
public class HomeLocalFragment extends BaseFragment<HomeLocalFragmentPresenter.IView,HomeLocalFragmentPresenter>
        implements HomeLocalFragmentPresenter.IView, ViewPager.OnPageChangeListener {
    @BindView(R.id.local_tab_layout)
    TabLayout localTabLayout;
    @BindView(R.id.app_bar)
    AppBarLayout appBar;
    @BindView(R.id.local_view_pager)
    ViewPager localViewPager;
    @BindView(R.id.menu_item_add_local)
    FloatingActionButton menuItemAddLocal;
    @BindView(R.id.menu_item_piano)
    FloatingActionButton menuItemPiano;
    @BindView(R.id.menu_fab)
    FloatingActionMenu menuFab;
    @BindView(R.id.activity_audio_record)
    RelativeLayout activityAudioRecord;
    private LocalFragmentPagerAdapter adapter;
    private FragmentManager fm;
    private List<String> tabs = new ArrayList<>();
    private LocalRecordFragment localRecordFragment;
    private LocalSongFragment localSongFragment;
    private List<Fragment> fragmentList = new ArrayList<>();

    @Override
    protected HomeLocalFragmentPresenter createPresenter() {
        return new HomeLocalFragmentPresenter(this);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        fragmentList.clear();
        tabs.clear();
        //初始化tab名称
        tabs.add("定制乐谱");
        tabs.add("我的独奏");
        //初始化Fragment
        localSongFragment = new LocalSongFragment();
        localRecordFragment = new LocalRecordFragment();
        fragmentList.add(localSongFragment);
        fragmentList.add(localRecordFragment);
    }

    @Override
    public int setContentResource() {
        return R.layout.fragment_home_local;
    }


    @Override
    public void initView() {
        //监听
        localViewPager.addOnPageChangeListener(this);
        //viewpager
        fm = getChildFragmentManager();
        adapter = new LocalFragmentPagerAdapter(getActivity(), fm, tabs, fragmentList);
        localViewPager.setAdapter(adapter);
        localTabLayout.setupWithViewPager(localViewPager);
    }


    //必须要在onResume中设置
    @Override
    public void onResume() {
        super.onResume();
        localTabLayout.setTabTextColors(ContextCompat.getColor(getContext(), R.color.colorTitle), ContextCompat.getColor(getContext(), R.color.colorTitle));
        localTabLayout.setSelectedTabIndicatorColor(ContextCompat.getColor(getContext(), R.color.colorTitle));
    }


    @Override
    public void finish() {
        getActivity().finish();
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {
        //Floating的显示和隐藏
        if (position == 0) {
            menuFab.showMenu(true);
        } else {
            menuFab.hideMenu(true);
        }
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == LeadSongActivity.REQUEST_LEAD && resultCode == LeadSongActivity.RESULT_OK) {
            localSongFragment.onRefresh();
        }
    }


    @OnClick({R.id.menu_item_add_local, R.id.menu_item_piano})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.menu_item_add_local:
                presenter.enterLeadActivity();
                break;
            case R.id.menu_item_piano:
                presenter.enterPianoActivity();
                break;
        }
    }
}
