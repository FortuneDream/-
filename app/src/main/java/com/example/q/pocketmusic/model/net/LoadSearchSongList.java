package com.example.q.pocketmusic.model.net;

import android.os.AsyncTask;

import com.example.q.pocketmusic.config.Constant;
import com.example.q.pocketmusic.model.bean.Song;
import com.example.q.pocketmusic.util.StringUtil;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by 鹏君 on 2016/9/4.
 */
public class LoadSearchSongList extends AsyncTask<String, Void, List<Song>> {
    private int page;

    protected LoadSearchSongList(int page) {
        this.page = page;
    }

    /**
     * 得到搜索曲谱列表,来自网络
     *
     * @param strings
     * @return
     */
    @Override
    protected List<Song> doInBackground(String... strings) {
        List<Song> songs = new ArrayList<>();
        String query = strings[0];
        String urlCode = null;//转换为URLCode
        int number = 0;
        try {
            urlCode = URLEncoder.encode(query, "gb2312");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        try {
            Document doc = Jsoup.connect(Constant.SO_PU_SEARCH + urlCode + "&start=" + page * 10)
                    .timeout(6000)
                    .userAgent(Constant.USER_AGENT)
                    .get();
            //得到总数量

            String info = doc.getElementById("labelSummary").text().replace(" ", "");
            number = Integer.parseInt(info.substring(info.indexOf("约") + 1, info.indexOf("篇")));
            if (number <= page * 10) {
                return null;
            }
//            LogUtils.e("TAG", "搜索到了" + number + "页");
            Element c_list = doc.select("div.c_list").get(0);
            Elements uls = c_list.getElementsByTag("ul");
            //判断数量
            uls.remove(0);
            for (Element ul : uls) {
                Elements lis = ul.getElementsByTag("li");
                //曲谱地址
                String url = lis.get(0).getElementsByTag("a").get(0).attr("href");
                //谱曲名字
                String name = lis.get(0).getElementsByTag("a").get(0).text();
                //内容,这个内容需要处理一下
                String content = StringUtil.fixName5(lis.get(1).text());
                Song song = new Song(name, url);
                song.setSearchFrom(Constant.FROM_SEARCH_NET);
                song.setContent(content);
                songs.add(song);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
        return songs;
    }
}
