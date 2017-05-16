package com.example.q.pocketmusic.module.home.net;

import android.content.Intent;

import com.example.q.pocketmusic.callback.ToastQueryListener;
import com.example.q.pocketmusic.config.Constant;
import com.example.q.pocketmusic.model.bean.Song;
import com.example.q.pocketmusic.model.bean.SongObject;
import com.example.q.pocketmusic.model.bean.share.ShareSong;
import com.example.q.pocketmusic.model.net.LoadRecommendList;
import com.example.q.pocketmusic.module.common.BasePresenter;
import com.example.q.pocketmusic.module.common.IBaseList;
import com.example.q.pocketmusic.module.home.net.type.SongTypeActivity;
import com.example.q.pocketmusic.module.search.SearchMainActivity;
import com.example.q.pocketmusic.module.song.SongActivity;
import com.example.q.pocketmusic.module.user.suggestion.SuggestionActivity;
import com.example.q.pocketmusic.util.ACacheUtil;
import com.example.q.pocketmusic.util.LogUtils;

import java.util.List;

import cn.bmob.v3.exception.BmobException;

/**
 * Created by YQ on 2016/8/29.
 */
public class HomeNetFragmentPresenter extends BasePresenter<HomeNetFragmentPresenter.IView> {
    private IView fragment;
    private int mPage;

    public HomeNetFragmentPresenter(IView fragment) {
        attachView(fragment);
        this.fragment=getIViewRef();
    }

    //可以得到推荐列表
    public void getList() {
        String url = Constant.RECOMMEND_LIST_URL + mPage + ".html";
        new LoadRecommendList() {
            @Override
            protected void onPostExecute(List<Song> songs) {
                super.onPostExecute(songs);
                fragment.setList(songs);
            }
        }.execute(url);
    }

    public int getmPage() {
        return mPage;
    }


    public void setPage(int page) {
        this.mPage = page;
    }

    public void enterSongActivity(Song song) {
        Intent intent = new Intent(fragment.getCurrentContext(), SongActivity.class);
        SongObject object = new SongObject(song, Constant.FROM_RECOMMEND, Constant.SHOW_COLLECTION_MENU, Constant.NET);
        intent.putExtra(SongActivity.PARAM_SONG_OBJECT_PARCEL, object);
        fragment.getCurrentContext().startActivity(intent);
    }




    //进入乐器类型界面
    public void enterTypeActivity(int position) {
        Intent intent = new Intent(fragment.getCurrentContext(), SongTypeActivity.class);
        intent.putExtra(SongTypeActivity.PARAM_POSITION, position);
        fragment.getCurrentContext().startActivity(intent);
    }



    public void enterBannerActivity(int picPosition) {
//        Intent intent=new Intent(context,BannerActivity.class);
//        intent.putExtra(BannerActivity.PARAM_PIC_POSITION,picPosition);
//        context.startActivity(intent);
    }

    public void enterSearchMainActivity() {
        fragment.getCurrentContext().startActivity(new Intent(fragment.getCurrentContext(), SearchMainActivity.class));
    }

    public void enterSuggestionActivity() {
        fragment.getCurrentContext().startActivity(new Intent(fragment.getCurrentContext(), SuggestionActivity.class));
    }

    public interface IView extends IBaseList {
        void setList(List<Song> list);

    }

}
