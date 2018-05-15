package com.example.q.pocketmusic.util.common;

import android.support.annotation.Nullable;

import com.dell.fortune.tools.toast.ToastUtil;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import okhttp3.Response;

/**
 * Created by 鹏君 on 2016/10/2.
 */

public class FileUtils {

    public static void copyFile(String oldPath, String newPath) {
        if (oldPath != null) {
            File s = new File(oldPath);
            if (s.exists()) {
                s.renameTo(new File(newPath));
            }
        }
    }

    //深搜删除
    public static void deleteFile(File file) {
        if (file != null && file.exists()) {
            if (file.isDirectory()) {
                File[] files = file.listFiles();
                for (int i = 0; i < files.length; i++) {
                    deleteFile(files[i]);
                }
            }
            file.delete();
        }
    }

    //根据url后缀名创建文件,返回String类型的本地地址,可能为Null
    @Nullable
    public static File createFileByNet(String url, String dirPath) {
        boolean isSucceed = true;
        if (!dirPath.endsWith("/")) {
            dirPath = dirPath + "/";
        }
        File path = new File(dirPath);
        if (!path.exists()) {
            isSucceed = path.mkdirs();
            if (!isSucceed) {
                return null;
            }
        }
        String[] s = url.split("/");//123.apk
        String filePathString = dirPath + s[s.length - 1];
        File file = new File(filePathString);
        if (!file.exists()) {
            try {
                isSucceed = file.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
                ToastUtil.showToast(e.getMessage());
            }
        }
        if (isSucceed) {
            return file;
        } else {
            return null;
        }
    }


    public static File saveFile(Response response, String dirPath, String destPath) throws IOException {
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
}
