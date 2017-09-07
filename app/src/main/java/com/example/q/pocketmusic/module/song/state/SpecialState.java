package com.example.q.pocketmusic.module.song.state;

import com.example.q.pocketmusic.callback.ToastQueryListener;
import com.example.q.pocketmusic.config.Constant;
import com.example.q.pocketmusic.model.bean.Song;
import com.example.q.pocketmusic.model.bean.share.SharePic;
import com.example.q.pocketmusic.model.bean.special.SpecialColumnPic;
import com.example.q.pocketmusic.model.bean.special.SpecialColumnSong;
import com.example.q.pocketmusic.module.song.SongActivityPresenter;

import java.util.ArrayList;
import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.datatype.BmobPointer;

/**
 * Created by 81256 on 2017/9/7.
 */

public class SpecialState extends BaseState implements IState {
    private SpecialColumnSong specialColumnSong;
    private SongActivityPresenter.IView activity;

    public SpecialState(Song song, SpecialColumnSong specialColumnSong, SongActivityPresenter.IView activity) {
        super(song, Constant.NET);
        this.specialColumnSong = specialColumnSong;
        this.activity = activity;
    }

    @Override
    public void loadPic() {
        BmobQuery<SpecialColumnPic> query = new BmobQuery<>();
        query.addWhereEqualTo("song", new BmobPointer(specialColumnSong));
        query.findObjects(new ToastQueryListener<SpecialColumnPic>() {
            @Override
            public void onSuccess(List<SpecialColumnPic> list) {
                List<String> pics = new ArrayList<>();
                for (SpecialColumnPic pic : list) {
                    pics.add(pic.getUrl());
                }
                getSong().setIvUrl(pics);
                activity.setPicResult(getSong().getIvUrl(), getLoadingWay());
            }
        });
    }
}
