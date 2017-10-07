package com.example.q.pocketmusic.module.home.profile.convert.temporary;

import com.example.q.pocketmusic.callback.ToastQueryListListener;
import com.example.q.pocketmusic.callback.ToastQueryListener;
import com.example.q.pocketmusic.callback.ToastUpdateListener;
import com.example.q.pocketmusic.config.BmobConstant;
import com.example.q.pocketmusic.model.bean.MyUser;
import com.example.q.pocketmusic.model.bean.collection.CollectionPic;
import com.example.q.pocketmusic.model.bean.collection.CollectionSong;
import com.example.q.pocketmusic.model.bean.convert.ConvertSong;
import com.example.q.pocketmusic.module.common.BaseModel;
import com.example.q.pocketmusic.util.UserUtil;

import java.util.ArrayList;
import java.util.List;

import cn.bmob.v3.BmobBatch;
import cn.bmob.v3.BmobObject;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.datatype.BatchResult;
import cn.bmob.v3.datatype.BmobPointer;
import cn.bmob.v3.datatype.BmobRelation;

/**
 * Created by 81256 on 2017/10/6.
 */

public class ProfileTemporaryConvertModel extends BaseModel<ConvertSong> {

    @Override
    public void getList(int page, ToastQueryListener<ConvertSong> listener) {
        super.getList(page, listener);
        BmobQuery<ConvertSong> query = new BmobQuery<>();
        initDefaultListQuery(query, page);
        query.addWhereEqualTo(BmobConstant.BMOB_USER, new BmobPointer(UserUtil.user));
        query.findObjects(listener);
    }

    public void deleteConvertSong(MyUser user, ConvertSong convertSong, final ToastUpdateListener listener) {
        //删除收藏
        convertSong.delete(listener);
    }

    public void getAllList(ToastQueryListener<ConvertSong> listener) {
        BmobQuery<ConvertSong> query = new BmobQuery<>();
        query.addWhereEqualTo(BmobConstant.BMOB_USER, new BmobPointer(UserUtil.user));
        query.findObjects(listener);
    }
}
