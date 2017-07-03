package com.example.q.pocketmusic.util.common;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;

/**
 * Created by 鹏君 on 2017/7/3.
 * （￣m￣）
 */

public class IntentUtil {

    //获得AppStoreIntent
    private static Intent getAppStoreIntent(Context context) {
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setData(Uri.parse("market://details?id=" + context.getPackageName()));
        return intent;

    }

    //进入AppStore
    public static void enterAppStore(Context context) {
        Intent intent = getAppStoreIntent(context);
        if (intent.resolveActivity(context.getPackageManager()) != null) { //可以接收
            context.startActivity(intent);
        } else {
            ToastUtil.showToast("没有找到应用市场~");
        }
    }

    private static Intent getShareTextIntent(String content) {
        Intent intent = new Intent("android.intent.action.SEND");
        intent.addCategory("android.intent.category.DEFAULT");
        intent.setType("text/plain");
        intent.putExtra(Intent.EXTRA_TEXT, content);
        return intent;
    }

    //分享apk
    public static void shareText(Context context, String content) {
        Intent intent = getShareTextIntent(content);
        if (intent.resolveActivity(context.getPackageManager()) != null) {
            context.startActivity(intent);
        } else {
            ToastUtil.showToast("你的手机不支持分享~");
        }
    }


}
