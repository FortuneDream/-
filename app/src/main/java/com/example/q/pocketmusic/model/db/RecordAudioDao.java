package com.example.q.pocketmusic.model.db;

import android.content.Context;

import com.example.q.pocketmusic.model.bean.local.RecordAudio;
import com.j256.ormlite.dao.Dao;

import java.sql.SQLException;
import java.util.List;

/**
 * Created by 81256 on 2016/10/23.
 */

public class RecordAudioDao {
    private Dao<RecordAudio, Integer> recordAudioOpe;//操作对象
    private DatabaseHelper helper;

    public RecordAudioDao(Context context) {
        try {
            helper = DatabaseHelper.getHelper(context.getApplicationContext());
            recordAudioOpe = helper.getDao(RecordAudio.class);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    //添加
    public boolean add(RecordAudio recordAudio) {
        try {
            List<RecordAudio> list = queryForAll();
            if (list!=null){
                for (RecordAudio audio : list) {
                    if (audio.getName().equals(recordAudio.getName())) {
                        return false;
                    }
                }
            }
            recordAudioOpe.create(recordAudio);
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    //删除
    public void delete(RecordAudio recordAudio) {
        try {
            recordAudioOpe.delete(recordAudio);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<RecordAudio> queryForAll() {
        try {
            return recordAudioOpe.queryForAll();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
}
