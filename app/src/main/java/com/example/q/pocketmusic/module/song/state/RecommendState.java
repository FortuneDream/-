package com.example.q.pocketmusic.module.song.state;

import android.content.Context;

import com.example.q.pocketmusic.config.Constant;
import com.example.q.pocketmusic.model.bean.Song;
import com.example.q.pocketmusic.model.net.LoadRecommendSongPic;
import com.example.q.pocketmusic.module.song.SongActivity;
import com.example.q.pocketmusic.module.song.SongActivityPresenter;

/**
 * Created by 鹏君 on 2017/5/3.
 */
//推荐状态
public class RecommendState extends BaseState implements IState {
    private Context context;
    private SongActivityPresenter.IView activity;

    public RecommendState(Song song, Context context, SongActivityPresenter.IView activity) {
        super(song, Constant.NET);
        this.context = context;
        this.activity = activity;
    }

    @Override
    public void loadPic() {
        new LoadRecommendSongPic() {
            @Override
            protected void onPostExecute(Integer integer) {
                super.onPostExecute(integer);
                setLoadIntResult(integer);
            }
        }.execute(getSong());
    }

    private void setLoadIntResult(int result) {
        if (result == Constant.FAIL) {
            activity.loadFail();
        } else {
            activity.setPicResult(getSong().getIvUrl(), getLoadingWay());
        }
    }

}