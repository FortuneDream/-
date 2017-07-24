package com.example.q.pocketmusic.module.home.seek.share;

import android.support.v4.widget.SwipeRefreshLayout;

import com.example.q.pocketmusic.R;
import com.example.q.pocketmusic.model.bean.share.ShareSong;
import com.example.q.pocketmusic.module.common.BaseFragment;
import com.jude.easyrecyclerview.EasyRecyclerView;
import com.jude.easyrecyclerview.adapter.RecyclerArrayAdapter;

import java.util.List;

import butterknife.BindView;

/**
 * Created by 鹏君 on 2017/5/16.
 */

public class ShareListFragment extends BaseFragment<ShareListPresenter.IView, ShareListPresenter>
        implements ShareListPresenter.IView, SwipeRefreshLayout.OnRefreshListener, RecyclerArrayAdapter.OnItemClickListener
        , RecyclerArrayAdapter.OnMoreListener, ShareListAdapter.OnClickHeadListener {
    @BindView(R.id.recycler)
    EasyRecyclerView recycler;
    private ShareListAdapter adapter;


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
        initRecyclerView(recycler, adapter);
        recycler.setEmptyView(R.layout.view_not_found);
        presenter.getShareList(false);
        adapter.setListener(this);
    }

    @Override
    public void setList(List<ShareSong> list) {
        adapter.addAll(list);
    }

    @Override
    public void setListWithRefreshing(List<ShareSong> list) {
        adapter.clear();
        adapter.addAll(list);
    }

    @Override
    public void onRefresh() {
        presenter.setSharePage(0);
        presenter.getShareList(true);
    }

    @Override
    public void setMore(List<ShareSong> list) {
        adapter.addAll(list);
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

    @Override
    public void onClick(int position) {
        presenter.enterOtherProfileActivity(adapter.getItem(position).getUser());
    }
}
