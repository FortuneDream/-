package com.example.q.pocketmusic.module.home.profile.contribution;

import android.support.design.widget.AppBarLayout;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.Toolbar;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.q.pocketmusic.R;
import com.example.q.pocketmusic.model.bean.MyUser;
import com.example.q.pocketmusic.module.common.AuthActivity;
import com.example.q.pocketmusic.util.DisplayStrategy;
import com.jude.easyrecyclerview.EasyRecyclerView;

import java.util.List;

import butterknife.BindView;

public class ContributionActivity extends AuthActivity<ContributionPresenter.IView, ContributionPresenter>
        implements SwipeRefreshLayout.OnRefreshListener, ContributionPresenter.IView {
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.app_bar)
    AppBarLayout appBar;
    @BindView(R.id.recycler)
    EasyRecyclerView recycler;
    @BindView(R.id.top_iv)
    ImageView topIv;
    @BindView(R.id.nick_name_tv)
    TextView nickNameTv;
    @BindView(R.id.contribution_tv)
    TextView contributionTv;
    private ContributionAdapter adapter;

    @Override
    public int setContentResource() {
        return R.layout.activity_contribution;
    }

    @Override
    public void initUserView() {
        //监听
        adapter = new ContributionAdapter(this);
        recycler.setRefreshListener(this);
        //初始化
        new DisplayStrategy().displayCircle(this, user.getHeadImg(), topIv);
        nickNameTv.setText(user.getNickName());
        contributionTv.setText("硬币：" + user.getContribution() + "枚");
        initToolbar(toolbar, "硬币榜");
        initRecyclerView(recycler, adapter);
        presenter.init();
    }


    @Override
    public void onRefresh() {
        presenter.init();
    }

    @Override
    public void setListResult(List<MyUser> list) {
        adapter.clear();
        adapter.addAll(list);
    }


    @Override
    protected ContributionPresenter createPresenter() {
        return new ContributionPresenter(this);
    }
}
