package com.example.q.pocketmusic.util.common;

/**
 * Created by 鹏君 on 2017/3/12.
 */

public class XUtil {
    private static long lastClickTime;
    public synchronized static boolean isFastDoubleClick(int min) {
        long time = System.currentTimeMillis();
        if ( time - lastClickTime < min) {
            return true;
        }
        lastClickTime = time;
        return false;
    }
}
