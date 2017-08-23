package com.example.q.pocketmusic.module.home.net.acg;

import android.support.design.widget.AppBarLayout;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.support.v7.widget.Toolbar;

import com.example.q.pocketmusic.R;
import com.example.q.pocketmusic.model.bean.acg.ACGAlbum;
import com.example.q.pocketmusic.module.common.BaseActivity;
import com.jude.easyrecyclerview.EasyRecyclerView;

import java.util.List;

import butterknife.BindView;

public class ACGActivity extends BaseActivity<ACGPresenter.IView, ACGPresenter>
        implements ACGPresenter.IView {
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.app_bar)
    AppBarLayout appBar;
    @BindView(R.id.recycler)
    EasyRecyclerView recycler;

    private ACGAdapter adapter;

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
        initToolbar(toolbar, "ACG专区");
        adapter = new ACGAdapter(getCurrentContext());
        recycler.setLayoutManager(new StaggeredGridLayoutManager(3,StaggeredGridLayoutManager.VERTICAL));
        recycler.setAdapter(adapter);
        presenter.getACGAlbumList();
    }

    @Override
    public void setAlbumList(List<ACGAlbum> list) {
        adapter.addAll(list);
    }
}
