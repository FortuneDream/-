package com.example.q.pocketmusic.module.home.profile.collection;

import android.text.TextUtils;
import android.view.View;

import androidx.appcompat.widget.Toolbar;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.dell.fortune.tools.dialog.DialogEditSureCancel;
import com.dell.fortune.tools.toast.Toasts;
import com.example.q.pocketmusic.R;
import com.example.q.pocketmusic.data.bean.collection.CollectionSong;
import com.example.q.pocketmusic.module.common.AuthActivity;
import com.google.android.material.appbar.AppBarLayout;
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
    private DialogEditSureCancel dialogEditSureCancel;

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
        dialogEditSureCancel=new DialogEditSureCancel(context);
        dialogEditSureCancel.getTvTitle().setText("修改名字");
        dialogEditSureCancel.getTvSure().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String nickName=dialogEditSureCancel.getEditText().getText().toString();
                if (TextUtils.isEmpty(nickName)){
                    Toasts.error("不能为空哦~");
                    return;
                }
                dialogEditSureCancel.dismiss();
                presenter.updateCollectionName(adapter.getItem(position), nickName);
            }
        });
        dialogEditSureCancel.getTvCancel().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialogEditSureCancel.cancel();
            }
        });
        dialogEditSureCancel.show();
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