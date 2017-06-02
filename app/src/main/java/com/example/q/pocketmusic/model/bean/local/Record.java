package com.example.q.pocketmusic.model.bean.local;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

/**
 * Created by 鹏君 on 2016/9/21.
 */
@DatabaseTable(tableName = "tb_record")
public class Record {
    @DatabaseField(generatedId = true)
    private int id;
    @DatabaseField
    private String name;

    public Record() {
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

    @Override
    public String toString() {
        return "Record{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }
}
