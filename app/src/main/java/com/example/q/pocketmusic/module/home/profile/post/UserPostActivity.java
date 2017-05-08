package com.example.q.pocketmusic.module.home.profile.post;

import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.Toolbar;

import com.example.q.pocketmusic.R;
import com.example.q.pocketmusic.model.bean.ask.AskSongComment;
import com.example.q.pocketmusic.model.bean.ask.AskSongPost;
import com.example.q.pocketmusic.module.common.AuthActivity;
import com.jude.easyrecyclerview.EasyRecyclerView;
import com.jude.easyrecyclerview.adapter.RecyclerArrayAdapter;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class UserPostActivity extends AuthActivity<UserPostPresenter.IView,UserPostPresenter>
        implements UserPostPresenter.IView, SwipeRefreshLayout.OnRefreshListener, RecyclerArrayAdapter.OnItemClickListener {
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.app_bar)
    AppBarLayout appBar;
    @BindView(R.id.recycler)
    EasyRecyclerView recycler;
    private UserPostAdapter adapter;

    @Override
    public int setContentResource() {
        return R.layout.activity_user_post;
    }

    @Override
    public void initUserView() {
        adapter = new UserPostAdapter(this);
        recycler.setRefreshListener(this);
        adapter.setOnItemClickListener(this);

        presenter.setUser(user);
        initToolbar(toolbar, "我的求谱");
        initRecyclerView(recycler, adapter, 1, true);
        onRefresh();
    }


    @Override
    public void onRefresh() {
        adapter.clear();
        presenter.getUserPostList();
    }

    @Override
    public void onItemClick(int position) {
        presenter.enterPostInfo(adapter.getItem(position));
    }

    @Override
    public void showRefreshing(boolean isShow) {
        recycler.setRefreshing(isShow);
    }

    @Override
    public void setInitPostList(List<AskSongPost> list) {
        adapter.addAll(list);
    }

    @Override
    protected UserPostPresenter createPresenter() {
        return new UserPostPresenter(this);
    }
}
