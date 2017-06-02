package com.example.q.pocketmusic.module.home.local.localsong;

import com.example.q.pocketmusic.model.bean.local.LocalSong;

import java.text.SimpleDateFormat;
import java.util.Comparator;
import java.util.Locale;

/**
 * Created by 鹏君 on 2017/3/14.
 */

public class LocalSongComparator implements Comparator<LocalSong> {
    public static final SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy.MM.dd HH:mm", Locale.CHINA);


    @Override
    public int compare(LocalSong o1, LocalSong o2) {
        int o1_top = o1.getSort();
        int o2_top = o2.getSort();
        return compareTop(o1_top, o2_top);
    }

    public int compareTop(int x, int y) {
        if (x - y < 0) {
            return 1;
        } else if (x - y > 0) {
            return -1;
        }
        return 0;
    }

}
