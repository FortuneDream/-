package com.example.q.pocketmusic.module.home.profile.user.other.collection;

import android.content.Intent;

import com.example.q.pocketmusic.callback.ToastQueryListener;
import com.example.q.pocketmusic.config.constant.BmobConstant;
import com.example.q.pocketmusic.config.constant.Constant;
import com.example.q.pocketmusic.config.constant.IntentConstant;
import com.example.q.pocketmusic.data.bean.MyUser;
import com.example.q.pocketmusic.data.bean.Song;
import com.example.q.pocketmusic.data.bean.SongObject;
import com.example.q.pocketmusic.data.bean.collection.CollectionPic;
import com.example.q.pocketmusic.data.bean.collection.CollectionSong;
import com.example.q.pocketmusic.data.model.UserCollectionModel;
import com.example.q.pocketmusic.module.common.BasePresenter;
import com.example.q.pocketmusic.module.common.IBaseView;
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
    private UserCollectionModel userCollectionModel;
    private int mPage;

    public OtherCollectionPresenter(IView fragment) {
        super(fragment);
        userCollectionModel = new UserCollectionModel();
    }

    public void getOtherCollectionList(MyUser other, final boolean isRefreshing) {
        BmobQuery<CollectionSong> query = new BmobQuery<>();
        query.setLimit(10);
        query.addWhereRelatedTo("collections", new BmobPointer(other));
        query.order(BmobConstant.BMOB_CREATE_AT);
        query.findObjects(new ToastQueryListener<CollectionSong>() {
            @Override
            public void onSuccess(List<CollectionSong> list) {
                if (!isRefreshing) {
                    mView.setOtherCollectionList(list);
                } else {
                    mView.setOtherCollectionListWithRefreshing(list);
                }

            }
        });

    }

    public void enterSongActivity(final CollectionSong collectionSong) {
        mView.showLoading(true);
        userCollectionModel.getCollectionPicList(collectionSong, new ToastQueryListener<CollectionPic>() {
            @Override
            public void onSuccess(List<CollectionPic> list) {
                mView.showLoading(false);
                Song song = new Song();
                song.setName(collectionSong.getName());
                song.setContent(collectionSong.getContent());
                List<String> urls = new ArrayList<>();
                for (CollectionPic pic : list) {
                    urls.add(pic.getUrl());
                }
                song.setIvUrl(urls);
                Intent intent = new Intent(mContext, SongActivity.class);
                SongObject songObject = new SongObject(song, Constant.FROM_COLLECTION, Constant.MENU_DOWNLOAD_SHARE, Constant.NET);
                intent.putExtra(IntentConstant.EXTRA_SONG_ACTIVITY_SONG_OBJECT, songObject);
                mContext.startActivity(intent);
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
        query.order(BmobConstant.BMOB_CREATE_AT);
        query.findObjects(new ToastQueryListener<CollectionSong>() {
            @Override
            public void onSuccess(List<CollectionSong> list) {
                mView.setOtherCollectionList(list);

            }
        });

    }

    public interface IView extends IBaseView {

        void setOtherCollectionList(List<CollectionSong> list);

        void setOtherCollectionListWithRefreshing(List<CollectionSong> list);
    }
}
