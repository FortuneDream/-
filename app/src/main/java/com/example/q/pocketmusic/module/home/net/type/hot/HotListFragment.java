package com.example.q.pocketmusic.module.home.net.type.hot;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.q.pocketmusic.R;
import com.example.q.pocketmusic.model.bean.Song;
import com.example.q.pocketmusic.module.common.BaseFragment;
import com.example.q.pocketmusic.module.home.net.type.SongTypeActivity;
import com.example.q.pocketmusic.util.InstrumentFlagUtil;
import com.jude.easyrecyclerview.EasyRecyclerView;
import com.jude.easyrecyclerview.adapter.RecyclerArrayAdapter;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;


public class HotListFragment extends BaseFragment<HotListFragmentPresenter.IView, HotListFragmentPresenter>
        implements HotListFragmentPresenter.IView, RecyclerArrayAdapter.OnMoreListener, RecyclerArrayAdapter.OnItemClickListener, SwipeRefreshLayout.OnRefreshListener {

    @BindView(R.id.hot_list_recycler)
    EasyRecyclerView hotListRecycler;
    Unbinder unbinder;
    private HotListFragmentAdapter adapter;
    private int typeId;


    @Override
    public void initView() {
        //监听
        adapter = new HotListFragmentAdapter(getCurrentContext());
        adapter.setOnItemClickListener(this);
        adapter.setMore(R.layout.view_more, this);
        hotListRecycler.setRefreshListener(this);
        initRecyclerView(hotListRecycler, adapter, 1);
        presenter.setPage(1);
        presenter.setTypeId(typeId);
        presenter.getList(typeId, false);
    }

    @Override
    public void setList(List<Song> songs) {
        adapter.addAll(songs);
    }

    @Override
    public void setListWithRefreshing(List<Song> songs) {
        adapter.clear();
        adapter.addAll(songs);
    }


    @Override
    public void onMoreShow() {
        presenter.setPage(presenter.getmPage() + 1);
        presenter.getList(typeId, false);
    }

    @Override
    public void onMoreClick() {

    }

    @Override
    public void onItemClick(int position) {
        presenter.enterSongActivity(adapter.getItem(position));
    }

    @Override
    public void onRefresh() {
        presenter.setPage(1);
        presenter.getList(typeId, true);
    }

    public static HotListFragment getInstance(int typeId) {
        HotListFragment fragment = new HotListFragment();
        Bundle args = new Bundle();
        args.putInt(SongTypeActivity.PARAM_POSITION, typeId);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.typeId = getArguments().getInt(SongTypeActivity.PARAM_POSITION);
    }

    @Override
    protected HotListFragmentPresenter createPresenter() {
        return new HotListFragmentPresenter(this);
    }

    @Override
    public int setContentResource() {
        return R.layout.fragment_hot_list;
    }
}