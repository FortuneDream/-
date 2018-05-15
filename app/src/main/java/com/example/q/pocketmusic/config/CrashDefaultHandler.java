package com.example.q.pocketmusic.config;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Build;
import android.util.Log;

import com.dell.fortune.tools.crash.OnOccurCrashListener;
import com.example.q.pocketmusic.data.bean.bmob.ExceptionBean;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.SaveListener;

public class CrashDefaultHandler implements OnOccurCrashListener {
    private Context context;

    public CrashDefaultHandler(Context context) {
        this.context = context;
    }

    @Override
    public void onCrash(Thread thread,Throwable ex) {
        ExceptionBean exceptionBean = new ExceptionBean();
        try {
            PackageManager pm = context.getPackageManager();
            PackageInfo pi = pm.getPackageInfo(context.getPackageName(), PackageManager.GET_ACTIVITIES);
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
                        Log.e("CrashCatch", "成功上传错误信息");
                    } else {
                        Log.e("CrashCatch", "上传错误信息失败");
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
}
