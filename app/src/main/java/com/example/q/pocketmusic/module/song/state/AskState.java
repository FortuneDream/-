package com.example.q.pocketmusic.module.song.state;

import com.example.q.pocketmusic.config.Constant;
import com.example.q.pocketmusic.model.bean.Song;
import com.example.q.pocketmusic.model.bean.ask.AskSongComment;
import com.example.q.pocketmusic.module.song.SongActivityPresenter;

/**
 * Created by 鹏君 on 2017/5/3.
 */
//求谱
public class AskState extends BaseState implements IState {
    private AskSongComment askSongComment;
    private SongActivityPresenter.IView activity;


    public AskState(Song song, AskSongComment askSongComment,SongActivityPresenter.IView activity) {
        super(song, Constant.NET);
        this.askSongComment = askSongComment;
        this.activity = activity;
    }

    @Override
    public void loadPic() {
        activity.setPicResult(getSong().getIvUrl(), getLoadingWay());
    }

}
