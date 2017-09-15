package com.example.q.pocketmusic.util.common.update;

import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.support.annotation.Nullable;

import com.example.q.pocketmusic.R;
import com.example.q.pocketmusic.module.home.HomeActivity;
import com.example.q.pocketmusic.util.common.FileUtils;
import com.example.q.pocketmusic.util.common.IntentUtil;
import com.example.q.pocketmusic.util.common.LogUtils;
import com.example.q.pocketmusic.util.common.ToastUtil;

import java.io.File;

public class DownloadService extends Service {
    private final static int DOWNLOAD_COMPLETE = -2;
    private final static int DOWNLOAD_FAIL = -1;
    public final static String PARAM_URL = "url";
    public final static String PARAM_DIR = "dir_path";
    // 自定义通知栏类
    private DownloadApkNotification downloadApkNotification;
    private DownFileThread downFileThread; // 自定义文件下载线程


    private Handler updateHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case DOWNLOAD_COMPLETE:
                    // 自动安装PendingIntent
                    IntentUtil.installApk(DownloadService.this, downFileThread.getApkFile());
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

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
//        LogUtils.e("service", "onStartCommand");
        if (intent == null) {
            stopSelf();
            return super.onStartCommand(null, flags, startId);
        }
        String url = intent.getStringExtra(PARAM_URL);
        String dirPath = intent.getStringExtra(PARAM_DIR);
        Intent updateIntent = new Intent(this, HomeActivity.class);
        PendingIntent updatePendingIntent = PendingIntent.getActivity(this,
                0,
                updateIntent, 0);
        downloadApkNotification = new DownloadApkNotification(this, updatePendingIntent, 1);
        downloadApkNotification.showCustomizeNotification(R.mipmap.ico_launcher,
                "开始下载", R.layout.notification_download);

        //创建文件
        File fileByNet = FileUtils.createFileByNet(url, dirPath);
        // 开启一个新的线程下载，如果使用Service同步下载，会导致ANR问题，Service本身也会阻塞
        downFileThread = new DownFileThread(
                updateHandler,
                url,
                fileByNet);
        new Thread(downFileThread).start();
        return super.onStartCommand(intent, flags, startId);
    }


    @Override
    public void onDestroy() {
        LogUtils.e("service", "onDestroy");
        if (downFileThread != null)
            downFileThread.interruptThread();
        if (downloadApkNotification != null) {
            downloadApkNotification.removeNotification();
        }
        stopSelf();
        super.onDestroy();
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }


}
