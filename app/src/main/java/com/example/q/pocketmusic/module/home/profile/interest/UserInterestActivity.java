package com.example.q.pocketmusic.module.home.profile.interest;

import android.support.design.widget.AppBarLayout;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.Toolbar;

import com.example.q.pocketmusic.R;
import com.example.q.pocketmusic.callback.AbsOnClickItemHeadListener;
import com.example.q.pocketmusic.data.bean.MyUser;
import com.example.q.pocketmusic.module.common.AuthActivity;
import com.jude.easyrecyclerview.EasyRecyclerView;
import com.jude.easyrecyclerview.adapter.RecyclerArrayAdapter;

import java.util.List;

import butterknife.BindView;

public class UserInterestActivity extends AuthActivity<UserInterestPresenter.IView, UserInterestPresenter>
        implements UserInterestPresenter.IView, SwipeRefreshLayout.OnRefreshListener,
        RecyclerArrayAdapter.OnMoreListener {
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.app_bar)
    AppBarLayout appBar;
    @BindView(R.id.recycler)
    EasyRecyclerView recycler;
    private UserInterestAdapter adapter;

    @Override
    protected UserInterestPresenter createPresenter() {
        return new UserInterestPresenter(this);
    }


    @Override
    public int setContentResource() {
        return R.layout.activity_user_interest;
    }

    @Override
    public void initUserView() {
        adapter = new UserInterestAdapter(this);
        initToolbar(toolbar, getResString(R.string.title_my_interest));
        initRecyclerView(recycler, adapter, 1);
        adapter.setListener(new AbsOnClickItemHeadListener() {
            @Override
            public void onClickItem(int position) {
                presenter.cancelInterest(adapter.getItem(position));//点击取消关注
            }
        });
        recycler.setRefreshListener(this);
        adapter.setMore(R.layout.view_more, this);
        presenter.getList(true);
    }


    @Override
    public void onRefresh() {
        presenter.getList(true);
    }

    @Override
    public void onMoreShow() {
        presenter.getList(false);
    }

    @Override
    public void onMoreClick() {

    }

    @Override
    public void setList(List<MyUser> list, boolean isRefreshing) {
        if (isRefreshing) {
            adapter.clear();
        }
        adapter.addAll(list);
    }
}