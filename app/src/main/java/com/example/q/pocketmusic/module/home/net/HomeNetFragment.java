package com.example.q.pocketmusic.module.home.net;

import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.example.q.pocketmusic.R;
import com.example.q.pocketmusic.model.bean.Song;
import com.example.q.pocketmusic.model.flag.BannerBean;
import com.example.q.pocketmusic.model.flag.ContentLL;
import com.example.q.pocketmusic.module.common.BaseFragment;
import com.jude.easyrecyclerview.EasyRecyclerView;
import com.jude.easyrecyclerview.adapter.RecyclerArrayAdapter;

import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * Created by 鹏君 on 2016/11/17.
 */
//网络Fragment
public class HomeNetFragment extends BaseFragment<HomeNetFragmentPresenter.IView, HomeNetFragmentPresenter>
        implements HomeNetFragmentPresenter.IView, SwipeRefreshLayout.OnRefreshListener, NetFragmentAdapter.OnOptionListener, RecyclerArrayAdapter.OnMoreListener {
    @BindView(R.id.email_iv)
    ImageView emailIv;
    @BindView(R.id.search_rl)
    LinearLayout searchRl;
    @BindView(R.id.recycler)
    EasyRecyclerView recycler;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    Unbinder unbinder;
    private NetFragmentAdapter adapter;

    @Override
    public int setContentResource() {
        return R.layout.fragment_home_net;
    }


    @Override
    public void initView() {
        //监听
        adapter = new NetFragmentAdapter(getContext());
        initRecyclerView(recycler, adapter);
        recycler.addItemDecoration(new NetFragmentDecoration(getCurrentContext()));
        recycler.setRefreshListener(this);
        adapter.setMore(R.layout.view_more, this);
        adapter.setListener(this);
        //初始化
        recycler.setEmptyView(R.layout.view_not_found);
        initList();
        presenter.setPage(1);
        presenter.getList(false);
    }


    //加载更多
    @Override
    public void onMoreShow() {
        presenter.setPage(presenter.getmPage() + 1);
        presenter.getList(false);
    }

    @Override
    public void onMoreClick() {

    }


    private void initList() {
        adapter.add(new BannerBean());//轮播
        adapter.add(new ContentLL());//乐器类型
    }


    @Override
    public void setList(List<Song> list) {
        adapter.addAll(list);
    }

    @Override
    public void setInitListWithRefreshing(List<Song> songs) {
        adapter.clear();
        initList();
        adapter.addAll(songs);
    }


    @Override
    public void onRefresh() {
        presenter.setPage(1);
        presenter.getList(true);
    }


    @Override
    public void finish() {
        getActivity().finish();
    }

    @Override
    public void onSelectType(int position) {
        presenter.enterTypeActivity(position);
    }

    @Override
    public void onSelectRecommendSong(int position) {
        Song song = (Song) adapter.getItem(position);
        presenter.enterSongActivity(song);
    }


    @Override
    public void onSelectRollView(int picPosition) {
        presenter.enterBannerActivity(picPosition);
    }


    @Override
    protected HomeNetFragmentPresenter createPresenter() {
        return new HomeNetFragmentPresenter(this);
    }


    @OnClick({R.id.email_iv, R.id.search_rl})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.email_iv:
                presenter.enterUserNotifyActivity();
                break;
            case R.id.search_rl:
                presenter.enterSearchMainActivity();
                break;
        }
    }

}
