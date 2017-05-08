package com.example.q.pocketmusic.module.search.net;

import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.q.pocketmusic.R;
import com.example.q.pocketmusic.model.bean.Song;
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

public class SearchNetFragment extends BaseFragment<SearchNetFragmentPresenter.IView, SearchNetFragmentPresenter>
        implements SearchNetFragmentPresenter.IView, RecyclerArrayAdapter.OnItemClickListener
        , RecyclerArrayAdapter.OnMoreListener, android.support.v4.widget.SwipeRefreshLayout.OnRefreshListener {
    @BindView(R.id.recycler)
    EasyRecyclerView recycler;

    private SearchNetAdapter adapter;
    private String query;


    @Override
    public void showRefreshing(boolean isShow) {
        recycler.setRefreshing(isShow);
    }

    @Override
    public void finish() {

    }

    @Override
    public int setContentResource() {
        return R.layout.fragment_search_net;
    }

    @Override
    public void initView() {
        adapter = new SearchNetAdapter(getContext());
        adapter.setMore(R.layout.view_more, this);
        adapter.setOnItemClickListener(this);
        recycler.setRefreshListener(this);
        initRecyclerView(recycler, adapter, 1, true);
    }


    @Override
    public void onResume() {
        super.onResume();
        onRefresh();
    }

    @Override
    public void setList(List<Song> lists) {
        adapter.addAll(lists);
    }

    @Override
    public void onItemClick(int position) {
        presenter.enterSongActivity(adapter.getItem(position), adapter.getItem(position).getSearchFrom());
    }

    @Override
    public void onMoreShow() {
        presenter.setPage(presenter.getmPage() + 1);
        presenter.getList(query);
    }

    @Override
    public void onMoreClick() {

    }

    @Override
    public void onRefresh() {
        LogUtils.e(TAG, "onRefresh");
        query = ((ISearchInfo) getActivity()).getQueryStr();
        if (query == null) {
            recycler.showEmpty();
            return;
        }
        adapter.clear();
        presenter.setPage(0);
        presenter.getList(query);
    }


    @Override
    protected SearchNetFragmentPresenter createPresenter() {
        return new SearchNetFragmentPresenter();
    }
}
