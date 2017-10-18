package com.example.q.pocketmusic.module.home.search.share;

import com.example.q.pocketmusic.R;
import com.example.q.pocketmusic.model.bean.share.ShareSong;
import com.example.q.pocketmusic.module.common.BaseFragment;
import com.example.q.pocketmusic.module.home.search.ISearchActivity;
import com.example.q.pocketmusic.module.home.search.ISearchFragment;
import com.jude.easyrecyclerview.EasyRecyclerView;
import com.jude.easyrecyclerview.adapter.RecyclerArrayAdapter;

import java.util.List;

import butterknife.BindView;

/**
 * Created by 鹏君 on 2017/4/14.
 */

public class SearchShareFragment extends BaseFragment<SearchShareFragmentPresenter.IView, SearchShareFragmentPresenter>
        implements SearchShareFragmentPresenter.IView, RecyclerArrayAdapter.OnItemClickListener,ISearchFragment {
    @BindView(R.id.recycler)
    EasyRecyclerView recycler;
    private SearchShareAdapter adapter;

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
        adapter.setOnItemClickListener(this);
        initRecyclerView(recycler, adapter, 1);
    }

    @Override
    public void getInitSearchList() {
        String query = ((ISearchActivity) getActivity()).getQueryStr();
        if (query == null) {
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

    @Override
    protected SearchShareFragmentPresenter createPresenter() {
        return new SearchShareFragmentPresenter(this);
    }
}
