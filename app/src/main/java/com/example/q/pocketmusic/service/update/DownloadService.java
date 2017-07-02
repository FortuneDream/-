package com.example.q.pocketmusic.service.update;

import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.support.annotation.Nullable;

import com.example.q.pocketmusic.R;
import com.example.q.pocketmusic.module.home.HomeActivity;
import com.example.q.pocketmusic.util.LogUtils;
import com.example.q.pocketmusic.util.ToastUtil;

import java.io.File;
import java.io.IOException;

public class DownloadService extends Service {
    private final static int DOWNLOAD_COMPLETE = -2;
    private final static int DOWNLOAD_FAIL = -1;

    // 自定义通知栏类
    DownloadApkNotification downloadApkNotification;

    String filePathString; // 下载文件绝对路径(包括文件名)

    // 通知栏跳转Intent
    private Intent updateIntent = null;

    private PendingIntent updatePendingIntent = null;

    DownFileThread downFileThread; // 自定义文件下载线程


    private Handler updateHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case DOWNLOAD_COMPLETE:
                    // 自动安装PendingIntent
                    installApk();
                    // 停止服务
                    downloadApkNotification.removeNotification();
                    stopSelf();
                    break;
                case DOWNLOAD_FAIL:
                    // 下载失败
                    downloadApkNotification.changeProgressStatus(DOWNLOAD_FAIL);
                    downloadApkNotification.changeNotificationText("文件下载失败！");
                    ToastUtil.showToast("文件下载失败");
                    stopSelf();
                    break;
                default: // 下载中
//                    LogUtils.e("service", "index" + msg.what);
                    downloadApkNotification.changeProgressStatus(msg.what);
            }
        }
    };

    //安装apk
    private void installApk() {
        //Android N以上需要特殊处理
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {

        } else {
            Uri uri = Uri.fromFile(downFileThread.getApkFile());
            Intent installIntent = new Intent(Intent.ACTION_VIEW);
            installIntent.setDataAndType(uri,
                    "application/vnd.android.package-archive");
            installIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(installIntent);
        }
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
//        LogUtils.e("service", "onStartCommand");
        String url = intent.getStringExtra("url");
        String dirPath = intent.getStringExtra("dir_path");
        updateIntent = new Intent(this, HomeActivity.class);//开启通知
        PendingIntent updatePendingIntent = PendingIntent.getActivity(this,
                0,
                updateIntent, 0);
        downloadApkNotification = new DownloadApkNotification(this, updatePendingIntent, 1);
        downloadApkNotification.showCustomizeNotification(R.mipmap.ico_launcher,
                "测试下载", R.layout.notification_download);
        createFile(url, dirPath);
        // 开启一个新的线程下载，如果使用Service同步下载，会导致ANR问题，Service本身也会阻塞
        downFileThread = new DownFileThread(
                updateHandler,
                url,
                filePathString);
        new Thread(downFileThread).start();

        return super.onStartCommand(intent, flags, startId);
    }

    //将原apk的名字，与SD卡路劲拼接，createFile
    private void createFile(String url, String dirPath) {
        if (!dirPath.endsWith("/")) {
            dirPath = dirPath + "/";
        }
        File path = new File(dirPath);
        if (!path.exists()) {
            path.mkdirs();
        }
        String[] s = url.split("/");//123.apk
        filePathString = dirPath + s[s.length - 1];
        File file = new File(filePathString);
        if (!file.exists()) {
            try {
                file.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
                ToastUtil.showToast(e.getMessage());
            }
        }
    }

    @Override
    public void onDestroy() {
        LogUtils.e("service", "onDestroy");
        if (downFileThread != null)
            downFileThread.interuptThread();
//      if (null != downloadApkNotification) {
//          downloadApkNotification.removeNotification();
//      }
        stopSelf();
        super.onDestroy();
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }


}
