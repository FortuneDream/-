package com.example.q.pocketmusic.data.net;

import android.os.AsyncTask;

import com.dell.fortune.tools.LogUtils;
import com.example.q.pocketmusic.config.constant.Constant;
import com.example.q.pocketmusic.data.bean.Song;
import com.example.q.pocketmusic.util.RegExUtils;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

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
        LogUtils.e("url:" + url);
        try {
            Document doc = Jsoup.connect(url).userAgent(Constant.USER_AGENT).get();
            Element tbody = doc.getElementsByTag("tbody").get(0);
            list.addAll(RegExUtils.getRecommendList(tbody.toString()));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }
}
