package com.example.q.pocketmusic.module.home.profile.contribution;

import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.Toolbar;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.q.pocketmusic.R;
import com.example.q.pocketmusic.callback.AbsOnClickItemHeadListener;
import com.example.q.pocketmusic.config.pic.DisplayStrategy;
import com.example.q.pocketmusic.data.bean.MyUser;
import com.example.q.pocketmusic.module.common.AuthActivity;
import com.example.q.pocketmusic.util.UserUtil;
import com.jude.easyrecyclerview.EasyRecyclerView;

import java.util.List;

import butterknife.BindView;

public class CoinRankActivity extends AuthActivity<CoinRankPresenter.IView, CoinRankPresenter>
        implements SwipeRefreshLayout.OnRefreshListener, CoinRankPresenter.IView {

    @BindView(R.id.top_iv)
    ImageView topIv;
    @BindView(R.id.nick_name_tv)
    TextView nickNameTv;
    @BindView(R.id.my_coin_tv)
    TextView myCoinTv;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.collapsing_toolbar_layout)
    CollapsingToolbarLayout collapsingToolbarLayout;
    @BindView(R.id.app_bar)
    AppBarLayout appBar;
    @BindView(R.id.recycler)
    EasyRecyclerView recycler;
    private CoinRankAdapter adapter;

    @Override
    public int setContentResource() {
        return R.layout.activity_coin_rank;
    }

    @Override
    public void initUserView() {
        //监听
        adapter = new CoinRankAdapter(this);
        recycler.setRefreshListener(this);
        //初始化
        new DisplayStrategy().displayCircle(this, UserUtil.user.getHeadImg(), topIv);
        nickNameTv.setText(UserUtil.user.getNickName());
        myCoinTv.setText("硬币：" + UserUtil.user.getContribution() + "枚");
        initToolbar(toolbar, "硬币榜");
        initRecyclerView(recycler, adapter);
        adapter.setAbsOnClickItemHeadListener(new AbsOnClickItemHeadListener() {
            @Override
            public void onClickItem(int position) {
                //什么都不做
            }
        });
        presenter.getCoinRankList();
    }


    @Override
    public void onRefresh() {
        presenter.getCoinRankList();
    }

    @Override
    public void setCoinRankList(List<MyUser> list) {
        adapter.clear();
        adapter.addAll(list);
    }


    @Override
    protected CoinRankPresenter createPresenter() {
        return new CoinRankPresenter(this);
    }

}
