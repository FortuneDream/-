package com.example.q.pocketmusic.module.home.net.type.community.ask;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;

import com.example.q.pocketmusic.R;
import com.example.q.pocketmusic.callback.AbsOnClickItemHeadListener;
import com.example.q.pocketmusic.config.Constant;
import com.example.q.pocketmusic.model.bean.ask.AskSongPost;
import com.example.q.pocketmusic.module.common.BaseFragment;
import com.example.q.pocketmusic.module.home.net.type.SongTypeActivity;
import com.example.q.pocketmusic.module.home.net.type.community.ask.publish.PublishAskActivity;
import com.jude.easyrecyclerview.EasyRecyclerView;
import com.jude.easyrecyclerview.adapter.RecyclerArrayAdapter;

import java.util.List;

import butterknife.BindView;

/**
 * Created by 鹏君 on 2017/1/26.
 */
public class AskListFragment extends BaseFragment<AskListFragmentPresenter.IView, AskListFragmentPresenter>
        implements AskListFragmentPresenter.IView, SwipeRefreshLayout.OnRefreshListener
        , RecyclerArrayAdapter.OnMoreListener {
    @BindView(R.id.recycler)
    EasyRecyclerView recycler;
    private AskListAdapter adapter;
    private int typeId;


    @Override
    public int setContentResource() {
        return R.layout.fragment_ask_list;
    }


    @Override
    public void initView() {
        adapter = new AskListAdapter(getContext());
        adapter.setMore(R.layout.view_more, this);
        recycler.setRefreshListener(this);
        initRecyclerView(recycler, adapter, 1);
        presenter.setType(typeId);
        presenter.getPostList(true);
        adapter.setAbsOnClickItemHeadListener(new AbsOnClickItemHeadListener() {
            @Override
            public void onClickItem(int position) {
                presenter.enterCommentActivity(adapter.getItem(position));
            }
        });
    }


    @Override
    public void setPostList(boolean isRefreshing, List<AskSongPost> list) {
        if (isRefreshing) {
            adapter.clear();
        }
        adapter.addAll(list);
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PublishAskActivity.REQUEST_ASK && resultCode == Constant.SUCCESS) {
            onRefresh();
        }
    }

    @Override
    public void onRefresh() {
        presenter.getPostList(true);
    }

    @Override
    public void finish() {
        getActivity().finish();
    }

    @Override
    public void onMoreShow() {
        presenter.getMore();
    }

    @Override
    public void onMoreClick() {

    }


    public static AskListFragment getInstance(int typeId) {
        AskListFragment fragment = new AskListFragment();
        Bundle args = new Bundle();
        args.putInt(SongTypeActivity.PARAM_POSITION, typeId);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.typeId = getArguments().getInt(SongTypeActivity.PARAM_POSITION);
    }

    @Override
    protected AskListFragmentPresenter createPresenter() {
        return new AskListFragmentPresenter(this);
    }
}
