package com.example.q.pocketmusic.module.home.profile.convert.post;


import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.q.pocketmusic.R;
import com.example.q.pocketmusic.model.bean.convert.ConvertComment;
import com.example.q.pocketmusic.module.common.BaseFragment;
import com.example.q.pocketmusic.util.common.LogUtils;
import com.jude.easyrecyclerview.EasyRecyclerView;
import com.jude.easyrecyclerview.adapter.RecyclerArrayAdapter;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class ProfileConvertPostFragment extends BaseFragment<ProfileConvertPostFragmentPresenter.IView, ProfileConvertPostFragmentPresenter>
        implements ProfileConvertPostFragmentPresenter.IView, RecyclerArrayAdapter.OnItemClickListener, SwipeRefreshLayout.OnRefreshListener,
        RecyclerArrayAdapter.OnMoreListener{
    @BindView(R.id.post_list_recycler)
    EasyRecyclerView postListRecycler;
    private ProfileConvertPostAdapter adapter;

    @Override
    public int setContentResource() {
        return R.layout.fragment_profile_convert_post;
    }

    @Override
    public void initView() {
        adapter = new ProfileConvertPostAdapter(getCurrentContext());
        initRecyclerView(postListRecycler, adapter, 1);
        adapter.setOnItemClickListener(this);
        adapter.setMore(R.layout.view_more,this);
        postListRecycler.setRefreshListener(this);
        LogUtils.e(TAG, String.valueOf(1234564));
        presenter.getInitCommentList(true);
    }

    @Override
    protected ProfileConvertPostFragmentPresenter createPresenter() {
        return new ProfileConvertPostFragmentPresenter(this);
    }

    @Override
    public void onItemClick(int position) {
            presenter.enterConvertPostActivity(adapter.getItem(position));
    }

    @Override
    public void onRefresh() {
        presenter.getInitCommentList(true);
    }

    @Override
    public void setList(List<ConvertComment> list, boolean isRefreshing) {
        if (isRefreshing) {
            adapter.clear();
        }
        adapter.addAll(list);
    }

    @Override
    public void onMoreShow() {
        presenter.getMoreCommentList(false);
    }

    @Override
    public void onMoreClick() {

    }
}