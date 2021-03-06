package com.example.q.pocketmusic.module.home.profile.gift;

import android.widget.ImageView;

import androidx.appcompat.widget.Toolbar;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.example.q.pocketmusic.R;
import com.example.q.pocketmusic.data.bean.bmob.Gift;
import com.example.q.pocketmusic.module.common.AuthActivity;
import com.example.q.pocketmusic.util.GuidePopHelper;
import com.google.android.material.appbar.AppBarLayout;
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
        initToolbar(toolbar, getResString(R.string.title_gift));
        initRecyclerView(recycler, adapter, 1);
        recycler.setRefreshListener(this);
        adapter.setMore(R.layout.view_more, this);
        onRefresh();
        GuidePopHelper.showGetCoinPop(receivedCoinIv);
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
