package com.example.q.pocketmusic.util.common;

import android.support.annotation.Nullable;

import java.io.File;
import java.io.IOException;

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
}
