package com.example.q.pocketmusic.module.home.net.type;

import android.content.Context;
import android.content.Intent;

import com.example.q.pocketmusic.config.Constant;
import com.example.q.pocketmusic.model.bean.Song;
import com.example.q.pocketmusic.model.bean.SongObject;
import com.example.q.pocketmusic.model.net.LoadTypeSongList;
import com.example.q.pocketmusic.module.common.BasePresenter;
import com.example.q.pocketmusic.module.common.IBaseView;
import com.example.q.pocketmusic.module.song.SongActivity;

import java.util.List;


/**
 * Created by YQ on 2016/8/29.
 */
public class SongTypeActivityPresenter extends BasePresenter<SongTypeActivityPresenter.IView> {
    private IView activity;
    private int mPage;

    public SongTypeActivityPresenter(IView activity) {
        attachView(activity);
        this.activity = getIViewRef();
    }

    public int getmPage() {
        return mPage;
    }


    public void getList(int typeId, final boolean isRefreshing) {
        String url = Constant.BASE_URL + "/qiyue/" + Constant.namesUrl[typeId] + mPage + ".html";
        new LoadTypeSongList(typeId) {
            @Override
            protected void onPostExecute(List<Song> songs) {
                super.onPostExecute(songs);
                if (!isRefreshing) {
                    activity.setList(songs);
                } else {
                    activity.setListWithRefreshing(songs);
                }

            }
        }.execute(url);
    }


    public void setPage(int page) {
        this.mPage = page;
    }

    public void enterSongActivity(Song song) {
        Intent intent = new Intent(activity.getCurrentContext(), SongActivity.class);
        SongObject object = new SongObject(song, Constant.FROM_TYPE, Constant.SHOW_COLLECTION_MENU, Constant.NET);
        intent.putExtra(SongActivity.PARAM_SONG_OBJECT_PARCEL, object);
        activity.getCurrentContext().startActivity(intent);
    }

    public interface IView extends IBaseView {

        void setList(List<Song> songs);

        void setListWithRefreshing(List<Song> songs);
    }
}
