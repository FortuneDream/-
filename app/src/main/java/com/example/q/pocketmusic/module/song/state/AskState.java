package com.example.q.pocketmusic.module.song.state;

import android.content.Context;

import com.example.q.pocketmusic.config.Constant;
import com.example.q.pocketmusic.model.bean.Song;
import com.example.q.pocketmusic.model.bean.ask.AskSongComment;
import com.example.q.pocketmusic.module.song.SongActivity;
import com.example.q.pocketmusic.module.song.SongActivityPresenter;

/**
 * Created by 鹏君 on 2017/5/3.
 */
//求谱
public class AskState extends BaseState implements IState {
    private AskSongComment askSongComment;
    private Context context;
    private SongActivityPresenter.IView activity;


    public AskState(Song song, AskSongComment askSongComment, Context context, SongActivityPresenter.IView activity) {
        super(song, Constant.NET);
        this.askSongComment = askSongComment;
        this.context = context;
        this.activity = activity;
    }

    @Override
    public void loadPic() {
        activity.setPicResult(getSong().getIvUrl(), getLoadingWay());
    }

}
