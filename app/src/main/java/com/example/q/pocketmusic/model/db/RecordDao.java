package com.example.q.pocketmusic.model.db;

import android.content.Context;

import com.example.q.pocketmusic.model.bean.local.Record;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.stmt.QueryBuilder;

import java.sql.SQLException;
import java.util.Collections;
import java.util.List;

/**
 * Created by 鹏君 on 2016/9/21.
 */

public class RecordDao {
    private Dao<Record, Integer> recordOpe;
    private DatabaseHelper helper;

    public RecordDao(Context context) {
        helper = DatabaseHelper.getHelper(context.getApplicationContext());
        try {
            recordOpe = helper.getDao(Record.class);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void add(Record record) {
        try {
            List<Record> list = queryForAll();
            if (list != null) {
                for (Record rec : list) {
                    if (rec.getName().equals(record.getName())) {
                        return;
                    }
                }
            }
            recordOpe.createIfNotExists(record);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void delete(Record record) {
        try {
            recordOpe.delete(record);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<Record> queryForAll() {
        try {
            return recordOpe.queryForAll();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    //得到最新10个
    public List<Record> queryForLimitTen() {
        try {
            QueryBuilder<Record, Integer> queryBuilder = recordOpe.queryBuilder();
            queryBuilder.limit((long) 10);
            List<Record> list=queryBuilder.query();
            Collections.reverse(list);
            return list;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }


    public void deleteAllRecord() {
        try {
            recordOpe.delete(queryForAll());
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
