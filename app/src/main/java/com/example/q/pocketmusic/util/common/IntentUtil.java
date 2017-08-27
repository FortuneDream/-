package com.example.q.pocketmusic.util.common;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.support.v4.content.FileProvider;

import java.io.File;

/**
 * Created by 鹏君 on 2017/7/3.
 * （￣m￣）
 */

public class IntentUtil {

    //获得AppStoreIntent
    private static Intent getAppStoreIntent(Context context) {
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);//加个栈
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

    //安装APk,manifest的provider.authorities=包名.fileProvider
    public static void installApk(Context context, File apkFile) {
        //Android N以上需要特殊处理
        Intent intent = new Intent();
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.setAction(Intent.ACTION_VIEW);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            intent.setFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
            Uri installUri = FileProvider.getUriForFile(context, context.getApplicationContext().getPackageName() + ".fileProvider", apkFile);
            intent.setDataAndType(installUri, "application/vnd.android.package-archive");
        } else {
            intent.setDataAndType(Uri.fromFile(apkFile), "application/vnd.android.package-archive");
        }
        context.startActivity(intent);
    }


}
