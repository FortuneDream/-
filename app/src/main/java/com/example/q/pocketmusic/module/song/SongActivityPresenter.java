package com.example.q.pocketmusic.module.song;

import android.content.Intent;
import android.support.v4.app.FragmentManager;

import com.example.q.pocketmusic.R;
import com.example.q.pocketmusic.config.Constant;
import com.example.q.pocketmusic.module.common.BasePresenter;
import com.example.q.pocketmusic.module.common.IBasePresenter;
import com.example.q.pocketmusic.module.common.IBaseView;
import com.example.q.pocketmusic.module.song.bottom.SongMenuFragment;
import com.example.q.pocketmusic.module.song.bottom.SongRecordFragment;
import com.example.q.pocketmusic.module.song.state.SongController;
import com.example.q.pocketmusic.util.ToastUtil;

import java.util.List;

/**
 * Created by 鹏君 on 2016/8/30.
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
            ToastUtil.showToast( "无法进入页面");
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
