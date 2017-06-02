package com.example.q.pocketmusic.model.net;

import android.os.AsyncTask;

import com.example.q.pocketmusic.config.Constant;
import com.example.q.pocketmusic.model.bean.Song;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import java.util.ArrayList;

/**
 * Created by 鹏君 on 2016/8/30.
 */
public class LoadTypeSongPic extends AsyncTask<Song, Void, Integer> {
    private Song song;
    private ArrayList<String> imgs = new ArrayList<>();

    /**
     * 得到类型列表中某一首歌的图片，存入歌曲的ivUrls中
     *
     * @param songs 得到歌曲
     * @return 失败or成功
     */
    @Override
    protected Integer doInBackground(Song... songs) {
        song = songs[0];
        try {
            Document doc = Jsoup.connect(song.getUrl()).userAgent(Constant.USER_AGENT).timeout(6000).get();
            Element imageList=doc.select("div.imageList").get(0);
            String onePic= Constant.BASE_URL+imageList.getElementsByTag("a").get(0).attr("href");
            imgs.add(onePic);
            song.setIvUrl(imgs);
        } catch (Exception e) {
            e.printStackTrace();
            return Constant.FAIL;
        }
        return Constant.SUCCESS;
    }

}
