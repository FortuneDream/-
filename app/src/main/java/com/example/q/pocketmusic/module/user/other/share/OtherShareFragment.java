package com.example.q.pocketmusic.module.user.other.share;

import android.support.v4.widget.SwipeRefreshLayout;

import com.example.q.pocketmusic.R;
import com.example.q.pocketmusic.model.bean.MyUser;
import com.example.q.pocketmusic.model.bean.share.ShareSong;
import com.example.q.pocketmusic.module.common.AuthFragment;
import com.example.q.pocketmusic.module.user.other.OtherProfileActivity;
import com.jude.easyrecyclerview.EasyRecyclerView;
import com.jude.easyrecyclerview.adapter.RecyclerArrayAdapter;

import java.util.List;

import butterknife.BindView;

/**
 * Created by 鹏君 on 2017/7/24.
 * （￣m￣）
 */

public class OtherShareFragment extends AuthFragment<OtherSharePresenter.IView, OtherSharePresenter>
        implements OtherSharePresenter.IView, RecyclerArrayAdapter.OnItemClickListener, SwipeRefreshLayout.OnRefreshListener,RecyclerArrayAdapter.OnMoreListener {
    @BindView(R.id.share_recycler)
    EasyRecyclerView shareRecycler;
    private OtherShareAdapter adapter;
    private MyUser other;

    @Override
    public int setContentResource() {
        return R.layout.fragment_other_share;
    }

    @Override
    public void initView() {
        adapter = new OtherShareAdapter(getCurrentContext());
        initRecyclerView(shareRecycler, adapter, 1);
        other = ((OtherProfileActivity) getActivity()).otherUser;
        adapter.setOnItemClickListener(this);
        adapter.setMore(R.layout.view_more,this);
        shareRecycler.setRefreshListener(this);
        onRefresh();
    }


    @Override
    protected OtherSharePresenter createPresenter() {
        return new OtherSharePresenter(this);
    }

    @Override
    public void setOtherShareList(List<ShareSong> list) {
        adapter.addAll(list);
    }

    @Override
    public void setOtherShareListWithRefreshing(List<ShareSong> list) {
        adapter.clear();
        adapter.addAll(list);
    }

    @Override
    public void onItemClick(int position) {
        presenter.enterSongActivity(adapter.getItem(position));
    }

    @Override
    public void onRefresh() {
        presenter.setPage(0);
        presenter.getOtherCollectionList(other, true);
    }

    @Override
    public void onMoreShow() {
        presenter.getMoreOtherCollectionList(other);
    }

    @Override
    public void onMoreClick() {

    }
}
