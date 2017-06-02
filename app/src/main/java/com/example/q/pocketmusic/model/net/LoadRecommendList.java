package com.example.q.pocketmusic.model.net;

import android.os.AsyncTask;

import com.example.q.pocketmusic.config.Constant;
import com.example.q.pocketmusic.model.bean.Song;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by 鹏君 on 2016/9/7.
 */
public class LoadRecommendList extends AsyncTask<String, Void, List<Song>> {

    /**
     * @param strings 加载桃李醉春风列表的完整url
     * @return 列表(名字, 推荐类型, 演唱者)
     */
    @Override
    protected List<Song> doInBackground(String... strings) {
        List<Song> list = new ArrayList<>();
        String url = strings[0];
        try {
            Document doc = Jsoup.connect(url).userAgent(Constant.USER_AGENT).get();
            Element tbody = doc.getElementsByTag("tbody").get(0);
            Elements trs = tbody.getElementsByTag("tr");
            //去掉所有的横线
            trs.remove(29);
            trs.remove(23);
            trs.remove(17);
            trs.remove(11);
            trs.remove(5);
            for (int i = 0; i < trs.size(); i++) {
                Element tr = trs.get(i);
                Element f1 = tr.getElementsByTag("td").get(1);
                String a = f1.getElementsByTag("a").get(0).attr("href");
                String name1 = f1.getElementsByTag("a").get(0).text();
                String name2 = tr.getElementsByTag("td").get(3).text();
                String date = tr.getElementsByTag("td").get(4).text();
                Song song = new Song(name1, Constant.RECOMMEND_BASE_URL + a);
                song.setDate(date);
                song.setArtist(name2);
                list.add(song);
            }

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
        return list;
    }
}
