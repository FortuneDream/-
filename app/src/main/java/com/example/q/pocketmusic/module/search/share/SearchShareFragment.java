package com.example.q.pocketmusic.module.search.share;

import android.support.v4.widget.SwipeRefreshLayout;

import com.example.q.pocketmusic.R;
import com.example.q.pocketmusic.model.bean.share.ShareSong;
import com.example.q.pocketmusic.module.common.BaseFragment;
import com.example.q.pocketmusic.module.search.ISearchInfo;
import com.example.q.pocketmusic.util.LogUtils;
import com.jude.easyrecyclerview.EasyRecyclerView;
import com.jude.easyrecyclerview.adapter.RecyclerArrayAdapter;

import java.util.List;

import butterknife.BindView;

/**
 * Created by 81256 on 2017/4/14.
 */

public class SearchShareFragment extends BaseFragment implements SearchShareFragmentPresenter.IView, SwipeRefreshLayout.OnRefreshListener, RecyclerArrayAdapter.OnItemClickListener {
    @BindView(R.id.recycler)
    EasyRecyclerView recycler;
    private SearchShareAdapter adapter;
    private SearchShareFragmentPresenter presenter;

    @Override
    public void showRefreshing(boolean isShow) {
        recycler.setRefreshing(isShow);
    }

    @Override
    public void finish() {
        getActivity().finish();
    }

    @Override
    public int setContentResource() {
        return R.layout.fragment_search_share;
    }

    @Override
    public void initView() {
        adapter = new SearchShareAdapter(getContext());
        recycler.setRefreshListener(this);
        adapter.setOnItemClickListener(this);

        presenter = new SearchShareFragmentPresenter( this);
        initRecyclerView(recycler, adapter, 1, true);
    }

    @Override
    public void onResume() {
        super.onResume();
        onRefresh();
    }

    @Override
    public void onRefresh() {
        LogUtils.e(TAG,"onRefresh");
        String query = ((ISearchInfo) getActivity()).getQueryStr();
        if (query == null) {
            recycler.showEmpty();
            return;
        }
        adapter.clear();
        presenter.queryFromShareSongList(query);
    }

    @Override
    public void onItemClick(int position) {
        presenter.enterSongActivity(adapter.getItem(position));
    }

    @Override
    public void setShareSongList(List<ShareSong> list) {
        adapter.addAll(list);
    }
}
