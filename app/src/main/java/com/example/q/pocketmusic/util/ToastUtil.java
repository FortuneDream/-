package com.example.q.pocketmusic.util;

import android.content.Context;
import android.widget.Toast;

/**
 * Created by 鹏君 on 2016/8/28.
 */

//在Application中初始化，之后直接使用ToastUtil("");
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
