package com.example.q.pocketmusic.module.home.convert.convert.piano;

import android.os.Handler;
import android.os.Message;

import com.example.q.pocketmusic.module.common.BasePresenter;
import com.example.q.pocketmusic.module.common.IBaseView;
import com.example.q.pocketmusic.util.MusicUtils;
import com.example.q.pocketmusic.util.common.ToastUtil;

public class PianoFragmentPresenter extends BasePresenter<PianoFragmentPresenter.IView> {
    private IView fragment;
    private MusicUtils mMusic;
    private StringBuilder builder = new StringBuilder();
    private static final int WHAT_BACK = 1002;
    private static final int DELAY_TIME = 250;
    private Boolean isCloseQuickBack;
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (isCloseQuickBack) {
                return;
            }
            fragment.back();
            handler.sendEmptyMessageDelayed(WHAT_BACK, DELAY_TIME);
        }
    };

    public PianoFragmentPresenter(final IView fragment) {
        attachView(fragment);
        this.fragment = getIViewRef();
        mMusic = new MusicUtils(fragment.getCurrentContext());
    }

    //后退
    public String setBack() {
        if (builder.length() <= 0) {
            return "";
        }
        return builder.deleteCharAt(builder.length() - 1).toString();
    }

    public String setTab() {
        return builder.append("  ").toString();
    }

    public String setEnter() {
        return builder.append("\n").toString();
    }

    public String setBoLang() {
        return builder.append("~").toString();
    }

    public void openQuickBack() {
        isCloseQuickBack = false;
        handler.sendEmptyMessageDelayed(WHAT_BACK, DELAY_TIME);
    }

    public void closeQuickBack() {
        isCloseQuickBack = true;
    }

    public void keepPic(String title) {
        ToastUtil.showToast("保存(未实现)");
    }

    public void setClickScale(int id) {//播放
        mMusic.soundPlay(id);
        builder.append(mMusic.getNote(id));
    }

    public interface IView extends IBaseView {

        void back();
    }
}
