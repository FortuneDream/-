package com.example.q.pocketmusic.module.home.net.special.list;

import com.example.q.pocketmusic.callback.ToastQueryListener;
import com.example.q.pocketmusic.model.bean.MoneySupport;
import com.example.q.pocketmusic.model.bean.special.SpecialColumn;
import com.example.q.pocketmusic.model.bean.special.SpecialColumnSong;
import com.example.q.pocketmusic.module.common.BaseModel;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.datatype.BmobPointer;

/**
 * Created by 81256 on 2017/9/7.
 */

public class SpecialListModel extends BaseModel {
    public void getSpecialList(SpecialColumn column, int mPage, ToastQueryListener<SpecialColumnSong> toastQueryListener) {
        BmobQuery<SpecialColumnSong> query=new BmobQuery<>();
        initDefaultListQuery(query, mPage);
        query.addWhereEqualTo("column",new BmobPointer(column));
        query.findObjects(toastQueryListener);
    }
}
