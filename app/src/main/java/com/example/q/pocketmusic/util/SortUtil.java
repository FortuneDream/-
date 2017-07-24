package com.example.q.pocketmusic.util;

import com.example.q.pocketmusic.util.common.SharedPrefsUtil;

/**
 * Created by 鹏君 on 2017/7/20.
 * （￣m￣）
 */

public class SortUtil {
    //增加本地曲谱需要获得顺序
    //置顶
    public static int sort_value = 1;
    public final static String sort_key = "top_key";

    public static int getSort() {
        int oldSort = SharedPrefsUtil.getInt(sort_key, sort_value);
        SharedPrefsUtil.putInt(sort_key, oldSort + 1);//放入新的值
        return oldSort;
    }
}
