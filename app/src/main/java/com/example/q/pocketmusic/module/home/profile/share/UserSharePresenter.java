package com.example.q.pocketmusic.module.home.profile.share;

import android.content.Intent;

import com.example.q.pocketmusic.callback.ToastQueryListener;
import com.example.q.pocketmusic.config.Constant;
import com.example.q.pocketmusic.data.bean.Song;
import com.example.q.pocketmusic.data.bean.SongObject;
import com.example.q.pocketmusic.data.bean.share.ShareSong;
import com.example.q.pocketmusic.data.model.UserShareModel;
import com.example.q.pocketmusic.module.common.BasePresenter;
import com.example.q.pocketmusic.module.common.IBaseView;
import com.example.q.pocketmusic.module.song.SongActivity;
import com.example.q.pocketmusic.util.UserUtil;

import java.util.List;

/**
 * Created by 鹏君 on 2017/5/26.
 */

public class UserSharePresenter extends BasePresenter<UserSharePresenter.IView> {
    private IView activity;
    private int mPage;
    private UserShareModel model;

    public UserSharePresenter(IView activity) {
        attachView(activity);
        this.activity = getIViewRef();
        model = new UserShareModel();
        this.mPage=0;
    }

    public void getUserShareList(final boolean isRefreshing) {
        mPage++;
        if (isRefreshing) {
            mPage = 0;
        }
        model.getUserShareList(UserUtil.user, mPage, new ToastQueryListener<ShareSong>() {
            @Override
            public void onSuccess(List<ShareSong> list) {
                activity.setList(isRefreshing, list);
            }
        });
    }



    public void enterSongActivity(ShareSong shareSong) {
        Song song = new Song();
        song.setContent(shareSong.getContent());
        song.setName(shareSong.getName());
        Intent intent = new Intent(activity.getCurrentContext(), SongActivity.class);
        SongObject songObject = new SongObject(song, Constant.FROM_SHARE, Constant.MENU_DOWNLOAD_COLLECTION_AGREE_SHARE, Constant.NET);
        songObject.setCommunity(shareSong.getInstrument());
        intent.putExtra(SongActivity.PARAM_SONG_OBJECT_SERIALIZABLE, songObject);
        intent.putExtra(SongActivity.SHARE_SONG, shareSong);
        activity.getCurrentContext().startActivity(intent);
    }



    interface IView extends IBaseView {
        void setList(boolean isRefreshing, List<ShareSong> list);

    }
}
