package com.example.q.pocketmusic.module.home.convert.comment.convert.piano;


import com.example.q.pocketmusic.R;
import com.example.q.pocketmusic.module.common.BaseFragment;

import com.example.q.pocketmusic.module.common.BasePresenter;
import com.example.q.pocketmusic.module.common.IBaseView;

public class SongFragment extends BaseFragment<SongFragmentPresenter.IView, SongFragmentPresenter>
        implements SongFragmentPresenter.IView {
    @Override
    public int setContentResource() {
        return R.layout.fragment_song;
    }

    @Override
    public void initView() {

    }

    @Override
    protected SongFragmentPresenter createPresenter() {
        return new SongFragmentPresenter(this);
    }
}