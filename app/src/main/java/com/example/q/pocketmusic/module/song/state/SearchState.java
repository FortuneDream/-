package com.example.q.pocketmusic.module.song.state;

import android.content.Context;
import android.os.AsyncTask;

import com.example.q.pocketmusic.config.Constant;
import com.example.q.pocketmusic.model.bean.Song;
import com.example.q.pocketmusic.model.net.LoadSearchSongPic;
import com.example.q.pocketmusic.module.song.SongActivityPresenter;

/**
 * Created by 鹏君 on 2017/5/3.
 */
//搜索状态
public class SearchState extends BaseState implements IState {
    private Context context;
    private SongActivityPresenter.IView activity;



    public SearchState(Song song, Context context, SongActivityPresenter.IView activity) {
        super(song, Constant.NET);
        this.context = context;
        this.activity = activity;
    }

    @Override
    public void loadPic() {
         new LoadSearchSongPic() {
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