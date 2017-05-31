package com.example.q.pocketmusic.module.search.net;

import com.example.q.pocketmusic.R;
import com.example.q.pocketmusic.model.bean.Song;
import com.example.q.pocketmusic.module.common.BaseFragment;
import com.example.q.pocketmusic.module.search.ISearchActivity;
import com.example.q.pocketmusic.module.search.ISearchFragment;
import com.jude.easyrecyclerview.EasyRecyclerView;
import com.jude.easyrecyclerview.adapter.RecyclerArrayAdapter;

import java.util.List;

import butterknife.BindView;

/**
 * Created by 81256 on 2017/4/14.
 */

public class SearchNetFragment extends BaseFragment<SearchNetFragmentPresenter.IView, SearchNetFragmentPresenter>
        implements SearchNetFragmentPresenter.IView, RecyclerArrayAdapter.OnItemClickListener, ISearchFragment
        , RecyclerArrayAdapter.OnMoreListener {
    @BindView(R.id.recycler)
    EasyRecyclerView recycler;

    private SearchNetAdapter adapter;
    private String query;

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
        initRecyclerView(recycler, adapter, 1);
        adapter.setMore(R.layout.view_more, this);
        adapter.setOnItemClickListener(this);
    }

    @Override
    public void getInitSearchList() {
        query = ((ISearchActivity) getActivity()).getQueryStr();
        if (query == null) {
            return;
        }
        adapter.clear();
        presenter.setPage(0);
        presenter.getList(query);
    }

    @Override
    public void setList(List<Song> lists) {
        adapter.addAll(lists);
    }

    @Override
    public void onItemClick(int position) {
        presenter.enterSongActivity(adapter.getItem(position));
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
    protected SearchNetFragmentPresenter createPresenter() {
        return new SearchNetFragmentPresenter(this);
    }
}
