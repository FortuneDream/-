package com.example.q.pocketmusic.module.home.seek.ask.comment;

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
import com.example.q.pocketmusic.model.bean.MyUser;
import com.example.q.pocketmusic.model.bean.Song;
import com.example.q.pocketmusic.model.bean.SongObject;
import com.example.q.pocketmusic.model.bean.ask.AskSongComment;
import com.example.q.pocketmusic.model.bean.ask.AskSongPic;
import com.example.q.pocketmusic.model.bean.ask.AskSongPost;
import com.example.q.pocketmusic.model.bean.local.Img;
import com.example.q.pocketmusic.model.bean.local.LocalSong;
import com.example.q.pocketmusic.model.db.ImgDao;
import com.example.q.pocketmusic.model.db.LocalSongDao;
import com.example.q.pocketmusic.module.common.BasePresenter;
import com.example.q.pocketmusic.module.common.IBaseView;
import com.example.q.pocketmusic.module.song.SongActivity;
import com.example.q.pocketmusic.util.MyToast;
import com.j256.ormlite.dao.CloseableIterator;
import com.j256.ormlite.dao.ForeignCollection;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Observable;

import cn.bmob.v3.BmobBatch;
import cn.bmob.v3.BmobObject;
import cn.bmob.v3.datatype.BatchResult;
import cn.bmob.v3.datatype.BmobFile;
import cn.bmob.v3.listener.UploadBatchListener;
import cn.finalteam.galleryfinal.FunctionConfig;
import cn.finalteam.galleryfinal.GalleryFinal;
import cn.finalteam.galleryfinal.model.PhotoInfo;
import rx.Observer;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.schedulers.Schedulers;

/**
 * Created by 鹏君 on 2016/11/14.
 */

public class AskSongCommentPresenter extends BasePresenter<AskSongCommentPresenter.IView> {
    private IView activity;

    private AskSongCommentModel model;
    private AskSongPost post;
    private MyUser user;

    public void setPost(AskSongPost post) {
        this.post = post;
    }

    public void setUser(MyUser user) {
        this.user = user;
    }

    public AskSongCommentPresenter(IView activity) {
        attachView(activity);
        this.activity = getIViewRef();
        model = new AskSongCommentModel();
    }

    //获得发帖人
    public AskSongPost getPost() {
        return post;
    }

    //获得初始列表
    public void getInitCommentList(final boolean isRefreshing) {
        model.getInitCommentList(post, new ToastQueryListener<AskSongComment>(activity) {
            @Override
            public void onSuccess(List<AskSongComment> list) {
                if (!isRefreshing) {
                    activity.setCommentList(list);
                } else {
                    activity.setCommentListWithRefreshing(list);
                }

            }
        });
    }


    //发送评论
    public void sendComment(final String comment) {
        if (TextUtils.isEmpty(comment)) {
            return;
        }
        Boolean hasPic;

        hasPic = model.getPicUrls().size() > 0;
        final AskSongComment askSongComment = new AskSongComment(user, post, comment, hasPic);
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
                        if (model.getPicUrls().size() <= 0) {//无图
                            activity.showLoading(false);
                            activity.sendCommentResult(s, askSongComment);
                            return;
                        }
                        //批量上传图片
                        BmobFile.uploadBatch(model.getPicUrls().toArray(new String[model.getPicUrls().size()]), new UploadBatchListener() {
                            @Override
                            public void onSuccess(final List<BmobFile> list, List<String> list1) {
                                if (model.getPicUrls().size() == list1.size()) {// 全部的图片上传成功后调用
                                    List<BmobObject> askSongPics = new ArrayList<>();
                                    for (int i = 0; i < list.size(); i++) {
                                        AskSongPic askSongPic = new AskSongPic(askSongComment, list1.get(i));
                                        askSongPics.add(askSongPic);
                                    }
                                    //批量添加AskSongPic表
                                    new BmobBatch().insertBatch(askSongPics).doBatch(new ToastQueryListListener<BatchResult>(activity) {
                                        @Override
                                        public void onSuccess(List<BatchResult> list) {
                                            user.increment("contribution", Constant.ADD_CONTRIBUTION_COMMENT_WITH_PIC); //原子操作
                                            user.update(new ToastUpdateListener(activity) {
                                                @Override
                                                public void onSuccess() {
                                                    activity.showLoading(false);
                                                    MyToast.showToast(activity.getCurrentContext(), CommonString.ADD_COIN_BASE + (Constant.ADD_CONTRIBUTION_COMMENT_WITH_PIC));
                                                    activity.sendCommentResult(s, askSongComment);
                                                }
                                            });
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
                                MyToast.showToast(activity.getCurrentContext(), CommonString.STR_ERROR_INFO + s);
                            }
                        });
                    }
                });
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
                model.getPicUrls().clear();
                for (PhotoInfo photoInfo : resultList) {
                    model.getPicUrls().add(photoInfo.getPhotoPath());
                }
                activity.addPicResult(model.getPicUrls());
            }

            @Override
            public void onHanlderFailure(int requestCode, String errorMsg) {
                MyToast.showToast(activity.getCurrentContext(), CommonString.STR_ERROR_INFO + errorMsg);
            }
        });
    }

    //得到所有图片，显示dialog
    public void alertPicDialog(final AskSongComment askSongComment) {
        if (askSongComment.getHasPic()) {
            activity.showLoading(true);
            //查询有多少张图片
            model.getPicList(askSongComment, new ToastQueryListener<AskSongPic>(activity) {
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
        song.setNeedGrade(true);//收费
        SongObject songObject = new SongObject(song, Constant.FROM_ASK, Constant.SHOW_ALL_MENU, Constant.NET);
        intent.putExtra(SongActivity.PARAM_SONG_OBJECT_PARCEL, songObject);
        intent.putExtra(SongActivity.ASK_COMMENT, askSongComment);
        activity.getCurrentContext().startActivity(intent);
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
                        activity.alertLocalSongDialog(strings);
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
                        model.getPicUrls().clear();
                        model.getPicUrls().addAll(strings);
                        activity.addPicResult(model.getPicUrls());
                    }
                });
    }

    @NonNull
    private List<String> getLocalImgs(String name) {
        LocalSongDao localSongDao = new LocalSongDao(activity.getCurrentContext());
        List<String> imgUrls = new ArrayList<>();
        LocalSong localSong = localSongDao.findByName(name);
        if (localSong == null) {
            MyToast.showToast(activity.getCurrentContext(), "曲谱消失在了异次元。");
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


    public interface IView extends IBaseView {

        void setCommentList(List<AskSongComment> list);

        void sendCommentResult(String s, AskSongComment askSongComment);

        void addPicResult(List<String> picUrls);

        void setCommentInput(String s);

        void showPicDialog(Song song, AskSongComment askSongComment);

        void setCommentListWithRefreshing(List<AskSongComment> list);

        void alertLocalSongDialog(List<String> localSongs);
    }
}
