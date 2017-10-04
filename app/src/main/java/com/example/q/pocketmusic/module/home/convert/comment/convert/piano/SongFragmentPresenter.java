package com.example.q.pocketmusic.module.home.convert.comment.convert.piano;

import android.content.Context;

import com.example.q.pocketmusic.module.common.BasePresenter;
import com.example.q.pocketmusic.module.common.IBaseView;

public class SongFragmentPresenter extends BasePresenter<SongFragmentPresenter.IView> {
    private IView fragment;

    public SongFragmentPresenter(IView fragment) {
        attachView(fragment);
        this.fragment = getIViewRef();
    }



    interface IView extends IBaseView {

    }
}
