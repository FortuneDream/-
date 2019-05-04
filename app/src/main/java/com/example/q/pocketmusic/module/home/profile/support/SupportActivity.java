package com.example.q.pocketmusic.module.home.profile.support;

import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.widget.Toolbar;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.example.q.pocketmusic.R;
import com.example.q.pocketmusic.data.bean.MoneySupport;
import com.example.q.pocketmusic.module.common.AuthActivity;
import com.google.android.material.appbar.AppBarLayout;
import com.jude.easyrecyclerview.EasyRecyclerView;
import com.jude.easyrecyclerview.adapter.RecyclerArrayAdapter;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class SupportActivity extends AuthActivity<SupportPresenter.IView, SupportPresenter>
        implements SupportPresenter.IView, RecyclerArrayAdapter.OnMoreListener, SwipeRefreshLayout.OnRefreshListener {
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.app_bar)
    AppBarLayout appBar;
    @BindView(R.id.alipay_tv)
    TextView alipayTv;
    @BindView(R.id.weixin_tv)
    TextView weixinTv;
    @BindView(R.id.recycler)
    EasyRecyclerView recycler;
    @BindView(R.id.content_ll)
    LinearLayout contentLl;
    private MoneyAdapter mMoneyAdapter;

    @Override
    protected SupportPresenter createPresenter() {
        return new SupportPresenter(this);
    }


    @Override
    public int setContentResource() {
        return R.layout.activity_support;
    }

    @Override
    public void initUserView() {
        mMoneyAdapter = new MoneyAdapter(this);
        initToolbar(toolbar, "支持开发者");
        initRecyclerView(recycler, mMoneyAdapter, 1);
        recycler.setRefreshListener(this);
        mMoneyAdapter.setMore(R.layout.view_more, this);
        presenter.getSupportMoneyList(true);
    }

    @Override
    public void setMoneyList(boolean isRefreshing, List<MoneySupport> list) {
        if (isRefreshing) {
            mMoneyAdapter.clear();
        }
        mMoneyAdapter.addAll(list);
    }


    @Override
    public void onMoreShow() {
        presenter.getSupportMoneyList(false);
    }

    @Override
    public void onMoreClick() {

    }

    @Override
    public void onRefresh() {
        presenter.getSupportMoneyList(true);
    }


    @OnClick({R.id.alipay_tv, R.id.weixin_tv})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.alipay_tv:
                presenter.enterDimensionActivity(IPayType.ALIPAY);
                break;
            case R.id.weixin_tv:
                presenter.enterDimensionActivity(IPayType.WEIXIN);
                break;
        }
    }
}
