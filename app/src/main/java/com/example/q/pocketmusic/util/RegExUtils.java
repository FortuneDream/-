package com.example.q.pocketmusic.util;

import com.example.q.pocketmusic.config.constant.Constant;
import com.example.q.pocketmusic.data.bean.Song;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by 鹏君 on 2017/7/3.
 * （￣m￣）
 */

//
public class RegExUtils {
    private static final String omit = "[\\s\\S]*?";//坑1,包括了空格换行
    private static final String select = "(.*?)";


    //获得乐器类型的列表曲谱
    public static List<Song> getTypeSongList(int typeId, String html) {
//        LogUtils.e("TAG",html);
        List<Song> list = new ArrayList<>();
        String regex = "<tr>" + omit + "<td class=\"f1\">" + omit + "<a href=\"" + select + "\"" + omit
                + ">" + select + "</a>" + omit + "<td class=\"f6\">" + select + "</td>"+omit+"</tr>";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(html);
        while (matcher.find()) {//坑2
            Song song = new Song();
            String url = Constant.RECOMMEND_BASE_URL + matcher.group(1).trim();//group(0)是整个字符串
            String name = matcher.group(2).trim();
            String date = matcher.group(3).trim();
            song.setTypeId(typeId);
            song.setName(name);
            song.setUrl(url);
            song.setDate(date);
            list.add(song);
//            LogUtils.e("TAG",name);
        }
        return list;
    }

    //获得乐器类型的曲谱图片
    public static void setTypePic(Song song, String html) {
        String regex = "<div class=\"imageList\">" + omit + "<img src=\"" + select + "\"";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(html);
        ArrayList<String> list = new ArrayList<>();
        while (matcher.find()) {
            list.add(Constant.RECOMMEND_BASE_URL+matcher.group(1));
        }
        song.setIvUrl(list);
    }

    //传入tbody，获得推荐列表
    public static List<Song> getRecommendList(String html) {
        List<Song> list = new ArrayList<>();
        String regex = "<tr>" + omit + "<a href=\"" + select + "\"" + omit + ">" + select + "<" + omit + "<td class=\"f3\">" + select + "</td>" + omit + "<td class=\"f4\">" + select + "</td>";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(html);
        while (matcher.find()) {
            Song song = new Song(matcher.group(2), Constant.RECOMMEND_BASE_URL + matcher.group(1));
            song.setDate(matcher.group(4));
            song.setArtist(matcher.group(3));
            list.add(song);
        }
        return list;
    }

    //c_list  获得搜索列表
    public static List<Song> getSearchList(String html) {
        List<Song> list = new ArrayList<>();
        String regex = "<ul>" + omit + "<a href=\"" + select + "\"" + omit + ">" + select + "</a>" + omit + "</ul>";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(html);
        while (matcher.find()) {
            String name = matcher.group(2).replace("<span class=\"c_hightlight\">", "").replace("<span class=\"c_hightlight\">", "").replace("</span>","");
            String url = matcher.group(1);
            Song song = new Song(name, url);
            song.setSearchFrom(Constant.FROM_SEARCH_NET);
            song.setContent("暂无");
            list.add(song);
        }
        return list;
    }


}
