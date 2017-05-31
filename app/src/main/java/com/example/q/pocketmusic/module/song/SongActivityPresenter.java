package com.example.q.pocketmusic.module.song;

import android.Manifest;

import android.content.Intent;
import android.media.MediaPlayer;
import android.media.MediaRecorder;
import android.net.Uri;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.provider.Settings;
import android.support.v4.app.FragmentManager;
import android.view.Menu;

import com.example.q.pocketmusic.R;
import com.example.q.pocketmusic.config.Constant;
import com.example.q.pocketmusic.model.bean.Song;
import com.example.q.pocketmusic.model.bean.SongObject;
import com.example.q.pocketmusic.model.bean.local.RecordAudio;
import com.example.q.pocketmusic.model.db.RecordAudioDao;
import com.example.q.pocketmusic.module.common.BaseActivity;
import com.example.q.pocketmusic.module.common.BasePresenter;
import com.example.q.pocketmusic.module.common.IBasePresenter;
import com.example.q.pocketmusic.module.common.IBaseView;
import com.example.q.pocketmusic.module.song.bottom.SongMenuFragment;
import com.example.q.pocketmusic.module.song.bottom.SongRecordFragment;
import com.example.q.pocketmusic.module.song.state.SongController;
import com.example.q.pocketmusic.util.FileUtils;
import com.example.q.pocketmusic.util.MyToast;

import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import pub.devrel.easypermissions.EasyPermissions;

/**
 * Created by YQ on 2016/8/30.
 */
public class SongActivityPresenter extends BasePresenter<SongActivityPresenter.IView> implements IBasePresenter {
    private Intent intent;
    private IView activity;
    private SongController controller;//状态控制器,用于加载图片
    private FragmentManager fm;
    private SongMenuFragment songMenuFragment;
    private SongRecordFragment songRecordFragment;


    public void setFragmentManager(FragmentManager fragmentManager) {
        this.fm = fragmentManager;
    }


    public SongActivityPresenter(IView activity) {
        attachView(activity);
        this.activity = getIViewRef();
    }


    public void setIntent(Intent intent) {
        this.intent = intent;
        controller = SongController.getInstance(intent, activity);
        if (controller == null) {
            MyToast.showToast(activity.getCurrentContext(), "无法进入页面");
            activity.finish();
        }
    }

    public void init(FragmentManager fm) {
        this.fm = fm;
    }

    //加载图片
    public void loadPic() {
        controller.loadPic();
    }


    public void showBottomFragment(int isFrom) {
        if (isFrom == Constant.FROM_LOCAL) {
            songRecordFragment = SongRecordFragment.newInstance(intent);
            fm.beginTransaction().replace(R.id.bottom_content, songRecordFragment).commit();
        } else {
            songMenuFragment = SongMenuFragment.newInstance(intent);
            fm.beginTransaction().replace(R.id.bottom_content, songMenuFragment).commit();
        }
    }

    @Override
    public void release() {

    }


    public interface IView extends IBaseView {
        void loadFail();

        void setPicResult(List<String> ivUrl, int from);
    }
}
