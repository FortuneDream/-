package com.example.q.pocketmusic.util;

/**
 * Created by 鹏君 on 2016/8/30.
 */
public class StringUtil {
    //《abc》（dce）-->abc和dce  或者 《abc》--->abc

    public static String[] fixName(String title) {
        String temp = title.replace("《", "").replace("》", "")
                .replace("（", "\n").replace("）", "")
                .replace("演唱", "")
                .replace("(", "").replace(")", "");
        return temp.split("\n");
    }

    //abc(dce)-> abc
    public static String fixName2(String title) {
        int a = title.length();
        if (title.contains("（")) {
            a = title.indexOf("（");
        }
        return title.substring(0, a);
    }

    // [吉他谱]  -> 吉他谱
    public static String fixName3(String title) {
        return title.replace("[", "").replace("]", "");
    }

    //../pu/91/91123.htm ->/pu/91/91123.htm
    public static String fixName4(String title) {
        return title.replace(".", "") + ".htm";
    }

    //标题:梦里梦外全是你你你我我我 演唱者:我 格式:简谱 类别:流行 来源:乐友lovepu传谱 人气:275  ->
    public static String fixName5(String title) {
        String[] strs = title.split(" ");
        return strs[1] + " " + strs[2] + " " + strs[strs.length-1];//演唱者:我 格式:简谱  人气:275
    }

    //判断是否含有中文
    public static boolean hasFullChar(String str) {
        return str.getBytes().length != str.length();
    }

}
