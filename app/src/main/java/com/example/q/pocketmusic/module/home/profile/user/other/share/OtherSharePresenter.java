package com.example.q.pocketmusic.module.home.profile.user.other.share;

import android.content.Intent;

import com.example.q.pocketmusic.callback.ToastQueryListener;
import com.example.q.pocketmusic.config.constant.BmobConstant;
import com.example.q.pocketmusic.config.constant.Constant;
import com.example.q.pocketmusic.data.bean.MyUser;
import com.example.q.pocketmusic.data.bean.Song;
import com.example.q.pocketmusic.data.bean.SongObject;
import com.example.q.pocketmusic.data.bean.share.ShareSong;
import com.example.q.pocketmusic.module.common.BasePresenter;
import com.example.q.pocketmusic.module.common.IBaseView;
import com.example.q.pocketmusic.module.song.SongActivity;

import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.datatype.BmobPointer;

/**
 * Created by 鹏君 on 2017/7/24.
 * （￣m￣）
 */

public class OtherSharePresenter extends BasePresenter<OtherSharePresenter.IView> {
    private IView fragment;
    private int mPage;

    public OtherSharePresenter(IView fragment) {
        attachView(fragment);
        this.fragment = getIViewRef();
    }


    public void enterSongActivity(ShareSong shareSong) {
        Song song = new Song();
        song.setContent(shareSong.getContent());
        song.setName(shareSong.getName());
        SongObject songObject = new SongObject(song, Constant.FROM_SHARE, Constant.MENU_DOWNLOAD_COLLECTION_AGREE_SHARE, Constant.NET);
        fragment.getCurrentContext().startActivity(
                SongActivity.buildShareIntent(fragment.getCurrentContext(),songObject,shareSong.getInstrument(),shareSong));
    }

    public void getOtherCollectionList(MyUser other, final boolean isRefreshing) {
        BmobQuery<ShareSong> query = new BmobQuery<>();
        query.setLimit(10);
        query.include("user");
        query.addWhereEqualTo("user", new BmobPointer(other));
        query.order(BmobConstant.BMOB_CREATE_AT);
        query.findObjects(new ToastQueryListener<ShareSong>() {
            @Override
            public void onSuccess(List<ShareSong> list) {
                if (!isRefreshing) {
                    fragment.setOtherShareList(list);
                } else {
                    fragment.setOtherShareListWithRefreshing(list);
                }

            }
        });
    }

    public void setPage(int page) {
        this.mPage = page;
    }

    public void getMoreOtherCollectionList(MyUser other) {
        mPage++;
        BmobQuery<ShareSong> query = new BmobQuery<>();
        query.setLimit(10);
        query.setSkip(10 * mPage);
        query.include("user");
        query.addWhereEqualTo("user", new BmobPointer(other));
        query.order(BmobConstant.BMOB_CREATE_AT);
        query.findObjects(new ToastQueryListener<ShareSong>() {
            @Override
            public void onSuccess(List<ShareSong> list) {
                fragment.setOtherShareList(list);
            }
        });
    }


    public interface IView extends IBaseView {

        void setOtherShareList(List<ShareSong> list);

        void setOtherShareListWithRefreshing(List<ShareSong> list);
    }
}
