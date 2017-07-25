package com.example.q.pocketmusic.module.home.seek.ask;

import android.content.Intent;
import android.support.v4.widget.SwipeRefreshLayout;

import com.example.q.pocketmusic.R;
import com.example.q.pocketmusic.callback.AbsOnClickItemHeadListener;
import com.example.q.pocketmusic.config.Constant;
import com.example.q.pocketmusic.model.bean.ask.AskSongPost;
import com.example.q.pocketmusic.module.common.BaseFragment;
import com.example.q.pocketmusic.module.home.seek.publish.AskSongActivity;
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
        presenter.setmPage(0);
        presenter.getPostList(false);

        adapter.setAbsOnClickItemHeadListener(new AbsOnClickItemHeadListener() {
            @Override
            public void onClickItem(int position) {
                presenter.enterCommentActivity(adapter.getItem(position));
            }
        });
    }


    @Override
    public void setPostList(List<AskSongPost> list) {
        adapter.addAll(list);
    }

    @Override
    public void setPostListWithRefreshing(List<AskSongPost> list) {
        adapter.clear();
        adapter.addAll(list);
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == AskSongActivity.REQUEST_ASK && resultCode == Constant.SUCCESS) {
            onRefresh();
        }
    }

    @Override
    public void onRefresh() {
        presenter.setmPage(0);
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

    @Override
    protected AskListFragmentPresenter createPresenter() {
        return new AskListFragmentPresenter(this);
    }
}
