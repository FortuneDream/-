package com.example.q.pocketmusic.module.home.profile.convert.temporary;

import com.example.q.pocketmusic.callback.ToastQueryListener;
import com.example.q.pocketmusic.config.BmobConstant;
import com.example.q.pocketmusic.model.bean.local.ConvertSong;
import com.example.q.pocketmusic.module.common.BaseModel;
import com.example.q.pocketmusic.util.UserUtil;

import cn.bmob.v3.Bmob;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.datatype.BmobPointer;

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
}
