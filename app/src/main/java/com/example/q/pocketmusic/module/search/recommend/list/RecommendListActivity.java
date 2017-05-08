package com.example.q.pocketmusic.module.search.recommend.list;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.Toolbar;

import com.example.q.pocketmusic.R;
import com.example.q.pocketmusic.config.CommonString;
import com.example.q.pocketmusic.model.bean.Song;
import com.example.q.pocketmusic.module.common.BaseActivity;
import com.example.q.pocketmusic.util.MyToast;
import com.jude.easyrecyclerview.EasyRecyclerView;
import com.jude.easyrecyclerview.adapter.RecyclerArrayAdapter;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Cloud on 2016/11/14.
 */

public class RecommendListActivity extends BaseActivity<RecommendListActivityPresenter.IView,RecommendListActivityPresenter>
        implements RecommendListActivityPresenter.IView, RecyclerArrayAdapter.OnItemClickListener, RecyclerArrayAdapter.OnMoreListener, SwipeRefreshLayout.OnRefreshListener {
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.recycler)
    EasyRecyclerView recycler;
    private RecommendListAdapter adapter;

    @Override
    public int setContentResource() {
        return R.layout.activity_recommend_list;
    }

    @Override
    public void initView() {
        adapter = new RecommendListAdapter(this);
        adapter.setMore(R.layout.view_more, this);
        adapter.setOnItemClickListener(this);
        recycler.setRefreshListener(this);

        initToolbar(toolbar, "推荐列表");
        initRecyclerView(recycler, adapter, 1, false);
        onRefresh();
    }



    @Override
    public void setList(List<Song> list) {
        adapter.addAll(list);
    }


    //点击Item
    @Override
    public void onItemClick(int position) {
        Song song = adapter.getItem(position);
        presenter.enterSongActivity(song);
    }

    //加载更多
    @Override
    public void onMoreShow() {
        presenter.setPage(presenter.getmPage()+1);
        presenter.getList();
    }

    @Override
    public void onMoreClick() {

    }

    @Override
    public void onRefresh() {
        adapter.clear();
        presenter.setPage(1);
        presenter.getList();
    }

    @Override
    protected RecommendListActivityPresenter createPresenter() {
        return new RecommendListActivityPresenter(this);
    }
}