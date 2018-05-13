package com.example.q.pocketmusic.data.model;

import android.content.Context;
import android.media.MediaPlayer;
import android.os.Environment;
import android.support.annotation.NonNull;

import com.dell.fortune.tools.info.SharedPrefsUtil;
import com.example.q.pocketmusic.config.constant.Constant;
import com.example.q.pocketmusic.data.bean.local.Img;
import com.example.q.pocketmusic.data.bean.local.LocalSong;
import com.example.q.pocketmusic.data.bean.local.RecordAudio;
import com.example.q.pocketmusic.data.db.ImgDao;
import com.example.q.pocketmusic.data.db.LocalSongDao;
import com.example.q.pocketmusic.data.db.RecordAudioDao;
import com.example.q.pocketmusic.util.RxApi;
import com.example.q.pocketmusic.util.SortUtil;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.Callable;

import io.reactivex.Single;

/**
 * Created by 鹏君 on 2017/7/27.
 * （￣m￣）
 */

public class LocalModel {
    public static final SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy.MM.dd HH:mm", Locale.CHINA);
    private RecordAudioDao recordAudioDao;
    private LocalSongDao localSongDao;
    private ImgDao imgDao;

    public LocalModel(Context context) {
        recordAudioDao = new RecordAudioDao(context);
        localSongDao = new LocalSongDao(context);
        imgDao = new ImgDao(context);
    }

    //同步本地录音
    public Single<List<RecordAudio>> synchronizeRecordAudio() {
        return RxApi.create(new Callable<List<RecordAudio>>() {
            @Override
            public List<RecordAudio> call() throws Exception {
                updateRecordAudio();
                return recordAudioDao.queryForAll();
            }
        });
    }

    //同步本地曲谱
    public Single<List<LocalSong>> synchronizeLocalSong() {
        return RxApi.create(new Callable<List<LocalSong>>() {
            @Override
            public List<LocalSong> call() throws Exception {
                updateLocalSong();
                return localSongDao.queryForAll();
            }
        });
    }

    //得到本地曲谱列表
    public Single<List<LocalSong>> getLocalSongList() {
        return RxApi.create(new Callable<List<LocalSong>>() {
            @Override
            public List<LocalSong> call() throws Exception {
                return localSongDao.queryForAll();
            }
        });
    }

    //置顶
    public void setTop(LocalSong item) {
        int top_value = SharedPrefsUtil.getInt(SortUtil.sort_key, SortUtil.sort_value);
        top_value++;
        item.setSort(top_value);
        SharedPrefsUtil.putInt(SortUtil.sort_key, top_value);//修改最高值
        localSongDao.update(item);
    }


    //更新记录
    private void updateRecordAudio() {
        MediaPlayer mediaPlayer = new MediaPlayer();
        File dir = new File(Environment.getExternalStorageDirectory() + "/" + Constant.RECORD_FILE);
        if (dir.exists()) {
            File[] files = dir.listFiles();
            for (File file : files) {
                try {
                    RecordAudio recordAudio = getRecordAudio(mediaPlayer, file);
                    recordAudioDao.add(recordAudio);//同步时有可能出现名字相同的情况
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    //测量，并得到RecordAudio
    @NonNull
    private RecordAudio getRecordAudio(MediaPlayer mediaPlayer, File file) throws IOException {
        //长度
        RecordAudio recordAudio = new RecordAudio();
        //时间
        recordAudio.setDate(dateFormat.format(new Date(file.lastModified())));
        //名字
        String name = file.getName();
        recordAudio.setName(name.replace(".3gp", ""));//去掉后缀名再保存到数据库
        //路径
        recordAudio.setPath(file.getAbsolutePath());
        mediaPlayer.reset();
        mediaPlayer.setDataSource(file.getAbsolutePath());
        mediaPlayer.prepare();//同步
        recordAudio.setDuration(mediaPlayer.getDuration());
        return recordAudio;
    }


    //更新本地曲谱
    private boolean updateLocalSong() {
        File dir = new File(Environment.getExternalStorageDirectory() + "/" + Constant.FILE_NAME + "/");
        if (!dir.exists()) {
            dir.mkdirs();
        }
        File[] files = dir.listFiles();
        for (File file : files) {
            addLocalSong(file);
        }
        return true;
    }

    //添加LocalSong 进数据库
    private void addLocalSong(File file) {
        //所有图片
        File[] imgFiles = file.listFiles();
        //没有图片,直接删除文件夹
        if (imgFiles == null) {
            file.delete();
            return;
        }
        LocalSong localSong = new LocalSong();
        localSong.setDate(dateFormat.format(new Date()));
        String name = file.getName();
        localSong.setName(name);
        boolean isSuccess = localSongDao.add(localSong);//LocalSong要先加入数据库
        if (!isSuccess) {
            return;
        }
        for (File imgFile : imgFiles) {
            Img img = new Img();
            img.setUrl(imgFile.getAbsolutePath());
            img.setLocalSong(localSong);
            imgDao.add(img);
        }
    }
}
