package com.example.q.pocketmusic.module.home.convert.comment.convert.piano;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.q.pocketmusic.R;
import com.example.q.pocketmusic.model.bean.ConvertObject;
import com.example.q.pocketmusic.module.common.BaseFragment;
import com.example.q.pocketmusic.module.home.convert.comment.convert.ConvertActivity;
import com.example.q.pocketmusic.module.song.SongActivityAdapter;
import com.example.q.pocketmusic.view.widget.net.HackyViewPager;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class SongFragment extends BaseFragment<SongFragmentPresenter.IView, SongFragmentPresenter>
        implements SongFragmentPresenter.IView {
    @BindView(R.id.view_pager)
    HackyViewPager viewPager;
    private ConvertObject convertObject;

    private SongActivityAdapter adapter;

    @Override
    public int setContentResource() {
        return R.layout.fragment_song;
    }

    @Override
    public void initView() {
        adapter = new SongActivityAdapter(getCurrentContext(), convertObject.getIvUrl(), convertObject.getLoadingWay());
        viewPager.setAdapter(adapter);
    }

    @Override
    protected SongFragmentPresenter createPresenter() {
        return new SongFragmentPresenter(this);
    }

    public static SongFragment getInstance(ConvertObject convertObject) {
        SongFragment songFragment = new SongFragment();
        Bundle bundle = new Bundle();
        bundle.putSerializable(ConvertActivity.PARAM_CONVERT_OBJECT, convertObject);
        songFragment.setArguments(bundle);
        return songFragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.convertObject = (ConvertObject) getArguments().getSerializable(ConvertActivity.PARAM_CONVERT_OBJECT);
    }


}