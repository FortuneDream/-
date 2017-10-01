package com.example.q.pocketmusic.module.home.net.type.community.share;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;

import com.example.q.pocketmusic.R;
import com.example.q.pocketmusic.callback.AbsOnClickItemHeadListener;
import com.example.q.pocketmusic.model.bean.share.ShareSong;
import com.example.q.pocketmusic.module.common.BaseFragment;
import com.example.q.pocketmusic.module.home.net.type.SongTypeActivity;
import com.example.q.pocketmusic.module.home.net.type.community.ask.AskListFragment;
import com.jude.easyrecyclerview.EasyRecyclerView;
import com.jude.easyrecyclerview.adapter.RecyclerArrayAdapter;

import java.util.List;

import butterknife.BindView;

/**
 * Created by 鹏君 on 2017/5/16.
 */

public class ShareListFragment extends BaseFragment<ShareListPresenter.IView, ShareListPresenter>
        implements ShareListPresenter.IView, SwipeRefreshLayout.OnRefreshListener, RecyclerArrayAdapter.OnItemClickListener
        , RecyclerArrayAdapter.OnMoreListener {
    @BindView(R.id.recycler)
    EasyRecyclerView recycler;
    private ShareListAdapter adapter;
    private int typeId;


    @Override
    public void finish() {

    }

    @Override
    public int setContentResource() {
        return R.layout.fragment_share_list;
    }

    @Override
    public void initView() {
        //监听
        adapter = new ShareListAdapter(getContext());
        recycler.setRefreshListener(this);
        adapter.setOnItemClickListener(this);
        adapter.setMore(R.layout.view_more, this);
        //初始化
        presenter.setSharePage(0);
        presenter.setType(typeId);
        initRecyclerView(recycler, adapter);
        recycler.setEmptyView(R.layout.view_not_found);
        presenter.getShareList(true);
        adapter.setAbsOnClickItemHeadListener(new AbsOnClickItemHeadListener() {
            @Override
            public void onClickItem(int position) {
                presenter.enterOtherProfileActivity(adapter.getItem(position).getUser());
            }
        });
    }

    @Override
    public void setList(boolean isRefreshing,List<ShareSong> list) {
        if (isRefreshing){
            adapter.clear();
        }
        adapter.addAll(list);
    }


    @Override
    public void onRefresh() {
        presenter.getShareList(true);
    }


    @Override
    public void onMoreShow() {
        presenter.getMoreShareList();
    }

    @Override
    public void onMoreClick() {

    }


    @Override
    protected ShareListPresenter createPresenter() {
        return new ShareListPresenter(this);
    }


    @Override
    public void onItemClick(int position) {
        ShareSong shareSong = adapter.getItem(position);
        presenter.enterSongActivityByShare(shareSong);
    }

    public static ShareListFragment getInstance(int typeId) {
        ShareListFragment fragment = new ShareListFragment();
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
}
