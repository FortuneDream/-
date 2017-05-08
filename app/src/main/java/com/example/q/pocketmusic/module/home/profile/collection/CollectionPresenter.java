package com.example.q.pocketmusic.module.home.profile.collection;

import android.content.Context;
import android.content.Intent;

import com.example.q.pocketmusic.module.common.BasePresenter;
import com.example.q.pocketmusic.module.common.IBaseList;
import com.example.q.pocketmusic.callback.ToastQueryListener;
import com.example.q.pocketmusic.callback.ToastUpdateListener;
import com.example.q.pocketmusic.config.Constant;
import com.example.q.pocketmusic.model.bean.MyUser;
import com.example.q.pocketmusic.model.bean.Song;
import com.example.q.pocketmusic.model.bean.SongObject;
import com.example.q.pocketmusic.model.bean.collection.CollectionPic;
import com.example.q.pocketmusic.model.bean.collection.CollectionSong;
import com.example.q.pocketmusic.module.song.SongActivity;
import com.example.q.pocketmusic.util.MyToast;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Cloud on 2016/11/14.
 */

public class CollectionPresenter extends BasePresenter<CollectionPresenter.IView> {
    private IView activity;

    private MyUser user;
    private CollectionModel collectionModel;
    private int mPage;

    public void setUser(MyUser user) {
        this.user = user;
    }

    public CollectionPresenter() {
        activity = getIViewRef();
        collectionModel = new CollectionModel();

    }

    //获得收藏曲谱列表
    public void getCollectionList() {
        collectionModel.getInitCollectionList(user, new ToastQueryListener<CollectionSong>(activity) {
            @Override
            public void onSuccess(List<CollectionSong> list) {
                activity.setCollectionList(list);
            }
        });
    }

    //加载更多
    public void getMoreList() {
        mPage++;
        collectionModel.getMoreList(user, mPage, new ToastQueryListener<CollectionSong>(activity) {
            @Override
            public void onSuccess(List<CollectionSong> list) {
                activity.setCollectionList(list);
            }
        });
    }

    //先查询，后进入SongActivity
    public void queryAndEnterSongActivity(final CollectionSong collectionSong) {
        activity.showLoading(true);
        collectionModel.querySong(collectionSong, new ToastQueryListener<CollectionPic>(activity) {
            @Override
            public void onSuccess(List<CollectionPic> list) {
                activity.showLoading(false);
                Song song = new Song();
                song.setName(collectionSong.getName());
                song.setContent(collectionSong.getContent());
                List<String> urls = new ArrayList<String>();
                for (CollectionPic pic : list) {
                    urls.add(pic.getUrl());
                }
                song.setIvUrl(urls);
                song.setNeedGrade(collectionSong.getNeedGrade());//设置消耗
                Intent intent = new Intent(activity.getCurrentContext(), SongActivity.class);
                SongObject songObject = new SongObject(song, Constant.FROM_COLLECTION, Constant.SHOW_ONLY_DOWNLOAD, Constant.NET);
                intent.putExtra(SongActivity.PARAM_SONG_OBJECT_PARCEL, songObject);
                activity.getCurrentContext().startActivity(intent);
            }
        });
    }

    //删除收藏
    public void deleteCollection(final CollectionSong collectionSong) {
        collectionModel.deleteCollection(user, collectionSong, activity.getCurrentContext(), activity, new ToastUpdateListener(activity) {
            @Override
            public void onSuccess() {
                MyToast.showToast(activity.getCurrentContext(), "已删除");
            }
        });
    }

    public void setPage(int page) {
        this.mPage = page;
    }


    public interface IView extends IBaseList {

        void setCollectionList(List<CollectionSong> list);

    }
}
