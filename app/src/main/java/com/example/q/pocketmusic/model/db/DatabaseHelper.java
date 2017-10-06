package com.example.q.pocketmusic.model.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.example.q.pocketmusic.model.bean.local.Img;
import com.example.q.pocketmusic.model.bean.local.ConvertSong;
import com.example.q.pocketmusic.model.bean.local.LocalSong;
import com.example.q.pocketmusic.model.bean.local.Record;
import com.example.q.pocketmusic.model.bean.local.RecordAudio;
import com.j256.ormlite.android.apptools.OrmLiteSqliteOpenHelper;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map; /**
 * Created by 鹏君 on 2016/8/31.
 */

/**
 * 数据库的打开和关闭
 */
public class DatabaseHelper extends OrmLiteSqliteOpenHelper {
    private static final String TABLE_NAME = "sqlite-song.db";//数据库名
    private static final int TABLE_VERSION = 7;
    private Map<String, Dao> daos = new HashMap<>();
    private static DatabaseHelper helper;

    public DatabaseHelper(Context context) {
        super(context, TABLE_NAME, null, TABLE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase database, ConnectionSource connectionSource) {
        try {
            TableUtils.createTable(connectionSource, LocalSong.class);
            TableUtils.createTable(connectionSource, Img.class);
            TableUtils.createTable(connectionSource, Record.class);
            TableUtils.createTable(connectionSource, RecordAudio.class);
            //还可以创建其他表
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase database, ConnectionSource connectionSource, int oldVersion, int newVersion) {
        try {
            TableUtils.dropTable(connectionSource, LocalSong.class, true);//删除表然后创建新表
            TableUtils.dropTable(connectionSource, Img.class, true);
            TableUtils.dropTable(connectionSource, Record.class, true);
            TableUtils.dropTable(connectionSource, RecordAudio.class, true);
            onCreate(database, connectionSource);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    //双重锁单例
    public static synchronized DatabaseHelper getHelper(Context context) {
        if (helper == null) {
            synchronized (DatabaseHelper.class) {
                if (helper == null) {
                    helper = new DatabaseHelper(context.getApplicationContext());//防止内存泄漏,因为Database是长期存在，如果传入是Activity.this，那么Activity将永远不会销毁
                }
            }
        }
        return helper;
    }


    //通过泛型得到Dao，交给Map容器统一管理
    public synchronized Dao getDao(Class clazz) throws SQLException {
        Dao dao = null;
        String className = clazz.getSimpleName();
        if (daos.containsKey(className)) {
            dao = daos.get(className);
        }
        if (dao == null) {
            dao = super.getDao(clazz);
            daos.put(className, dao);
        }
        return dao;
    }

    //释放资源
    @Override
    public void close() {
        super.close();
        daos.clear();
    }
}
