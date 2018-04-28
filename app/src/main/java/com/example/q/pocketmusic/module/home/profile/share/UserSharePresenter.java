package com.example.q.pocketmusic.module.home.profile.share;

import android.content.Intent;

import com.example.q.pocketmusic.callback.ToastQueryListener;
import com.example.q.pocketmusic.callback.ToastUpdateListener;
import com.example.q.pocketmusic.config.constant.Constant;
import com.example.q.pocketmusic.config.constant.IntentConstant;
import com.example.q.pocketmusic.data.bean.Song;
import com.example.q.pocketmusic.data.bean.SongObject;
import com.example.q.pocketmusic.data.bean.share.ShareSong;
import com.example.q.pocketmusic.data.model.UserShareModel;
import com.example.q.pocketmusic.module.common.BasePresenter;
import com.example.q.pocketmusic.module.common.IBaseView;
import com.example.q.pocketmusic.module.song.SongActivity;
import com.example.q.pocketmusic.util.UserUtil;
import com.example.q.pocketmusic.util.common.ToastUtil;

import java.util.List;

/**
 * Created by 鹏君 on 2017/5/26.
 */

public class UserSharePresenter extends BasePresenter<UserSharePresenter.IView> {

    private int mPage;
    private UserShareModel model;

    public UserSharePresenter(IView activity) {
        super(activity);
        model = new UserShareModel();
        this.mPage=0;
    }

    public void getUserShareList(final boolean isRefreshing) {
        mPage++;
        if (isRefreshing) {
            mPage = 0;
        }
        model.getUserShareList(UserUtil.user, mPage, new ToastQueryListener<ShareSong>() {
            @Override
            public void onSuccess(List<ShareSong> list) {
                mView.setList(isRefreshing, list);
            }
        });
    }



    public void enterSongActivity(ShareSong shareSong) {
        Song song = new Song();
        song.setContent(shareSong.getContent());
        song.setName(shareSong.getName());
        SongObject songObject = new SongObject(song, Constant.FROM_SHARE, Constant.MENU_DOWNLOAD_COLLECTION_AGREE_SHARE, Constant.NET);
        songObject.setCommunity(shareSong.getInstrument());
        mView.getCurrentContext().startActivity(
                SongActivity.buildShareIntent(mView.getCurrentContext(),songObject,shareSong.getInstrument(),shareSong)
        );
    }

    public void updateShareName(ShareSong item, String nickName) {
        model.updateShareName(item, nickName, new ToastUpdateListener() {
            @Override
            public void onSuccess() {
                mView.setList(true, null);
            }
        });
    }

    public void deleteShareSong(ShareSong shareSong) {
        model.deleteShareSong(shareSong, new ToastUpdateListener() {
            @Override
            public void onSuccess() {
                ToastUtil.showToast("已删除");
            }
        });
    }

    public void updateShareType(ShareSong item,int type) {
        model.updateShareType(item,type, new ToastUpdateListener() {
            @Override
            public void onSuccess() {
                getUserShareList(true);//刷新一下
            }
        });
    }


    interface IView extends IBaseView {
        void setList(boolean isRefreshing, List<ShareSong> list);

    }
}
