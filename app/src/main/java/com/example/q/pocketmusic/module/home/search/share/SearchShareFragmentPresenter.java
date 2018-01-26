package com.example.q.pocketmusic.module.home.search.share;

import android.content.Intent;

import com.example.q.pocketmusic.callback.ToastQueryListener;
import com.example.q.pocketmusic.config.constant.Constant;
import com.example.q.pocketmusic.config.constant.IntentConstant;
import com.example.q.pocketmusic.data.bean.Song;
import com.example.q.pocketmusic.data.bean.SongObject;
import com.example.q.pocketmusic.data.bean.share.ShareSong;
import com.example.q.pocketmusic.module.common.BasePresenter;
import com.example.q.pocketmusic.module.common.IBaseView;
import com.example.q.pocketmusic.module.song.SongActivity;

import java.util.List;

import cn.bmob.v3.BmobQuery;

/**
 * Created by 鹏君 on 2017/4/14.
 */

public class SearchShareFragmentPresenter extends BasePresenter<SearchShareFragmentPresenter.IView> {
    private IView fragment;

    public SearchShareFragmentPresenter(IView fragment) {
        attachView(fragment);
        this.fragment = getIViewRef();
    }

    public void queryFromShareSongList(String s) {
        BmobQuery<ShareSong> query = new BmobQuery<>();
        query.addWhereContains("name", s);
        query.findObjects(new ToastQueryListener<ShareSong>() {
            @Override
            public void onSuccess(List<ShareSong> list) {
                fragment.setShareSongList(list);
            }
        });
    }

    public void enterSongActivity(ShareSong shareSong) {
        Song song = new Song();
        song.setContent(shareSong.getContent());
        song.setName(shareSong.getName());
        SongObject songObject = new SongObject(song, Constant.FROM_SHARE, Constant.MENU_DOWNLOAD_COLLECTION_AGREE_SHARE, Constant.NET);
        fragment.getCurrentContext().startActivity(
                SongActivity.buildShareIntent(fragment.getCurrentContext(),songObject,shareSong.getInstrument(),shareSong));
    }

    interface IView extends IBaseView {

        void setShareSongList(List<ShareSong> list);
    }

}
