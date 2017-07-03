package com.example.q.pocketmusic.util.common.update;

import android.content.Context;
import android.content.Intent;
import android.os.Environment;

/**
 * Created by 鹏君 on 2017/7/1.
 * （￣m￣）
 */

public class UpdateUtils {

    public static UpdateUtils mUpdateUtils;

    public static UpdateUtils getInstanse() {
        if (null == mUpdateUtils) {
            mUpdateUtils = new UpdateUtils();
        }
        return mUpdateUtils;
    }

    public void update(Context context, String url) {
        String dirPath = Environment.getExternalStorageDirectory().getPath();
        download(context, url, dirPath);
    }

    public void download(Context context, String url, String dirPath) {
        Intent intent = new Intent(context, DownloadService.class);
        intent.putExtra("url", url);
        intent.putExtra("dir_path", dirPath);
        context.startService(intent);
    }
}