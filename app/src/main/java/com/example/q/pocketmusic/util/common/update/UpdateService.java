package com.example.q.pocketmusic.util.common.update;

import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.support.annotation.Nullable;

import com.dell.fortune.tools.info.LogUtils;
import com.example.q.pocketmusic.R;
import com.example.q.pocketmusic.module.home.HomeActivity;
import com.example.q.pocketmusic.util.common.FileUtils;
import com.example.q.pocketmusic.util.common.IntentUtil;

import com.example.q.pocketmusic.util.common.ToastUtil;

import java.io.File;

public class UpdateService extends Service {
    private final static int DOWNLOAD_COMPLETE = -2;
    private final static int DOWNLOAD_FAIL = -1;
    public final static String PARAM_URL = "url";
    public final static String PARAM_DIR = "dir_path";
    public final static String PARAM_NOTIFICATION_CONFIGURATION = "notification_configuration";
    // 自定义通知栏类
    private UpdateNotification updateNotification;
    private UpdateThread downFileThread; // 自定义文件下载线程


    private Handler updateHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case DOWNLOAD_COMPLETE:
                    // 自动安装PendingIntent
                    IntentUtil.installApk(UpdateService.this, downFileThread.getApkFile());
                    // 停止服务
                    updateNotification.removeNotification();
                    stopSelf();
                    break;
                case DOWNLOAD_FAIL:
                    // 下载失败
                    updateNotification.changeProgressStatus(DOWNLOAD_FAIL);
                    updateNotification.changeNotificationText("文件下载失败！");
                    ToastUtil.showToast("文件下载失败");
                    stopSelf();
                    break;
                default: // 下载中
//                    LogUtils.e("service", "index" + msg.what);
                    updateNotification.changeProgressStatus(msg.what);
            }
        }
    };

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        LogUtils.e("UpdateService", "onStartCommand");
        if (intent == null) {
            stopSelf();
            return super.onStartCommand(null, flags, startId);
        }
        String url = intent.getStringExtra(PARAM_URL);
        String dirPath = intent.getStringExtra(PARAM_DIR);
        UpdateNotificationConfiguration configuration = (UpdateNotificationConfiguration) intent.getSerializableExtra(PARAM_NOTIFICATION_CONFIGURATION);
        openUpdateNotification(configuration);//开启下载通知栏
        //创建文件
        File fileByNet = FileUtils.createFileByNet(url, dirPath);
        // 开启一个新的线程下载，如果使用Service同步下载，会导致ANR问题，Service本身也会阻塞
        downFileThread = new UpdateThread(updateHandler, url, fileByNet);
        new Thread(downFileThread).start();
        return super.onStartCommand(intent, flags, startId);
    }

    //下载通知栏
    private void openUpdateNotification(UpdateNotificationConfiguration configuration) {
        Intent updateIntent = new Intent(this, HomeActivity.class);
        PendingIntent updatePendingIntent = PendingIntent.getActivity(this,
                0,
                updateIntent, 0);
        updateNotification = new UpdateNotification(this, updatePendingIntent, 1);
        updateNotification.showCustomizeNotification(configuration.getAppIcons(),
                configuration.getAppName(), R.layout.notification_download);
    }


    @Override
    public void onDestroy() {
        LogUtils.e("UpdateService", "onDestroy");
        if (downFileThread != null)
            downFileThread.interruptThread();
        if (updateNotification != null) {
            updateNotification.removeNotification();
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
