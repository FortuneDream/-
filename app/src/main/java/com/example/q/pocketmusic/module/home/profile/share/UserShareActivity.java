package com.example.q.pocketmusic.module.home.profile.share;

import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.Toolbar;

import com.example.q.pocketmusic.R;
import com.example.q.pocketmusic.model.bean.share.ShareSong;
import com.example.q.pocketmusic.module.common.AuthActivity;
import com.jude.easyrecyclerview.EasyRecyclerView;
import com.jude.easyrecyclerview.adapter.RecyclerArrayAdapter;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class UserShareActivity extends AuthActivity<UserSharePresenter.IView, UserSharePresenter>
        implements UserSharePresenter.IView, SwipeRefreshLayout.OnRefreshListener, RecyclerArrayAdapter.OnItemClickListener {
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.app_bar)
    AppBarLayout appBar;
    @BindView(R.id.recycler)
    EasyRecyclerView recycler;
    private UserShareAdapter adapter;

    @Override
    protected UserSharePresenter createPresenter() {
        return new UserSharePresenter(this);
    }

    @Override
    public int setContentResource() {
        return R.layout.activity_user_share;
    }

    @Override
    public void initUserView() {
        presenter.setUser(user);
        adapter = new UserShareAdapter(getCurrentContext());
        recycler.setRefreshListener(this);
        adapter.setOnItemClickListener(this);
        initToolbar(toolbar, "我的分享");
        initRecyclerView(recycler, adapter, 1);
        onRefresh();
    }

    @Override
    public void showRefreshing(boolean isShow) {
        recycler.setRefreshing(isShow);
    }

    @Override
    public void onRefresh() {
        adapter.clear();
        presenter.getInitList();
    }

    @Override
    public void setInitList(List<ShareSong> list) {
        adapter.addAll(list);
    }

    @Override
    public void onItemClick(int position) {
            presenter.enterSongActivity(adapter.getItem(position));
    }
}
