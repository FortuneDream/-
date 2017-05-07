package com.example.q.pocketmusic.module.home.net;

import android.content.Context;
import android.content.Intent;

import com.example.q.pocketmusic.module.common.IBaseList;
import com.example.q.pocketmusic.callback.ToastQueryListener;
import com.example.q.pocketmusic.config.Constant;
import com.example.q.pocketmusic.model.bean.Song;
import com.example.q.pocketmusic.model.bean.SongObject;
import com.example.q.pocketmusic.model.bean.share.ShareSong;
import com.example.q.pocketmusic.module.common.BasePresenter;

import com.example.q.pocketmusic.module.home.net.type.SongTypeActivity;
import com.example.q.pocketmusic.module.search.SearchMainActivity;
import com.example.q.pocketmusic.module.song.SongActivity;
import com.example.q.pocketmusic.util.ACacheUtil;

import java.util.List;

import cn.bmob.v3.exception.BmobException;

/**
 * Created by YQ on 2016/8/29.
 */
public class HomeNetFragmentPresenter extends BasePresenter {
    private IView fragment;
    private Context context;
    private int mPage;
    private HomeNetModel homeNetModel;

    public HomeNetFragmentPresenter(IView fragment, Context context) {
        this.fragment = fragment;
        this.context = context;
        homeNetModel = new HomeNetModel();
    }

    public void loadMore() {
        mPage++;
        homeNetModel.getMoreShareList(mPage, new ToastQueryListener<ShareSong>(context, fragment) {
            @Override
            public void onSuccess(List<ShareSong> list) {
                fragment.setMore(list);
            }
        });

    }

    public void getShareList() {
        homeNetModel.getInitShareList(new ToastQueryListener<ShareSong>(context, fragment) {
            @Override
            public void onSuccess(List<ShareSong> list) {
                ACacheUtil.putShareSongCache(context, list);//添加缓存
                fragment.setList(list);
            }

            @Override
            public void onFail(BmobException e) {
                super.onFail(e);
                fragment.setList(ACacheUtil.getShareSongCache(context));
            }
        });

    }

    public void setSharePage(int page) {
        this.mPage = page;
    }

    //通过分享乐曲item进入SongActivity
    public void enterSongActivityByShare(ShareSong shareSong) {
        Song song = new Song();
        song.setNeedGrade(true);//需要积分
        song.setContent(shareSong.getContent());
        song.setName(shareSong.getName());
        Intent intent = new Intent(context, SongActivity.class);
        SongObject songObject = new SongObject(song, Constant.FROM_SHARE, Constant.SHOW_COLLECTION_MENU, Constant.NET);
        intent.putExtra(SongActivity.PARAM_SONG_OBJECT_PARCEL, songObject);
        intent.putExtra(SongActivity.SHARE_SONG, shareSong);
        context.startActivity(intent);
    }

    //进入乐器类型界面
    public void enterTypeActivity(int position) {
        Intent intent = new Intent(context, SongTypeActivity.class);
        intent.putExtra(SongTypeActivity.PARAM_POSITION, position);
        context.startActivity(intent);
    }

    //获取缓存
    public void getCacheList() {
        List<ShareSong> list = ACacheUtil.getShareSongCache(context);//先获取缓存
        if (list == null) {
            getShareList();
        }
        fragment.setList(list);
    }

    public void enterBannerActivity(int picPosition) {
//        Intent intent=new Intent(context,BannerActivity.class);
//        intent.putExtra(BannerActivity.PARAM_PIC_POSITION,picPosition);
//        context.startActivity(intent);
    }

    public void enterSearchMainActivity() {
        context.startActivity(new Intent(context, SearchMainActivity.class));
    }

    public interface IView extends IBaseList {
        void setList(List<ShareSong> list);

        void setMore(List<ShareSong> list);

    }

}
