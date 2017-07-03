package com.example.q.pocketmusic.util.common;

import android.content.Context;
import android.widget.Toast;

/**
 * Created by 鹏君 on 2016/8/28.
 */


public class ToastUtil {
    private static Toast toast;
    private static Context context;

    public static void init(Context context) {
        ToastUtil.context = context;
    }

    public static void showToast(String content) {
        if (toast == null) {
            toast = Toast.makeText(context, content, Toast.LENGTH_SHORT);
        }
        toast.setText(content);
        toast.show();
    }
}
