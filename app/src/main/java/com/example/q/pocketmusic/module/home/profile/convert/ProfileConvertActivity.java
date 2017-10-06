package com.example.q.pocketmusic.module.home.profile.convert;


import android.support.v4.widget.SwipeRefreshLayout;

import com.example.q.pocketmusic.R;
import com.example.q.pocketmusic.module.common.AuthActivity;
import com.example.q.pocketmusic.module.home.profile.convert.temporary.ProfileTemporaryConvertAdapter;
import com.example.q.pocketmusic.view.widget.view.TopTabView;
import com.jude.easyrecyclerview.adapter.RecyclerArrayAdapter;

import butterknife.BindView;


public class ProfileConvertActivity extends AuthActivity<ProfileConvertActivityPresenter.IView, ProfileConvertActivityPresenter>
        implements ProfileConvertActivityPresenter.IView, TopTabView.TopTabListener {


    @BindView(R.id.top_tab_view)
    TopTabView topTabView;

    @Override
    public int setContentResource() {
        return R.layout.activity_profile_convert;
    }

    @Override
    public void initUserView() {
        presenter.initFragment();
        presenter.setFragmentManager(getSupportFragmentManager());
        topTabView.setListener(this);
        topTabView.setCheck(0);
        presenter.clickPost();
    }


    @Override
    protected ProfileConvertActivityPresenter createPresenter() {
        return new ProfileConvertActivityPresenter(this);
    }


    @Override
    public void onSelectPost() {
        topTabView.setCheck(0);
    }

    @Override
    public void onSelectTemporary() {
        topTabView.setCheck(1);
    }


    @Override
    public void setTopTabCheck(int position) {
        switch (position) {
            case 0:
                presenter.clickPost();
                break;
            case 1:
                presenter.clickTemporary();
                break;
        }
    }
}