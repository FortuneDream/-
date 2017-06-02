package com.example.q.pocketmusic.module.song.state;

import com.example.q.pocketmusic.callback.ToastQueryListener;
import com.example.q.pocketmusic.config.Constant;
import com.example.q.pocketmusic.model.bean.Song;
import com.example.q.pocketmusic.model.bean.share.SharePic;
import com.example.q.pocketmusic.model.bean.share.ShareSong;
import com.example.q.pocketmusic.module.song.SongActivityPresenter;

import java.util.ArrayList;
import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.datatype.BmobPointer;

/**
 * Created by 鹏君 on 2017/5/3.
 */
//分享状态
public class ShareState extends BaseState implements IState {
    private ShareSong shareSong;
    private SongActivityPresenter.IView activity;

    public ShareState(Song song, ShareSong shareSong,SongActivityPresenter.IView activity) {
        super(song,Constant.NET);
        this.shareSong=shareSong;
        this.activity=activity;
    }

    @Override
    public void loadPic() {
        BmobQuery<SharePic> query = new BmobQuery<>();
        query.addWhereEqualTo("shareSong", new BmobPointer(shareSong));
        query.findObjects(new ToastQueryListener<SharePic>(activity) {
            @Override
            public void onSuccess(List<SharePic> list) {
                List<String> pics = new ArrayList<>();
                for (SharePic sharePic : list) {
                    pics.add(sharePic.getUrl());
                }
                getSong().setIvUrl(pics);
                activity.setPicResult(getSong().getIvUrl(), getLoadingWay());
            }
        });
    }

}