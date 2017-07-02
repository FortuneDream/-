package com.example.q.pocketmusic.service.update;

/**
 * Created by 鹏君 on 2017/7/1.
 * （￣m￣）
 */


import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.widget.RemoteViews;

import com.example.q.pocketmusic.R;

public class DownloadApkNotification {
    public final static int DOWNLOAD_COMPLETE = -2;
    public final static int DOWNLOAD_FAIL = -1;
    private Context mContext; // Activity或Service上下文
    Notification notification; // notification
    private NotificationManager nm;
    private PendingIntent contentIntent; // 点击通知后的动作
    private int notificationID; // 通知的唯一标示ID
    private long when = System.currentTimeMillis();
    private RemoteViews remoteView = null; // 自定义的通知栏视图

    /**
     * @param context       Activity或Service上下文
     * @param contentIntent 点击通知后的动作
     * @param id            通知的唯一标示ID
     */
    public DownloadApkNotification(Context context, PendingIntent contentIntent, int id) {
        mContext = context;
        notificationID = id;
        this.contentIntent = contentIntent;
        this.nm = (NotificationManager) mContext.getSystemService(Context.NOTIFICATION_SERVICE);
    }

    /**
     * 显示自定义通知
     *
     * @param icoId    自定义视图中的图片ID
     * @param titleStr 通知栏标题
     * @param layoutId 自定义布局文件ID
     */
    public void showCustomizeNotification(int icoId, String titleStr,
                                          int layoutId) {
        notification = new Notification(R.mipmap.ico_launcher, titleStr, when);
        notification.flags = Notification.FLAG_ONLY_ALERT_ONCE;
        notification.flags |= Notification.FLAG_AUTO_CANCEL;
        notification.contentIntent = this.contentIntent;
        if (remoteView == null) {
            remoteView = new RemoteViews(mContext.getPackageName(), layoutId);
            remoteView.setImageViewResource(R.id.notification_iv, icoId);
            remoteView.setTextViewText(R.id.tip_tv, "开始下载");
            remoteView.setProgressBar(R.id.notification_pb, 100, 0, false);
            notification.contentView = remoteView;
        }
        nm.notify(notificationID, notification);
    }

    //更新Progress和文字
    public void changeProgressStatus(int p) {
        if (notification.contentView != null) {
            if (p == DOWNLOAD_FAIL)
                notification.contentView.setTextViewText(R.id.tip_tv, "下载失败！ ");
            else if (p == 100) {
                notification.contentView.setTextViewText(R.id.tip_tv, "下载完成，请点击安装");
            } else {
                notification.contentView.setTextViewText(R.id.tip_tv, "进度(" + p + "%)");
                notification.contentView.setProgressBar(R.id.notification_pb, 100, p, false);
            }
        }
        nm.notify(notificationID, notification);
    }

    /**
     * 改变默认通知栏的通知内容
     *
     * @param content
     */
    public void changeNotificationText(String content) {
        notification.contentView.setTextViewText(R.id.tip_tv, content);
        nm.notify(notificationID, notification);
    }

    /**
     * 移除通知
     */
    public void removeNotification() {
        // 取消的只是当前Context的Notification
        nm.cancel(notificationID);
    }
}
