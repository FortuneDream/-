package com.example.q.pocketmusic.module.home.ask.list;

import android.content.Intent;
import android.support.v4.widget.SwipeRefreshLayout;

import com.example.q.pocketmusic.R;
import com.example.q.pocketmusic.config.Constant;
import com.example.q.pocketmusic.model.bean.ask.AskSongPost;
import com.example.q.pocketmusic.module.common.BaseFragment;
import com.example.q.pocketmusic.module.home.ask.publish.AskSongActivity;
import com.example.q.pocketmusic.view.widget.net.FloatingDraftButton;
import com.example.q.pocketmusic.view.widget.net.TitleBar;
import com.jude.easyrecyclerview.EasyRecyclerView;
import com.jude.easyrecyclerview.adapter.RecyclerArrayAdapter;

import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by Cloud on 2017/1/26.
 */
public class HomeAskListFragment extends BaseFragment<HomeAskListFragmentPresenter.IView, HomeAskListFragmentPresenter>
        implements HomeAskListFragmentPresenter.IView, SwipeRefreshLayout.OnRefreshListener, HomeAskListAdapter.OnItemClickListener
        , RecyclerArrayAdapter.OnMoreListener {
    @BindView(R.id.title_bar)
    TitleBar titleBar;
    @BindView(R.id.recycler)
    EasyRecyclerView recycler;
    @BindView(R.id.ask_song_fab)
    FloatingDraftButton askSongFab;
    private HomeAskListAdapter adapter;

    @Override
    public int setContentResource() {
        return R.layout.fragment_home_ask_list;
    }


    @Override
    public void initView() {
        adapter = new HomeAskListAdapter(getContext());
        adapter.setListener(this);
        adapter.setMore(R.layout.view_more, this);
        recycler.setRefreshListener(this);
        initRecyclerView(recycler, adapter, 72, false);
        presenter.setmPage(0);
        titleBar.setMyCenterTitle("大家都在找");
        titleBar.setBackgroundColor(getContext().getResources().getColor(R.color.colorPrimary));
        presenter.getPostList();
    }


    @Override
    public void setPostList(List<AskSongPost> list) {
        recycler.setRefreshing(false);
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
        adapter.clear();
        presenter.getPostList();
    }

    @Override
    public void finish() {
        getActivity().finish();
    }

    @Override
    public void onClickHeadIv(int position) {
        presenter.enterOtherProfileActivity(adapter.getItem(position));
    }

    @Override
    public void onClickContent(int position) {
        presenter.enterCommentActivity(adapter.getItem(position));
    }

    @Override
    public void onMoreShow() {
        presenter.getMore();
    }

    @Override
    public void onMoreClick() {

    }

    @Override
    public void showRefreshing(boolean isShow) {
        recycler.setRefreshing(isShow);
    }


    @OnClick(R.id.ask_song_fab)
    public void onClick() {
        presenter.enterAskSongActivity();
    }

    @Override
    protected HomeAskListFragmentPresenter createPresenter() {
        return new HomeAskListFragmentPresenter();
    }
}
