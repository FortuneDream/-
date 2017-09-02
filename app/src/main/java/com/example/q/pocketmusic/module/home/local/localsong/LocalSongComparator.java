package com.example.q.pocketmusic.module.home.local.localsong;

import com.example.q.pocketmusic.model.bean.local.LocalSong;

import java.text.ParseException;
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
        if (o1_top > o2_top) {
            return -1;
        } else if (o1_top < o2_top) {
            return 1;
        } else {
            try {
                long temp = dateFormat.parse(o1.getDate()).getTime() - dateFormat.parse(o1.getDate()).getTime();
                if (temp > 0) {
                    return -1;
                } else if (temp < 0) {
                    return 1;
                } else {
                    return 0;
                }
            } catch (ParseException e) {
                e.printStackTrace();
            }
            return 0;
        }
    }

}
