package com.example.q.pocketmusic.module.home.net.type.community.share;

import android.content.Intent;

import com.example.q.pocketmusic.callback.ToastQueryListener;
import com.example.q.pocketmusic.config.Constant;
import com.example.q.pocketmusic.model.bean.MyUser;
import com.example.q.pocketmusic.model.bean.Song;
import com.example.q.pocketmusic.model.bean.SongObject;
import com.example.q.pocketmusic.model.bean.share.ShareSong;
import com.example.q.pocketmusic.module.common.BasePresenter;
import com.example.q.pocketmusic.module.common.IBaseView;
import com.example.q.pocketmusic.module.home.profile.share.UserShareModel;
import com.example.q.pocketmusic.module.song.SongActivity;
import com.example.q.pocketmusic.module.user.other.OtherProfileActivity;


import java.util.List;

/**
 * Created by 鹏君 on 2017/5/16.
 */

public class ShareListPresenter extends BasePresenter<ShareListPresenter.IView> {
    private IView fragment;
    private UserShareModel model;
    private int mPage;
    private int typeId;

    public ShareListPresenter(IView fragment) {
        attachView(fragment);
        this.fragment = getIViewRef();
        model = new UserShareModel();
    }

    public void getMoreShareList() {
        mPage++;
        model.getAllShareList(typeId,mPage, new ToastQueryListener<ShareSong>(fragment) {
            @Override
            public void onSuccess(List<ShareSong> list) {
                fragment.setList(false, list);
            }
        });

    }

    public void getShareList(final boolean isRefreshing) {
        if (isRefreshing) {
            mPage = 0;
        }
        model.getAllShareList(typeId,mPage, new ToastQueryListener<ShareSong>(fragment) {
            @Override
            public void onSuccess(List<ShareSong> list) {
                fragment.setList(isRefreshing, list);
            }
        });
    }

    public void setSharePage(int page) {
        this.mPage = page;
    }

    //通过分享乐曲item进入SongActivity
    public void enterSongActivityByShare(ShareSong shareSong) {
        Song song = new Song();
        song.setContent(shareSong.getContent());
        song.setName(shareSong.getName());
        Intent intent = new Intent(fragment.getCurrentContext(), SongActivity.class);
        SongObject songObject = new SongObject(song, Constant.FROM_SHARE, Constant.MENU_DOWNLOAD_COLLECTION_AGREE_SHARE, Constant.NET);
        intent.setExtrasClassLoader(getClass().getClassLoader());
        intent.putExtra(SongActivity.PARAM_SONG_OBJECT_SERIALIZEABLE, songObject);
        intent.putExtra(SongActivity.SHARE_SONG, shareSong);
        fragment.getCurrentContext().startActivity(intent);
    }

    public void enterOtherProfileActivity(MyUser other) {
        Intent intent = new Intent(fragment.getCurrentContext(), OtherProfileActivity.class);
        intent.putExtra(OtherProfileActivity.PARAM_USER, other);
        fragment.getCurrentContext().startActivity(intent);
    }

    public void setType(int typeId) {
        this.typeId=typeId;
    }

    interface IView extends IBaseView {

        void setList(boolean isRefreshing, List<ShareSong> shareSongCache);
    }
}
