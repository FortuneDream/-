package com.example.q.pocketmusic.module.song;

import android.content.Intent;

import androidx.fragment.app.FragmentManager;

import com.dell.fortune.tools.toast.ToastUtil;
import com.example.q.pocketmusic.R;
import com.example.q.pocketmusic.callback.ToastUpdateListener;
import com.example.q.pocketmusic.config.constant.CoinConstant;
import com.example.q.pocketmusic.config.constant.Constant;
import com.example.q.pocketmusic.module.common.BaseActivity;
import com.example.q.pocketmusic.module.common.BasePresenter;
import com.example.q.pocketmusic.module.common.IBasePresenter;
import com.example.q.pocketmusic.module.common.IBaseView;
import com.example.q.pocketmusic.module.song.bottom.SongMenuFragment;
import com.example.q.pocketmusic.module.song.bottom.SongRecordFragment;
import com.example.q.pocketmusic.module.song.state.SongController;
import com.example.q.pocketmusic.util.UserUtil;

import java.util.List;

/**
 * Created by 鹏君 on 2016/8/30.
 */
public class SongActivityPresenter extends BasePresenter<SongActivityPresenter.IView> implements IBasePresenter {
    private Intent intent;
    private SongController controller;//状态控制器,用于加载图片
    private FragmentManager fm;
    private SongMenuFragment songMenuFragment;
    private SongRecordFragment songRecordFragment;
    private int isFrom;


    public void setFragmentManager(FragmentManager fragmentManager) {
        this.fm = fragmentManager;
    }


    public SongActivityPresenter(IView activity) {
        super(activity);
    }

    public void setIsFrom(int isFrom) {
        this.isFrom = isFrom;
    }

    public int getIsFrom() {
        return isFrom;
    }

    public void setIntent(Intent intent) {
        this.intent = intent;
        controller = SongController.getInstance(intent, mView);
        if (controller == null) {
            ToastUtil.showToast("无法进入页面");
            mView.finish();
        }
    }

    public void init(FragmentManager fm) {
        this.fm = fm;
    }

    //加载图片
    public void loadPic() {
        controller.loadPic();
    }


    public void showBottomFragment() {
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

    public void punish() {
        if (UserUtil.checkLocalUser((BaseActivity) mContext) && UserUtil.user.getContribution() >= CoinConstant.REDUCE_COIN_PUNISH) {
            UserUtil.increment(-CoinConstant.REDUCE_COIN_PUNISH, new ToastUpdateListener() {
                @Override
                public void onSuccess() {
                    ToastUtil.showToast(mView.getResString(R.string.reduce_coin) + CoinConstant.REDUCE_COIN_PUNISH);
                }
            });
        }
    }


    public interface IView extends IBaseView {
        void loadFail();

        void setPicResult(List<String> ivUrl, int from);
    }
}
