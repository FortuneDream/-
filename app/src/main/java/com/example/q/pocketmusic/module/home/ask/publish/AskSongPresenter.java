package com.example.q.pocketmusic.module.home.ask.publish;

import android.content.Context;
import android.text.TextUtils;

import com.example.q.pocketmusic.module.common.IBaseList;
import com.example.q.pocketmusic.callback.ToastUpdateListener;
import com.example.q.pocketmusic.config.CommonString;
import com.example.q.pocketmusic.config.Constant;
import com.example.q.pocketmusic.model.bean.ask.AskSongPost;
import com.example.q.pocketmusic.callback.ToastSaveListener;
import com.example.q.pocketmusic.module.common.BaseActivity;
import com.example.q.pocketmusic.module.common.BasePresenter;
import com.example.q.pocketmusic.util.CheckUserUtil;
import com.example.q.pocketmusic.util.MyToast;

/**
 * Created by Cloud on 2016/11/14.
 */

public class AskSongPresenter extends BasePresenter {
    private IView activity;

    public AskSongPresenter(IView activity) {
        this.activity = activity;
    }

    public void askForSong(String title, final String content, final com.example.q.pocketmusic.model.bean.MyUser user) {
        if (TextUtils.isEmpty(content) || TextUtils.isEmpty(title)) {
            MyToast.showToast(activity.getCurrentContext(), CommonString.STR_COMPLETE_INFO);
            return;
        }
        if (!CheckUserUtil.checkUserContribution((BaseActivity) activity.getCurrentContext(), Constant.REDUCE_CONTRIBUTION_ASK)) {
            MyToast.showToast(activity.getCurrentContext(), CommonString.STR_NOT_ENOUGH_COIN);
            return;
        }
        activity.showLoading(true);
        AskSongPost askSongPost = new AskSongPost(user, title, content);
        askSongPost.save(new ToastSaveListener<String>( activity) {
            @Override
            public void onSuccess(String s) {
                user.increment("contribution", -Constant.REDUCE_CONTRIBUTION_ASK);
                user.update(new ToastUpdateListener(activity) {
                    @Override
                    public void onSuccess() {
                        MyToast.showToast(activity.getCurrentContext(), CommonString.REDUCE_COIN_BASE + Constant.REDUCE_CONTRIBUTION_ASK);
                        activity.showLoading(false);
                        activity.setAskResult(Constant.SUCCESS);
                        activity.finish();
                    }
                });
            }
        });


    }

    public interface IView extends IBaseList {
        void finish();

        void setAskResult(Integer success);
    }
}
