package com.example.q.pocketmusic.module.home.local.localsong;

import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.example.q.pocketmusic.R;
import com.example.q.pocketmusic.data.bean.local.LocalSong;
import com.example.q.pocketmusic.module.common.BaseFragment;
import com.jude.easyrecyclerview.EasyRecyclerView;
import com.jude.easyrecyclerview.adapter.RecyclerArrayAdapter;

import java.util.List;

import butterknife.BindView;

/**
 * Created by 鹏君 on 2016/11/17.
 */

public class LocalSongFragment extends BaseFragment<LocalSongFragmentPresenter.IView, LocalSongFragmentPresenter>
        implements LocalSongFragmentPresenter.IView, SwipeRefreshLayout.OnRefreshListener, RecyclerArrayAdapter.OnItemClickListener,
        LocalSongFragmentAdapter.OnItemSelectListener {
    @BindView(R.id.recycler)
    EasyRecyclerView recycler;
    private LocalSongFragmentAdapter adapter;


    @Override
    public void finish() {
        getActivity().finish();
    }

    @Override
    public int setContentResource() {
        return R.layout.fragment_local_song;
    }


    @Override
    public void initView() {
        //监听
        adapter = new LocalSongFragmentAdapter(getContext());
        adapter.setOnItemClickListener(this);
        adapter.setOnSelectListener(this);
        recycler.setRefreshListener(this);
        //初始化
        initRecyclerView(recycler, adapter, 1);
    }


    @Override
    public void onStart() {
        super.onStart();
        presenter.loadLocalSong();
    }

    //注意这里不能用addAll，因为只能有一个List的引用,
    @Override
    public void setList(List<LocalSong> lists) {
        adapter.clear();
        adapter.addAll(lists);
        //置顶+逆序
        adapter.sort(new LocalSongComparator());
    }


    @Override
    public void onRefresh() {
        presenter.synchronizedSong();
    }

    @Override
    public void onItemClick(int position) {
        LocalSong localSong = adapter.getItem(position);
        presenter.enterSongActivity(localSong);
    }

    @Override
    public void onSelectedDelete(int position) {
        presenter.deleteSong(adapter.getItem(position));
        onRefresh();
    }

    @Override
    public void onSelectedShare(int position) {
        presenter.enterShareActivity(adapter.getItem(position));
    }

    //选择置顶
    @Override
    public void onSelectedTop(int position) {
        presenter.setTop(adapter.getItem(position));//置顶
    }

    @Override
    protected LocalSongFragmentPresenter createPresenter() {
        return new LocalSongFragmentPresenter(this);
    }
}
