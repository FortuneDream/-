package com.example.q.pocketmusic.module.home.profile.convert.post;

import com.example.q.pocketmusic.callback.ToastQueryListener;
import com.example.q.pocketmusic.config.BmobConstant;
import com.example.q.pocketmusic.model.bean.convert.ConvertComment;
import com.example.q.pocketmusic.module.common.BaseModel;
import com.example.q.pocketmusic.util.UserUtil;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.datatype.BmobPointer;

/**
 * Created by 81256 on 2017/10/6.
 */

public class ProfileConvertPostModel extends BaseModel<ConvertComment> {

    @Override
    public void getList(int page, ToastQueryListener<ConvertComment> listener) {
        super.getList(page, listener);
        BmobQuery<ConvertComment> query=new BmobQuery<>();
        initDefaultListQuery(query,page);
        query.include("user,post");
        query.addWhereRelatedTo("consume", new BmobPointer(UserUtil.user));
        query.findObjects(listener);
    }
}
