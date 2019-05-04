package com.example.q.pocketmusic.module.home.net.type.community.state;


import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.example.q.pocketmusic.R;
import com.example.q.pocketmusic.callback.AbsOnClickItemHeadListener;
import com.example.q.pocketmusic.data.bean.CommunityState;
import com.example.q.pocketmusic.module.common.BaseFragment;
import com.example.q.pocketmusic.module.home.net.type.SongTypeActivity;
import com.jude.easyrecyclerview.EasyRecyclerView;
import com.jude.easyrecyclerview.adapter.RecyclerArrayAdapter;

import java.util.List;

import butterknife.BindView;
import butterknife.Unbinder;

public class CommunityStateFragment extends BaseFragment<CommunityStateFragmentPresenter.IView, CommunityStateFragmentPresenter>
        implements CommunityStateFragmentPresenter.IView, RecyclerArrayAdapter.OnMoreListener, SwipeRefreshLayout.OnRefreshListener {
    @BindView(R.id.community_state_recycler)
    EasyRecyclerView communityStateRecycler;
    private int typeId;
    private CommunityStateAdapter adapter;

    @Override
    public int setContentResource() {
        return R.layout.fragment_community_state;
    }

    @Override
    public void initView() {
        presenter.setType(typeId);
        adapter = new CommunityStateAdapter(getCurrentContext());
        initRecyclerView(communityStateRecycler, adapter, 1);
        adapter.setMore(R.layout.view_more, this);
        communityStateRecycler.setRefreshListener(this);
        adapter.setListener(new AbsOnClickItemHeadListener() {
            @Override
            public void onClickItem(int position) {
                //什么都不做
            }
        });
        presenter.getList(true);
    }

    public static CommunityStateFragment getInstance(int typeId) {
        CommunityStateFragment fragment = new CommunityStateFragment();
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
    protected CommunityStateFragmentPresenter createPresenter() {
        return new CommunityStateFragmentPresenter(this);
    }


    @Override
    public void setList(boolean isRefreshing, List<CommunityState> list) {
        if (isRefreshing) {
            adapter.clear();
        }
        adapter.addAll(list);
    }

    @Override
    public void onMoreShow() {
        presenter.getList(false);
    }

    @Override
    public void onMoreClick() {

    }

    @Override
    public void onRefresh() {
        presenter.getList(true);
    }
}