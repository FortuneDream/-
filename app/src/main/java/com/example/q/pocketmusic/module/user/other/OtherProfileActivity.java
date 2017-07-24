package com.example.q.pocketmusic.module.user.other;

import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.TabLayout;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.example.q.pocketmusic.R;
import com.example.q.pocketmusic.model.bean.MyUser;
import com.example.q.pocketmusic.module.common.AuthActivity;

import butterknife.BindView;
import butterknife.ButterKnife;

public class OtherProfileActivity extends AuthActivity<OtherProfilePresenter.IView, OtherProfilePresenter>
        implements OtherProfilePresenter.IView {
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.app_bar)
    AppBarLayout appBar;
    @BindView(R.id.tab_layout)
    TabLayout tabLayout;
    @BindView(R.id.view_pager)
    ViewPager viewPager;
    public MyUser otherUser;
    private OtherAdapter adapter;
    public final static String PARAM_USER = "other_user";

    @Override
    protected OtherProfilePresenter createPresenter() {
        return new OtherProfilePresenter(this);
    }


    @Override
    public int setContentResource() {
        return R.layout.activity_other_profile;
    }

    @Override
    public void initUserView() {
        otherUser = (MyUser) getIntent().getSerializableExtra(PARAM_USER);
        initToolbar(toolbar, otherUser.getNickName());
        adapter = new OtherAdapter(getSupportFragmentManager());
        viewPager.setAdapter(adapter);
        tabLayout.setupWithViewPager(viewPager);
    }
}
