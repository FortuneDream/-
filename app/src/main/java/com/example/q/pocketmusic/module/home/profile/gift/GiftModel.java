package com.example.q.pocketmusic.module.home.profile.gift;

import com.example.q.pocketmusic.callback.ToastQueryListener;
import com.example.q.pocketmusic.model.bean.MyUser;
import com.example.q.pocketmusic.model.bean.bmob.Gift;
import com.example.q.pocketmusic.module.common.BaseModel;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.datatype.BmobPointer;

/**
 * Created by 81256 on 2017/9/6.
 */

public class GiftModel extends BaseModel {

    public void getGiftList(MyUser user, int mPage, ToastQueryListener<Gift> listener) {
        BmobQuery<Gift> query = new BmobQuery<>();
        initDefaultListQuery(query, mPage);
        query.addWhereEqualTo("isGet", false);
        query.addWhereEqualTo("user", new BmobPointer(user));
        query.findObjects(listener);
    }

}
