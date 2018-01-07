package com.example.q.pocketmusic.data.net;

import android.os.AsyncTask;

import com.example.q.pocketmusic.config.Constant;
import com.example.q.pocketmusic.data.bean.Song;
import com.example.q.pocketmusic.util.RegExUtils;
import com.example.q.pocketmusic.util.common.LogUtils;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

/**
 * Created by 鹏君 on 2016/10/31.
 */

public class LoadRecommendSongPic extends AsyncTask<Song,Void,Integer> {

    @Override
    protected Integer doInBackground(Song... params) {
        Song song=params[0];
        try {
            Document doc =Jsoup.connect(song.getUrl()).userAgent(Constant.USER_AGENT).timeout(5000).get();
            RegExUtils.setTypePic(song,doc.toString());
        } catch (Exception e) {
            e.printStackTrace();
            return Constant.FAIL;
        }
        return Constant.SUCCESS;
    }
}
