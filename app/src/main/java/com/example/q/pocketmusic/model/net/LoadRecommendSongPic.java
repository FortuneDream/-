package com.example.q.pocketmusic.model.net;

import android.os.AsyncTask;

import com.example.q.pocketmusic.config.Constant;
import com.example.q.pocketmusic.model.bean.Song;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.ArrayList;

/**
 * Created by 鹏君 on 2016/10/31.
 */

public class LoadRecommendSongPic extends AsyncTask<Song,Void,Integer> {
    private Song song;
    @Override
    protected Integer doInBackground(Song... params) {
        song=params[0];
        ArrayList<String> list=new ArrayList<>();
        try {
            Document doc =Jsoup.connect(song.getUrl()).userAgent(Constant.USER_AGENT).get();
            Elements as=doc.select("div.imageList").get(0).getElementsByTag("a");
            for (Element a:as){
                String href=a.attr("href");
                list.add(Constant.RECOMMEND_BASE_URL +href);
            }
            song.setIvUrl(list);
        } catch (Exception e) {
            e.printStackTrace();
            return Constant.FAIL;
        }
        return Constant.SUCCESS;
    }
}
