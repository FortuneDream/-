package com.example.q.pocketmusic.module.home.local.localsong;

import android.content.Intent;

import com.example.q.pocketmusic.config.Constant;
import com.example.q.pocketmusic.data.bean.Song;
import com.example.q.pocketmusic.data.bean.SongObject;
import com.example.q.pocketmusic.data.bean.local.LocalSong;
import com.example.q.pocketmusic.data.db.LocalSongDao;
import com.example.q.pocketmusic.data.model.LocalModel;
import com.example.q.pocketmusic.module.common.BasePresenter;
import com.example.q.pocketmusic.module.common.IBaseView;
import com.example.q.pocketmusic.module.home.net.type.community.share.publish.ShareActivity;
import com.example.q.pocketmusic.module.song.SongActivity;
import com.example.q.pocketmusic.util.common.LogUtils;
import com.example.q.pocketmusic.util.common.ToastUtil;

import java.util.List;

import rx.functions.Action1;

/**
 * Created by 鹏君 on 2016/9/2.
 */
public class LocalSongFragmentPresenter extends BasePresenter<LocalSongFragmentPresenter.IView> {
    private IView fragment;
    private LocalSongDao localSongDao;
    private LocalModel localModel;


    //Dao有必要关闭吗？iterator呢？
    public LocalSongFragmentPresenter(IView fragment) {
        attachView(fragment);
        this.fragment = getIViewRef();
        localModel = new LocalModel(fragment.getAppContext());
        localSongDao = new LocalSongDao(fragment.getAppContext());
    }

    public void loadLocalSong() {
        if (fragment.getAppContext() == null) {
            return;
        }
        localModel.getLocalSongList(new Action1<List<LocalSong>>() {
            @Override
            public void call(List<LocalSong> localSongs) {
                fragment.setList(localSongs);
                LogUtils.e(TAG, "本地乐谱数量：" + localSongs.size());
            }
        });
    }

    //删除乐谱要删除数据库和list.position,还有本地的文件！
    public void deleteSong(LocalSong localSong) {
        localSongDao.deleteLocalRelation(fragment.getCurrentContext(), localSong);
    }

    //同步乐谱
    public void synchronizedSong() {
        //先遍历文件夹的图片，添加到数据库（不重复添加），然后再从数据库取出来
        localModel.synchronizeLocalSong(new Action1<Boolean>() {
            @Override
            public void call(Boolean aBoolean) {
                loadLocalSong();
            }
        });
    }


    public void enterShareActivity(LocalSong localSong) {
        Intent intent = new Intent(fragment.getCurrentContext(), ShareActivity.class);
        intent.putExtra(ShareActivity.PARAM_LOCAL_SONG, localSong);
        intent.putExtra(ShareActivity.PARAM_TYPE_ID, 0);
        fragment.getCurrentContext().startActivity(intent);
    }

    public void enterSongActivity(LocalSong localSong) {
        Intent intent = new Intent(fragment.getCurrentContext(), SongActivity.class);
        Song song = new Song();
        song.setName(localSong.getName());
        SongObject songObject = new SongObject(song, Constant.FROM_LOCAL, Constant.SHOW_NO_MENU, Constant.LOCAL);
        intent.putExtra(SongActivity.PARAM_SONG_OBJECT_SERIALIZABLE, songObject);
        intent.putExtra(SongActivity.LOCAL_SONG, localSong);
        fragment.getCurrentContext().startActivity(intent);
    }

    public void setTop(LocalSong item) {
        localModel.setTop(item);
        ToastUtil.showToast("已置顶");
        fragment.onRefresh();
    }


    public interface IView extends IBaseView {
        void setList(List<LocalSong> list);


        void onRefresh();
    }
}
