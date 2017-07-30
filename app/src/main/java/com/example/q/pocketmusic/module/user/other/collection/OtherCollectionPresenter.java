package com.example.q.pocketmusic.module.user.other.collection;

import android.content.Intent;

import com.example.q.pocketmusic.callback.ToastQueryListener;
import com.example.q.pocketmusic.config.BmobConstant;
import com.example.q.pocketmusic.config.Constant;
import com.example.q.pocketmusic.model.bean.MyUser;
import com.example.q.pocketmusic.model.bean.Song;
import com.example.q.pocketmusic.model.bean.SongObject;
import com.example.q.pocketmusic.model.bean.collection.CollectionPic;
import com.example.q.pocketmusic.model.bean.collection.CollectionSong;
import com.example.q.pocketmusic.module.common.BasePresenter;
import com.example.q.pocketmusic.module.common.IBaseView;
import com.example.q.pocketmusic.module.home.profile.collection.CollectionModel;
import com.example.q.pocketmusic.module.song.SongActivity;

import java.util.ArrayList;
import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.datatype.BmobPointer;

/**
 * Created by 鹏君 on 2017/7/24.
 * （￣m￣）
 */

public class OtherCollectionPresenter extends BasePresenter<OtherCollectionPresenter.IView> {
    private IView fragment;
    private CollectionModel collectionModel;
    private int mPage;

    public OtherCollectionPresenter(IView fragment) {
        attachView(fragment);
        this.fragment = getIViewRef();
        collectionModel = new CollectionModel();
    }

    public void getOtherCollectionList(MyUser other, final boolean isRefreshing) {
        BmobQuery<CollectionSong> query = new BmobQuery<>();
        query.setLimit(10);
        query.addWhereRelatedTo("collections", new BmobPointer(other));
        query.order( BmobConstant.BMOB_CREATE_AT);
        query.findObjects(new ToastQueryListener<CollectionSong>() {
            @Override
            public void onSuccess(List<CollectionSong> list) {
                if (!isRefreshing) {
                    fragment.setOtherCollectionList(list);
                } else {
                    fragment.setOtherCollectionListWithRefreshing(list);
                }

            }
        });

    }

    public void enterSongActivity(final CollectionSong collectionSong) {
        fragment.showLoading(true);
        collectionModel.getCollectionPicList(collectionSong, new ToastQueryListener<CollectionPic>(fragment) {
            @Override
            public void onSuccess(List<CollectionPic> list) {
                fragment.showLoading(false);
                Song song = new Song();
                song.setName(collectionSong.getName());
                song.setContent(collectionSong.getContent());
                List<String> urls = new ArrayList<>();
                for (CollectionPic pic : list) {
                    urls.add(pic.getUrl());
                }
                song.setIvUrl(urls);
                Intent intent = new Intent(fragment.getCurrentContext(), SongActivity.class);
                SongObject songObject = new SongObject(song, Constant.FROM_COLLECTION, Constant.SHOW_ONLY_DOWNLOAD, Constant.NET);
                intent.putExtra(SongActivity.PARAM_SONG_OBJECT_SERIALIZEABLE, songObject);
                fragment.getCurrentContext().startActivity(intent);
            }
        });
    }

    public void setPage(int page) {
        this.mPage = page;
    }

    public void getMoreOtherCollectionList(MyUser other) {
        mPage++;
        BmobQuery<CollectionSong> query = new BmobQuery<>();
        query.addWhereRelatedTo("collections", new BmobPointer(other));
        query.setLimit(10);
        query.setSkip(mPage * 10);
        query.order( BmobConstant.BMOB_CREATE_AT);
        query.findObjects(new ToastQueryListener<CollectionSong>() {
            @Override
            public void onSuccess(List<CollectionSong> list) {
                fragment.setOtherCollectionList(list);

            }
        });

    }

    public interface IView extends IBaseView {

        void setOtherCollectionList(List<CollectionSong> list);

        void setOtherCollectionListWithRefreshing(List<CollectionSong> list);
    }
}
