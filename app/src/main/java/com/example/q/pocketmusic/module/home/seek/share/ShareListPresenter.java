package com.example.q.pocketmusic.module.home.seek.share;

import android.content.Intent;

import com.example.q.pocketmusic.callback.ToastQueryListener;
import com.example.q.pocketmusic.config.Constant;
import com.example.q.pocketmusic.model.bean.Song;
import com.example.q.pocketmusic.model.bean.SongObject;
import com.example.q.pocketmusic.model.bean.share.ShareSong;
import com.example.q.pocketmusic.module.common.BasePresenter;
import com.example.q.pocketmusic.module.common.IBaseList;
import com.example.q.pocketmusic.module.song.SongActivity;
import com.example.q.pocketmusic.util.ACacheUtil;

import java.util.List;

import cn.bmob.v3.exception.BmobException;

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

    public void loadMore() {
        mPage++;
        model.getMoreShareList(mPage, new ToastQueryListener<ShareSong>(fragment) {
            @Override
            public void onSuccess(List<ShareSong> list) {
                fragment.setMore(list);
            }
        });

    }

    public void getShareList() {
        model.getInitShareList(new ToastQueryListener<ShareSong>(fragment) {
            @Override
            public void onSuccess(List<ShareSong> list) {
                ACacheUtil.putShareSongCache(fragment.getAppContext(), list);//添加缓存
                fragment.setList(list);
            }

            @Override
            public void onFail(BmobException e) {
                super.onFail(e);
                fragment.setList(ACacheUtil.getShareSongCache(fragment.getAppContext()));
            }
        });
    }

    //获取缓存
    public void getCacheList() {
        List<ShareSong> list = ACacheUtil.getShareSongCache(fragment.getAppContext());//先获取缓存
        if (list == null) {
            getShareList();
        }
        fragment.setList(list);
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

    interface IView extends IBaseList {

        void setMore(List<ShareSong> list);

        void setList(List<ShareSong> shareSongCache);
    }
}
