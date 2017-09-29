package com.example.q.pocketmusic.model.net;

import android.os.AsyncTask;

import com.example.q.pocketmusic.config.Constant;
import com.example.q.pocketmusic.model.bean.Song;
import com.example.q.pocketmusic.util.RegExUtils;
import com.example.q.pocketmusic.util.common.LogUtils;


import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import java.util.List;

/**
 * Created by 鹏君 on 2016/8/29.
 */
public class LoadTypeSongList extends AsyncTask<String, Void, List<Song> > {
    private int typeId;


    protected LoadTypeSongList( int typeId) {
        this.typeId = typeId;
    }

    /**
     * 通过类型列表的position得到list
     *
     * @param strings 完整的某种乐器的url
     * @return 里诶包
     */
    @Override
    protected List<Song> doInBackground(String... strings) {
        String typeUrl = strings[0];
        LogUtils.e("url:"+typeUrl);
        List<Song> list;
        try {
            Document doc = Jsoup.connect(typeUrl)
                    .userAgent(Constant.USER_AGENT)
                    .timeout(6000)
                    .get();
//            Element tBody=doc.getElementsByTag("tbody").get(0);
            list= RegExUtils.getTypeSongList(typeId,doc.toString());
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
        return list;
    }


}
