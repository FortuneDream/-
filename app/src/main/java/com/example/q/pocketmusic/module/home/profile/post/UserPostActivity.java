package com.example.q.pocketmusic.module.home.profile.post;

import androidx.appcompat.widget.Toolbar;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.example.q.pocketmusic.R;
import com.example.q.pocketmusic.data.bean.ask.AskSongPost;
import com.example.q.pocketmusic.module.common.AuthActivity;
import com.google.android.material.appbar.AppBarLayout;
import com.jude.easyrecyclerview.EasyRecyclerView;
import com.jude.easyrecyclerview.adapter.RecyclerArrayAdapter;

import java.util.List;

import butterknife.BindView;

public class UserPostActivity extends AuthActivity<UserPostPresenter.IView, UserPostPresenter>
        implements UserPostPresenter.IView, SwipeRefreshLayout.OnRefreshListener, RecyclerArrayAdapter.OnItemClickListener,
        RecyclerArrayAdapter.OnMoreListener {
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
        adapter.setMore(R.layout.view_more, this);
        adapter.setOnItemClickListener(this);
        initToolbar(toolbar, "我的求谱");
        initRecyclerView(recycler, adapter, 1);
        onRefresh();
    }


    @Override
    public void onRefresh() {
        presenter.getUserPostList(true);
    }

    @Override
    public void onItemClick(int position) {
        presenter.enterPostInfo(adapter.getItem(position));
    }


    @Override
    public void setUserPostList(boolean isRefreshing, List<AskSongPost> list) {
        if (isRefreshing) {
            adapter.clear();
        }
        adapter.addAll(list);
    }


    @Override
    protected UserPostPresenter createPresenter() {
        return new UserPostPresenter(this);
    }

    @Override
    public void onMoreShow() {
        presenter.getUserPostList(false);
    }

    @Override
    public void onMoreClick() {

    }
}
