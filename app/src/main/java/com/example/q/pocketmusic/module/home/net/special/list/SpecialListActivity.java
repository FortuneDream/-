package com.example.q.pocketmusic.module.home.net.special.list;

import android.support.design.widget.AppBarLayout;
import android.support.v7.widget.Toolbar;

import com.example.q.pocketmusic.R;
import com.example.q.pocketmusic.module.common.BaseActivity;
import com.jude.easyrecyclerview.EasyRecyclerView;

import butterknife.BindView;

public class SpecialListActivity extends BaseActivity<SpecialListPresenter.IView, SpecialListPresenter>
        implements SpecialListPresenter.IView {

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.app_bar)
    AppBarLayout appBar;
    @BindView(R.id.recycler)
    EasyRecyclerView recycler;

    @Override
    protected SpecialListPresenter createPresenter() {
        return new SpecialListPresenter(this);
    }


    @Override
    public int setContentResource() {
        return R.layout.activity_special_list;
    }

    @Override
    public void initView() {

    }

}
