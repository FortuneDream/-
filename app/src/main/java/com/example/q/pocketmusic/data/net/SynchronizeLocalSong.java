package com.example.q.pocketmusic.data.net;

import android.os.AsyncTask;
import android.os.Environment;

import com.example.q.pocketmusic.config.Constant;
import com.example.q.pocketmusic.data.bean.local.Img;
import com.example.q.pocketmusic.data.bean.local.LocalSong;
import com.example.q.pocketmusic.data.db.ImgDao;
import com.example.q.pocketmusic.data.db.LocalSongDao;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * Created by 鹏君 on 2017/2/11.
 */

public class SynchronizeLocalSong extends AsyncTask<Void, Void, Void> {
    public static final SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy.MM.dd HH:mm", Locale.CHINA);
    private LocalSongDao localSongDao;
    private ImgDao imgDao;

    public SynchronizeLocalSong(ImgDao imgDao, LocalSongDao localSongDao) {
        this.imgDao = imgDao;
        this.localSongDao = localSongDao;
    }

    @Override
    protected Void doInBackground(Void... params) {
        File dir = new File(Environment.getExternalStorageDirectory() + "/" + Constant.FILE_NAME + "/");
        if (!dir.exists()) {
            dir.mkdirs();
        }
        File[] files = dir.listFiles();
        for (File file : files) {
            LocalSong localSong = new LocalSong();
            //时间
            localSong.setDate(dateFormat.format(new Date()));
            //名字
            String name = file.getName();
            localSong.setName(name);
            boolean isSuccess = localSongDao.add(localSong);//LocalSong要先加入数据库
            if (!isSuccess) {
                continue;
            }
            //所有图片
            File[] imgFiles = file.listFiles();
            for (File imgFile : imgFiles) {
                Img img = new Img();
                img.setUrl(imgFile.getAbsolutePath());
                img.setLocalSong(localSong);
                imgDao.add(img);
            }
        }

        return null;
    }
}
