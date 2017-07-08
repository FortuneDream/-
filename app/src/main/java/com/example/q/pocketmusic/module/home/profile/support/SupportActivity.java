package com.example.q.pocketmusic.module.home.profile.support;

import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.v7.widget.Toolbar;
import android.widget.TextView;

import com.example.q.pocketmusic.R;
import com.example.q.pocketmusic.module.common.AuthActivity;
import com.jude.easyrecyclerview.EasyRecyclerView;

import butterknife.BindView;
import butterknife.ButterKnife;

public class SupportActivity extends AuthActivity<SupportPresenter.IView, SupportPresenter>
        implements SupportPresenter.IView {


    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.app_bar)
    AppBarLayout appBar;
    @BindView(R.id.dev_plan_tv)
    TextView devPlanTv;
    @BindView(R.id.alipay_tv)
    TextView alipayTv;
    @BindView(R.id.recycler)
    EasyRecyclerView recycler;
    private SupportAdapter adapter;

    @Override
    protected SupportPresenter createPresenter() {
        return new SupportPresenter(this);
    }


    @Override
    public int setContentResource() {
        return R.layout.activity_support;
    }

    @Override
    public void initUserView() {
        adapter = new SupportAdapter(this);
        initToolbar(toolbar, "支持开发者");
        initRecyclerView(recycler, adapter, 1);
    }

}
