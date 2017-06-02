package com.example.q.pocketmusic.module.home.seek.share;

import android.content.Intent;

import com.example.q.pocketmusic.callback.ToastQueryListener;
import com.example.q.pocketmusic.config.Constant;
import com.example.q.pocketmusic.model.bean.Song;
import com.example.q.pocketmusic.model.bean.SongObject;
import com.example.q.pocketmusic.model.bean.share.ShareSong;
import com.example.q.pocketmusic.module.common.BasePresenter;
import com.example.q.pocketmusic.module.common.IBaseView;
import com.example.q.pocketmusic.module.song.SongActivity;


import java.util.List;

/**
 * Created by 鹏君 on 2017/5/16.
 */

public class ShareListPresenter extends BasePresenter<ShareListPresenter.IView> {
    private IView fragment;
    private ShareListModel model;
    private int mPage;

    public ShareListPresenter(IView fragment) {
        attachView(fragment);
        this.fragment = getIViewRef();
        model = new ShareListModel();
    }

    public void getMoreShareList() {
        mPage++;
        model.getMoreShareList(mPage, new ToastQueryListener<ShareSong>(fragment) {
            @Override
            public void onSuccess(List<ShareSong> list) {
                fragment.setMore(list);
            }
        });

    }

    public void getShareList(final boolean isRefreshing) {
        model.getInitShareList(new ToastQueryListener<ShareSong>(fragment) {
            @Override
            public void onSuccess(List<ShareSong> list) {
                if (!isRefreshing){
                    fragment.setList(list);
                }else {
                    fragment.setListWithRefreshing(list);
                }

            }
        });
    }

    public void setSharePage(int page) {
        this.mPage = page;
    }

    //通过分享乐曲item进入SongActivity
    public void enterSongActivityByShare(ShareSong shareSong) {
        Song song = new Song();
        song.setNeedGrade(true);//需要积分
        song.setContent(shareSong.getContent());
        song.setName(shareSong.getName());
        Intent intent = new Intent(fragment.getCurrentContext(), SongActivity.class);
        SongObject songObject = new SongObject(song, Constant.FROM_SHARE, Constant.SHOW_COLLECTION_MENU, Constant.NET);
        intent.putExtra(SongActivity.PARAM_SONG_OBJECT_PARCEL, songObject);
        intent.putExtra(SongActivity.SHARE_SONG, shareSong);
        fragment.getCurrentContext().startActivity(intent);
    }

    interface IView extends IBaseView {

        void setMore(List<ShareSong> list);

        void setList(List<ShareSong> shareSongCache);

        void setListWithRefreshing(List<ShareSong> list);
    }
}
