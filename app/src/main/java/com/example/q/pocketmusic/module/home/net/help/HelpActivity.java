package com.example.q.pocketmusic.module.home.net.help;

import android.support.design.widget.AppBarLayout;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.Toolbar;
import android.widget.LinearLayout;

import com.example.q.pocketmusic.R;
import com.example.q.pocketmusic.module.common.BaseActivity;
import com.example.q.pocketmusic.module.home.profile.suggestion.LeftAndRightTagDecoration;
import com.jude.easyrecyclerview.EasyRecyclerView;

import java.util.List;

import butterknife.BindView;

public class HelpActivity extends BaseActivity<HelpPresenter.IView, HelpPresenter>
        implements SwipeRefreshLayout.OnRefreshListener, HelpPresenter.IView {


    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.app_bar)
    AppBarLayout appBar;
    @BindView(R.id.recycler)
    EasyRecyclerView recycler;
    @BindView(R.id.activity_help)
    LinearLayout activityHelp;
    private HelpAdapter adapter;

    @Override
    public int setContentResource() {
        return R.layout.activity_help;
    }

    @Override
    public void initView() {
        adapter = new HelpAdapter(this);
        recycler.setRefreshListener(this);
        initToolbar(toolbar, "帮助信息");
        initRecyclerView(recycler, adapter);
        recycler.addItemDecoration(new LeftAndRightTagDecoration(getCurrentContext()));
        recycler.addItemDecoration(new DividerItemDecoration(getCurrentContext(), DividerItemDecoration.VERTICAL));
        onRefresh();
    }


    @Override
    public void onRefresh() {
        presenter.getList();
    }

    @Override
    protected HelpPresenter createPresenter() {
        return new HelpPresenter(this);
    }

    @Override
    public void setList(List<String> strings) {
        adapter.clear();
        adapter.addAll(strings);
    }
}
