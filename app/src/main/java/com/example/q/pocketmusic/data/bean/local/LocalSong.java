package com.example.q.pocketmusic.data.bean.local;

import com.j256.ormlite.dao.ForeignCollection;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.field.ForeignCollectionField;
import com.j256.ormlite.table.DatabaseTable;

import java.io.Serializable;

/**
 * Created by 鹏君 on 2016/8/31.
 */
@DatabaseTable(tableName = "tb_localsong")
public class LocalSong implements Serializable {
    @DatabaseField(generatedId = true)
    private int id;
    @DatabaseField
    private String name;
    @DatabaseField
    private String date;
    @DatabaseField
    private String type;
    @ForeignCollectionField
    private ForeignCollection<Img> imgs;
    @DatabaseField
    private int sort;//置顶

    public int getSort() {
        return sort;
    }

    public void setSort(int sort) {
        this.sort = sort;
    }

    @Override
    public String toString() {
        return "LocalSong{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", date='" + date + '\'' +
                ", type='" + type + '\'' +
                ", imgs=" + imgs +
                '}';
    }



    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public ForeignCollection<Img> getImgs() {
        return imgs;
    }

    public void setImgs(ForeignCollection<Img> imgs) {
        this.imgs = imgs;
    }

}
