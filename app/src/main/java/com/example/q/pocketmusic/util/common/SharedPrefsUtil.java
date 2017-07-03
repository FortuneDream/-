package com.example.q.pocketmusic.util.common;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * SharedPreference工具类，获取实例后直接set值。
 * 用该工具存储用户偏好设置：震动、声音、是否推送、是否第一次进入软件等信息
 * <p/>
 * 方式一：
 * 写入文件：
 * SharedPrefsUtil.getInstance().edit().putString("username", username).putString("mac", mac).apply();
 * 从文件读出：
 * String username1 = SharedPrefsUtil.getInstance().getString("username","");
 * <p/>
 * <p/>
 * 方式二：
 * 也可调用putString("xx","yy")、putInt("num", 1);
 * getString("xxx", "defaultValue");
 * <p/>
 * Created by Heyiyong on 2015/7/4.
 */
public class SharedPrefsUtil {
    private static SharedPreferences sp;
    private static final String defaultFileName = "setting";

    private SharedPrefsUtil() {
    }

    /**
     * 初始化SharedPreference实例，只需在全局Application调用
     *
     * @param context 上下文
     */
    public static void init(Context context) {
        if (sp == null) {
            sp = context.getSharedPreferences(defaultFileName, Context.MODE_PRIVATE);
        }
    }

    /**
     * 获取SharedPreference实例
     *
     * @return SharedPreference实例
     */
    public static SharedPreferences getInstance() {
        if (sp == null) {
            throw new ExceptionInInitializerError("SharedPreference尚未初始化，请在Application中进行初始化。");
        }
        return sp;
    }

    public static String getString(String key, String defaultValue) {
        return sp.getString(key, defaultValue);
    }

    /**
     * 不为空的情况下，保存到本地，并且去掉值的前后空格
     * @param key 键
     * @param value 值
     */
    public static void putString(String key, String value) {
        if (value != null) {
            sp.edit().putString(key, value.trim()).apply();
        }
    }

    public static boolean getBoolean(String key, boolean defaultValue) {
        return sp.getBoolean(key, defaultValue);
    }

    public static void putBoolean(String key, boolean value) {
        sp.edit().putBoolean(key, value).apply();
    }

    public static void putInt(String key, int value) {
        sp.edit().putInt(key, value).apply();
    }

    public static int getInt(String key, int defaultValue) {
        return sp.getInt(key, defaultValue);
    }

    public static void putFloat(String key, float value) {
        sp.edit().putFloat(key, value).apply();
    }

    public static float getFloat(String key, float defaultValue) {
        return sp.getFloat(key, defaultValue);
    }

    public static void putLong(String key, long value) {
        sp.edit().putLong(key, value).apply();
    }

    public static long getLong(String key, long defaultValue) {
        return sp.getLong(key, defaultValue);
    }

    /**
     * 清除某个值
     * @param key key
     */
    public static void remove(String key) {
        sp.edit().remove(key).apply();
    }
}
