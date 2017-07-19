package com.example.q.pocketmusic.module.home.net.acg;

import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.v7.widget.Toolbar;

import com.example.q.pocketmusic.R;
import com.example.q.pocketmusic.module.common.BaseActivity;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ACGActivity extends BaseActivity<ACGPresenter.IView, ACGPresenter>
        implements ACGPresenter.IView {

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.app_bar)
    AppBarLayout appBar;

    @Override
    protected ACGPresenter createPresenter() {
        return new ACGPresenter(this);
    }


    @Override
    public int setContentResource() {
        return R.layout.activity_acg;
    }

    @Override
    public void initView() {
        initToolbar(toolbar,"ACG专区");
    }

}
