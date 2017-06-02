package com.example.q.pocketmusic.config;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Environment;
import android.os.Process;
import android.util.Log;

import com.example.q.pocketmusic.model.bean.bmob.ExceptionBean;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.SaveListener;

/**
 * Created by 鹏君 on 2016/9/11.
 */
public class CrashHandler implements Thread.UncaughtExceptionHandler {
    private static final String TAG = "CrashHandler";
    private static final String PATH = Environment.getExternalStorageDirectory().getAbsolutePath() + "/YuPuCrash/log/";
    private static CrashHandler instance = new CrashHandler();
    private static Thread.UncaughtExceptionHandler mDefaultCrashHandler;
    private Context mContext;
    private static final boolean DEBUG = true;
    private static final String FILE_NAME = "crash";
    private static final String FILE_NAME_SUFFIX = ".txt";


    @Override
    public void uncaughtException(Thread thread, Throwable ex) {
        dumpExceptionToSDCard(ex);//保存到文件
        //默认上传
        uploadExceptionToServer(ex);//上传至Bmob
        ex.printStackTrace();
        if (mDefaultCrashHandler != null) {
            mDefaultCrashHandler.uncaughtException(thread, ex);
        } else {
            Process.killProcess(Process.myPid());
        }
    }

    private void uploadExceptionToServer(Throwable ex) {
        ExceptionBean exceptionBean = new ExceptionBean();
        try {
            PackageManager pm = mContext.getPackageManager();
            PackageInfo pi = pm.getPackageInfo(mContext.getPackageName(), PackageManager.GET_ACTIVITIES);
            exceptionBean.setVersion(pi.versionName + "_" + pi.versionCode);
            exceptionBean.setVendor(Build.VERSION.RELEASE + "_" + Build.VERSION.SDK_INT);
            exceptionBean.setModel(Build.MODEL);
            exceptionBean.setCpu(Build.CPU_ABI);
            exceptionBean.setDate(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.CHINA).format(new Date()));
            String content = getExceptionContent(ex);
            exceptionBean.setContent(content);
            exceptionBean.save(new SaveListener<String>() {
                @Override
                public void done(String s, BmobException e) {
                    if (e == null) {
                        Log.e("CrashHandler", "成功上传错误信息");
                    } else {
                        Log.e("CrashHandler", "上传错误信息失败");
                    }
                }
            });
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
    }

    //设置异常信息
    private String getExceptionContent(Throwable ex) {
        StringBuilder builder = new StringBuilder();
        builder.append("异常类型: ").append(ex.toString()).append("\n");
        for (StackTraceElement traceElement : ex.getStackTrace()) {
            builder.append("at: ").append(traceElement.getClassName()).append(".").append(traceElement.getMethodName()).append(traceElement.getLineNumber()).append("\n");
        }
        return builder.toString();
    }

    private void dumpExceptionToSDCard(Throwable ex) {
        if (!Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
            if (DEBUG) {
                Log.e(TAG, "sdcard unmounted,skip dump exception");
                return;
            }
        }
        File dir = new File(PATH);
        if (!dir.exists()) {
            dir.mkdirs();
        }
        long current = System.currentTimeMillis();
        String time = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.CHINA).format(new Date(current));
        File file = new File(PATH + FILE_NAME + time + FILE_NAME_SUFFIX);
        try {
            PrintWriter pw = new PrintWriter(new BufferedWriter(new FileWriter(file)));
            pw.println(time);
            dumpPhoneInfo(pw);
            pw.println();
            ex.printStackTrace(pw);
            pw.close();
        } catch (Exception e) {
            e.printStackTrace();
            Log.e(TAG, "dump crash info fail");
        }
    }

    private void dumpPhoneInfo(PrintWriter pw) throws PackageManager.NameNotFoundException {
        PackageManager pm = mContext.getPackageManager();
        PackageInfo pi = pm.getPackageInfo(mContext.getPackageName(), PackageManager.GET_ACTIVITIES);

        pw.print("App Version:");
        pw.print(pi.versionName);
        pw.print("_");
        pw.println(pi.versionCode);

        //Android版本号
        pw.print("OS Version:");
        pw.print(Build.VERSION.RELEASE);
        pw.print("_");
        pw.println(Build.VERSION.SDK_INT);

        //手机厂商:
        pw.print("Vendor:");
        pw.println(Build.MANUFACTURER);

        //手机型号
        pw.print("Model:");
        pw.println(Build.MODEL);

        //CPU架构
        pw.print("CPU ABI:");
        pw.println(Build.CPU_ABI);
    }

    private CrashHandler() {

    }

    public static CrashHandler getInstance() {
        return instance;
    }

    public void init(Context context) {
        mDefaultCrashHandler = Thread.getDefaultUncaughtExceptionHandler();
        Thread.setDefaultUncaughtExceptionHandler(this);
        mContext = context.getApplicationContext();
    }
}
