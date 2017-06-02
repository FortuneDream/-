package com.example.q.pocketmusic.util;

import android.os.Environment;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;

/**
 * Created by 鹏君 on 2016/10/2.
 */

public class FileUtils {
    private static File file;

    public static boolean hasSDcard() {
        return Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED);
    }

    public static File getNewFile() {
        if (file == null)
            file = new File(Environment.getExternalStorageDirectory(), System.currentTimeMillis() + ".apk");
        return file;
    }

    public static File getLastFile(){
        return file;
    }

    public static void clearFile() {
        file = null;
    }

    public static void copyFile(String oldPath, String newPath) {
        try {
            int bytesum = 0;
            int byteread = 0;
            //这里会覆盖同名(先这样写吧。。。)
            File newfile = new File(newPath);
            if (newfile.exists()) {
                newfile.delete();
            }
            File oldfile = new File(oldPath);
            if (oldfile.exists()) { //文件存在时
                InputStream inStream = new FileInputStream(oldPath); //读入原文件
                FileOutputStream fs = new FileOutputStream(newPath);
                byte[] buffer = new byte[1444];
                int length;
                while ((byteread = inStream.read(buffer)) != -1) {
                    bytesum += byteread; //字节数 文件大小
                    fs.write(buffer, 0, byteread);
                }
                inStream.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
