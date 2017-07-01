package com.example.q.pocketmusic.module.home.local.localsong;

import android.content.Intent;
import android.database.SQLException;

import com.example.q.pocketmusic.config.Constant;
import com.example.q.pocketmusic.model.bean.Song;
import com.example.q.pocketmusic.model.bean.SongObject;
import com.example.q.pocketmusic.model.bean.local.Img;
import com.example.q.pocketmusic.model.bean.local.LocalSong;
import com.example.q.pocketmusic.model.db.ImgDao;
import com.example.q.pocketmusic.model.db.LocalSongDao;
import com.example.q.pocketmusic.model.net.LoadLocalSongList;
import com.example.q.pocketmusic.model.net.SynchronizeLocalSong;
import com.example.q.pocketmusic.module.common.BasePresenter;
import com.example.q.pocketmusic.module.common.IBaseView;
import com.example.q.pocketmusic.module.share.ShareActivity;
import com.example.q.pocketmusic.module.song.SongActivity;
import com.example.q.pocketmusic.util.LogUtils;
import com.example.q.pocketmusic.util.ToastUtil;
import com.example.q.pocketmusic.util.SharedPrefsUtil;
import com.j256.ormlite.dao.CloseableIterator;
import com.j256.ormlite.dao.ForeignCollection;

import java.io.File;
import java.io.IOException;
import java.util.List;

/**
 * Created by 鹏君 on 2016/9/2.
 */
public class LocalSongFragmentPresenter extends BasePresenter<LocalSongFragmentPresenter.IView> {
    private IView fragment;
    private LocalSongDao localSongDao;
    private ImgDao imgDao;


    //Dao有必要关闭吗？iterator呢？
    public LocalSongFragmentPresenter(IView fragment) {
        attachView(fragment);
        this.fragment = getIViewRef();
        localSongDao = new LocalSongDao(fragment.getAppContext());
        imgDao = new ImgDao(fragment.getAppContext());

    }

    public void loadLocalSong() {
        if (fragment.getAppContext() == null) {
            return;
        }
        new LoadLocalSongList(localSongDao, fragment.getAppContext()) {
            @Override
            protected void onPostExecute(List<LocalSong> localSongs) {
                super.onPostExecute(localSongs);
                fragment.setList(localSongs);
                LogUtils.e(TAG, "本地乐谱数量：" + localSongs.size());
            }
        }.execute();

    }

    //删除乐谱要删除数据库和list.position,还有本地的文件！
    public void deleteSong(LocalSong localSong) {
        deleteFromDatabase(localSong);
    }

    private void deleteFromDatabase(LocalSong localSong) {
        //从数据库删除
        localSongDao.delete(localSong);
        ForeignCollection<Img> imgs = localSong.getImgs();
        CloseableIterator<Img> iterator = imgs.closeableIterator();
        String parent = null;
        try {
            while (iterator.hasNext()) {
                Img img = iterator.next();
                imgDao.delete(img);
                //删除文件
                File file = new File(img.getUrl());
                parent = file.getParent();
                if (file.exists()) {
                    file.delete();
                }
            }
            //删除文件夹
            if (parent != null) {
                new File(parent).delete();
            }
        } finally {
            try {
                iterator.close();
            } catch (SQLException | IOException e) {
                e.printStackTrace();
            }
        }
    }

    //同步乐谱
    public void synchronizedSong() {
        //先遍历文件夹的图片，添加到数据库（不重复添加），然后再从数据库取出来
        new SynchronizeLocalSong(imgDao, localSongDao) {
            @Override
            protected void onPostExecute(Void aVoid) {
                super.onPostExecute(aVoid);
                loadLocalSong();
            }
        }.execute();
    }


    public void enterShareActivity(LocalSong localSong) {
        Intent intent = new Intent(fragment.getCurrentContext(), ShareActivity.class);
        intent.putExtra(ShareActivity.LOCAL_SONG, localSong);
        fragment.getCurrentContext().startActivity(intent);
    }

    public void enterPictureActivity(LocalSong localSong) {
        Intent intent = new Intent(fragment.getCurrentContext(), SongActivity.class);
        Song song = new Song();
        song.setName(localSong.getName());
        SongObject songObject = new SongObject(song, Constant.FROM_LOCAL, Constant.SHOW_NO_MENU, Constant.LOCAL);
        intent.putExtra(SongActivity.LOCAL_SONG, localSong);
        intent.putExtra(SongActivity.PARAM_SONG_OBJECT_PARCEL, songObject);
        fragment.getCurrentContext().startActivity(intent);
    }

    public void setTop(LocalSong item) {
        int top_value = SharedPrefsUtil.getInt(Constant.sort_key, Constant.sort_value);
        top_value++;
        item.setSort(top_value);
        SharedPrefsUtil.putInt(Constant.sort_key, top_value);//修改最高值
        localSongDao.update(item);
        ToastUtil.showToast( "已置顶");
        fragment.onRefresh();
    }


    public interface IView extends IBaseView {
        void setList(List<LocalSong> list);


        void onRefresh();
    }
}
