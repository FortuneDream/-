package com.example.q.pocketmusic.module.home.net.special;

import android.support.design.widget.AppBarLayout;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.support.v7.widget.Toolbar;

import com.example.q.pocketmusic.R;
import com.example.q.pocketmusic.model.bean.special.SpecialColumn;
import com.example.q.pocketmusic.module.common.BaseActivity;
import com.example.q.pocketmusic.util.common.ToastUtil;
import com.jude.easyrecyclerview.EasyRecyclerView;
import com.jude.easyrecyclerview.adapter.RecyclerArrayAdapter;

import java.util.List;

import butterknife.BindView;

public class SpecialColumnActivity extends BaseActivity<SpecialColumnPresenter.IView, SpecialColumnPresenter>
        implements SpecialColumnPresenter.IView, RecyclerArrayAdapter.OnItemClickListener {


    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.app_bar)
    AppBarLayout appBar;
    @BindView(R.id.recycler)
    EasyRecyclerView recycler;
    private SpecialColumnAdapter adapter;

    @Override
    protected SpecialColumnPresenter createPresenter() {
        return new SpecialColumnPresenter(this);
    }


    @Override
    public int setContentResource() {
        return R.layout.activity_special_column;
    }

    @Override
    public void initView() {
        initToolbar(toolbar, "专题专栏");
        adapter = new SpecialColumnAdapter(getCurrentContext());
        recycler.setLayoutManager(new StaggeredGridLayoutManager(3, StaggeredGridLayoutManager.VERTICAL));
        recycler.setAdapter(adapter);
        presenter.getACGAlbumList();
        adapter.setOnItemClickListener(this);
    }

    @Override
    public void setAlbumList(List<SpecialColumn> list) {
        adapter.addAll(list);
    }

    @Override
    public void onItemClick(int position) {
        presenter.enterSpecialListActivity(adapter.getItem(position));
    }
}
