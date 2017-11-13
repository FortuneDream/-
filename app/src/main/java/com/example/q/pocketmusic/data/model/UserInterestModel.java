package com.example.q.pocketmusic.data.model;

import com.example.q.pocketmusic.callback.ToastQueryListener;
import com.example.q.pocketmusic.data.bean.MyUser;
import com.example.q.pocketmusic.module.common.BaseModel;
import com.example.q.pocketmusic.util.UserUtil;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.datatype.BmobPointer;

/**
 * Created by 81256 on 2017/10/31.
 */

public class UserInterestModel extends BaseModel<MyUser> {


    @Override
    public void getList(int page, ToastQueryListener<MyUser> listener) {
        super.getList(page, listener);
        BmobQuery<MyUser> query = new BmobQuery<>();
        initDefaultListQuery(query, page);
        query.addWhereRelatedTo("interests", new BmobPointer(UserUtil.user));
        query.findObjects(listener);
    }
}
