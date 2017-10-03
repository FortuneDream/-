package com.example.q.pocketmusic.model.db;

import android.content.Context;


import com.example.q.pocketmusic.model.bean.local.LocalConvertSong;
import com.example.q.pocketmusic.model.bean.local.LocalSong;
import com.j256.ormlite.dao.Dao;

import java.sql.SQLException;
import java.util.List;


/**
 * Created by 81256 on 2017/10/3.
 */

public class LocalConvertSongDao {
    private Dao<LocalConvertSong, Integer> mOpe;//操作对象
    private DatabaseHelper helper;

    public LocalConvertSongDao(Context context) {
        try {
            helper = DatabaseHelper.getHelper(context.getApplicationContext());
            mOpe = helper.getDao(LocalConvertSong.class);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    //添加
    public boolean add(LocalConvertSong addItem) {
        try {
            List<LocalConvertSong> list = queryForAll();
            if (list != null) {
                for (LocalConvertSong item : list) {
                    if (item.getName().equals(addItem.getName())) {
                        return false;
                    }
                }
            }
            mOpe.create(addItem);
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    //删除
    public void delete(LocalConvertSong recordAudio) {
        try {
            mOpe.delete(recordAudio);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<LocalConvertSong> queryForAll() {
        try {
            return mOpe.queryForAll();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public LocalConvertSong findByName(String name) {
        try {
            List<LocalConvertSong> list = mOpe.queryForEq("name", name);
            if (list == null || list.size() == 0) {
                return null;
            }
            return list.get(0);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
}
