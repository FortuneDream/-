package com.example.q.pocketmusic.data.model;

import com.example.q.pocketmusic.callback.ToastQueryListener;
import com.example.q.pocketmusic.config.constant.BmobConstant;
import com.example.q.pocketmusic.data.bean.MoneySupport;
import com.example.q.pocketmusic.module.common.BaseModel;

import cn.bmob.v3.BmobQuery;

/**
 * Created by 81256 on 2017/10/31.
 */

public class SupportModel extends BaseModel<MoneySupport> {

    @Override
    public void getList(int page, ToastQueryListener<MoneySupport> listener) {
        super.getList(page, listener);
        BmobQuery<MoneySupport> query = new BmobQuery<>();
        initDefaultListQuery(query, page);
        query.include(BmobConstant.BMOB_USER);
        query.findObjects(listener);
    }


}
