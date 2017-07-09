package com.example.q.pocketmusic.module.user.notify.version;

import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.v7.widget.Toolbar;

import com.example.q.pocketmusic.R;
import com.example.q.pocketmusic.config.BmobInfo;
import com.example.q.pocketmusic.module.common.BaseActivity;
import com.jude.easyrecyclerview.EasyRecyclerView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class PreviewVersionActivity extends BaseActivity<PreviewVersionPresenter.IView, PreviewVersionPresenter>
        implements PreviewVersionPresenter.IView {

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.app_bar)
    AppBarLayout appBar;
    @BindView(R.id.recycler)
    EasyRecyclerView recycler;
    private PlanAdapter adapter;

    @Override
    protected PreviewVersionPresenter createPresenter() {
        return new PreviewVersionPresenter(this);
    }


    @Override
    public int setContentResource() {
        return R.layout.activity_preview_version;
    }

    @Override
    public void initView() {
        adapter = new PlanAdapter(this);
        initRecyclerView(recycler, adapter, 1);
        initToolbar(toolbar, "新版本预览");
        presenter.getDevPlanList();
    }

    @Override
    public void setDevPlanList(List<BmobInfo> list) {
        adapter.addAll(list);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }
}
