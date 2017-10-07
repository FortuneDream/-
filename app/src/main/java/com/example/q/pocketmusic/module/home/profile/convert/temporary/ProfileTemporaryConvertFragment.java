package com.example.q.pocketmusic.module.home.profile.convert.temporary;


import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AlertDialog;

import com.example.q.pocketmusic.R;
import com.example.q.pocketmusic.model.bean.convert.ConvertSong;
import com.example.q.pocketmusic.module.common.BaseFragment;
import com.jude.easyrecyclerview.EasyRecyclerView;
import com.jude.easyrecyclerview.adapter.RecyclerArrayAdapter;

import java.util.List;

import butterknife.BindView;

public class ProfileTemporaryConvertFragment extends BaseFragment<ProfileTemporaryConvertFragmentPresenter.IView, ProfileTemporaryConvertFragmentPresenter>
        implements ProfileTemporaryConvertFragmentPresenter.IView, RecyclerArrayAdapter.OnItemClickListener, SwipeRefreshLayout.OnRefreshListener, ProfileTemporaryConvertAdapter.OnSelectListener,
        RecyclerArrayAdapter.OnMoreListener {
    @BindView(R.id.local_convert_recycler)
    EasyRecyclerView localConvertRecycler;
    private ProfileTemporaryConvertAdapter adapter;

    @Override
    public int setContentResource() {
        return R.layout.fragment_local_convert;
    }

    @Override
    public void initView() {
        adapter = new ProfileTemporaryConvertAdapter(getCurrentContext());
        initRecyclerView(localConvertRecycler, adapter);
        adapter.setOnItemClickListener(this);
        adapter.setOnSelectListener(this);
        adapter.setMore(R.layout.view_more, this);
        localConvertRecycler.setRefreshListener(this);
        presenter.getInitTemporaryList(true);
    }

    @Override
    protected ProfileTemporaryConvertFragmentPresenter createPresenter() {
        return new ProfileTemporaryConvertFragmentPresenter(this);
    }


    @Override
    public void onItemClick(int position) {
        new AlertDialog.Builder(getCurrentContext())
                .setTitle(adapter.getItem(position).getName())
                .setMessage(adapter.getItem(position).getContent())
                .show();
    }


    @Override
    public void setList(List<ConvertSong> convertSongs, boolean isRefreshing) {
        if (isRefreshing) {
            adapter.clear();
        }
        adapter.addAll(convertSongs);
    }

    @Override
    public void onRefresh() {
        presenter.getInitTemporaryList(true);
    }

    @Override
    public void onSelectDelete(int position) {
        presenter.deleteConvertSong(adapter.getItem(position));
    }


    @Override
    public void onMoreShow() {
        presenter.getMoreTemporaryList(false);
    }

    @Override
    public void onMoreClick() {

    }
}