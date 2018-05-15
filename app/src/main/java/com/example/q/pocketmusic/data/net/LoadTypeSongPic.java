package com.example.q.pocketmusic.data.net;

import android.os.AsyncTask;

import com.dell.fortune.tools.LogUtils;
import com.example.q.pocketmusic.config.constant.Constant;
import com.example.q.pocketmusic.data.bean.Song;
import com.example.q.pocketmusic.util.RegExUtils;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

/**
 * Created by 鹏君 on 2016/8/30.
 */
public class LoadTypeSongPic extends AsyncTask<Song, Void, Integer> {


    /**
     * 得到类型列表中某一首歌的图片，存入歌曲的ivUrls中
     *
     * @param songs 得到歌曲
     * @return 失败or成功
     */
    @Override
    protected Integer doInBackground(Song... songs) {
        Song song = songs[0];
        LogUtils.e("url:"+song.getUrl());
        try {
            Document doc = Jsoup.connect(song.getUrl()).userAgent(Constant.USER_AGENT).timeout(6000).get();
            RegExUtils.setTypePic(song,doc.toString());
        } catch (Exception e) {
            e.printStackTrace();
            return Constant.FAIL;
        }
        return Constant.SUCCESS;
    }

}
