package com.example.q.pocketmusic.module.search.recommend;

import android.content.Context;
import android.content.Intent;

import com.example.q.pocketmusic.module.common.IBaseView;
import com.example.q.pocketmusic.config.Constant;
import com.example.q.pocketmusic.model.bean.Song;
import com.example.q.pocketmusic.model.bean.SongObject;
import com.example.q.pocketmusic.model.net.LoadRecommendList;
import com.example.q.pocketmusic.module.common.BasePresenter;
import com.example.q.pocketmusic.module.search.recommend.list.RecommendListActivity;
import com.example.q.pocketmusic.module.song.SongActivity;

import java.util.List;

/**
 * Created by 81256 on 2017/4/14.
 */

public class SearchRecommendFragmentPresenter extends BasePresenter<SearchRecommendFragmentPresenter.IView> {
    private IView fragment;

    public SearchRecommendFragmentPresenter(IView fragment) {
        attachView(fragment);
        this.fragment=getIViewRef();
    }


    //得到推荐列表
    public void getRecommendList() {
        String url = Constant.RECOMMEND_LIST_URL + "1" + ".html";
        new LoadRecommendList() {
            @Override
            protected void onPostExecute(List<Song> list) {
                super.onPostExecute(list);
                if (list == null) {
                    return;
                }
                for (int i = list.size() - 1; i >= 10; i--) {
                    list.remove(i);
                }
                fragment.setRecommendList(list);
            }
        }.execute(url);
    }

    //进入推荐列表
    public void enterRecommendListActivity() {
        fragment.getCurrentContext().startActivity(new Intent(fragment.getCurrentContext(), RecommendListActivity.class));
    }

    //进入歌曲详情
    public void enterSongActivityByRecommendTag(Song song) {
        Intent intent = new Intent(fragment.getCurrentContext(), SongActivity.class);
        SongObject object = new SongObject(song, Constant.FROM_RECOMMEND, Constant.SHOW_COLLECTION_MENU, Constant.NET);
        intent.putExtra(SongActivity.PARAM_SONG_OBJECT_PARCEL, object);
        fragment.getCurrentContext().startActivity(intent);
    }


    interface IView extends IBaseView {
        void setRecommendList(List<Song> list);
    }
}
