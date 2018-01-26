package com.example.q.pocketmusic.module.song.state;

import com.example.q.pocketmusic.config.constant.Constant;
import com.example.q.pocketmusic.data.bean.Song;
import com.example.q.pocketmusic.module.song.SongActivityPresenter;

/**
 * Created by 鹏君 on 2017/5/3.
 */
//收藏
public class CollectionState extends BaseState implements IState {
    private SongActivityPresenter.IView activity;

    public CollectionState(Song song, SongActivityPresenter.IView activity) {
        super(song, Constant.NET);
        this.activity = activity;
    }

    @Override
    public void loadPic() {
        activity.setPicResult(getSong().getIvUrl(), getLoadingWay());
    }

}