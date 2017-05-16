package com.example.q.pocketmusic.config;

import android.app.Application;
import android.os.Build;
import android.support.v4.content.ContextCompat;

import com.example.q.pocketmusic.R;
import com.example.q.pocketmusic.util.GlideImageLoader;
import com.example.q.pocketmusic.util.MyToast;
import com.example.q.pocketmusic.util.SharedPrefsUtil;

import cn.bmob.v3.Bmob;
import cn.bmob.v3.listener.BmobUpdateListener;
import cn.bmob.v3.statistics.AppStat;
import cn.bmob.v3.update.BmobUpdateAgent;
import cn.bmob.v3.update.UpdateResponse;
import cn.finalteam.galleryfinal.CoreConfig;
import cn.finalteam.galleryfinal.FunctionConfig;
import cn.finalteam.galleryfinal.GalleryFinal;
import cn.finalteam.galleryfinal.ImageLoader;
import cn.finalteam.galleryfinal.ThemeConfig;

/**
 * Created by YQ on 2016/9/10.
 */

public class MyApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
//        initHotfix();
        SharedPrefsUtil.init(getApplicationContext());
        Bmob.initialize(this, Constant.APP_ID, "Bmob");
        //官网SDK
        AppStat.i(Constant.APP_ID, "Bmob");
        //    initAutoUpdate(); //自动表创建
        //发布时，开启异常捕获器
//        CrashHandler crashHandler = CrashHandler.getInstance();
//        crashHandler.init(this);
        initGalleryFinal();
    }

    private void initAutoUpdate() {
        BmobUpdateAgent.initAppVersion();
    }


//    //热修复
//    private void initHotfix() {
//        SophixManager.getInstance().setContext(this)
//                .setAppVersion(BuildConfig.VERSION_NAME)
//                .setAesKey(null)
//                .setEnableDebug(true)
//                .setPatchLoadStatusStub(new PatchLoadStatusListener() {
//                    @Override
//                    public void onLoad(int i, int i1, String s, int i2) {
//                        LogUtils.e("hotfix", "i:" + i);
//                        LogUtils.e("hotfix", "i1:" + i1);
//                        LogUtils.e("hotfix", "s:" + s);
//                        LogUtils.e("hotfix", "i2:" + i2);
//                        // 补丁加载回调通知
////                        LogUtils.e("TAG","code:"+code);
////                        if (code == PatchStatus.CODE_LOAD_SUCCESS) {
//////                            MyToast.showToast(getApplicationContext(), "补丁加载成功");
////                            // 表明补丁加载成功
////                        } else if (code == PatchStatus.CODE_LOAD_RELAUNCH) {
////                            // 表明新补丁生效需要重启. 开发者可提示用户或者强制重启;
////                            // 建议: 用户可以监听进入后台事件, 然后应用自杀
////                            MyToast.showToast(getApplicationContext(), "已修复部分bug~请重启app");
////                        } else if (code == PatchStatus.CODE_LOAD_FAIL) {
////                            // 内部引擎异常, 推荐此时清空本地补丁, 防止失败补丁重复加载
////                            SophixManager.getInstance().cleanPatches();
////                        } else {
////                            LogUtils.e("HotFix", info);
////                            // 其它错误信息, 查看PatchStatus类说明
////                        }
//                    }
//                }).initialize();
//        SophixManager.getInstance().queryAndLoadNewPatch();
//    }

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
