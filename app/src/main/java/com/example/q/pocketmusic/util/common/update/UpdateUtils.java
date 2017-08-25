package com.example.q.pocketmusic.util.common.update;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Environment;
import android.support.v7.app.AlertDialog;

import com.example.q.pocketmusic.callback.ToastQueryListener;

import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.update.AppVersion;

/**
 * Created by 鹏君 on 2017/7/1.
 * （￣m￣）
 */

public class UpdateUtils {

    public static UpdateUtils mUpdateUtils;

    private UpdateUtils() {

    }

    public static UpdateUtils getInstanse() {
        if (null == mUpdateUtils) {
            mUpdateUtils = new UpdateUtils();
        }
        return mUpdateUtils;
    }

    public void update(final Context context) {
        PackageManager pm = context.getPackageManager();
        PackageInfo pi = null;
        try {
            pi = pm.getPackageInfo(context.getPackageName(), PackageManager.GET_ACTIVITIES);
            BmobQuery<AppVersion> query = new BmobQuery<>();
            query.addWhereGreaterThan("version_i", pi.versionCode);
            query.findObjects(new ToastQueryListener<AppVersion>() {
                @Override
                public void onSuccess(List<AppVersion> list) {
                    if (list.size() >= 1) {
                        alertUpdateDialog(context, list.get(list.size() - 1));
                    }

                }


            });
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
    }

    //弹出dialog,是否强制升级
    private void alertUpdateDialog(final Context context, final AppVersion updateResponse) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context)
                .setTitle("新版本：" + updateResponse.getVersion())
                .setMessage(updateResponse.getUpdate_log())
                .setPositiveButton("更新", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        String dirPath = Environment.getExternalStorageDirectory().getPath();
                        download(context, updateResponse.getPath().getUrl(), dirPath);
                    }
                });
        if (updateResponse.getIsforce()) {
            builder.setCancelable(false);
        } else {
            builder.setNegativeButton("算了", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                }
            });
        }
        builder.show();
    }

    //开始下载
    public void download(Context context, String url, String dirPath) {
        Intent intent = new Intent(context, DownloadService.class);
        intent.putExtra("url", url);
        intent.putExtra("dir_path", dirPath);
        context.startService(intent);
    }
}