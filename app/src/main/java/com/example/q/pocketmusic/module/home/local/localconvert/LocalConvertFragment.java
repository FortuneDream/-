package com.example.q.pocketmusic.module.home.local.localconvert;


import android.support.v7.app.AlertDialog;

import com.example.q.pocketmusic.R;
import com.example.q.pocketmusic.model.bean.local.LocalConvertSong;
import com.example.q.pocketmusic.module.common.BaseFragment;
import com.jude.easyrecyclerview.EasyRecyclerView;
import com.jude.easyrecyclerview.adapter.RecyclerArrayAdapter;

import butterknife.BindView;

public class LocalConvertFragment extends BaseFragment<LocalConvertFragmentPresenter.IView, LocalConvertFragmentPresenter>
        implements LocalConvertFragmentPresenter.IView, RecyclerArrayAdapter.OnItemClickListener {
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
        presenter.getList();
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
}