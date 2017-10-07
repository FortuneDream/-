package com.example.q.pocketmusic.module.home.net.special.list;

import android.content.Intent;

import com.example.q.pocketmusic.callback.ToastQueryListener;
import com.example.q.pocketmusic.config.Constant;
import com.example.q.pocketmusic.model.bean.Song;
import com.example.q.pocketmusic.model.bean.SongObject;
import com.example.q.pocketmusic.model.bean.special.SpecialColumn;
import com.example.q.pocketmusic.model.bean.special.SpecialColumnSong;
import com.example.q.pocketmusic.module.common.BasePresenter;
import com.example.q.pocketmusic.module.common.IBaseView;
import com.example.q.pocketmusic.module.song.SongActivity;

import java.util.List;

/**
 * Created by 鹏君 on 2017/8/24.
 * （￣m￣）
 */

public class SpecialListPresenter extends BasePresenter<SpecialListPresenter.IView> {
    private IView activity;
    private SpecialColumn column;
    private int mPage;
    private SpecialListModel model;

    public SpecialListPresenter(IView activity) {
        attachView(activity);
        this.activity = getIViewRef();
        model = new SpecialListModel();
    }

    public void setSpecialColumnSong(SpecialColumn column) {
        this.column = column;
    }

    public void getSpecialList(final boolean isRefreshing) {
        if (isRefreshing) {
            mPage = 0;
        }
        model.getSpecialList(column, mPage, new ToastQueryListener<SpecialColumnSong>() {
            @Override
            public void onSuccess(List<SpecialColumnSong> list) {
                activity.setSpecialList(list, isRefreshing);
            }
        });


    }

    public void getMoreSpecialList() {
        mPage++;
        model.getSpecialList(column, mPage, new ToastQueryListener<SpecialColumnSong>() {
            @Override
            public void onSuccess(List<SpecialColumnSong> list) {
                activity.setSpecialList(list, false);
            }
        });
    }

    public void enterSongActivity(SpecialColumnSong item) {
        Intent intent = new Intent(activity.getCurrentContext(), SongActivity.class);
        Song song = new Song();
        song.setName(item.getName());
        SongObject songObject = new SongObject(song, Constant.FROM_SPECIAL, Constant.MENU_DOWNLOAD_COLLECTION_SHARE, Constant.NET);
        intent.putExtra(SongActivity.PARAM_SONG_OBJECT_SERIALIZABLE, songObject);
        intent.putExtra(SongActivity.SPECIAL_SONG, item);
        activity.getCurrentContext().startActivity(intent);
    }


    interface IView extends IBaseView {

        void setSpecialList(List<SpecialColumnSong> list, boolean isRefreshing);
    }
}
