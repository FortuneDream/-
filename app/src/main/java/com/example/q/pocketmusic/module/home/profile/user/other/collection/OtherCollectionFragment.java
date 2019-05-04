package com.example.q.pocketmusic.module.home.profile.user.other.collection;


import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.example.q.pocketmusic.R;
import com.example.q.pocketmusic.data.bean.MyUser;
import com.example.q.pocketmusic.data.bean.collection.CollectionSong;
import com.example.q.pocketmusic.module.common.AuthFragment;
import com.example.q.pocketmusic.module.home.profile.user.other.OtherProfileActivity;
import com.jude.easyrecyclerview.EasyRecyclerView;
import com.jude.easyrecyclerview.adapter.RecyclerArrayAdapter;

import java.util.List;

import butterknife.BindView;

/**
 * Created by 鹏君 on 2017/7/24.
 * （￣m￣）
 */

public class OtherCollectionFragment extends AuthFragment<OtherCollectionPresenter.IView, OtherCollectionPresenter>
        implements OtherCollectionPresenter.IView, RecyclerArrayAdapter.OnItemClickListener, RecyclerArrayAdapter.OnMoreListener, SwipeRefreshLayout.OnRefreshListener {
    @BindView(R.id.collection_recycler)
    EasyRecyclerView collectionRecycler;
    private OtherCollectionAdapter adapter;
    private MyUser other;

    @Override
    public int setContentResource() {
        return R.layout.fragment_other_collection;
    }


    @Override
    public void initView() {
        super.initView();
        adapter = new OtherCollectionAdapter(getCurrentContext());
        initRecyclerView(collectionRecycler, adapter, 1);
        other = ((OtherProfileActivity) getActivity()).otherUser;
        adapter.setOnItemClickListener(this);
        adapter.setMore(R.layout.view_more, this);
        collectionRecycler.setRefreshListener(this);
        onRefresh();
    }

    @Override
    protected OtherCollectionPresenter createPresenter() {
        return new OtherCollectionPresenter(this);
    }


    @Override
    public void setOtherCollectionList(List<CollectionSong> list) {
        adapter.addAll(list);
    }

    @Override
    public void setOtherCollectionListWithRefreshing(List<CollectionSong> list) {
        adapter.clear();
        adapter.addAll(list);
    }

    @Override
    public void onItemClick(int position) {
        presenter.enterSongActivity(adapter.getItem(position));
    }

    @Override
    public void onMoreShow() {
        presenter.getMoreOtherCollectionList(other);
    }

    @Override
    public void onMoreClick() {

    }

    @Override
    public void onRefresh() {
        presenter.setPage(0);
        presenter.getOtherCollectionList(other, true);
    }
}
