package com.example.q.pocketmusic.module.home.net.special.list;

import android.support.design.widget.AppBarLayout;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.Toolbar;

import com.example.q.pocketmusic.R;
import com.example.q.pocketmusic.model.bean.special.SpecialColumn;
import com.example.q.pocketmusic.model.bean.special.SpecialColumnSong;
import com.example.q.pocketmusic.module.common.BaseActivity;
import com.example.q.pocketmusic.module.search.SearchMainAdapter;
import com.jude.easyrecyclerview.EasyRecyclerView;
import com.jude.easyrecyclerview.adapter.RecyclerArrayAdapter;

import java.util.List;

import butterknife.BindView;

public class SpecialListActivity extends BaseActivity<SpecialListPresenter.IView, SpecialListPresenter>
        implements SpecialListPresenter.IView, SwipeRefreshLayout.OnRefreshListener,RecyclerArrayAdapter.OnMoreListener, RecyclerArrayAdapter.OnItemClickListener {

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.app_bar)
    AppBarLayout appBar;
    @BindView(R.id.recycler)
    EasyRecyclerView recycler;
    private SpecialListAdapter adapter;
    public final static String SPECIAL_COLUMN = "SPECIAL_COLUMN";

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
        SpecialColumn column = (SpecialColumn) getIntent().getSerializableExtra(SPECIAL_COLUMN);
        presenter.setSpecialColumnSong(column);
        adapter = new SpecialListAdapter(getCurrentContext());
        initToolbar(toolbar, column.getAlbumName());
        initRecyclerView(recycler, adapter, 1);
        recycler.setRefreshListener(this);
        adapter.setMore(R.layout.view_more,this);
        adapter.setOnItemClickListener(this);
        presenter.getSpecialList(true);
    }

    @Override
    public void onRefresh() {
        presenter.getSpecialList(true);
    }

    @Override
    public void onMoreShow() {
        presenter.getMoreSpecialList();
    }

    @Override
    public void onMoreClick() {

    }

    @Override
    public void setSpecialList(List<SpecialColumnSong> list, boolean isRefreshing) {
        if (isRefreshing){
            adapter.clear();
        }
        adapter.addAll(list);
    }

    @Override
    public void onItemClick(int position) {
        presenter.enterSongActivity(adapter.getItem(position));
    }
}
