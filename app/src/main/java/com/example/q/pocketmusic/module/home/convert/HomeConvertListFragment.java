package com.example.q.pocketmusic.module.home.convert;


import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.q.pocketmusic.R;
import com.example.q.pocketmusic.model.bean.convert.ConvertPost;
import com.example.q.pocketmusic.module.common.BaseFragment;
import com.jude.easyrecyclerview.EasyRecyclerView;
import com.jude.easyrecyclerview.adapter.RecyclerArrayAdapter;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;


public class HomeConvertListFragment extends BaseFragment<HomeConvertListFragmentPresenter.IView, HomeConvertListFragmentPresenter>
        implements HomeConvertListFragmentPresenter.IView, RecyclerArrayAdapter.OnItemClickListener, SwipeRefreshLayout.OnRefreshListener
        , RecyclerArrayAdapter.OnMoreListener {

    @BindView(R.id.publish_convert_iv)
    ImageView publishConvertIv;
    @BindView(R.id.title_bar)
    TextView titleBar;
    @BindView(R.id.search_iv)
    ImageView searchIv;
    @BindView(R.id.convert_list_recycler)
    EasyRecyclerView convertListRecycler;
    Unbinder unbinder;
    private HomeConvertListAdapter adapter;

    @Override
    public int setContentResource() {
        return R.layout.fragment_home_convert_list;
    }

    @Override
    public void initView() {
        adapter = new HomeConvertListAdapter(getCurrentContext());
        initRecyclerView(convertListRecycler, adapter);
        adapter.setOnItemClickListener(this);
        convertListRecycler.setRefreshListener(this);
        adapter.setMore(R.layout.item_home_convert_list, this);
        presenter.setPage(0);
        presenter.getConvertPostList(true);
    }

    @Override
    protected HomeConvertListFragmentPresenter createPresenter() {
        return new HomeConvertListFragmentPresenter(this);
    }


    @Override
    public void onItemClick(int position) {
        presenter.enterConvertComment(adapter.getItem(position));
    }

    @Override
    public void onRefresh() {
        presenter.getConvertPostList(true);
    }

    @Override
    public void onMoreShow() {
        presenter.getMoreConvertList();
    }

    @Override
    public void onMoreClick() {

    }

    @Override
    public void setConvertList(boolean isRefreshing, List<ConvertPost> list) {
        if (isRefreshing) {
            adapter.clear();
        }
        adapter.addAll(list);
    }

    @OnClick({R.id.publish_convert_iv, R.id.search_iv})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.publish_convert_iv:
                presenter.enterPublishConvertActivity();
                break;
            case R.id.search_iv:
                presenter.enterSearchMainActivity();
                break;
        }
    }
}