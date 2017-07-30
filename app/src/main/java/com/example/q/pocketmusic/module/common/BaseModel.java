package com.example.q.pocketmusic.module.common;

import com.example.q.pocketmusic.config.BmobConstant;
import com.example.q.pocketmusic.config.Constant;

import cn.bmob.v3.BmobQuery;

/**
 * Created by 鹏君 on 2017/7/30.
 * （￣m￣）
 */

public class BaseModel {
    public static final String DEFAULT_INVERTED_CREATE = BmobConstant.BMOB_CREATE_AT;//默认逆序
    public static final int DEFAULT_LIMIT = 10;


    public void initDefaultListQuery(BmobQuery<?> query, int mPage) {
        query.setLimit(DEFAULT_LIMIT);
        query.setSkip(mPage * DEFAULT_LIMIT);
        query.order(DEFAULT_INVERTED_CREATE);
    }
}
