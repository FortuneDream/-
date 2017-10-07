package com.example.q.pocketmusic.module.home.convert;


import android.content.Intent;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.q.pocketmusic.R;
import com.example.q.pocketmusic.callback.AbsOnClickItemHeadListener;
import com.example.q.pocketmusic.model.bean.convert.ConvertPost;
import com.example.q.pocketmusic.module.common.BaseFragment;
import com.example.q.pocketmusic.module.home.convert.publish.PublishConvertActivity;
import com.jude.easyrecyclerview.EasyRecyclerView;
import com.jude.easyrecyclerview.adapter.RecyclerArrayAdapter;

import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import butterknife.Unbinder;


public class HomeConvertListFragment extends BaseFragment<HomeConvertListFragmentPresenter.IView, HomeConvertListFragmentPresenter>
        implements HomeConvertListFragmentPresenter.IView, SwipeRefreshLayout.OnRefreshListener
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
        initRecyclerView(convertListRecycler, adapter, 1);

        adapter.setOnClickItemHeadListener(new AbsOnClickItemHeadListener() {
            @Override
            public void onClickItem(int position) {
                presenter.enterConvertComment(adapter.getItem(position));
            }
        });
        convertListRecycler.setRefreshListener(this);
        adapter.setMore(R.layout.view_more, this);
        presenter.setPage(0);
        presenter.getConvertPostList(true);
    }

    @Override
    protected HomeConvertListFragmentPresenter createPresenter() {
        return new HomeConvertListFragmentPresenter(this);
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

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == PublishConvertActivity.RESULT_OK) {
            onRefresh();
        }
    }
}