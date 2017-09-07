package com.example.q.pocketmusic.config;

import android.content.Context;
import android.content.Intent;
import android.content.pm.FeatureInfo;
import android.database.ContentObserver;
import android.database.Cursor;
import android.graphics.BitmapFactory;
import android.graphics.Point;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.provider.MediaStore;
import android.support.design.widget.Snackbar;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.view.WindowManager;

import com.example.q.pocketmusic.R;
import com.example.q.pocketmusic.callback.ToastUpdateListener;
import com.example.q.pocketmusic.model.bean.MyUser;
import com.example.q.pocketmusic.module.home.HomeActivity;
import com.example.q.pocketmusic.util.CheckUserUtil;
import com.example.q.pocketmusic.util.common.LogUtils;
import com.example.q.pocketmusic.util.common.ToastUtil;
import com.example.q.pocketmusic.view.widget.net.SnackBarUtil;

import java.io.File;

import cn.bmob.v3.BmobUser;

/**
 * Created by 81256 on 2017/9/6.
 */

public class ScreenshotContentObserver extends ContentObserver {
    private static ScreenshotContentObserver instance;
    private Context mContext;
    private Handler handler;

    public ScreenshotContentObserver(Handler handler,Context context) {
        super(null);//传入Null，表示onChange操作在Binder线程中，所以要自己创建handler发到自己的MessageQueue中
        this.mContext = context.getApplicationContext();
        this.handler=handler;


    }

    public static void startObserve(Handler handler,Context context) {
        if (instance == null) {
            instance = new ScreenshotContentObserver(handler,context);
            instance.register();
        }
    }

    public static void stopObserve() {
        instance.unregister();
        instance = null;
    }

    private void register() {
        mContext.getContentResolver().registerContentObserver(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, false, this);
    }

    private void unregister() {
        mContext.getContentResolver().unregisterContentObserver(this);
    }


    //监控变化
    @Override
    public void onChange(boolean selfChange) {
        super.onChange(selfChange);
        String[] columns = {MediaStore.MediaColumns.DATE_ADDED, MediaStore.MediaColumns.DATA};
        Cursor cursor = null;
        try {
            cursor = mContext.getContentResolver().query(
                    MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                    columns,
                    null,
                    null,
                    MediaStore.MediaColumns.DATE_MODIFIED + " desc");
            if (cursor == null) {
                return;
            }
            if (cursor.moveToFirst()) {
                String filePath = cursor.getString(cursor.getColumnIndex(MediaStore.MediaColumns.DATA));
                long addTime = cursor.getLong(cursor.getColumnIndex(MediaStore.MediaColumns.DATE_ADDED));
                if (matchAddTime(addTime) && matchPath(filePath) && matchSize(filePath)) {
                        handler.sendEmptyMessage(1);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (cursor != null) {
                try {
                    cursor.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }



    private boolean matchSize(String filePath) {
        WindowManager wm = (WindowManager) mContext.getSystemService(Context.WINDOW_SERVICE);
        int width = wm.getDefaultDisplay().getWidth();
        int height = wm.getDefaultDisplay().getHeight();
        Point size = new Point(width, height);
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(filePath, options);
        return size.x >= options.outWidth && size.y >= options.outHeight;
    }

    private boolean matchPath(String filePath) {
        String lower = filePath.toLowerCase();
        return lower.contains("screenshot");
    }

    private boolean matchAddTime(long addTime) {
        return System.currentTimeMillis() - addTime * 1000 < 1500;
    }
}
