package com.example.q.pocketmusic.module.song;

import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.design.widget.AppBarLayout;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.example.q.pocketmusic.R;
import com.example.q.pocketmusic.config.CommonString;
import com.example.q.pocketmusic.config.Constant;
import com.example.q.pocketmusic.config.ScreenshotContentObserver;
import com.example.q.pocketmusic.data.bean.Song;
import com.example.q.pocketmusic.data.bean.SongObject;
import com.example.q.pocketmusic.data.bean.ask.AskSongComment;
import com.example.q.pocketmusic.data.bean.ask.AskSongPost;
import com.example.q.pocketmusic.data.bean.local.LocalSong;
import com.example.q.pocketmusic.data.bean.share.ShareSong;
import com.example.q.pocketmusic.module.common.BaseActivity;
import com.example.q.pocketmusic.util.common.ToastUtil;
import com.example.q.pocketmusic.view.widget.net.HackyViewPager;
import com.example.q.pocketmusic.view.widget.net.SnackBarUtil;

import java.io.Serializable;
import java.util.List;

import butterknife.BindView;
import pub.devrel.easypermissions.EasyPermissions;

//查看大图界面
public class SongActivity extends BaseActivity<SongActivityPresenter.IView, SongActivityPresenter>
        implements SongActivityPresenter.IView, EasyPermissions.PermissionCallbacks, Serializable {
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.app_bar)
    AppBarLayout appBar;
    @BindView(R.id.view_pager)
    HackyViewPager viewPager;
    @BindView(R.id.bottom_content)
    FrameLayout bottomContent;
    @BindView(R.id.page_tv)
    TextView pageTv;
    private SongActivityAdapter adapter;


    public final static String PARAM_SONG_OBJECT_SERIALIZABLE = "PARAM_SONG_OBJECT_SERIALIZABLE";//Serializeable

    public final static String LOCAL_SONG = "PARAM_LOCAL_SONG";//可选的传递参数，用于传递本地的localSong

    public final static String ASK_COMMENT = "ASK_COMMENT";//可选的传递参数，用于传递某条有图的Comment

    public final static String SHARE_SONG = "SHARE_SONG";//可选传递参数，用于传递shareSong


    private Handler handler = new Handler(Looper.myLooper()) {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            showSnack();
            presenter.punish();

        }
    };

    //显示SnackBar
    private void showSnack() {
        SnackBarUtil.IndefiniteSnackbar(getWindow().getDecorView(), "在干啥坏事呢！  ( ´◔ ‸◔`)", 4000, R.color.black, SnackBarUtil.green).setAction("...", new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        }).setActionTextColor(ContextCompat.getColor(getCurrentContext(), R.color.white)).show();
    }

    //本地
    public static Intent buildLocalIntent(Context context, SongObject songObject, LocalSong localSong){
        Intent intent = new Intent(context, SongActivity.class);
        intent.putExtra(SongActivity.PARAM_SONG_OBJECT_SERIALIZABLE, songObject);
        intent.putExtra(SongActivity.LOCAL_SONG, localSong);
        return intent;
    }

    //推荐
    public static Intent buildRecommendIntent(Context context, SongObject songObject){
        Intent intent = new Intent(context, SongActivity.class);
        intent.putExtra(SongActivity.PARAM_SONG_OBJECT_SERIALIZABLE, songObject);
        return intent;
    }

    //求谱
    public static Intent buildAskIntent(Context context, SongObject songObject, int community, AskSongComment askSongComment){
        Intent intent = new Intent(context, SongActivity.class);
        songObject.setCommunity(community);
        intent.putExtra(SongActivity.PARAM_SONG_OBJECT_SERIALIZABLE, songObject);
        intent.putExtra(SongActivity.ASK_COMMENT, askSongComment);
        return intent;
    }

    //分享
    public static Intent buildShareIntent(Context context, SongObject songObject, int typeId, ShareSong shareSong){
        Intent intent = new Intent(context, SongActivity.class);
        songObject.setCommunity(typeId);
        intent.putExtra(SongActivity.PARAM_SONG_OBJECT_SERIALIZABLE, songObject);
        intent.putExtra(SongActivity.SHARE_SONG, shareSong);
        return intent;
    }

    //乐器
    public static Intent buildTypeIntent(Context context, SongObject songObject,int community){
        Intent intent = new Intent(context, SongActivity.class);
        songObject.setCommunity(community);
        intent.putExtra(SongActivity.PARAM_SONG_OBJECT_SERIALIZABLE, songObject);
        return intent;
    }



    @Override
    public int setContentResource() {
        return R.layout.activity_song;
    }

    @Override
    public void initView() {
        //屏幕不灭
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON, WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        presenter.setIntent(getIntent());//设置intent
        presenter.init(getSupportFragmentManager());//初始化
        SongObject songObject = (SongObject) getIntent().getSerializableExtra(SongActivity.PARAM_SONG_OBJECT_SERIALIZABLE);
        Song song = songObject.getSong();
        initToolbar(toolbar, song.getName());//toolbar
        presenter.loadPic();  //查找图片
        presenter.showBottomFragment(songObject.getFrom());//显示底部Fragment
    }

    //加载失败
    @Override
    public void loadFail() {
        ToastUtil.showToast(getResString(R.string.not_found_net_data));
        finish();
    }

    @Override
    protected SongActivityPresenter createPresenter() {
        return new SongActivityPresenter(this);
    }


    @Override
    public void setPicResult(List<String> ivUrl, int from) {
        if (!isViewValid()){
            return;
        }
        //本地和网络加载图片的地址有所不同
        adapter = new SongActivityAdapter(this, ivUrl, from);
        final int page = ivUrl.size();
        pageTv.setText(1 + "/" + page);//初始化
        viewPager.setAdapter(adapter);
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                pageTv.setText(position + 1 + "/" + page);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }


    //权限相关
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        EasyPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults, this);
    }

    @Override
    public void onPermissionsGranted(int requestCode, List<String> perms) {
        ToastUtil.showToast("成功获得权限");
    }

    @Override
    public void onPermissionsDenied(int requestCode, List<String> perms) {
        ToastUtil.showToast("录音权限被拒绝,如需录音请到设置中心--权限管理中修改");
        //presenter.enterSystemSetting();
    }

    //截屏监听
    @Override
    public void onStart() {
        super.onStart();
        ScreenshotContentObserver.startObserve(handler, getAppContext());
    }

    @Override
    public void onStop() {
        super.onStop();
        ScreenshotContentObserver.stopObserve();
    }
}
