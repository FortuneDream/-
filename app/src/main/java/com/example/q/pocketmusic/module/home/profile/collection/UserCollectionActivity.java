package com.example.q.pocketmusic.module.home.profile.collection;

import android.support.design.widget.AppBarLayout;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.Toolbar;

import com.example.q.pocketmusic.R;
import com.example.q.pocketmusic.data.bean.collection.CollectionSong;
import com.example.q.pocketmusic.module.common.AuthActivity;
import com.example.q.pocketmusic.view.dialog.EditDialog;
import com.jude.easyrecyclerview.EasyRecyclerView;
import com.jude.easyrecyclerview.adapter.RecyclerArrayAdapter;

import java.util.List;

import butterknife.BindView;

/**
 * Created by 鹏君 on 2016/11/14.
 */

public class UserCollectionActivity extends AuthActivity<UserCollectionPresenter.IView, UserCollectionPresenter>
        implements UserCollectionPresenter.IView, SwipeRefreshLayout.OnRefreshListener, UserCollectionAdapter.OnSelectListener, RecyclerArrayAdapter.OnMoreListener {
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.app_bar)
    AppBarLayout appBar;
    @BindView(R.id.recycler)
    EasyRecyclerView recycler;
    private UserCollectionAdapter adapter;

    @Override
    public int setContentResource() {
        return R.layout.activity_collection;
    }


    @Override
    public void initUserView() {
        adapter = new UserCollectionAdapter(this);
        adapter.setOnSelectListener(this);
        recycler.setRefreshListener(this);
        adapter.setMore(R.layout.view_more, this);
        initToolbar(toolbar, "我的收藏");
        initRecyclerView(recycler, adapter, 1);
        onRefresh();
    }


    @Override
    public void setCollectionList(boolean isRefreshing, List<CollectionSong> list) {
        if (isRefreshing) {
            adapter.clear();
        }
        if (list == null) {
            onRefresh();
        } else {
            adapter.addAll(list);
        }
    }


    @Override
    public void onRefresh() {
        presenter.getCollectionList(true);
    }

    //更多选项
    @Override
    public void onSelectDelete(int position) {
        CollectionSong collectionSong = adapter.getItem(position);
        adapter.remove(collectionSong);
        presenter.deleteCollection(collectionSong);
    }

    //item
    @Override
    public void onSelectItem(int position) {
        presenter.queryAndEnterSongActivity(adapter.getItem(position));
    }

    @Override
    public void onSelectModify(final int position) {
        new EditDialog.Builder(getCurrentContext())
                .setTitle("修改名字")
                .setListener(new EditDialog.Builder.OnSelectedListener() {
                    @Override
                    public void onSelectedOk(String str) {
                        presenter.updateConnectionName(adapter.getItem(position), str);
                    }

                    @Override
                    public void onSelectedCancel() {

                    }
                })
                .create().show();
    }

    //加载更多
    @Override
    public void onMoreShow() {
        presenter.getCollectionList(false);
    }

    @Override
    public void onMoreClick() {

    }

    @Override
    protected UserCollectionPresenter createPresenter() {
        return new UserCollectionPresenter(this);
    }
}