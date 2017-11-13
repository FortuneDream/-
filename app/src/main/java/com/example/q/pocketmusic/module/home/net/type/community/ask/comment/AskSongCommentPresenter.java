package com.example.q.pocketmusic.module.home.net.type.community.ask.comment;

import android.content.Intent;
import android.database.SQLException;
import android.support.annotation.NonNull;
import android.text.TextUtils;

import com.example.q.pocketmusic.callback.ToastQueryListListener;
import com.example.q.pocketmusic.callback.ToastQueryListener;
import com.example.q.pocketmusic.callback.ToastSaveListener;
import com.example.q.pocketmusic.callback.ToastUpdateListener;
import com.example.q.pocketmusic.config.CommonString;
import com.example.q.pocketmusic.config.Constant;
import com.example.q.pocketmusic.data.bean.Song;
import com.example.q.pocketmusic.data.bean.SongObject;
import com.example.q.pocketmusic.data.bean.ask.AskSongComment;
import com.example.q.pocketmusic.data.bean.ask.AskSongPic;
import com.example.q.pocketmusic.data.bean.ask.AskSongPost;
import com.example.q.pocketmusic.data.bean.collection.CollectionPic;
import com.example.q.pocketmusic.data.bean.collection.CollectionSong;
import com.example.q.pocketmusic.data.bean.local.Img;
import com.example.q.pocketmusic.data.bean.local.LocalSong;
import com.example.q.pocketmusic.data.bean.share.SharePic;
import com.example.q.pocketmusic.data.bean.share.ShareSong;
import com.example.q.pocketmusic.data.db.LocalSongDao;
import com.example.q.pocketmusic.module.common.BaseActivity;
import com.example.q.pocketmusic.module.common.BasePresenter;
import com.example.q.pocketmusic.module.common.IBaseView;
import com.example.q.pocketmusic.data.model.UserCollectionModel;
import com.example.q.pocketmusic.data.model.UserShareModel;
import com.example.q.pocketmusic.module.song.SongActivity;
import com.example.q.pocketmusic.util.UserUtil;
import com.example.q.pocketmusic.util.common.ToastUtil;
import com.j256.ormlite.dao.CloseableIterator;
import com.j256.ormlite.dao.ForeignCollection;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import cn.bmob.v3.BmobBatch;
import cn.bmob.v3.BmobObject;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.datatype.BatchResult;
import cn.bmob.v3.datatype.BmobFile;
import cn.bmob.v3.datatype.BmobPointer;
import cn.bmob.v3.listener.UploadBatchListener;
import cn.finalteam.galleryfinal.FunctionConfig;
import cn.finalteam.galleryfinal.GalleryFinal;
import cn.finalteam.galleryfinal.model.PhotoInfo;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.schedulers.Schedulers;

/**
 * Created by 鹏君 on 2016/11/14.
 */

public class AskSongCommentPresenter extends BasePresenter<AskSongCommentPresenter.IView> {
    private IView activity;
    private AskSongCommentModel mAskSongCommentModel;
    private UserShareModel mUserShareModel;
    private UserCollectionModel mUserCollectionModel;
    private AskSongPost post;
    private int mPage;

    public static final int LOCAL = 0;
    public static final int SHARE = 1;
    public static final int COLLECTION = 2;
    public int mUploadTypeFlag = -1;

    public void setPost(AskSongPost post) {
        this.post = post;
    }


    public AskSongCommentPresenter(IView activity) {
        attachView(activity);
        this.activity = getIViewRef();
        mAskSongCommentModel = new AskSongCommentModel();
        mUserShareModel = new UserShareModel();
        mUserCollectionModel = new UserCollectionModel();
    }

    public void setPage(int page) {
        this.mPage = page;
    }

    //获得发帖人
    public AskSongPost getPost() {
        return post;
    }

    //获得初始列表
    public void getInitCommentList(final boolean isRefreshing) {
        if (isRefreshing) {
            mPage = 0;
        }
        mAskSongCommentModel.getUserCommentList(post, mPage, new ToastQueryListener<AskSongComment>() {
            @Override
            public void onSuccess(List<AskSongComment> list) {
                activity.setCommentList(isRefreshing, list);
            }
        });
    }

    //获得更多
    public void getMoreCommentList() {
        mPage++;
        mAskSongCommentModel.getUserCommentList(post, mPage, new ToastQueryListener<AskSongComment>() {
            @Override
            public void onSuccess(List<AskSongComment> list) {
                activity.setCommentList(false, list);
            }
        });
    }


    //发送评论
    public void sendComment(final String comment) {
        if (TextUtils.isEmpty(comment)) {
            ToastUtil.showToast("评论文字不能为空哦~");
            return;
        }
        Boolean hasPic;
        hasPic = mAskSongCommentModel.getPicUrls().size() > 0;
        final AskSongComment askSongComment = new AskSongComment(UserUtil.user, post, comment, hasPic);
        activity.showLoading(true);
        activity.setCommentInput("");//空
        //添加评论表记录
        askSongComment.save(new ToastSaveListener<String>(activity) {
            @Override
            public void onSuccess(final String s) {
                //帖子表的评论数+1
                post.increment("commentNum", 1);
                post.update(new ToastUpdateListener(activity) {
                    @Override
                    public void onSuccess() {
                        if (mAskSongCommentModel.getPicUrls().size() <= 0) {//无图
                            activity.showLoading(false);
                            activity.sendCommentResult(s, askSongComment);
                            return;
                        }
                        //批量上传图片
                        switch (mUploadTypeFlag) {
                            case COLLECTION:
                                uploadCollectionPic(askSongComment, s);
                                break;
                            case SHARE:
                                uploadSharePic(askSongComment, s);
                                break;
                            case LOCAL:
                                uploadLocalPic(askSongComment, s);
                                break;
                        }
                    }
                });
            }
        });
    }

    private void uploadSharePic(final AskSongComment askSongComment, final String s) {
        List<BmobObject> askSongPics = new ArrayList<>();
        for (int i = 0; i < mAskSongCommentModel.getPicUrls().size(); i++) {
            AskSongPic askSongPic = new AskSongPic(askSongComment, mAskSongCommentModel.getPicUrls().get(i));
            askSongPics.add(askSongPic);
        }
        //批量添加AskSongPic表
        new BmobBatch().insertBatch(askSongPics).doBatch(new ToastQueryListListener<BatchResult>(activity) {
            @Override
            public void onSuccess(List<BatchResult> list) {
                UserUtil.increment(Constant.ADD_CONTRIBUTION_COMMENT_WITH_PIC, new ToastUpdateListener() {
                    @Override
                    public void onSuccess() {
                        activity.showLoading(false);
                        ToastUtil.showToast(CommonString.ADD_COIN_BASE + (Constant.ADD_CONTRIBUTION_COMMENT_WITH_PIC));
                        activity.sendCommentResult(s, askSongComment);
                    }
                }); //原子操作
            }
        });
    }

    private void uploadCollectionPic(final AskSongComment askSongComment, final String s) {
        List<BmobObject> askSongPics = new ArrayList<>();
        for (int i = 0; i < mAskSongCommentModel.getPicUrls().size(); i++) {
            AskSongPic askSongPic = new AskSongPic(askSongComment, mAskSongCommentModel.getPicUrls().get(i));
            askSongPics.add(askSongPic);
        }
        //批量添加AskSongPic表
        new BmobBatch().insertBatch(askSongPics).doBatch(new ToastQueryListListener<BatchResult>(activity) {
            @Override
            public void onSuccess(List<BatchResult> list) {
                UserUtil.increment(Constant.ADD_CONTRIBUTION_COMMENT_WITH_PIC, new ToastUpdateListener() {
                    @Override
                    public void onSuccess() {
                        activity.showLoading(false);
                        ToastUtil.showToast(CommonString.ADD_COIN_BASE + (Constant.ADD_CONTRIBUTION_COMMENT_WITH_PIC));
                        activity.sendCommentResult(s, askSongComment);
                    }
                }); //原子操作
            }
        });

    }

    //本地上传求谱
    private void uploadLocalPic(final AskSongComment askSongComment, final String s) {
        BmobFile.uploadBatch(mAskSongCommentModel.getPicUrls().toArray(new String[mAskSongCommentModel.getPicUrls().size()]), new UploadBatchListener() {
            @Override
            public void onSuccess(final List<BmobFile> list, List<String> list1) {
                if (mAskSongCommentModel.getPicUrls().size() == list1.size()) {// 全部的图片上传成功后调用
                    List<BmobObject> askSongPics = new ArrayList<>();
                    for (int i = 0; i < list.size(); i++) {
                        AskSongPic askSongPic = new AskSongPic(askSongComment, list1.get(i));
                        askSongPics.add(askSongPic);
                    }
                    //批量添加AskSongPic表
                    new BmobBatch().insertBatch(askSongPics).doBatch(new ToastQueryListListener<BatchResult>(activity) {
                        @Override
                        public void onSuccess(List<BatchResult> list) {
                            UserUtil.increment(Constant.ADD_CONTRIBUTION_COMMENT_WITH_PIC, new ToastUpdateListener() {
                                @Override
                                public void onSuccess() {
                                    activity.showLoading(false);
                                    mAskSongCommentModel.getPicUrls().clear();//清空
                                    ToastUtil.showToast(CommonString.ADD_COIN_BASE + (Constant.ADD_CONTRIBUTION_COMMENT_WITH_PIC));
                                    activity.sendCommentResult(s, askSongComment);
                                }
                            }); //原子操作
                        }
                    });
                }
            }

            @Override
            public void onProgress(int i, int i1, int i2, int i3) {

            }

            @Override
            public void onError(int i, String s) {
                //文件上传失败
                activity.showLoading(false);
                ToastUtil.showToast(CommonString.STR_ERROR_INFO + s);
            }
        });
    }


    //添加图片
    public void addPicByAlbum() {
        FunctionConfig config = new FunctionConfig.Builder()
                .setMutiSelectMaxSize(8)
                .build();
        GalleryFinal.openGalleryMuti(3, config, new GalleryFinal.OnHanlderResultCallback() {
            @Override
            public void onHanlderSuccess(int reqeustCode, List<PhotoInfo> resultList) {
                mAskSongCommentModel.getPicUrls().clear();
                for (PhotoInfo photoInfo : resultList) {
                    mAskSongCommentModel.getPicUrls().add(photoInfo.getPhotoPath());
                }
                mUploadTypeFlag = LOCAL;
                activity.addPicResult(mAskSongCommentModel.getPicUrls());
            }

            @Override
            public void onHanlderFailure(int requestCode, String errorMsg) {
                ToastUtil.showToast(CommonString.STR_ERROR_INFO + errorMsg);
            }
        });
    }

    //得到所有图片，显示dialog
    public void alertPicDialog(final AskSongComment askSongComment) {
        if (askSongComment.getHasPic()) {
            activity.showLoading(true);
            //查询有多少张图片
            mAskSongCommentModel.getPicList(askSongComment, new ToastQueryListener<AskSongPic>(activity) {
                @Override
                public void onSuccess(List<AskSongPic> list) {
                    final Song song = new Song(askSongComment.getContent(), null);//将评论者的内容当做标题
                    song.setDate(askSongComment.getCreatedAt());
                    List<String> urls = new ArrayList<>();
                    for (AskSongPic askSongPic : list) {
                        urls.add(askSongPic.getUrl());
                    }
                    song.setIvUrl(urls);
                    activity.showLoading(false);
                    activity.showPicDialog(song, askSongComment);
                }
            });
        }

    }

    //进入SongActivity
    public void enterSongActivity(Song song, AskSongComment askSongComment) {
        Intent intent = new Intent(activity.getCurrentContext(), SongActivity.class);
        SongObject songObject = new SongObject(song, Constant.FROM_ASK, Constant.MENU_DOWNLOAD_COLLECTION_AGREE_SHARE, Constant.NET);
        songObject.setCommunity(post.getInstrument());
        intent.putExtra(SongActivity.PARAM_SONG_OBJECT_SERIALIZABLE, songObject);
        intent.putExtra(SongActivity.ASK_COMMENT, askSongComment);
        activity.getCurrentContext().startActivity(intent);
    }


    @NonNull
    private List<String> getLocalImgs(String name) {
        LocalSongDao localSongDao = new LocalSongDao(activity.getCurrentContext());
        List<String> imgUrls = new ArrayList<>();
        LocalSong localSong = localSongDao.findByName(name);
        if (localSong == null) {
            ToastUtil.showToast("曲谱消失在了异次元。");
            return new ArrayList<>();
        }
        ForeignCollection<Img> imgs = localSong.getImgs();
        CloseableIterator<Img> iterator = imgs.closeableIterator();
        try {
            while (iterator.hasNext()) {
                Img img = iterator.next();
                imgUrls.add(img.getUrl());
            }
        } finally {
            try {
                iterator.close();
            } catch (SQLException | IOException e) {
                e.printStackTrace();
            }
        }
        return imgUrls;
    }

    //消耗硬币，置顶
    public void reduceIndexCoin() {
        final int coin = 2;
        if (!UserUtil.checkUserContribution((BaseActivity) activity, coin)) {
            ToastUtil.showToast("硬币不够哦~");
            return;
        }

        post.increment("index", coin / 2);
        post.update(new ToastUpdateListener() {
            @Override
            public void onSuccess() {
                UserUtil.increment(-coin, new ToastUpdateListener() {
                    @Override
                    public void onSuccess() {
                        ToastUtil.showToast(CommonString.REDUCE_COIN_BASE + coin);
                        activity.addHotIndex();
                    }
                });
            }
        });

    }

    //得到我的收藏列表
    public void queryMyCollectionList() {
        mUserCollectionModel.getAllUserCollectionList(UserUtil.user, new ToastQueryListener<CollectionSong>() {
            @Override
            public void onSuccess(List<CollectionSong> list) {
                activity.alertCollectionListDialog(list);
            }
        });
    }


    //得到我的分享列表
    public void queryMyShareList() {
        mUserShareModel.getAllUserShareList(UserUtil.user, new ToastQueryListener<ShareSong>() {
            @Override
            public void onSuccess(List<ShareSong> list) {
                activity.alertShareListDialog(list);
            }
        });
    }

    //得到我的收藏的某一首曲子
    public void addPicByMyCollection(final CollectionSong collection) {
        mUserCollectionModel.getCollectionPicList(collection, new ToastQueryListener<CollectionPic>() {
            @Override
            public void onSuccess(List<CollectionPic> list) {
                List<String> strList = new ArrayList<String>();
                for (int i = 0; i < list.size(); i++) {
                    strList.add(list.get(i).getUrl());
                }
                mAskSongCommentModel.getPicUrls().clear();
                mAskSongCommentModel.getPicUrls().addAll(strList);
                mUploadTypeFlag = COLLECTION;
                activity.addPicResult(mAskSongCommentModel.getPicUrls());
                activity.setCommentInput(collection.getName());
            }
        });
    }

    public void addPicByMyShare(final ShareSong shareSong) {
        BmobQuery<SharePic> query = new BmobQuery<>();
        query.addWhereEqualTo("shareSong", new BmobPointer(shareSong));
        query.findObjects(new ToastQueryListener<SharePic>() {
            @Override
            public void onSuccess(List<SharePic> list) {
                List<String> strList = new ArrayList<String>();
                for (int i = 0; i < list.size(); i++) {
                    strList.add(list.get(i).getUrl());
                }
                mAskSongCommentModel.getPicUrls().clear();
                mAskSongCommentModel.getPicUrls().addAll(strList);
                mUploadTypeFlag = SHARE;
                activity.addPicResult(mAskSongCommentModel.getPicUrls());
                activity.setCommentInput(shareSong.getName());
            }
        });
    }

    //RxJava,先查询本地曲谱的列表
    public void queryLocalSongList() {
        rx.Observable.create(new rx.Observable.OnSubscribe<List<String>>() {
            @Override
            public void call(Subscriber<? super List<String>> subscriber) {
                LocalSongDao dao = new LocalSongDao(activity.getCurrentContext());
                List<LocalSong> list = dao.queryForAll();
                List<String> names = new ArrayList<String>();
                for (LocalSong localSong : list) {
                    names.add(localSong.getName());
                }
                subscriber.onNext(names);
            }
        })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<List<String>>() {
                    @Override
                    public void call(List<String> strings) {
                        activity.alertLocalListDialog(strings);
                    }
                });
    }

    //通过曲谱的名字找到本地图片的路径，并添加，显示已添加的图片数量
    public void addPicByLocalSong(final String name) {
        rx.Observable.create(new rx.Observable.OnSubscribe<List<String>>() {
            @Override
            public void call(Subscriber<? super List<String>> subscriber) {
                subscriber.onNext(getLocalImgs(name));
            }
        })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<List<String>>() {
                    @Override
                    public void call(List<String> strings) {
                        mAskSongCommentModel.getPicUrls().clear();
                        mAskSongCommentModel.getPicUrls().addAll(strings);
                        mUploadTypeFlag = LOCAL;
                        activity.addPicResult(mAskSongCommentModel.getPicUrls());
                    }
                });
    }


    public interface IView extends IBaseView {

        void setCommentList(boolean isRefreshing, List<AskSongComment> list);

        void sendCommentResult(String s, AskSongComment askSongComment);

        void addPicResult(List<String> picUrls);

        void setCommentInput(String s);

        void showPicDialog(Song song, AskSongComment askSongComment);

        void addHotIndex();

        void alertShareListDialog(List<ShareSong> list);

        void alertCollectionListDialog(List<CollectionSong> list);

        void alertLocalListDialog(List<String> list);
    }
}
