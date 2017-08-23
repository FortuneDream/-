package com.example.q.pocketmusic.module.home.net.type;

import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.Toolbar;
import android.widget.ImageView;

import com.example.q.pocketmusic.R;
import com.example.q.pocketmusic.model.bean.Song;
import com.example.q.pocketmusic.module.common.BaseActivity;
import com.example.q.pocketmusic.util.InstrumentFlagUtil;
import com.jude.easyrecyclerview.EasyRecyclerView;
import com.jude.easyrecyclerview.adapter.RecyclerArrayAdapter;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class SongTypeActivity extends BaseActivity<SongTypeActivityPresenter.IView, SongTypeActivityPresenter>
        implements SongTypeActivityPresenter.IView, RecyclerArrayAdapter.OnMoreListener, RecyclerArrayAdapter.OnItemClickListener, SwipeRefreshLayout.OnRefreshListener {

    @BindView(R.id.top_iv)
    ImageView topIv;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.collapsing_toolbar_layout)
    CollapsingToolbarLayout collapsingToolbarLayout;
    @BindView(R.id.app_bar)
    AppBarLayout appBar;
    @BindView(R.id.recycler)
    EasyRecyclerView recycler;
    @BindView(R.id.study_fab)
    FloatingActionButton studyFab;
    //500*300

    private SongTypeActivityAdapter adapter;
    private Integer typeId;
    public final static String PARAM_POSITION = "position";


    @Override
    public int setContentResource() {
        return R.layout.activity_type_song;
    }

    @Override
    public void initView() {
        //监听
        adapter = new SongTypeActivityAdapter(this);
        adapter.setOnItemClickListener(this);
        adapter.setMore(R.layout.view_more, this);
        recycler.setRefreshListener(this);

        //初始化
        //获取乐器类型
        typeId = getIntent().getIntExtra(PARAM_POSITION, 0);
        initRecyclerView(recycler, adapter, 1);
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
        presenter.setPage(1);
        presenter.getList(typeId, false);
    }


    @Override
    public void setList(List<Song> songs) {
        adapter.addAll(songs);
    }

    @Override
    public void setListWithRefreshing(List<Song> songs) {
        adapter.clear();
        adapter.addAll(songs);
    }


    @Override
    public void onMoreShow() {
        presenter.setPage(presenter.getmPage() + 1);
        presenter.getList(typeId, false);
    }

    @Override
    public void onMoreClick() {

    }

    @Override
    public void onItemClick(int position) {
        presenter.enterSongActivity(adapter.getItem(position));
    }

    @Override
    public void onRefresh() {
        presenter.setPage(1);
        presenter.getList(typeId, true);
    }

    @Override
    protected SongTypeActivityPresenter createPresenter() {
        return new SongTypeActivityPresenter(this);
    }

    @OnClick(R.id.study_fab)
    public void onViewClicked() {
        presenter.enterStudyActivity(typeId);
    }
}
