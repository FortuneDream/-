package com.example.q.pocketmusic.module.home.profile.collection;

import android.content.Intent;

import com.example.q.pocketmusic.callback.ToastQueryListener;
import com.example.q.pocketmusic.callback.ToastUpdateListener;
import com.example.q.pocketmusic.config.Constant;
import com.example.q.pocketmusic.model.bean.MyUser;
import com.example.q.pocketmusic.model.bean.Song;
import com.example.q.pocketmusic.model.bean.SongObject;
import com.example.q.pocketmusic.model.bean.collection.CollectionPic;
import com.example.q.pocketmusic.model.bean.collection.CollectionSong;
import com.example.q.pocketmusic.module.common.BasePresenter;
import com.example.q.pocketmusic.module.common.IBaseView;
import com.example.q.pocketmusic.module.song.SongActivity;
import com.example.q.pocketmusic.util.common.ToastUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by 鹏君 on 2016/11/14.
 */

public class CollectionPresenter extends BasePresenter<CollectionPresenter.IView> {
    private IView activity;

    private MyUser user;
    private CollectionModel collectionModel;
    private int mPage;

    public void setUser(MyUser user) {
        this.user = user;
    }

    public CollectionPresenter(IView activity) {
        attachView(activity);
        this.activity=getIViewRef();
        collectionModel = new CollectionModel();

    }

    //获得收藏曲谱列表
    public void getCollectionList(final boolean isRefreshing) {
        collectionModel.getInitCollectionList(user, new ToastQueryListener<CollectionSong>(activity) {
            @Override
            public void onSuccess(List<CollectionSong> list) {
                if (!isRefreshing){
                    activity.setCollectionList(list);
                }else {
                    activity.setCollectionListWithRefreshing(list);
                }


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
                List<String> urls = new ArrayList<>();
                for (CollectionPic pic : list) {
                    urls.add(pic.getUrl());
                }
                song.setIvUrl(urls);
                song.setNeedGrade(collectionSong.getNeedGrade());//设置消耗
                Intent intent = new Intent(activity.getCurrentContext(), SongActivity.class);
                SongObject songObject = new SongObject(song, Constant.FROM_COLLECTION, Constant.SHOW_ONLY_DOWNLOAD, Constant.NET);
                intent.setExtrasClassLoader(getClass().getClassLoader());
                intent.putExtra(SongActivity.PARAM_SONG_OBJECT_PARCEL, songObject);
                activity.getCurrentContext().startActivity(intent);
            }
        });
    }

    //删除收藏
    public void deleteCollection(final CollectionSong collectionSong) {
        collectionModel.deleteCollection(user, collectionSong, activity, new ToastUpdateListener(activity) {
            @Override
            public void onSuccess() {
                ToastUtil.showToast( "已删除");
            }
        });
    }

    public void setPage(int page) {
        this.mPage = page;
    }


    public interface IView extends IBaseView {

        void setCollectionList(List<CollectionSong> list);

        void setCollectionListWithRefreshing(List<CollectionSong> list);
    }
}
