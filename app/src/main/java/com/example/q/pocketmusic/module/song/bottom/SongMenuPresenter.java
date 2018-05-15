package com.example.q.pocketmusic.module.song.bottom;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.text.TextUtils;

import com.dell.fortune.tools.IntentUtil;
import com.dell.fortune.tools.LogUtils;
import com.dell.fortune.tools.toast.ToastUtil;
import com.example.q.pocketmusic.R;
import com.example.q.pocketmusic.callback.ToastQueryListener;
import com.example.q.pocketmusic.callback.ToastSaveListener;
import com.example.q.pocketmusic.callback.ToastUpdateListener;
import com.example.q.pocketmusic.config.constant.BmobConstant;
import com.example.q.pocketmusic.config.constant.CoinConstant;
import com.example.q.pocketmusic.config.constant.Constant;
import com.example.q.pocketmusic.config.constant.IntentConstant;
import com.example.q.pocketmusic.data.bean.DownloadInfo;
import com.example.q.pocketmusic.data.bean.MyUser;
import com.example.q.pocketmusic.data.bean.Song;
import com.example.q.pocketmusic.data.bean.SongObject;
import com.example.q.pocketmusic.data.bean.ask.AskSongComment;
import com.example.q.pocketmusic.data.bean.local.LocalSong;
import com.example.q.pocketmusic.data.bean.share.ShareSong;
import com.example.q.pocketmusic.data.db.LocalSongDao;
import com.example.q.pocketmusic.data.model.GiftModel;
import com.example.q.pocketmusic.data.model.UserCollectionModel;
import com.example.q.pocketmusic.data.model.UserCommunityStateModel;
import com.example.q.pocketmusic.module.common.BaseActivity;
import com.example.q.pocketmusic.module.common.BasePresenter;
import com.example.q.pocketmusic.module.common.IBaseView;
import com.example.q.pocketmusic.util.DownloadUtil;
import com.example.q.pocketmusic.util.UserUtil;

import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.datatype.BmobPointer;
import cn.bmob.v3.datatype.BmobRelation;

/**
 * Created by 鹏君 on 2017/5/31.
 */

public class SongMenuPresenter extends BasePresenter<SongMenuPresenter.IView> {
    private Intent intent;
    private boolean isEnableAgree = true;//是否能够点赞
    private Song song;
    private int isFrom;
    private UserCollectionModel userCollectionModel;

    public SongMenuPresenter(IView fragment) {
        super(fragment);
        userCollectionModel = new UserCollectionModel();
    }


    public Song getSong() {
        return song;
    }


    //下载
    public void download(final String name) {
        DownloadUtil downloadUtil = new DownloadUtil(mContext);
        downloadUtil.setOnDownloadListener(new DownloadUtil.OnDownloadListener() {
                                               @Override
                                               public DownloadInfo onStart() {
                                                   mView.dismissEditDialog();
                                                   return downloadStartCheck();
                                               }

                                               @Override
                                               public void onSuccess() {
                                                   addDownLoadNum();//增加下载量
                                                   addCommunityState(Constant.COMMUNITY_STATE_DOWNLOAD);//添加动态
                                                   mView.downloadResult(Constant.SUCCESS, "下载成功");
                                               }

                                               @Override
                                               public void onFailed(String info) {
                                                   mView.downloadResult(Constant.FAIL, info);
                                               }
                                           }
        ).downloadBatchPic(name, song.getIvUrl(), song.getTypeId());
    }


    //添加下载量
    private void addDownLoadNum() {
        if (isFrom == Constant.FROM_SHARE) {
            ShareSong shareSong = (ShareSong) intent.getSerializableExtra(IntentConstant.EXTRA_OPTIONAL_SONG_ACTIVITY_SHARE_SONG);
            shareSong.increment(BmobConstant.BMOB_DOWNLOAD_NUM);
            shareSong.update(new ToastUpdateListener() {
                @Override
                public void onSuccess() {
                    LogUtils.e("下载量+1");
                }
            });
        }
    }

    //下载检测
    private DownloadInfo downloadStartCheck() {
        //如果无图
        if (song.getIvUrl() == null || song.getIvUrl().size() <= 0) {
            return new DownloadInfo("没有图片", false);
        }
        //如果本地已经存在
        if (new LocalSongDao(mContext).isExist(song.getName())) {
            return new DownloadInfo("本地已存在", false);
        }
        //找不到用户
        if (!UserUtil.checkLocalUser((BaseActivity) mContext)) {
            return new DownloadInfo("找不到用户", false);
        }
        //硬币不足
        if (!UserUtil.checkUserContribution(((BaseActivity) mContext), CoinConstant.REDUCE_COIN_DOWNLOAD)) {
            return new DownloadInfo(mView.getResString(R.string.coin_not_enough), false);
        }
        //扣除硬币
        UserUtil.increment(-CoinConstant.REDUCE_COIN_DOWNLOAD, new ToastUpdateListener() {
            @Override
            public void onSuccess() {
                ToastUtil.showToast(mView.getResString(R.string.reduce_coin) + (CoinConstant.REDUCE_COIN_DOWNLOAD));
            }
        });
        return new DownloadInfo("", true);
    }

    //判断当前的评论的图片是否可以点赞
    public void checkAskHasAgree() {
        if (UserUtil.user == null) {
            //没有登录默认可以点赞
            isEnableAgree = true;
            return;
        }
        BmobQuery<MyUser> query = new BmobQuery<>();
        final MyUser user = MyUser.getCurrentUser(MyUser.class);
        AskSongComment askSongComment = (AskSongComment) intent.getSerializableExtra(IntentConstant.EXTRA_OPTIONAL_SONG_ACTIVITY_ASK_COMMENT);
        query.addWhereRelatedTo(BmobConstant.BMOB_AGREES, new BmobPointer(askSongComment));
        query.findObjects(new ToastQueryListener<MyUser>() {
            @Override
            public void onSuccess(List<MyUser> list) {
                for (MyUser other : list) {
                    if (other.getObjectId().equals(user.getObjectId())) {
                        //已经点赞
                        isEnableAgree = false;
                        break;
                    }
                    isEnableAgree = true;
                }
            }
        });
    }

    //检查分享是否能点赞
    private void checkShareHasAgree() {
        BmobQuery<MyUser> query = new BmobQuery<>();
        final MyUser user = MyUser.getCurrentUser(MyUser.class);
        ShareSong shareSong = (ShareSong) intent.getSerializableExtra(IntentConstant.EXTRA_OPTIONAL_SONG_ACTIVITY_SHARE_SONG);
        query.addWhereRelatedTo("agrees", new BmobPointer(shareSong));
        query.findObjects(new ToastQueryListener<MyUser>() {
            @Override
            public void onSuccess(List<MyUser> list) {
                for (MyUser other : list) {
                    if (other.getObjectId().equals(user.getObjectId())) {
                        //已经点赞
                        isEnableAgree = false;
                        break;
                    }
                    isEnableAgree = true;
                }
            }
        });
    }

    public boolean isEnableAgree() {
        return isEnableAgree;
    }


    //点赞
    public void agree() {
        if (isEnableAgree()) {
            if (isFrom == Constant.FROM_ASK) {
                agreeWithCommentPic();
            }
            if (isFrom == Constant.FROM_SHARE) {
                agreeWithSharePic();
            }

        } else {
            ToastUtil.showToast("已经赞过了哦~");
        }
    }

    //分享点赞
    private void agreeWithSharePic() {
        final ShareSong shareSong = (ShareSong) intent.getSerializableExtra(IntentConstant.EXTRA_OPTIONAL_SONG_ACTIVITY_SHARE_SONG);
        final MyUser user = MyUser.getCurrentUser(MyUser.class);
        if (TextUtils.equals(shareSong.getUser().getObjectId(), user.getObjectId())) {
            ToastUtil.showToast(mView.getResString(R.string.no_agree_self));
            return;
        }
        BmobRelation relation = new BmobRelation();
        relation.add(user);
        shareSong.setAgrees(relation);
        shareSong.increment(BmobConstant.BMOB_AGREE_NUM);//原子操作，点赞数加一
        shareSong.update(new ToastUpdateListener() {
            @Override
            public void onSuccess() {
                //增加分享人的硬币
                new GiftModel().addGift(shareSong.getUser(), CoinConstant.ADD_COIN_AGREE_OTHER, user, GiftModel.TYPE.SHARE, new ToastSaveListener<String>() {
                    @Override
                    public void onSuccess(String s) {
                        ToastUtil.showToast("已点赞");
                        addCommunityState(Constant.COMMUNITY_STATE_AGREE);
                    }
                });

            }
        });
    }

    //评论图片点赞
    private void agreeWithCommentPic() {
        final AskSongComment askSongComment = (AskSongComment) intent.getSerializableExtra(IntentConstant.EXTRA_OPTIONAL_SONG_ACTIVITY_ASK_COMMENT);
        final MyUser user = MyUser.getCurrentUser(MyUser.class);
        if (TextUtils.equals(askSongComment.getUser().getObjectId(), user.getObjectId())) {
            ToastUtil.showToast(mView.getResString(R.string.no_agree_self));
            return;
        }
        BmobRelation relation = new BmobRelation();
        relation.add(user);
        askSongComment.setAgrees(relation);
        askSongComment.increment(BmobConstant.BMOB_AGREE_NUM);//原子操作，点赞数加一
        askSongComment.update(new ToastUpdateListener() {
            @Override
            public void onSuccess() {
                new GiftModel().addGift(askSongComment.getUser(), CoinConstant.ADD_COIN_AGREE_OTHER, user, GiftModel.TYPE.COMMENT, new ToastSaveListener<String>() {
                    @Override
                    public void onSuccess(String s) {
                        ToastUtil.showToast("已点赞");
                    }
                });
            }
        });
    }

    //添加活跃状态
    private void addCommunityState(int state) {
        SongObject songObject = (SongObject) intent.getSerializableExtra(IntentConstant.EXTRA_SONG_ACTIVITY_SONG_OBJECT);
        new UserCommunityStateModel().addCommunityState(songObject.getCommunity(), state, songObject.getSong().getName(), new ToastSaveListener<String>() {
            @Override
            public void onSuccess(String s) {

            }
        });
    }


    //添加收藏
    public void addCollection() {
        userCollectionModel.addCollection(mContext, song, intent, new UserCollectionModel.OnAddCollectionResult() {
            @Override
            public void onResult() {
                addCommunityState(Constant.COMMUNITY_STATE_COLLECTION);
                //如果是分享的曲谱，就添加收藏记录
                addCollectionNum();
                mView.addCollectionResult();
            }
        });
    }

    //添加收藏量
    private void addCollectionNum() {
        if (isFrom == Constant.FROM_SHARE) {
            ShareSong shareSong = (ShareSong) intent.getSerializableExtra(IntentConstant.EXTRA_OPTIONAL_SONG_ACTIVITY_SHARE_SONG);
            shareSong.increment(BmobConstant.BMOB_COLLECTION_NUM);
            shareSong.update(new ToastUpdateListener() {
                @Override
                public void onSuccess() {
                    LogUtils.e("收藏量+1");
                }
            });
        }
    }

    //分享乐谱,本地和网络
    public void share() {
        List<String> list = null;
        SongObject songObject = (SongObject) intent.getSerializableExtra(IntentConstant.EXTRA_SONG_ACTIVITY_SONG_OBJECT);
        int loadingWay = songObject.getLoadingWay();
        switch (loadingWay) {
            case Constant.NET:
                list = song.getIvUrl();
                break;
            case Constant.LOCAL:
                list = getLocalImgs();
                break;
        }
        if (list == null || list.size() <= 0) {
            ToastUtil.showToast("没有图片");
            return;
        }

        StringBuilder sb = new StringBuilder();
        for (String url : list) {
            sb.append(url).append("和");
        }
        //分享
        IntentUtil.shareText(mContext, "推荐一首歌：" + "<<" + song.getName() + ">>:" + sb.toString());
    }

    //得到本地图片
    @NonNull
    private List<String> getLocalImgs() {
        LocalSong localsong = (LocalSong) intent.getSerializableExtra(IntentConstant.EXTRA_OPTIONAL_SONG_ACTIVITY_LOCAL_SONG);
        LocalSongDao localSongDao = new LocalSongDao(mContext);
        return localSongDao.getLocalImgsPath(mContext, localsong);
    }

    public void setIntent(Intent intent) {
        this.intent = intent;
    }

    public void init() {
        SongObject songObject = (SongObject) intent.getSerializableExtra(IntentConstant.EXTRA_SONG_ACTIVITY_SONG_OBJECT);
        isFrom = songObject.getFrom();
        song = songObject.getSong();

        //求谱，检测是否可以点赞,
        if (isFrom == Constant.FROM_ASK) {
            checkAskHasAgree();
        }

        if (isFrom == Constant.FROM_SHARE) {
            checkShareHasAgree();
        }

    }


    public int getShowMenuFlag() {
        return ((SongObject) intent.getSerializableExtra(IntentConstant.EXTRA_SONG_ACTIVITY_SONG_OBJECT)).getShowMenu();
    }


    interface IView extends IBaseView {

        void dismissEditDialog();

        void downloadResult(Integer success, String str);

        void addCollectionResult();
    }
}
