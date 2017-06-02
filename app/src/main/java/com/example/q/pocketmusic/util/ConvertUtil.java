package com.example.q.pocketmusic.util;

import android.content.Context;

/**
 * Created by 鹏君 on 2016/9/10.
 */
public class ConvertUtil {
    //dp->px
    public static int Dp2Px(Context context, float dp) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dp * scale + 0.5f);
    }

    //px->dp
    public static int Px2Dp(Context context, float px) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (px / scale + 0.5f);
    }
}
