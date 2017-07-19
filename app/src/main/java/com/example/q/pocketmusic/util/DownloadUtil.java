package com.example.q.pocketmusic.util;

import android.content.Context;
import android.os.Environment;

import com.example.q.pocketmusic.config.Constant;
import com.example.q.pocketmusic.model.bean.DownloadInfo;
import com.example.q.pocketmusic.model.bean.local.Img;
import com.example.q.pocketmusic.model.bean.local.LocalSong;
import com.example.q.pocketmusic.model.db.ImgDao;
import com.example.q.pocketmusic.model.db.LocalSongDao;
import com.example.q.pocketmusic.module.common.BaseActivity;


import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLClassLoader;
import java.net.URLConnection;
import java.text.SimpleDateFormat;
import java.util.Date;
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
    private ImgDao imgDao;
    private LocalSong mDownloadSong;
    private SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.CHINA);
    private Context context;
    private DownloadInfo downloadInfo;

    public DownloadUtil(Context context) {
        localSongDao = new LocalSongDao(context);
        imgDao = new ImgDao(context);
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
            saveFile(response, dirPath, destPath);//保存文件
            saveLocalSongToDatabase(name, InstrumentFlagUtil.getTypeName(typeId), destPath);//保存到数据库
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

    /**
     * @param response 返回包
     * @param dirPath  文件目录路径
     * @param destPath 文件路径
     * @return 文件
     * @throws IOException
     */
    private File saveFile(Response response, String dirPath, String destPath) throws IOException {
        InputStream is = null;
        FileOutputStream fos = null;
        byte[] buff = new byte[1024 * 2];
        int len = 0;//每次读取的字节长度
        int sum = 0;//总得字节长度
        File destFile = new File(destPath);
        File dirFile = new File(dirPath);
        if (!dirFile.exists()) {
            dirFile.mkdirs();
        }
        try {
            fos = new FileOutputStream(destFile);
            is = response.body().byteStream();
            while ((len = is.read(buff)) != -1) {
                sum += len;//
                fos.write(buff, 0, len);
            }
            fos.flush();
            return destFile;
        } finally {
            response.body().close();
            if (is != null) {
                try {
                    is.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            if (fos != null) {
                try {
                    fos.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }
        }
    }

    //批量下载图片
    public void downloadBatchPic(final String name, final List<String> urls, final int typeId) {
        if (onDownloadListener != null) {
            downloadInfo = onDownloadListener.onStart();
        }
        if (!downloadInfo.isStart()) {
            if (onDownloadListener != null) {
                onDownloadListener.onFailed(downloadInfo.getInfo());
                return;
            }
        }
        //截取格式
        if (urls == null || urls.size() == 0) {
            if (onDownloadListener != null) {
                onDownloadListener.onFailed("地址错误");
                return;
            }
        }
        String urlType = URLConnection.guessContentTypeFromName(urls.get(0));

        if (urlType == null) {
            if (onDownloadListener != null) {
                onDownloadListener.onFailed("图片格式错误");
                return;
            }
        }

        String type = urlType.replaceAll("image/", "");
        //建立歌曲名分包
        final String fileDir = Environment.getExternalStorageDirectory() + "/" + Constant.FILE_NAME + "/" + name + "/";
        for (int i = 0; i < urls.size(); i++) {//顺序下载
            final String url = urls.get(i);
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

    private void onFinish() {
        pool.shutdown();//关闭线程池
        mDownloadSong = null;
    }

    private void saveLocalSongToDatabase(String name, String type, String url) {
        if (mDownloadSong == null) {
            mDownloadSong = new LocalSong();
            mDownloadSong.setName(name);
            mDownloadSong.setDate(format.format(new Date()));
            mDownloadSong.setSort(Constant.getSort());
            mDownloadSong.setType(type);
            localSongDao.add(mDownloadSong);
        }
        Img img = new Img();
        img.setUrl(url);
        img.setLocalSong(mDownloadSong);
        imgDao.add(img);
    }
}
