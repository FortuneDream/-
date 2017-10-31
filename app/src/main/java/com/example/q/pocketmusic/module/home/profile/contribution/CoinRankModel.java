package com.example.q.pocketmusic.module.home.profile.contribution;

import com.example.q.pocketmusic.callback.ToastQueryListener;
import com.example.q.pocketmusic.callback.ToastSaveListener;
import com.example.q.pocketmusic.callback.ToastUpdateListener;
import com.example.q.pocketmusic.config.BmobConstant;
import com.example.q.pocketmusic.model.bean.MyUser;
import com.example.q.pocketmusic.model.bean.bmob.Gift;
import com.example.q.pocketmusic.module.common.BaseModel;

import java.util.List;

import cn.bmob.v3.BmobQuery;

/**
 * Created by 81256 on 2017/9/7.
 */
//硬币模块
public class CoinRankModel extends BaseModel {

    //贡献前20个
    public void getCoinRankList(ToastQueryListener<MyUser> listener) {
        BmobQuery<MyUser> query = new BmobQuery<>();
        query.setLimit(20);
        query.order("-"+ BmobConstant.BMOB_COIN);
        query.findObjects(listener);
    }
}
