package com.example.q.pocketmusic.data.model;

import com.example.q.pocketmusic.callback.ToastQueryListListener;
import com.example.q.pocketmusic.callback.ToastQueryListener;
import com.example.q.pocketmusic.callback.ToastUpdateListener;
import com.example.q.pocketmusic.config.constant.BmobConstant;
import com.example.q.pocketmusic.data.bean.MyUser;
import com.example.q.pocketmusic.data.bean.collection.CollectionSong;
import com.example.q.pocketmusic.data.bean.share.SharePic;
import com.example.q.pocketmusic.data.bean.share.ShareSong;
import com.example.q.pocketmusic.module.common.BaseModel;

import java.util.ArrayList;
import java.util.List;

import cn.bmob.v3.BmobBatch;
import cn.bmob.v3.BmobObject;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.datatype.BatchResult;
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
    public void getAllShareList(int typeId, int mPage, ToastQueryListener<ShareSong> listener) {
        BmobQuery<ShareSong> query = new BmobQuery<>();
        query.order(DEFAULT_INVERTED_CREATE);
        query.addWhereEqualTo(BmobConstant.BMOB_INSTRUMENT, typeId);
        query.setLimit(DEFAULT_LIMIT);
        query.setSkip(mPage * DEFAULT_LIMIT);
        query.include(BmobConstant.BMOB_USER);
        query.findObjects(listener);
    }

    //修改分享名字
    public void updateShareName(ShareSong item, String str, ToastUpdateListener listener) {
        item.setName(str);
        item.update(listener);
    }

    public void deleteShareSong(final ShareSong shareSong, final ToastUpdateListener toastUpdateListener) {
        BmobQuery<SharePic> query = new BmobQuery<>();
        query.addWhereEqualTo("shareSong", shareSong);
        query.findObjects(new ToastQueryListener<SharePic>() {
            @Override
            public void onSuccess(List<SharePic> list) {
                List<BmobObject> list1 = new ArrayList<>();
                list1.addAll(list);
                new BmobBatch().deleteBatch(list1).doBatch(new ToastQueryListListener<BatchResult>() {
                    @Override
                    public void onSuccess(List<BatchResult> list) {
                        shareSong.delete(toastUpdateListener);
                    }
                });
            }
        });
    }

    public void updateShareType(ShareSong item, int type, ToastUpdateListener toastUpdateListener) {
        item.setInstrument(type);
        item.update(toastUpdateListener);
    }
}
