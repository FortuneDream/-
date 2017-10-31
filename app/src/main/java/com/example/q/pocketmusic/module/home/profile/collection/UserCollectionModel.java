package com.example.q.pocketmusic.module.home.profile.collection;

import com.example.q.pocketmusic.callback.ToastQueryListListener;
import com.example.q.pocketmusic.callback.ToastQueryListener;
import com.example.q.pocketmusic.callback.ToastUpdateListener;
import com.example.q.pocketmusic.config.BmobConstant;
import com.example.q.pocketmusic.model.bean.MyUser;
import com.example.q.pocketmusic.model.bean.collection.CollectionPic;
import com.example.q.pocketmusic.model.bean.collection.CollectionSong;
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
 * Created by 鹏君 on 2017/4/22.
 */

public class UserCollectionModel extends BaseModel {

    public UserCollectionModel() {
    }


    //获得某个用户的收藏
    public void getUserCollectionList(MyUser user, int page, ToastQueryListener<CollectionSong> listener) {
        BmobQuery<CollectionSong> query = new BmobQuery<>();
        initDefaultListQuery(query, page);
        query.addWhereRelatedTo(BmobConstant.BMOB_COLLECTIONS, new BmobPointer(user));
        query.findObjects(listener);
    }


    //获取所有列表
    public void getCollectionPicList(CollectionSong collectionSong, ToastQueryListener<CollectionPic> listener) {
        BmobQuery<CollectionPic> queryComment = new BmobQuery<>();
        queryComment.addWhereEqualTo("collectionSong", new BmobPointer(collectionSong));
        queryComment.findObjects(listener);
    }

    //删除收藏
    public void deleteCollection(final CollectionSong collectionSong, final ToastUpdateListener listener) {
        BmobRelation relation = new BmobRelation();
        relation.remove(collectionSong);
        UserUtil.user.setCollections(relation);
        UserUtil.user.update(new ToastUpdateListener() {
            @Override
            public void onSuccess() {
                //删除收藏多个图片表,
                BmobQuery<CollectionPic> query = new BmobQuery<>();
                query.addWhereEqualTo("collectionSong", collectionSong);
                query.findObjects(new ToastQueryListener<CollectionPic>() {
                    @Override
                    public void onSuccess(List<CollectionPic> list) {
                        List<BmobObject> pics = new ArrayList<>();
                        pics.addAll(list);
                        new BmobBatch().deleteBatch(pics).doBatch(new ToastQueryListListener<BatchResult>() {
                            @Override
                            public void onSuccess(List<BatchResult> list) {
                                //删除收藏记录
                                collectionSong.delete(listener);
                            }
                        });
                    }
                });

            }
        });
    }

    //拉取所有的列表
    public void getAllUserCollectionList(MyUser user, ToastQueryListener<CollectionSong> listener) {
        BmobQuery<CollectionSong> query = new BmobQuery<>();
        query.order(DEFAULT_INVERTED_CREATE);
        query.addWhereRelatedTo("collections", new BmobPointer(user));
        query.findObjects(listener);

    }


    //修改收藏名字
    public void updateConnectionName(CollectionSong item, String str, ToastUpdateListener listener) {
        item.setName(str);
        item.update(listener);
    }
}
