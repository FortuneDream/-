package com.example.q.pocketmusic.module.home.net.type.community.ask.comment;

import android.database.SQLException;
import android.support.annotation.NonNull;
import android.text.TextUtils;

import com.dell.fortune.tools.toast.ToastUtil;
import com.example.q.pocketmusic.R;
import com.example.q.pocketmusic.callback.ToastQueryListListener;
import com.example.q.pocketmusic.callback.ToastQueryListener;
import com.example.q.pocketmusic.callback.ToastSaveListener;
import com.example.q.pocketmusic.callback.ToastUpdateListener;
import com.example.q.pocketmusic.config.constant.BmobConstant;
import com.example.q.pocketmusic.config.constant.CoinConstant;
import com.example.q.pocketmusic.config.constant.Constant;
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
import com.example.q.pocketmusic.data.model.UserCollectionModel;
import com.example.q.pocketmusic.data.model.UserShareModel;
import com.example.q.pocketmusic.module.common.BaseActivity;
import com.example.q.pocketmusic.module.common.BasePresenter;
import com.example.q.pocketmusic.module.common.IBaseView;
import com.example.q.pocketmusic.module.song.SongActivity;
import com.example.q.pocketmusic.util.UserUtil;
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
        super(activity);
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
    public void getCommentList(final boolean isRefreshing) {
        mPage++;
        if (isRefreshing) {
            mPage = 0;
        }
        mAskSongCommentModel.getUserCommentList(post, mPage, new ToastQueryListener<AskSongComment>() {
            @Override
            public void onSuccess(List<AskSongComment> list) {
                mView.setCommentList(isRefreshing, list);
            }
        });
    }


    //发送评论
    public void sendComment(final String comment) {
        if (TextUtils.isEmpty(comment)) {
            ToastUtil.showToast(mView.getResString(R.string.complete_info));
            return;
        }
        Boolean hasPic;
        hasPic = mAskSongCommentModel.getPicUrls().size() > 0;
        final AskSongComment askSongComment = new AskSongComment(UserUtil.user, post, comment, hasPic);
        mView.showLoading(true);
        mView.setCommentInput("");//空
        //添加评论表记录
        askSongComment.save(new ToastSaveListener<String>() {
            @Override
            public void onSuccess(final String s) {
                //帖子表的评论数+1
                post.increment(BmobConstant.BMOB_COMMENT_NUM, 1);
                post.update(new ToastUpdateListener(mView) {
                    @Override
                    public void onSuccess() {
                        if (mAskSongCommentModel.getPicUrls().size() <= 0) {//无图
                            mView.showLoading(false);
                            mView.sendCommentResult(s, askSongComment);
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
        new BmobBatch().insertBatch(askSongPics).doBatch(new ToastQueryListListener<BatchResult>() {
            @Override
            public void onSuccess(List<BatchResult> list) {
                UserUtil.increment(CoinConstant.ADD_COIN_COMMENT_WITH_PIC, new ToastUpdateListener() {
                    @Override
                    public void onSuccess() {
                        mView.showLoading(false);
                        ToastUtil.showToast(mView.getResString(R.string.add_coin) + (CoinConstant.ADD_COIN_COMMENT_WITH_PIC));
                        mView.sendCommentResult(s, askSongComment);
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
        new BmobBatch().insertBatch(askSongPics).doBatch(new ToastQueryListListener<BatchResult>() {
            @Override
            public void onSuccess(List<BatchResult> list) {
                UserUtil.increment(CoinConstant.ADD_COIN_COMMENT_WITH_PIC, new ToastUpdateListener() {
                    @Override
                    public void onSuccess() {
                        mView.showLoading(false);
                        ToastUtil.showToast(mView.getResString(R.string.add_coin) + (CoinConstant.ADD_COIN_COMMENT_WITH_PIC));
                        mView.sendCommentResult(s, askSongComment);
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
                    new BmobBatch().insertBatch(askSongPics).doBatch(new ToastQueryListListener<BatchResult>() {
                        @Override
                        public void onSuccess(List<BatchResult> list) {
                            UserUtil.increment(CoinConstant.ADD_COIN_COMMENT_WITH_PIC, new ToastUpdateListener() {
                                @Override
                                public void onSuccess() {
                                    mView.showLoading(false);
                                    mAskSongCommentModel.getPicUrls().clear();//清空
                                    ToastUtil.showToast(mView.getResString(R.string.add_coin) + (CoinConstant.ADD_COIN_COMMENT_WITH_PIC));
                                    mView.sendCommentResult(s, askSongComment);
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
                mView.showLoading(false);
                ToastUtil.showToast(mView.getResString(R.string.send_error) + s);
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
                mView.addPicResult(mAskSongCommentModel.getPicUrls());
            }

            @Override
            public void onHanlderFailure(int requestCode, String errorMsg) {
                ToastUtil.showToast(mView.getResString(R.string.send_error) + errorMsg);
            }
        });
    }

    //得到所有图片，显示dialog
    public void alertPicDialog(final AskSongComment askSongComment) {
        if (askSongComment.getHasPic()) {
            mView.showLoading(true);
            //查询有多少张图片
            mAskSongCommentModel.getPicList(askSongComment, new ToastQueryListener<AskSongPic>() {
                @Override
                public void onSuccess(List<AskSongPic> list) {
                    final Song song = new Song(askSongComment.getContent(), null);//将评论者的内容当做标题
                    song.setDate(askSongComment.getCreatedAt());
                    List<String> urls = new ArrayList<>();
                    for (AskSongPic askSongPic : list) {
                        urls.add(askSongPic.getUrl());
                    }
                    song.setIvUrl(urls);
                    mView.showLoading(false);
                    mView.showPicDialog(song, askSongComment);
                }
            });
        }

    }

    //进入SongActivity
    public void enterSongActivity(Song song, AskSongComment askSongComment) {
        SongObject songObject = new SongObject(song, Constant.FROM_ASK, Constant.MENU_DOWNLOAD_COLLECTION_AGREE_SHARE, Constant.NET);
        mView.getCurrentContext().startActivity(SongActivity.buildAskIntent(
                mView.getCurrentContext(), songObject, post.getInstrument(), askSongComment
        ));
    }


    @NonNull
    private List<String> getLocalImgs(String name) {
        LocalSongDao localSongDao = new LocalSongDao(mView.getCurrentContext());
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
        if (!UserUtil.checkUserContribution((BaseActivity) mView, coin)) {
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
                        ToastUtil.showToast(mView.getResString(R.string.reduce_coin) + coin);
                        mView.addHotIndex();
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
                mView.alertCollectionListDialog(list);
            }
        });
    }


    //得到我的分享列表
    public void queryMyShareList() {
        mUserShareModel.getAllUserShareList(UserUtil.user, new ToastQueryListener<ShareSong>() {
            @Override
            public void onSuccess(List<ShareSong> list) {
                mView.alertShareListDialog(list);
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
                mView.addPicResult(mAskSongCommentModel.getPicUrls());
                mView.setCommentInput(collection.getName());
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
                mView.addPicResult(mAskSongCommentModel.getPicUrls());
                mView.setCommentInput(shareSong.getName());
            }
        });
    }

    //RxJava,先查询本地曲谱的列表
    public void queryLocalSongList() {
        rx.Observable.create(new rx.Observable.OnSubscribe<List<String>>() {
            @Override
            public void call(Subscriber<? super List<String>> subscriber) {
                LocalSongDao dao = new LocalSongDao(mView.getCurrentContext());
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
                        mView.alertLocalListDialog(strings);
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
                        mView.addPicResult(mAskSongCommentModel.getPicUrls());
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
