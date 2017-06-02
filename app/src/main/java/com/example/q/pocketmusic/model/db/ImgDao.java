package com.example.q.pocketmusic.model.db;

import android.content.Context;

import com.example.q.pocketmusic.model.bean.local.Img;

import com.j256.ormlite.dao.Dao;

import java.sql.SQLException;
import java.util.List;

/**
 * Created by 鹏君 on 2016/9/19.
 */
public class ImgDao {
    private Dao<Img, Integer> imgOpe;
    private DatabaseHelper helper;

    public ImgDao(Context context) {
        helper = DatabaseHelper.getHelper(context.getApplicationContext());
        try {
            imgOpe = helper.getDao(Img.class);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void add(Img img) {
        try {
            imgOpe.create(img);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<Img> findAllImg() {
        try {
            return imgOpe.queryForAll();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public void delete(Img img){
        try {
            imgOpe.delete(img);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
