package com.example.q.pocketmusic.util;

import com.example.q.pocketmusic.config.Constant;
import com.example.q.pocketmusic.model.bean.Song;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by 鹏君 on 2017/7/3.
 * （￣m￣）
 */

public class RegExUtils {
    private static final String omit = ".*?";
    private static final String select = "(.*?)";

    //获得推荐的列表曲谱
    public List<Song> getRecommendSongList(String html) {
        List<Song> list = new ArrayList<>();
        //select 1 路径,select 2 名字
        String regex = "<tr" + omit + "<td class=\"f1\">" + omit + "<a href=\"" + select + "\"" + omit
                + ">" + select + "</a>";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(html);
        for (int i = 0; i < matcher.groupCount(); i++) {
            Song song = new Song();
            String url = Constant.RECOMMEND_BASE_URL + matcher.group(0).trim();
            String name = matcher.group(1).trim();
            song.setName(name);
            song.setUrl(url);
            list.add(song);
        }
        return list;
    }

}
