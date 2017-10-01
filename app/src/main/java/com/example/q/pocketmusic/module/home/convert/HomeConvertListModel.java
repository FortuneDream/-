package com.example.q.pocketmusic.module.home.convert;

import com.example.q.pocketmusic.callback.ToastQueryListener;
import com.example.q.pocketmusic.model.bean.convert.ConvertPost;
import com.example.q.pocketmusic.module.common.BaseModel;

import cn.bmob.v3.BmobQuery;

/**
 * Created by 81256 on 2017/10/1.
 */

public class HomeConvertListModel extends BaseModel<ConvertPost> {

    @Override
    public void getList( int page, ToastQueryListener<ConvertPost> listener) {
        super.getList(page, listener);
        BmobQuery<ConvertPost> query = new BmobQuery<>();
        initDefaultListQuery(query, page);
        query.findObjects(listener);
    }
}
