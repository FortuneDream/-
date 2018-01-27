package com.example.q.pocketmusic.data.model;

import android.content.Context;
import android.content.Intent;

import com.example.q.pocketmusic.R;
import com.example.q.pocketmusic.callback.ToastQueryListListener;
import com.example.q.pocketmusic.callback.ToastQueryListener;
import com.example.q.pocketmusic.callback.ToastSaveListener;
import com.example.q.pocketmusic.callback.ToastUpdateListener;
import com.example.q.pocketmusic.config.MyApplication;
import com.example.q.pocketmusic.config.constant.BmobConstant;
import com.example.q.pocketmusic.config.constant.CoinConstant;
import com.example.q.pocketmusic.config.constant.IntentConstant;
import com.example.q.pocketmusic.data.bean.MyUser;
import com.example.q.pocketmusic.data.bean.Song;
import com.example.q.pocketmusic.data.bean.SongObject;
import com.example.q.pocketmusic.data.bean.collection.CollectionPic;
import com.example.q.pocketmusic.data.bean.collection.CollectionSong;
import com.example.q.pocketmusic.module.common.BaseActivity;
import com.example.q.pocketmusic.module.common.BaseModel;
import com.example.q.pocketmusic.util.UserUtil;
import com.example.q.pocketmusic.util.common.ToastUtil;

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

    public interface OnAddCollectionResult {
        void onResult();
    }

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

    //添加收藏
    //添加收藏
    public void addCollection(Context context, final Song song, final Intent intent, final OnAddCollectionResult onAddCollectionResult) {
        if (checkCollection(context, song, intent)) return;
        //检测是否已经收藏
        BmobQuery<CollectionSong> query = new BmobQuery<>();
        query.order("-updatedAt");
        query.addWhereRelatedTo(BmobConstant.BMOB_COLLECTIONS, new BmobPointer(UserUtil.user));//在user表的Collections找user
        query.findObjects(new ToastQueryListener<CollectionSong>() {
            @Override
            public void onSuccess(List<CollectionSong> list) {
                //是否已收藏
                for (CollectionSong collectionSong : list) {
                    if (collectionSong.getName().equals(song.getName())) {
                        ToastUtil.showToast("你已经收藏了同名的曲谱哦~");
                        return;
                    }
                }
                //添加收藏记录
                final CollectionSong collectionSong = new CollectionSong();
                collectionSong.setName(song.getName());
                collectionSong.setIsFrom(((SongObject) (intent.getSerializableExtra(IntentConstant.EXTRA_SONG_ACTIVITY_SONG_OBJECT))).getFrom());
                collectionSong.setContent(song.getContent());
                collectionSong.save(new ToastSaveListener<String>() {

                    @Override
                    public void onSuccess(String s) {
                        final int numPic = song.getIvUrl().size();
                        List<BmobObject> collectionPics = new ArrayList<>();
                        for (int i = 0; i < numPic; i++) {
                            CollectionPic collectionPic = new CollectionPic();
                            collectionPic.setCollectionSong(collectionSong);
                            collectionPic.setUrl(song.getIvUrl().get(i));
                            collectionPics.add(collectionPic);
                        }
                        //批量修改
                        new BmobBatch().insertBatch(collectionPics).doBatch(new ToastQueryListListener<BatchResult>() {
                            @Override
                            public void onSuccess(List<BatchResult> list) {
                                BmobRelation relation = new BmobRelation();
                                relation.add(collectionSong);
                                UserUtil.user.setCollections(relation);//添加用户收藏
                                UserUtil.user.update(new ToastUpdateListener() {
                                    @Override
                                    public void onSuccess() {
                                        ToastUtil.showToast("成功收藏");
                                        UserUtil.increment(-CoinConstant.REDUCE_COIN_COLLECTION, new ToastUpdateListener() {
                                            @Override
                                            public void onSuccess() {
                                                ToastUtil.showToast(MyApplication.context.getResources().getString(R.string.reduce_coin) + CoinConstant.REDUCE_COIN_COLLECTION);
                                                onAddCollectionResult.onResult();
                                            }
                                        });
                                    }
                                });
                            }
                        });
                    }
                });
            }
        });
    }

    //检验收藏
    private boolean checkCollection(Context context, Song song, Intent intent) {
        if (!UserUtil.checkLocalUser((BaseActivity) context)) {
            ToastUtil.showToast("请先登录~");
            return false;
        }
        if (song == null || intent == null) {
            ToastUtil.showToast("发生未知错误，请重新打开乐谱后添加");
            return false;
        }

        if (song.getIvUrl() == null || song.getIvUrl().size() <= 0) {
            ToastUtil.showToast("图片为空");
            return false;
        }

        //贡献度是否足够
        if (!UserUtil.checkUserContribution(((BaseActivity) context), CoinConstant.REDUCE_COIN_COLLECTION)) {
            ToastUtil.showToast(((BaseActivity) context).getResString(R.string.coin_not_enough));
            return false;
        }
        return true;
    }
}
