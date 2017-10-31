package com.example.q.pocketmusic.module.home.profile.gift;

import android.support.design.widget.AppBarLayout;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.Toolbar;
import android.widget.ImageView;

import com.example.q.pocketmusic.R;
import com.example.q.pocketmusic.model.bean.bmob.Gift;
import com.example.q.pocketmusic.module.common.AuthActivity;
import com.jude.easyrecyclerview.EasyRecyclerView;
import com.jude.easyrecyclerview.adapter.RecyclerArrayAdapter;

import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

//礼包界面
public class GiftActivity extends AuthActivity<GiftPresenter.IView, GiftPresenter>
        implements GiftPresenter.IView, SwipeRefreshLayout.OnRefreshListener, RecyclerArrayAdapter.OnMoreListener {
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.app_bar)
    AppBarLayout appBar;
    @BindView(R.id.recycler)
    EasyRecyclerView recycler;
    @BindView(R.id.received_coin_iv)
    ImageView receivedCoinIv;
    private GiftAdapter adapter;

    @Override
    protected GiftPresenter createPresenter() {
        return new GiftPresenter(this);
    }


    @Override
    public int setContentResource() {
        return R.layout.activity_gift;
    }

    @Override
    public void initUserView() {
        adapter = new GiftAdapter(getCurrentContext());
        initToolbar(toolbar, "礼包中心");
        initRecyclerView(recycler, adapter, 1);
        recycler.setRefreshListener(this);
        adapter.setMore(R.layout.view_more, this);
        onRefresh();
    }


    @Override
    public void onRefresh() {
        presenter.getGiftList(true);
    }

    @Override
    public void setGiftList(List<Gift> list, boolean isRefreshing) {
        if (isRefreshing) {
            adapter.clear();
        }
        adapter.addAll(list);
    }

    @Override
    public void onMoreShow() {
        presenter.getGiftList(false);
    }

    @Override
    public void onMoreClick() {

    }



    @OnClick(R.id.received_coin_iv)
    public void onViewClicked() {
        presenter.receivedAllCoin(adapter.getAllData());
    }
}
