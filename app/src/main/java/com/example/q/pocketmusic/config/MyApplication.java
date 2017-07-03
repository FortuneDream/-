package com.example.q.pocketmusic.config;

import android.app.Application;
import android.support.v4.content.ContextCompat;

import com.example.q.pocketmusic.R;
import com.example.q.pocketmusic.config.pic.GlideImageLoader;
import com.example.q.pocketmusic.util.common.SharedPrefsUtil;
import com.example.q.pocketmusic.util.common.ToastUtil;

import cn.bmob.v3.Bmob;
import cn.bmob.v3.statistics.AppStat;
import cn.finalteam.galleryfinal.CoreConfig;
import cn.finalteam.galleryfinal.FunctionConfig;
import cn.finalteam.galleryfinal.GalleryFinal;
import cn.finalteam.galleryfinal.ImageLoader;
import cn.finalteam.galleryfinal.ThemeConfig;

/**
 * Created by 鹏君 on 2016/9/10.
 */

public class MyApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        ToastUtil.init(this);
        SharedPrefsUtil.init(getApplicationContext());
        Bmob.initialize(this, Constant.APP_ID, "Bmob");
        //官网SDK
        AppStat.i(Constant.APP_ID, "Bmob");
        //发布时，开启异常捕获器
        CrashHandler crashHandler = CrashHandler.getInstance();
        crashHandler.init(this);
        initGalleryFinal();
    }

    private void initGalleryFinal() {
        ThemeConfig.Builder builder = new ThemeConfig.Builder();
        builder.setTitleBarBgColor(ContextCompat.getColor(this, R.color.colorPrimary));
        builder.setCheckSelectedColor(ContextCompat.getColor(this, R.color.colorPrimary));
        builder.setFabNornalColor(ContextCompat.getColor(this, R.color.colorPrimary));
        builder.setFabPressedColor(ContextCompat.getColor(this, R.color.colorAccent));
        builder.setTitleBarTextColor(ContextCompat.getColor(this, R.color.colorTitle));
        builder.setIconFab(R.drawable.ico_gou);
        ThemeConfig theme = builder.build();
        FunctionConfig functionConfig = new FunctionConfig.Builder()
                .setEnableEdit(true)
                .setEnableCrop(false)
                .setEnableRotate(false)
                .setCropSquare(true)
                .setEnablePreview(false)
                .build();
        ImageLoader imageloader = new GlideImageLoader();
        CoreConfig coreConfig = new CoreConfig.Builder(getApplicationContext(), imageloader, theme)
                .setFunctionConfig(functionConfig)
                .build();
        GalleryFinal.init(coreConfig);
    }
}
