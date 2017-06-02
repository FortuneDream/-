package com.example.q.pocketmusic.module.home.seek.publish;

import android.text.TextUtils;

import com.example.q.pocketmusic.callback.ToastSaveListener;
import com.example.q.pocketmusic.callback.ToastUpdateListener;
import com.example.q.pocketmusic.config.CommonString;
import com.example.q.pocketmusic.config.Constant;
import com.example.q.pocketmusic.model.bean.MyUser;
import com.example.q.pocketmusic.model.bean.ask.AskSongPost;
import com.example.q.pocketmusic.module.common.BaseActivity;
import com.example.q.pocketmusic.module.common.BasePresenter;
import com.example.q.pocketmusic.module.common.IBaseView;
import com.example.q.pocketmusic.util.CheckUserUtil;
import com.example.q.pocketmusic.util.MyToast;

import java.util.Iterator;
import java.util.Set;

/**
 * Created by 鹏君 on 2016/11/14.
 */

public class AskSongPresenter extends BasePresenter<AskSongPresenter.IView> {
    private final int NOT_SELECT = -1;
    private IView activity;
    private int type;

    public AskSongPresenter(IView activity) {
        attachView(activity);
        this.activity = getIViewRef();
        type = NOT_SELECT;
    }

    public void askForSong(String title, final String content, final MyUser user) {
        if (TextUtils.isEmpty(content) || TextUtils.isEmpty(title) || type == NOT_SELECT) {
            MyToast.showToast(activity.getCurrentContext(), CommonString.STR_COMPLETE_INFO);
            return;
        }
        if (!CheckUserUtil.checkUserContribution((BaseActivity) activity.getCurrentContext(), Constant.REDUCE_CONTRIBUTION_ASK)) {
            MyToast.showToast(activity.getCurrentContext(), CommonString.STR_NOT_ENOUGH_COIN);
            return;
        }
        activity.showLoading(true);
        AskSongPost askSongPost = new AskSongPost(user, title, type, content);
        askSongPost.save(new ToastSaveListener<String>(activity) {
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

    public void setSelectedTag(Set<Integer> selectPosSet) {
        Iterator<Integer> iterator = selectPosSet.iterator();
        if (iterator.hasNext()) {
            type = iterator.next();
        } else {
            type = NOT_SELECT;//没有选
        }
    }

    public interface IView extends IBaseView {
        void finish();

        void setAskResult(Integer success);
    }
}
