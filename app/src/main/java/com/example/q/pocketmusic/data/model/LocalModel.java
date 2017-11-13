package com.example.q.pocketmusic.data.model;

import android.content.Context;
import android.media.MediaPlayer;
import android.os.Environment;
import android.support.annotation.NonNull;

import com.example.q.pocketmusic.config.Constant;
import com.example.q.pocketmusic.data.bean.local.Img;
import com.example.q.pocketmusic.data.bean.local.LocalSong;
import com.example.q.pocketmusic.data.bean.local.RecordAudio;
import com.example.q.pocketmusic.data.db.ImgDao;
import com.example.q.pocketmusic.data.db.LocalSongDao;
import com.example.q.pocketmusic.data.db.RecordAudioDao;
import com.example.q.pocketmusic.util.SortUtil;
import com.example.q.pocketmusic.util.common.SharedPrefsUtil;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.schedulers.Schedulers;

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
    public void synchronizeRecordAudio(Action1<Boolean> isSucceedListener) {
        Observable.create(new Observable.OnSubscribe<Boolean>() {
            @Override
            public void call(Subscriber<? super Boolean> subscriber) {
                boolean isSucceed = updateRecordAudio();
                subscriber.onNext(isSucceed);
            }
        })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(isSucceedListener);
    }

    //得到本地录音列表
    public void getLocalRecordList(Action1<List<RecordAudio>> localRecordListener) {
        Observable.create(new Observable.OnSubscribe<List<RecordAudio>>() {
            @Override
            public void call(Subscriber<? super List<RecordAudio>> subscriber) {
                subscriber.onNext(recordAudioDao.queryForAll());
            }
        })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(localRecordListener);
    }

    //同步本地曲谱
    public void synchronizeLocalSong(final Action1<Boolean> isSucceedListener) {
        Observable.create(new Observable.OnSubscribe<Boolean>() {
            @Override
            public void call(Subscriber<? super Boolean> subscriber) {
                subscriber.onNext(updateLocalSong());
            }
        })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(isSucceedListener);
    }

    //得到本地曲谱列表
    public void getLocalSongList(Action1<List<LocalSong>> localSongListener) {
        Observable.create(new Observable.OnSubscribe<List<LocalSong>>() {
            @Override
            public void call(Subscriber<? super List<LocalSong>> subscriber) {
                subscriber.onNext(localSongDao.queryForAll());
            }
        })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(localSongListener);
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
    private Boolean updateRecordAudio() {
        MediaPlayer mediaPlayer = new MediaPlayer();
        boolean isSucceed = true;
        File dir = new File(Environment.getExternalStorageDirectory() + "/" + Constant.RECORD_FILE);
        if (dir.exists()) {
            File[] files = dir.listFiles();
            for (File file : files) {
                try {
                    RecordAudio recordAudio = getRecordAudio(mediaPlayer, file);
                    isSucceed = recordAudioDao.add(recordAudio);//同步时有可能出现名字相同的情况
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return isSucceed;
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
        if (imgFiles.length == 0) {
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
