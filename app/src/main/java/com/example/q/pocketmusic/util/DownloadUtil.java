package com.example.q.pocketmusic.util;

import android.content.Context;
import android.os.Environment;
import android.support.annotation.Nullable;

import com.dell.fortune.tools.LogUtils;
import com.dell.fortune.tools.toast.ToastUtil;
import com.example.q.pocketmusic.config.constant.Constant;
import com.example.q.pocketmusic.config.constant.InstrumentConstant;
import com.example.q.pocketmusic.data.bean.DownloadInfo;
import com.example.q.pocketmusic.data.bean.local.LocalSong;
import com.example.q.pocketmusic.data.db.LocalSongDao;
import com.example.q.pocketmusic.module.common.BaseActivity;
import com.example.q.pocketmusic.util.common.FileUtils;



import java.io.IOException;
import java.net.URLConnection;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;


/**
 * Created by 鹏君 on 2017/1/17.
 */
//下载曲谱并保存到本地数据库
public class DownloadUtil {
    private final OkHttpClient client = new OkHttpClient();
    private ExecutorService pool = Executors.newSingleThreadExecutor();
    private OnDownloadListener onDownloadListener;
    private LocalSongDao localSongDao;
    private LocalSong mDownloadSong;
    private SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.CHINA);
    private Context context;
    private DownloadInfo downloadInfo;

    public DownloadUtil(Context context) {
        localSongDao = new LocalSongDao(context);
        this.context = context;
    }

    public DownloadUtil setOnDownloadListener(OnDownloadListener onDownloadListener) {
        this.onDownloadListener = onDownloadListener;
        return this;
    }

    public interface OnDownloadListener {
        DownloadInfo onStart();

        void onSuccess();

        void onFailed(String info);
    }


    //同步下载并保存
    private void downloadFile(String name, int typeId, String url, String dirPath, String destPath) {
        Request request = new Request.Builder().url(url).build();
        try {
            Response response = client.newCall(request).execute();
            FileUtils.saveFile(response, dirPath, destPath);//保存文件
            localSongDao.saveLocalSong(mDownloadSong, name, InstrumentConstant.getTypeName(typeId), destPath, format);//保存到数据库
        } catch (IOException e) {
            e.printStackTrace();
            ((BaseActivity) context).runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    if (onDownloadListener != null) {
                        onDownloadListener.onFailed("出现问题了~");//某一个文件下载Or保存Or存记录失败
                    }
                }
            });
        }
    }

    //批量下载图片
    public void downloadBatchPic(final String name, final List<String> urls, final int typeId) {
        if (onDownloadListener != null) {
            downloadInfo = onDownloadListener.onStart();
        }
        String urlType = getUrlType(urls);
        if (urlType == null) return;
        String type = urlType.replaceAll("image/", "");
        //建立歌曲名分包
        final String fileDir = Environment.getExternalStorageDirectory() + "/" + Constant.FILE_NAME + "/" + name + "/";
        for (int i = 0; i < urls.size(); i++) {//顺序下载
            final String url = urls.get(i);
            LogUtils.e("下载url:" + url);
            final String fileName = name + "_" + (i + 1) + "." + type;//文件名
            final int finalI = i;
            pool.execute(new Runnable() {
                @Override
                public void run() {
                    downloadFile(name, typeId, url, fileDir, fileDir + fileName);//下载
                    if (finalI == urls.size() - 1) {//全部下载完成
                        ((BaseActivity) context).runOnUiThread(new Runnable() {//切换到主线程
                            @Override
                            public void run() {
                                if (onDownloadListener != null) {
                                    onDownloadListener.onSuccess();
                                }
                            }
                        });
                        onFinish();
                    }
                }
            });
        }
    }

    @Nullable
    private String getUrlType(List<String> urls) {
        if (!downloadInfo.isStart()) {
            if (onDownloadListener != null) {
                onDownloadListener.onFailed(downloadInfo.getInfo());
                return null;
            }
        }
        //截取格式
        if (urls == null || urls.size() == 0) {
            if (onDownloadListener != null) {
                onDownloadListener.onFailed("地址错误");
            }
            return null;
        }
        String urlType = URLConnection.guessContentTypeFromName(urls.get(0));

        if (urlType == null) {
            if (onDownloadListener != null) {
                onDownloadListener.onFailed("图片格式错误");
                return null;
            }
        }
        if (urlType == null) {
            ToastUtil.showToast("格式错误");
            return null;
        }
        return urlType;
    }

    private void onFinish() {
        pool.shutdown();//关闭线程池
        mDownloadSong = null;
    }

}
