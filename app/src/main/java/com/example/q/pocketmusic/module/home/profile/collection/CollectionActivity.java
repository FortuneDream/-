package com.example.q.pocketmusic.module.home.profile.collection;

import android.support.design.widget.AppBarLayout;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import com.example.q.pocketmusic.R;
import com.example.q.pocketmusic.model.bean.collection.CollectionSong;
import com.example.q.pocketmusic.module.common.AuthActivity;
import com.github.rubensousa.bottomsheetbuilder.BottomSheetBuilder;
import com.github.rubensousa.bottomsheetbuilder.BottomSheetMenuDialog;
import com.github.rubensousa.bottomsheetbuilder.adapter.BottomSheetItemClickListener;
import com.jude.easyrecyclerview.EasyRecyclerView;
import com.jude.easyrecyclerview.adapter.RecyclerArrayAdapter;

import java.util.List;

import butterknife.BindView;

/**
 * Created by Cloud on 2016/11/14.
 */

public class CollectionActivity extends AuthActivity<CollectionPresenter.IView, CollectionPresenter>
        implements CollectionPresenter.IView, SwipeRefreshLayout.OnRefreshListener, CollectionAdapter.OnSelectListener, RecyclerArrayAdapter.OnMoreListener {
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.app_bar)
    AppBarLayout appBar;
    @BindView(R.id.recycler)
    EasyRecyclerView recycler;
    private CollectionAdapter adapter;

    @Override
    public int setContentResource() {
        return R.layout.activity_collection;
    }


    @Override
    public void initUserView() {
        adapter = new CollectionAdapter(this);
        adapter.setOnSelectListener(this);
        recycler.setRefreshListener(this);
        adapter.setMore(R.layout.view_more, this);

        presenter.setUser(user);
        presenter.setPage(0);
        initToolbar(toolbar, "我的收藏");
        initRecyclerView(recycler, adapter, 1, true);
        onRefresh();
    }


    //弹出底部dialog
    private void alertBottomDialog(final int position) {
        BottomSheetMenuDialog dialog = new BottomSheetBuilder(this)
                .setMode(BottomSheetBuilder.MODE_LIST)
                .setMenu(R.menu.menu_collection_list)
                .setItemClickListener(new BottomSheetItemClickListener() {
                    @Override
                    public void onBottomSheetItemClick(MenuItem item) {
                        switch (item.getItemId()) {
                            case R.id.delete://删除收藏
                                adapter.remove(adapter.getItem(position));
                                presenter.deleteCollection(adapter.getItem(position));
                                break;
                        }
                    }
                })
                .createDialog();
        dialog.show();
    }


    @Override
    public void setCollectionList(List<CollectionSong> list) {
        adapter.addAll(list);
    }


    @Override
    public void onRefresh() {
        adapter.clear();
        presenter.setPage(0);
        presenter.getCollectionList();
    }

    //更多选项
    @Override
    public void onSelectMore(int position) {
        alertBottomDialog(position);
    }

    //item
    @Override
    public void onSelectItem(int position) {
        presenter.queryAndEnterSongActivity(adapter.getItem(position));
    }

    @Override
    public void showRefreshing(boolean isShow) {
        recycler.setRefreshing(isShow);
    }

    //加载更多
    @Override
    public void onMoreShow() {
        presenter.getMoreList();
    }

    @Override
    public void onMoreClick() {

    }

    @Override
    protected CollectionPresenter createPresenter() {
        return new CollectionPresenter(this);
    }
}