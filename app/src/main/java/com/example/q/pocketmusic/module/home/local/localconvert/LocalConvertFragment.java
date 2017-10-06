package com.example.q.pocketmusic.module.home.local.localconvert;


import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AlertDialog;
import android.view.MenuItem;

import com.example.q.pocketmusic.R;
import com.example.q.pocketmusic.model.bean.local.LocalConvertSong;
import com.example.q.pocketmusic.module.common.BaseFragment;
import com.github.rubensousa.bottomsheetbuilder.BottomSheetBuilder;
import com.github.rubensousa.bottomsheetbuilder.BottomSheetMenuDialog;
import com.github.rubensousa.bottomsheetbuilder.adapter.BottomSheetItemClickListener;
import com.jude.easyrecyclerview.EasyRecyclerView;
import com.jude.easyrecyclerview.adapter.RecyclerArrayAdapter;

import java.util.List;

import butterknife.BindView;

public class LocalConvertFragment extends BaseFragment<LocalConvertFragmentPresenter.IView, LocalConvertFragmentPresenter>
        implements LocalConvertFragmentPresenter.IView, RecyclerArrayAdapter.OnItemClickListener, SwipeRefreshLayout.OnRefreshListener, LocalConvertAdapter.OnSelectListener {
    @BindView(R.id.local_convert_recycler)
    EasyRecyclerView localConvertRecycler;
    private LocalConvertAdapter adapter;

    @Override
    public int setContentResource() {
        return R.layout.fragment_local_convert;
    }

    @Override
    public void initView() {
        adapter = new LocalConvertAdapter(getCurrentContext());
        initRecyclerView(localConvertRecycler, adapter);
        adapter.setOnItemClickListener(this);
        adapter.setOnSelectListener(this);
        presenter.getList();
        localConvertRecycler.setRefreshListener(this);
    }

    @Override
    protected LocalConvertFragmentPresenter createPresenter() {
        return new LocalConvertFragmentPresenter(this);
    }


    @Override
    public void onItemClick(int position) {
        presenter.getConvertContent(adapter.getItem(position));
    }

    @Override
    public void alertContentDialog(LocalConvertSong localConvertSong) {
        new AlertDialog.Builder(getCurrentContext())
                .setTitle(localConvertSong.getName())
                .setMessage(localConvertSong.getContent())
                .show();
    }

    @Override
    public void setList(List<LocalConvertSong> localConvertSongs) {
        adapter.addAll(localConvertSongs);
    }

    @Override
    public void onRefresh() {
        adapter.clear();
        presenter.getList();
    }

    @Override
    public void onSelectMore(int position) {
        alertDeleteDialog(adapter.getItem(position));
    }

    private void alertDeleteDialog(final LocalConvertSong localConvertSong) {
        BottomSheetMenuDialog dialog = new BottomSheetBuilder(getContext())
                .setMode(BottomSheetBuilder.MODE_LIST)
                .setMenu(R.menu.menu_home_local)
                .setItemClickListener(new BottomSheetItemClickListener() {
                    @Override
                    public void onBottomSheetItemClick(MenuItem item) {
                        switch (item.getItemId()) {
                            case R.id.delete://删除
                                presenter.deleteLocalConvertSong(localConvertSong);
                                adapter.remove(localConvertSong);
                                if (adapter.getCount() == 0) {
                                    localConvertRecycler.showEmpty();
                                }
                                break;
                        }
                    }
                })
                .createDialog();
        dialog.show();
    }
}