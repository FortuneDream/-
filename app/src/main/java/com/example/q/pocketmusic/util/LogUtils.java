package com.example.q.pocketmusic.util;

import android.util.Log;

/**
 * Created by 鹏君 on 2016/10/2.
 */

public class LogUtils {
    private static final boolean log = true;

    public static void d(String tag, String msg) {
        if (log)
            Log.d(tag, msg);
    }

    public static void e(String tag, String msg) {
        if (log)
            Log.e(tag, msg);
    }

    public static void v(String tag, String msg) {
        if (log)
            Log.v(tag, msg);
    }

    public static void i(String tag, String msg) {
        if (log)
            Log.i(tag, msg);
    }
}
