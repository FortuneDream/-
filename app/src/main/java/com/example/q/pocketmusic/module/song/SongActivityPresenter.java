package com.example.q.pocketmusic.module.song;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.database.SQLException;
import android.media.MediaPlayer;
import android.media.MediaRecorder;
import android.net.Uri;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.view.Menu;

import com.example.q.pocketmusic.R;
import com.example.q.pocketmusic.module.common.IBaseList;
import com.example.q.pocketmusic.module.common.IBasePresenter;
import com.example.q.pocketmusic.callback.ToastQueryListListener;
import com.example.q.pocketmusic.callback.ToastQueryListener;
import com.example.q.pocketmusic.callback.ToastSaveListener;
import com.example.q.pocketmusic.callback.ToastUpdateListener;
import com.example.q.pocketmusic.config.CommonString;
import com.example.q.pocketmusic.config.Constant;
import com.example.q.pocketmusic.model.bean.DownloadInfo;
import com.example.q.pocketmusic.model.bean.MyUser;
import com.example.q.pocketmusic.model.bean.Song;
import com.example.q.pocketmusic.model.bean.SongObject;
import com.example.q.pocketmusic.model.bean.ask.AskSongComment;
import com.example.q.pocketmusic.model.bean.collection.CollectionPic;
import com.example.q.pocketmusic.model.bean.collection.CollectionSong;
import com.example.q.pocketmusic.model.bean.local.Img;
import com.example.q.pocketmusic.model.bean.local.LocalSong;
import com.example.q.pocketmusic.model.bean.local.RecordAudio;
import com.example.q.pocketmusic.model.db.LocalSongDao;
import com.example.q.pocketmusic.model.db.RecordAudioDao;
import com.example.q.pocketmusic.module.common.BaseActivity;
import com.example.q.pocketmusic.module.common.BasePresenter;
import com.example.q.pocketmusic.module.song.state.SongController;
import com.example.q.pocketmusic.util.CheckUserUtil;
import com.example.q.pocketmusic.util.DownloadUtil;
import com.example.q.pocketmusic.util.FileUtils;
import com.example.q.pocketmusic.util.MyToast;
import com.j256.ormlite.dao.CloseableIterator;
import com.j256.ormlite.dao.ForeignCollection;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import cn.bmob.v3.BmobBatch;
import cn.bmob.v3.BmobObject;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.datatype.BatchResult;
import cn.bmob.v3.datatype.BmobPointer;
import cn.bmob.v3.datatype.BmobRelation;
import pub.devrel.easypermissions.EasyPermissions;

/**
 * Created by YQ on 2016/8/30.
 */
public class SongActivityPresenter extends BasePresenter implements IBasePresenter {
    private Context context;
    private IView activity;
    private Intent intent;
    private SongController controller;//状态控制器,用于加载图片
    private int isFrom;//资源来自
    private Song song;
    private int showMenuFlag;
    private int loadingWay;//加载方式
    private boolean isEnableAgree = true;//是否能够点赞

    //显示文字状态
    private RECORD_STATUS status = RECORD_STATUS.STOP;

    public enum RECORD_STATUS {
        PLAY, STOP
    }

    private RecordAudioDao recordAudioDao;
    //录音文件夹
    private final static String RECORD_DIR = Environment.getExternalStorageDirectory().getAbsolutePath() + "/" + Constant.RECORD_FILE + "/";
    //暂存文件夹
    private final static String TEMP_DIR = Environment.getExternalStorageDirectory().getAbsolutePath() + "/";
    //暂存文件名
    private final static String TEMP_NAME = "temp";
    //播放器和录音器
    private MediaRecorder mRecorder = new MediaRecorder();
    private MediaPlayer mPlayer = new MediaPlayer();
    //录音时间标志
    private static final int ADD_TIME = 0;
    //录音时间
    private int mRecordTime;
    //定时任务
    private Timer mRecordTimer;
    private final int REQUEST_RECORD_AUDIO = 1001;
    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case ADD_TIME:
                    mRecordTime++;
                    activity.changedTimeTv(String.valueOf(mRecordTime));
                    break;
            }
        }
    };


    public int getLoadingWay() {
        return loadingWay;
    }

    public Song getSong() {
        return song;
    }

    public boolean isEnableAgree() {
        return isEnableAgree;
    }

    public SongActivityPresenter(Context context, IView activity, Intent intent) {
        this.context = context;
        this.activity = activity;
        this.intent = intent;
        SongObject songObject = intent.getParcelableExtra(SongActivity.PARAM_SONG_OBJECT_PARCEL);
        song = songObject.getSong();
        this.isFrom = songObject.getFrom();
        this.showMenuFlag = songObject.getShowMenu();
        this.loadingWay = songObject.getLoadingWay();

        controller = SongController.getInstance(intent, context, activity);

        if (controller == null) {
            MyToast.showToast(context, "无法进入页面");
            activity.finish();
        }

        //求谱，检测是否可以点赞,
        if (isFrom == Constant.FROM_ASK) {
            checkHasAgree();
        }

        //本地可以录音
        if (loadingWay == Constant.LOCAL) {
            recordAudioDao = new RecordAudioDao(context);
            File file = new File(RECORD_DIR);
            if (!file.exists()) {
                file.mkdirs();
            }
        }
    }

    //加载图片
    public void loadPic() {
        controller.loadPic();
    }


    //根据不同的from加载不同的menu;
    public void CreateMenuByFrom(Menu menu) {
        switch (showMenuFlag) {
            case Constant.SHOW_ALL_MENU:
                ((BaseActivity) context).getMenuInflater().inflate(R.menu.menu_song_all, menu);//下载，收藏，点赞
                break;
            case Constant.SHOW_COLLECTION_MENU:
                ((BaseActivity) context).getMenuInflater().inflate(R.menu.menu_song_collection, menu);//下载和收藏
                break;
            case Constant.SHOW_ONLY_DOWNLOAD:
                ((BaseActivity) context).getMenuInflater().inflate(R.menu.menu_song_download, menu);//下载
                break;
            case Constant.SHOW_NO_MENU://不显示Menu
                break;
        }
    }

    //下载
    public void download(final String name) {
        activity.showLoading(true);
        DownloadUtil downloadUtil = new DownloadUtil(context);
        downloadUtil.setOnDownloadListener(new DownloadUtil.OnDownloadListener() {
                                               @Override
                                               public DownloadInfo onStart() {
                                                   activity.dismissEditDialog();
                                                   return downloadStartCheck();
                                               }

                                               @Override
                                               public void onSuccess() {
                                                   activity.showLoading(false);
                                                   activity.downloadResult(Constant.SUCCESS, "下载成功");
                                               }

                                               @Override
                                               public void onFailed(String info) {
                                                   activity.showLoading(false);
                                                   activity.downloadResult(Constant.FAIL, info);
                                               }
                                           }
        ).downloadBatchPic(name, song.getIvUrl(), song.getTypeId());
    }

    //下载检测
    private DownloadInfo downloadStartCheck() {
        //如果无图
        if (song.getIvUrl() == null || song.getIvUrl().size() <= 0) {
            activity.showLoading(false);
            return new DownloadInfo("没有图片", false);
        }
        //如果本地已经存在
        if (new LocalSongDao(context).isExist(song.getName())) {
            activity.showLoading(false);
            return new DownloadInfo("本地已存在", false);
        }

        //需要硬币
        if (song.isNeedGrade()) {
            MyUser user = CheckUserUtil.checkLocalUser((BaseActivity) context);
            //找不到用户
            if (user == null) {
                activity.showLoading(false);
                return new DownloadInfo("找不到用户", false);
            }
            //硬币不足
            if (!CheckUserUtil.checkUserContribution(((BaseActivity) context), Constant.REDUCE_COIN_UPLOAD)) {
                activity.showLoading(false);
                return new DownloadInfo(CommonString.STR_NOT_ENOUGH_COIN, false);
            }
            //扣除硬币
            user.increment("contribution", -Constant.REDUCE_COIN_UPLOAD);
            user.update(new ToastUpdateListener(context, activity) {
                @Override
                public void onSuccess() {
                    MyToast.showToast(context, CommonString.REDUCE_COIN_BASE + (Constant.REDUCE_COIN_UPLOAD));
                }
            });
        }
        return new DownloadInfo("", true);
    }


    //点赞
    public void agree() {
        if (isEnableAgree()) {
            BmobRelation relation = new BmobRelation();
            final MyUser user = MyUser.getCurrentUser(MyUser.class);
            relation.add(user);
            AskSongComment askSongComment = (AskSongComment) intent.getSerializableExtra(SongActivity.ASK_COMMENT);
            askSongComment.setAgrees(relation);
            askSongComment.increment("agreeNum");//原子操作，点赞数加一
            askSongComment.update(new ToastUpdateListener(context, activity) {
                @Override
                public void onSuccess() {
                    MyToast.showToast(context, "已点赞");
                    user.increment("contribution", Constant.ADD_CONTRIBUTION_AGREE);
                    user.update(new ToastUpdateListener(context, activity) {
                        @Override
                        public void onSuccess() {
                            MyToast.showToast(context, CommonString.ADD_COIN_BASE + Constant.ADD_CONTRIBUTION_AGREE);
                        }
                    });
                }
            });
        } else {
            MyToast.showToast(context, "已经赞过了哦~");
        }


    }


    //判断当前的评论的图片是否可以点赞
    public void checkHasAgree() {
        BmobQuery<MyUser> query = new BmobQuery<>();
        final MyUser user = MyUser.getCurrentUser(MyUser.class);
        AskSongComment askSongComment = (AskSongComment) intent.getSerializableExtra(SongActivity.ASK_COMMENT);
        query.addWhereRelatedTo("agrees", new BmobPointer(askSongComment));
        query.findObjects(new ToastQueryListener<MyUser>(context, activity) {
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

    //添加收藏
    public void addCollection() {
        activity.showLoading(true);
        final MyUser user = CheckUserUtil.checkLocalUser((BaseActivity) context);
        if (user == null) {
            activity.showLoading(false);
            MyToast.showToast(context, "请先登录~");
            return;
        }
        if (song.getIvUrl() == null || song.getIvUrl().size() <= 0) {
            activity.showLoading(false);
            MyToast.showToast(context, "图片为空");
            return;
        }
        //检测是否已经收藏
        BmobQuery<CollectionSong> query = new BmobQuery<>();
        query.order("-updatedAt");
        query.addWhereRelatedTo("collections", new BmobPointer(user));//在user表的Collections找user
        query.findObjects(new ToastQueryListener<CollectionSong>(context, activity) {
            @Override
            public void onSuccess(List<CollectionSong> list) {
                //是否已收藏
                for (CollectionSong collectionSong : list) {
                    if (collectionSong.getName().equals(song.getName())) {
                        activity.showLoading(false);
                        MyToast.showToast(context, "已收藏");
                        return;
                    }
                }
                //贡献度是否足够
                if (!CheckUserUtil.checkUserContribution(((BaseActivity) context), Constant.REDUCE_CONTRIBUTION_COLLECTION)) {
                    activity.showLoading(false);
                    MyToast.showToast(context, "贡献值不够~");
                    return;
                }


                //添加收藏记录
                final CollectionSong collectionSong = new CollectionSong();
                collectionSong.setName(song.getName());
                collectionSong.setNeedGrade(song.isNeedGrade());//是否需要积分
                collectionSong.setIsFrom(isFrom);
                collectionSong.setContent(song.getContent());
                collectionSong.save(new ToastSaveListener<String>(context, activity) {

                    @Override
                    public void onSuccess(String s) {
                        final int numPic = song.getIvUrl().size();
                        List<BmobObject> collectionPics = new ArrayList<BmobObject>();
                        for (int i = 0; i < numPic; i++) {
                            CollectionPic collectionPic = new CollectionPic();
                            collectionPic.setCollectionSong(collectionSong);
                            collectionPic.setUrl(song.getIvUrl().get(i));
                            collectionPics.add(collectionPic);
                        }
                        //批量修改
                        new BmobBatch().insertBatch(collectionPics).doBatch(new ToastQueryListListener<BatchResult>(context, activity) {
                            @Override
                            public void onSuccess(List<BatchResult> list) {
                                BmobRelation relation = new BmobRelation();
                                relation.add(collectionSong);
                                user.setCollections(relation);//添加用户收藏
                                user.update(new ToastUpdateListener(context, activity) {
                                    @Override
                                    public void onSuccess() {
                                        MyToast.showToast(context, "已收藏");
                                        user.increment("contribution", -Constant.REDUCE_CONTRIBUTION_COLLECTION);//贡献值-1
                                        user.update(new ToastUpdateListener(context, activity) {
                                            @Override
                                            public void onSuccess() {
                                                activity.showLoading(false);
                                                MyToast.showToast(context, CommonString.REDUCE_COIN_BASE + Constant.REDUCE_CONTRIBUTION_COLLECTION);
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

    //分享乐谱,本地和网络
    public void share() {
        List<String> list = null;
        switch (loadingWay) {
            case Constant.NET:
                list = song.getIvUrl();
                break;
            case Constant.LOCAL:
                list = getLocalImgs();
                break;
        }
        if (list == null || list.size() <= 0) {
            MyToast.showToast(context, "没有图片");
            return;
        }

        StringBuilder sb = new StringBuilder();
        for (String url : list) {
            sb.append(url).append(",");
        }

        Intent intent = new Intent("android.intent.action.SEND");
        intent.addCategory("android.intent.category.DEFAULT");
        intent.setType("text/plain");
        intent.putExtra(Intent.EXTRA_TEXT, "推荐一首歌：" + "<<" + song.getName() + ">>:" + sb.toString());
        if (intent.resolveActivity(context.getPackageManager()) != null) {
            context.startActivity(intent);
        } else {
            MyToast.showToast(context, "你的手机不支持分享~");
        }
    }

    //得到本地图片
    @NonNull
    private ArrayList<String> getLocalImgs() {
        LocalSong localsong = (LocalSong) intent.getSerializableExtra(SongActivity.LOCAL_SONG);
        LocalSongDao localSongDao = new LocalSongDao(context);
        ArrayList<String> imgUrls = new ArrayList<>();
        LocalSong localSong = localSongDao.findBySongId(localsong.getId());
        if (localSong == null) {
            MyToast.showToast(context, "曲谱消失在了异次元。");
            activity.finish();
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

    //录音
    public void record() {
        //请求权限
        String[] perms = {Manifest.permission.RECORD_AUDIO};
        if (!EasyPermissions.hasPermissions(context, perms)) {
            EasyPermissions.requestPermissions((BaseActivity) context, "录音权限", REQUEST_RECORD_AUDIO, perms);
            return;
        }

        activity.setBtnStatus(status);
        //开始录音
        if (status == SongActivityPresenter.RECORD_STATUS.STOP) {
            //设置按钮文字
            status = SongActivityPresenter.RECORD_STATUS.PLAY;
            //初始化
            mRecorder.setAudioSource(MediaRecorder.AudioSource.MIC);
            mRecorder.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP);
            //暂存文件夹和文件名,存在就先删除源文件
            File file = new File(TEMP_DIR + TEMP_NAME + ".3gp");
            if (file.exists()) {
                file.delete();
            }
            mRecorder.setOutputFile(TEMP_DIR + TEMP_NAME + ".3gp");
            mRecorder.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB);
            mRecordTime = 0;
            try {
                mRecorder.prepare();
                mRecorder.start();
                TimerTask task = new TimerTask() {
                    @Override
                    public void run() {
                        mHandler.sendEmptyMessage(ADD_TIME);
                    }
                };
                mRecordTimer = new Timer();
                mRecordTimer.schedule(task, 1000, 1000);

            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            //停止录音
            status = SongActivityPresenter.RECORD_STATUS.STOP;
            mRecorder.stop();
            mHandler.removeMessages(ADD_TIME);
            mRecordTimer.cancel();
            mRecordTime = 0;
            activity.changedTimeTv(String.valueOf(mRecordTime));
            //加入弹出dialog
            activity.showAddDialog(song.getName());
        }
    }

    //进入系统设置中心
    public void enterSystemSetting() {
        Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
        Uri uri = Uri.fromParts("package", context.getPackageName(), null);
        intent.setData(uri);
        context.startActivity(intent);
    }

    //s:歌曲名=editText的输入
    public void saveRecordAudio(final String s) {
        try {
            //得到时长
            mPlayer.reset();
            mPlayer.setDataSource(TEMP_DIR + TEMP_NAME + ".3gp");//设置为暂时的路径
            mPlayer.prepareAsync();
            mPlayer.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                @Override
                public void onPrepared(MediaPlayer mp) {
                    int duration = mp.getDuration();
                    //保存到数据库
                    RecordAudio recordAudio = new RecordAudio();
                    recordAudio.setName(s);
                    recordAudio.setDuration(duration);//存入的是毫秒
                    recordAudio.setDate(dateFormat.format(new Date()));//以存入的时间不同来区别不同
                    recordAudio.setPath(RECORD_DIR + s + ".3gp");
                    boolean isSucceed = recordAudioDao.add(recordAudio);
                    activity.setAddResult(isSucceed);//返回结果
                    //只有当没有重名的时候才移动文件
                    //将tempRecord移动到指定文件夹
                    if (isSucceed) {
                        FileUtils.copyFile(TEMP_DIR + TEMP_NAME + ".3gp", RECORD_DIR + s + ".3gp");
                    }
                }
            });
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void onStop() {
        //暂停线程,取消Timer
        mHandler.removeMessages(ADD_TIME);
        if (mRecordTimer != null) {
            mRecordTimer.cancel();
        }
        //重置mRecorder
        if (mRecorder != null) {
            mRecorder.reset();
        }
        //重置mPlayer
        if (mPlayer != null) {
            mPlayer.reset();
        }

        //初始化
        status = RECORD_STATUS.STOP;
        activity.setBtnStatus(RECORD_STATUS.PLAY);
        activity.changedTimeTv(String.valueOf(0));
    }

    //释放资源
    @Override
    public void release() {
        onStop();
        mPlayer.release();
        mRecorder.release();
    }


    public interface IView extends IBaseList {
        void loadFail();

        void downloadResult(Integer result, String info);

        void dismissEditDialog();

        void setPicResult(List<String> ivUrl, int from);

        void setBtnStatus(SongActivityPresenter.RECORD_STATUS status);

        void changedTimeTv(String s);

        void showAddDialog(String s);

        void setAddResult(boolean isSucceed);

        void finish();
    }
}
