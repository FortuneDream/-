package com.example.q.pocketmusic.module.search.share;

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

import cn.bmob.v3.BmobQuery;

/**
 * Created by 81256 on 2017/4/14.
 */

public class SearchShareFragmentPresenter extends BasePresenter<SearchShareFragmentPresenter.IView> {
    private IView fragment;

    public SearchShareFragmentPresenter(IView fragment) {
        attachView(fragment);
        this.fragment=getIViewRef();
    }

    public void queryFromShareSongListWithRefreing(String s) {
        BmobQuery<ShareSong> query = new BmobQuery<>();
        query.addWhereEqualTo("name", s);
        query.findObjects(new ToastQueryListener<ShareSong>(fragment) {
            @Override
            public void onSuccess(List<ShareSong> list) {
                fragment.setShareSongListWithRefreshing(list);
            }
        });
    }

    public void enterSongActivity(ShareSong shareSong) {
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

        void setShareSongListWithRefreshing(List<ShareSong> list);
    }

}
