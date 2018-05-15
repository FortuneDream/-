package com.example.q.pocketmusic.data.model;


import com.dell.fortune.tools.LogUtils;
import com.example.q.pocketmusic.config.constant.Constant;
import com.example.q.pocketmusic.data.bean.Song;
import com.example.q.pocketmusic.util.RegExUtils;
import com.example.q.pocketmusic.util.RxApi;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;

import io.reactivex.Single;

/**
 * Created by 81256 on 2018/2/7.
 */

public class NetSongModel {

    //获得推荐列表
    public Single<List<Song>> getRecommendSongList(final String url) {
        return RxApi.create(new Callable<List<Song>>() {
            @Override
            public List<Song> call() throws Exception {
                List<Song> list = new ArrayList<>();
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
        });
    }

    //获得推荐的图片列表
    public Single<Integer> getRecommendSongPicList(final Song song) {
        return RxApi.create(new Callable<Integer>() {
            @Override
            public Integer call() throws Exception {
                try {
                    Document doc = Jsoup.connect(song.getUrl()).userAgent(Constant.USER_AGENT).timeout(5000).get();
                    RegExUtils.setTypePic(song, doc.toString());
                } catch (Exception e) {
                    e.printStackTrace();
                    return Constant.FAIL;
                }
                return Constant.SUCCESS;
            }
        });
    }

    //获得搜索音乐的列表
    public Single<List<Song>> getSearchSongList(final String query, final int page) {
        return RxApi.create(new Callable<List<Song>>() {
            @Override
            public List<Song> call() throws Exception {
                List<Song> list;
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
                    LogUtils.e("url:" + Constant.SO_PU_SEARCH + urlCode + "&start=" + page * 10);
                    String info = doc.getElementById("labelSummary").text().replace(" ", "");
                    number = Integer.parseInt(info.substring(info.indexOf("约") + 1, info.indexOf("篇")));
                    if (number <= page * 10) {
                        return null;
                    }
                    Element c_list = doc.select("div.c_list").get(0);
                    list = RegExUtils.getSearchList(c_list.toString());
                } catch (Exception e) {
                    e.printStackTrace();
                    return null;
                }
                return list;
            }
        });
    }


}
