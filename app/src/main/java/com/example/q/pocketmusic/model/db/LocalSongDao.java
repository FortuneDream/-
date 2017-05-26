package com.example.q.pocketmusic.model.db;

import android.content.Context;


import com.example.q.pocketmusic.model.bean.Song;
import com.example.q.pocketmusic.model.bean.local.LocalSong;
import com.example.q.pocketmusic.util.LogUtils;
import com.j256.ormlite.dao.Dao;

import java.sql.SQLException;
import java.util.List;

/**
 * Created by YQ on 2016/8/31.
 */

/**
 * 本地乐谱Dao
 */
public class LocalSongDao {
    private Dao<LocalSong, Integer> localSongOpe;//操作对象
    private DatabaseHelper helper;

    public LocalSongDao(Context context) {
        try {
            helper = DatabaseHelper.getHelper(context.getApplicationContext());
            localSongOpe = helper.getDao(LocalSong.class);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    //添加一个localSong,不重复添加
    public boolean add(LocalSong localSong) {
        try {
            List<LocalSong> list = queryForAll();
            if (list != null) {
                for (LocalSong song : list) {
                    if (song.getName().equals(localSong.getName())) {
                        return false;
                    }
                }
            }
            localSongOpe.create(localSong);
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    public void update(LocalSong localSong) {
        try {
            localSongOpe.update(localSong);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    //删除一个localSong
    public void delete(LocalSong localSong) {
        try {
            localSongOpe.delete(localSong);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    //这里需要改成按照按照文件搜索，并且每个文件只显示文件名，点开之后进入SongActivity界面，可打开大图
    public List<LocalSong> queryForAll() {
        try {
            return localSongOpe.queryForAll();
        } catch (SQLException e) {
            e.printStackTrace();

        }
        return null;
    }

    public LocalSong findBySongId(int id) {
        try {
            return localSongOpe.queryForId(id);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public LocalSong findByName(String name) {
        try {
            List<LocalSong> list = localSongOpe.queryForEq("name", name);
            if (list == null || list.size() == 0) {
                return null;
            }
            return list.get(0);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    //检测某个名字是否存在
    public boolean isExist(String name) {
        LocalSong queryResult = findByName(name);
        if (queryResult == null) {
            return false;
        } else {
            return true;
        }
    }
}
