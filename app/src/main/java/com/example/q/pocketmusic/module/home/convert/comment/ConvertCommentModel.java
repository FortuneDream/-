package com.example.q.pocketmusic.module.home.convert.comment;

import com.example.q.pocketmusic.callback.ToastQueryListener;
import com.example.q.pocketmusic.model.bean.convert.ConvertComment;
import com.example.q.pocketmusic.module.common.BaseModel;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.datatype.BmobPointer;

/**
 * Created by 81256 on 2017/10/1.
 */

public class ConvertCommentModel extends BaseModel<ConvertComment> {
    @Override
    public void getList(Object equalsObject, int page, ToastQueryListener<ConvertComment> listener) {
        super.getList(equalsObject, page, listener);
        BmobQuery<ConvertComment> query = new BmobQuery<>();
        initDefaultListQuery(query, page);
        query.addWhereEqualTo("post", new BmobPointer(equalsObject));
        query.findObjects(listener);
    }
}
