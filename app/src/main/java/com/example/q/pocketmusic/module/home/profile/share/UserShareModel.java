package com.example.q.pocketmusic.module.home.profile.share;

import com.example.q.pocketmusic.callback.ToastQueryListener;
import com.example.q.pocketmusic.config.BmobConstant;
import com.example.q.pocketmusic.model.bean.MyUser;
import com.example.q.pocketmusic.model.bean.share.ShareSong;
import com.example.q.pocketmusic.module.common.BaseModel;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.datatype.BmobPointer;

/**
 * Created by 鹏君 on 2017/8/24.
 * （￣m￣）
 */

public class UserShareModel extends BaseModel {

    //某个用户的分段列表
    public void getUserShareList(MyUser user, int page, ToastQueryListener<ShareSong> listener) {
        BmobQuery<ShareSong> queryComment = new BmobQuery<>();
        initDefaultListQuery(queryComment, page);
        queryComment.addWhereEqualTo(BmobConstant.BMOB_USER, new BmobPointer(user));
        queryComment.findObjects(listener);
    }

    //某个用户的所有分享列表
    public void getAllUserShareList(MyUser user, ToastQueryListener<ShareSong> listener) {
        BmobQuery<ShareSong> query = new BmobQuery<>();
        query.order(DEFAULT_INVERTED_CREATE);
        query.addWhereEqualTo(BmobConstant.BMOB_USER, new BmobPointer(user));
        query.findObjects(listener);
    }


    //得到所有分享列表
    public void getAllShareList(int mPage, ToastQueryListener<ShareSong> listener) {
        BmobQuery<ShareSong> query = new BmobQuery<>();
        query.order(DEFAULT_INVERTED_CREATE);
        query.setLimit(DEFAULT_LIMIT);
        query.setSkip(mPage * DEFAULT_LIMIT);
        query.include(BmobConstant.BMOB_USER);
        query.findObjects(listener);
    }
}
