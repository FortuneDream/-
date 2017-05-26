package com.example.q.pocketmusic.module.home.profile.share;

import android.content.Intent;

import com.example.q.pocketmusic.callback.ToastQueryListener;
import com.example.q.pocketmusic.config.Constant;
import com.example.q.pocketmusic.model.bean.MyUser;
import com.example.q.pocketmusic.model.bean.Song;
import com.example.q.pocketmusic.model.bean.SongObject;
import com.example.q.pocketmusic.model.bean.share.ShareSong;
import com.example.q.pocketmusic.module.common.BasePresenter;
import com.example.q.pocketmusic.module.common.IBaseList;
import com.example.q.pocketmusic.module.song.SongActivity;

import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.datatype.BmobPointer;

/**
 * Created by 鹏君 on 2017/5/26.
 */

public class UserSharePresenter extends BasePresenter<UserSharePresenter.IView> {
    private IView activity;
    private MyUser user;

    public UserSharePresenter(IView activity) {
        attachView(activity);
        this.activity = getIViewRef();
    }

    public void getInitList() {
        BmobQuery<ShareSong> query = new BmobQuery<>();
        query.addWhereEqualTo("user", new BmobPointer(user));
        query.findObjects(new ToastQueryListener<ShareSong>(activity) {
            @Override
            public void onSuccess(List<ShareSong> list) {
                activity.setInitList(list);
            }
        });
    }

    public void setUser(MyUser user) {
        this.user = user;
    }

    public void enterSongActivity(ShareSong shareSong) {
        Song song = new Song();
        song.setNeedGrade(true);//需要积分
        song.setContent(shareSong.getContent());
        song.setName(shareSong.getName());
        Intent intent = new Intent(activity.getCurrentContext(), SongActivity.class);
        SongObject songObject = new SongObject(song, Constant.FROM_SHARE, Constant.SHOW_COLLECTION_MENU, Constant.NET);
        intent.putExtra(SongActivity.PARAM_SONG_OBJECT_PARCEL, songObject);
        intent.putExtra(SongActivity.SHARE_SONG, shareSong);
        activity.getCurrentContext().startActivity(intent);
    }

    interface IView extends IBaseList {

        void setInitList(List<ShareSong> list);
    }
}
