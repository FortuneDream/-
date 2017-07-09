package com.example.q.pocketmusic.module.home.profile.support;

import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

import com.example.q.pocketmusic.R;
import com.example.q.pocketmusic.model.bean.MoneySupport;
import com.example.q.pocketmusic.module.common.AuthActivity;
import com.jude.easyrecyclerview.EasyRecyclerView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class SupportActivity extends AuthActivity<SupportPresenter.IView, SupportPresenter>
        implements SupportPresenter.IView {
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
        initToolbar(toolbar, "捐赠");
        initRecyclerView(recycler, mMoneyAdapter, 1);
        presenter.getMoneyList();
    }

    @Override
    public void setMoneyList(List<MoneySupport> list) {
        mMoneyAdapter.addAll(list);
    }

    @OnClick({R.id.alipay_tv, R.id.weixin_tv})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.alipay_tv:
                presenter.alipay();
                break;
            case R.id.weixin_tv:
                break;
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }
}
