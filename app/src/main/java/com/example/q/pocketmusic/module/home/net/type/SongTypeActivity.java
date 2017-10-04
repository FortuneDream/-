package com.example.q.pocketmusic.module.home.net.type;

import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.Toolbar;
import android.widget.FrameLayout;
import android.widget.ImageView;

import com.example.q.pocketmusic.R;
import com.example.q.pocketmusic.module.common.BaseActivity;
import com.example.q.pocketmusic.util.InstrumentFlagUtil;
import com.example.q.pocketmusic.view.widget.view.TopTabView;

import butterknife.BindView;
import butterknife.OnClick;

public class SongTypeActivity extends BaseActivity<SongTypeActivityPresenter.IView, SongTypeActivityPresenter>
        implements SongTypeActivityPresenter.IView, TopTabView.TopTabListener {

    @BindView(R.id.top_iv)
    ImageView topIv;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.collapsing_toolbar_layout)
    CollapsingToolbarLayout collapsingToolbarLayout;
    @BindView(R.id.app_bar)
    AppBarLayout appBar;
    @BindView(R.id.study_fab)
    FloatingActionButton studyFab;
    //500*300
    public Integer typeId;
    public final static String PARAM_POSITION = "position";
    @BindView(R.id.type_tab_view)
    TopTabView typeTabView;
    @BindView(R.id.type_content)
    FrameLayout typeContent;


    @Override
    public int setContentResource() {
        return R.layout.activity_type_song;
    }

    @Override
    public void initView() {
        //初始化
        //获取乐器类型
        typeId = getIntent().getIntExtra(PARAM_POSITION, 0);
        //设置toolbar
        toolbar.setTitle(InstrumentFlagUtil.getTypeName(typeId));
        toolbar.setTitleTextColor(ContextCompat.getColor(context, R.color.colorTitle));
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        //只能通过这样才可以设置标题的颜色
        collapsingToolbarLayout.setCollapsedTitleTextColor(getResources().getColor(R.color.colorTitle));
        collapsingToolbarLayout.setExpandedTitleColor(getResources().getColor(R.color.colorTranslate));
        collapsingToolbarLayout.setTitle(InstrumentFlagUtil.getTypeName(typeId));

        //设置顶部图片
        topIv.setBackgroundResource(InstrumentFlagUtil.getTopDrawableResource(typeId));

        //初始化Fragemnt
        presenter.setFragmentManager(getSupportFragmentManager());
        presenter.initFragment(typeId);

        //top_view
        typeTabView.setListener(this);
        typeTabView.setCheck(0);
        presenter.clickHotList();
    }


    @Override
    protected SongTypeActivityPresenter createPresenter() {
        return new SongTypeActivityPresenter(this);
    }

    @OnClick(R.id.study_fab)
    public void onViewClicked() {
        presenter.enterStudyActivity(typeId);
    }

    @Override
    public void onSelectHotList() {
        typeTabView.setCheck(0);
    }

    @Override
    public void onSelectCommunity() {
        typeTabView.setCheck(1);
    }


    @Override
    public void setTopTabCheck(int position) {
        if (position == 0) {
            presenter.clickHotList();
        } else {
            presenter.clickCommunity();
        }
    }
}
