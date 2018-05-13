package com.example.q.pocketmusic.util.common.update;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Environment;
import android.support.v7.app.AlertDialog;

/**
 * Created by 鹏君 on 2017/7/1.
 * （￣m￣）
 */
//待测试
public class UpdateBuilder {
    private UpdateNotificationConfiguration mNotificationConfiguration;
    private Context mContext;
    private String mVersionCodeStr;
    private String mVersionContent;
    private String mUrl;
    private boolean mIsForce;
    private String mDir;

    public UpdateBuilder(Context context, UpdateNotificationConfiguration notificationConfiguration) {
        this.mContext = context;
        this.mNotificationConfiguration = notificationConfiguration;
        this.mVersionCodeStr = "1";
        this.mVersionContent = "暂无";
        this.mUrl = "";
        this.mIsForce = false;//默认不强制
        this.mDir = Environment.getExternalStorageDirectory().getPath();
    }

    //版本号
    public UpdateBuilder setVersionCodeStr(String versionCodeStr) {
        this.mVersionCodeStr = versionCodeStr;
        return this;
    }

    //版本信息
    public UpdateBuilder setVersionContent(String versionContent) {
        this.mVersionContent = versionContent;
        return this;
    }

    //下载路径
    public UpdateBuilder setUrl(String url) {
        this.mUrl = url;
        return this;
    }

    //下载的目录
    public UpdateBuilder setDir(String dir) {
        this.mDir = dir;
        return this;
    }

    public UpdateBuilder setIsForce(boolean isForce) {
        this.mIsForce = isForce;
        return this;
    }


    public void update() {
        AlertDialog.Builder builder = new AlertDialog.Builder(mContext)
                .setTitle("新版本号：" + mVersionCodeStr)
                .setMessage(mVersionContent)
                .setPositiveButton("更新", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        startDownloadApkService();
                    }
                });
        if (mIsForce) {
            builder.setCancelable(false);
        } else {
            builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                }
            });
        }
        builder.show();
    }

    //开始下载
    private void startDownloadApkService() {
        Intent intent = new Intent(mContext, UpdateService.class);
        intent.putExtra(UpdateService.PARAM_URL, mUrl);
        intent.putExtra(UpdateService.PARAM_DIR, mDir);
        intent.putExtra(UpdateService.PARAM_NOTIFICATION_CONFIGURATION, mNotificationConfiguration);
        mContext.startService(intent);
    }
}