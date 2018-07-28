package com.example.q.pocketmusic.data.net;

import android.os.AsyncTask;


import com.dell.fortune.tools.LogUtils;
import com.example.q.pocketmusic.config.constant.Constant;
import com.example.q.pocketmusic.data.bean.Song;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.ArrayList;

/**
 * Created by 鹏君 on 2016/9/4.
 */
public class LoadSearchSongPic extends AsyncTask<Song, Void, Integer> {
    private Song song;

    /**
     * 得到搜索列表中某一首歌的图片，存入歌曲的ivUrls中
     *
     * @param songs 选中歌曲
     * @return 正确or失败
     */
    @Override
    protected Integer doInBackground(Song... songs) {
        song = songs[0];
        ArrayList<String> list = new ArrayList<>();
        String songUrl = song.getUrl();
        try {
            Document doc = Jsoup.connect(songUrl).userAgent(Constant.USER_AGENT).timeout(6000).get();
            Element box = doc.getElementById("box");
            Element content = box.select("div.content").get(0);
            Elements imgs = content.getElementsByTag("img");
            for (Element img : imgs) {
                String url = Constant.SO_PU_BASE + img.attr("src");
                list.add(url);
                LogUtils.e("url:" + url);
            }
            song.setIvUrl(list);
        } catch (Exception e) {
            e.printStackTrace();
            return Constant.FAIL;
        }
        return Constant.SUCCESS;
    }
}
