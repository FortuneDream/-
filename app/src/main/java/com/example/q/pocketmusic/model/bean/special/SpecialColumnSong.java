package com.example.q.pocketmusic.model.bean.special;

import cn.bmob.v3.BmobObject;

/**
 * Created by 鹏君 on 2017/8/24.
 * （￣m￣）
 */

public class SpecialColumnSong extends BmobObject {
    private SpecialColumn column;
    private String name;

    public SpecialColumn getColumn() {
        return column;
    }

    public void setColumn(SpecialColumn column) {
        this.column = column;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
