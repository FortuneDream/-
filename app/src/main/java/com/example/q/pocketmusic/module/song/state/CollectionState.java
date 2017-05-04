package com.example.q.pocketmusic.module.song.state;

import android.content.Context;

import com.example.q.pocketmusic.config.Constant;
import com.example.q.pocketmusic.model.bean.Song;
import com.example.q.pocketmusic.model.bean.ask.AskSongComment;
import com.example.q.pocketmusic.module.song.SongActivityPresenter;

/**
 * Created by 鹏君 on 2017/5/3.
 */
//收藏
public class CollectionState extends BaseState implements IState {
    private Context context;
    private SongActivityPresenter.IView activity;

    public CollectionState(Song song, Context context, SongActivityPresenter.IView activity) {
        super(song, Constant.NET);
        this.activity = activity;
    }

    @Override
    public void loadPic() {
        activity.setPicResult(getSong().getIvUrl(), getLoadingWay());
    }

}