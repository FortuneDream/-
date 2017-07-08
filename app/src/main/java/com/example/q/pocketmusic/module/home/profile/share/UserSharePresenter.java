package com.example.q.pocketmusic.module.home.profile.share;

import android.content.Intent;

import com.example.q.pocketmusic.callback.ToastQueryListener;
import com.example.q.pocketmusic.config.Constant;
import com.example.q.pocketmusic.model.bean.MyUser;
import com.example.q.pocketmusic.model.bean.Song;
import com.example.q.pocketmusic.model.bean.SongObject;
import com.example.q.pocketmusic.model.bean.share.ShareSong;
import com.example.q.pocketmusic.module.common.BasePresenter;
import com.example.q.pocketmusic.module.common.IBaseView;
import com.example.q.pocketmusic.module.song.SongActivity;
import com.example.q.pocketmusic.util.BmobUtil;

import java.util.List;

import cn.bmob.v3.datatype.BmobPointer;

/**
 * Created by 鹏君 on 2017/5/26.
 */

public class UserSharePresenter extends BasePresenter<UserSharePresenter.IView> {
    private IView activity;
    private MyUser user;
    private int mPage;
    private BmobUtil bmobUtil;

    public UserSharePresenter(IView activity) {
        attachView(activity);
        this.activity = getIViewRef();
        bmobUtil = new BmobUtil();
    }

    public void getInitList(final boolean isRefreshing) {
        bmobUtil.getInitListWithEqual(ShareSong.class, null, Constant.BMOB_USER, new BmobPointer(user), new ToastQueryListener<ShareSong>(activity) {
            @Override
            public void onSuccess(List<ShareSong> list) {
                if (!isRefreshing){
                    activity.setList(list);
                }else {
                    activity.setListWithRefreshing(list);
                }

            }
        });
    }

    public void getMoreList() {
        mPage++;
        bmobUtil.getMoreListWithEqual(ShareSong.class, null, mPage, Constant.BMOB_USER, new BmobPointer(user), new ToastQueryListener<ShareSong>(activity) {
            @Override
            public void onSuccess(List<ShareSong> list) {
                activity.setList(list);
            }
        });
    }

    public void setUser(MyUser user) {
        this.user = user;
    }

    public void enterSongActivity(ShareSong shareSong) {
        Song song = new Song();
        song.setContent(shareSong.getContent());
        song.setName(shareSong.getName());
        Intent intent = new Intent(activity.getCurrentContext(), SongActivity.class);
        SongObject songObject = new SongObject(song, Constant.FROM_SHARE, Constant.SHOW_COLLECTION_MENU, Constant.NET);
        intent.setExtrasClassLoader(getClass().getClassLoader());
        intent.putExtra(SongActivity.PARAM_SONG_OBJECT_PARCEL, songObject);
        intent.putExtra(SongActivity.SHARE_SONG, shareSong);
        activity.getCurrentContext().startActivity(intent);
    }

    public void setPage(int page) {
        this.mPage = page;
    }

    interface IView extends IBaseView {
        void setList(List<ShareSong> list);

        void setListWithRefreshing(List<ShareSong> list);
    }
}
