package com.example.q.pocketmusic.module.home.net.type;

import android.content.Intent;

import com.example.q.pocketmusic.config.Constant;
import com.example.q.pocketmusic.model.bean.Song;
import com.example.q.pocketmusic.model.bean.SongObject;
import com.example.q.pocketmusic.model.net.LoadTypeSongList;
import com.example.q.pocketmusic.module.common.BasePresenter;
import com.example.q.pocketmusic.module.common.IBaseView;
import com.example.q.pocketmusic.module.home.net.type.study.StudyActivity;
import com.example.q.pocketmusic.module.song.SongActivity;
import com.example.q.pocketmusic.util.InstrumentFlagUtil;

import java.util.List;


/**
 * Created by 鹏君 on 2016/8/29.
 */
public class SongTypeActivityPresenter extends BasePresenter<SongTypeActivityPresenter.IView> {
    private IView activity;
    private int mPage;

    public SongTypeActivityPresenter(IView activity) {
        attachView(activity);
        this.activity = getIViewRef();
    }

    public int getmPage() {
        return mPage;
    }


    public void getList(int typeId, final boolean isRefreshing) {
        String url = Constant.BASE_URL + "/qiyue/" + InstrumentFlagUtil.getUrl(typeId) + mPage + ".html";
        new LoadTypeSongList(typeId) {
            @Override
            protected void onPostExecute(List<Song> songs) {
                super.onPostExecute(songs);
                if (!isRefreshing) {
                    activity.setList(songs);
                } else {
                    activity.setListWithRefreshing(songs);
                }

            }
        }.execute(url);
    }


    public void setPage(int page) {
        this.mPage = page;
    }

    public void enterSongActivity(Song song) {
        Intent intent = new Intent(activity.getCurrentContext(), SongActivity.class);
        SongObject object = new SongObject(song, Constant.FROM_TYPE, Constant.MENU_DOWNLOAD_COLLECTION_SHARE, Constant.NET);
        intent.setExtrasClassLoader(getClass().getClassLoader());
        intent.putExtra(SongActivity.PARAM_SONG_OBJECT_SERIALIZEABLE, object);
        activity.getCurrentContext().startActivity(intent);
    }

    //进入学习版块
    public void enterStudyActivity(Integer typeId) {
        Intent intent = new Intent(activity.getCurrentContext(), StudyActivity.class);
        intent.putExtra(StudyActivity.PARAM_TYPE, typeId);
        activity.getCurrentContext().startActivity(intent);
    }

    public interface IView extends IBaseView {

        void setList(List<Song> songs);

        void setListWithRefreshing(List<Song> songs);
    }
}
