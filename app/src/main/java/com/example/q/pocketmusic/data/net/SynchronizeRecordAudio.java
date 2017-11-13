package com.example.q.pocketmusic.data.net;

import android.content.Context;
import android.media.MediaPlayer;
import android.os.AsyncTask;
import android.os.Environment;

import com.example.q.pocketmusic.config.Constant;
import com.example.q.pocketmusic.data.bean.local.RecordAudio;
import com.example.q.pocketmusic.data.db.RecordAudioDao;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * Created by 鹏君 on 2017/2/11.
 */

public class SynchronizeRecordAudio extends AsyncTask<Void, Void, Boolean> {
    public static final SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy.MM.dd HH:mm", Locale.CHINA);
    private RecordAudioDao recordAudioDao;
    private Context context;

    public SynchronizeRecordAudio(Context context) {
        recordAudioDao=new RecordAudioDao(context);
        this.context = context;
    }

    @Override
    protected Boolean doInBackground(Void... params) {
        MediaPlayer mediaPlayer = new MediaPlayer();
        boolean isSucceed = true;
        File dir = new File(Environment.getExternalStorageDirectory() + "/" + Constant.RECORD_FILE);
        if (dir.exists()) {
            File[] files = dir.listFiles();
            for (File file : files) {
                RecordAudio recordAudio = new RecordAudio();
                //时间
                recordAudio.setDate(dateFormat.format(new Date(file.lastModified())));
                //名字
                String name = file.getName();
                recordAudio.setName(name.replace(".3gp", ""));//去掉后缀名再保存到数据库
                //路径
                recordAudio.setPath(file.getAbsolutePath());
                try {
                    //长度
                    mediaPlayer.reset();
                    mediaPlayer.setDataSource(file.getAbsolutePath());
                    mediaPlayer.prepare();//同步
                    recordAudio.setDuration(mediaPlayer.getDuration());
                    isSucceed = recordAudioDao.add(recordAudio);//同步时有可能出现名字相同的情况
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return isSucceed;
    }
}
