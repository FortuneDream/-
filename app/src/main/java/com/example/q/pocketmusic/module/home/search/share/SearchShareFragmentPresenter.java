package com.example.q.pocketmusic.module.home.search.share;

import com.example.q.pocketmusic.callback.ToastQueryListener;
import com.example.q.pocketmusic.config.constant.Constant;
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


    public SearchShareFragmentPresenter(IView fragment) {
        super(fragment);
    }

    public void queryFromShareSongList(String s) {
        BmobQuery<ShareSong> query = new BmobQuery<>();
        query.addWhereContains("name", s);
        query.findObjects(new ToastQueryListener<ShareSong>() {
            @Override
            public void onSuccess(List<ShareSong> list) {
                mView.setShareSongList(list);
            }
        });
    }

    public void enterSongActivity(ShareSong shareSong) {
        Song song = new Song();
        song.setContent(shareSong.getContent());
        song.setName(shareSong.getName());
        SongObject songObject = new SongObject(song, Constant.FROM_SHARE, Constant.MENU_DOWNLOAD_COLLECTION_AGREE_SHARE, Constant.NET);
        mContext.startActivity(
                SongActivity.buildShareIntent(mContext,songObject,shareSong.getInstrument(),shareSong));
    }

    interface IView extends IBaseView {

        void setShareSongList(List<ShareSong> list);
    }

}
