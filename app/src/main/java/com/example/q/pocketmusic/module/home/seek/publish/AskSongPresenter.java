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
import com.example.q.pocketmusic.util.common.ToastUtil;

import java.util.Iterator;
import java.util.Set;

/**
 * Created by 鹏君 on 2016/11/14.
 */

public class AskSongPresenter extends BasePresenter<AskSongPresenter.IView> {
    private final int NOT_SELECT = -1;
    private IView activity;
    private int type;
    private int index;

    public AskSongPresenter(IView activity) {
        attachView(activity);
        this.activity = getIViewRef();
        type = NOT_SELECT;
    }

    //指数*2+基础求谱硬币
    public void checkAsk(String title, final String content, final MyUser user) {
        if (TextUtils.isEmpty(content) || TextUtils.isEmpty(title) || type == NOT_SELECT) {
            ToastUtil.showToast(CommonString.STR_COMPLETE_INFO);
            return;
        }
        int coin = Constant.REDUCE_CONTRIBUTION_ASK + index * 2;
        if (!CheckUserUtil.checkUserContribution((BaseActivity) activity.getCurrentContext(), coin)) {
            ToastUtil.showToast(CommonString.STR_NOT_ENOUGH_COIN);
            return;
        }
        activity.alertCoinDialog(coin, title, content, user);
    }

    public void setSelectedTag(Set<Integer> selectPosSet) {
        Iterator<Integer> iterator = selectPosSet.iterator();
        if (iterator.hasNext()) {
            type = iterator.next();
        } else {
            type = NOT_SELECT;//没有选
        }
    }

    public void askForSong(String title, String content, final MyUser user) {
        activity.showLoading(true);
        final int coin = Constant.REDUCE_CONTRIBUTION_ASK + index * 2;
        AskSongPost askSongPost = new AskSongPost(user, title, type, content);
        askSongPost.setIndex(index);
        askSongPost.save(new ToastSaveListener<String>(activity) {
            @Override
            public void onSuccess(String s) {
                user.increment("contribution", -coin);
                user.update(new ToastUpdateListener(activity) {
                    @Override
                    public void onSuccess() {
                        ToastUtil.showToast(CommonString.REDUCE_COIN_BASE + coin);
                        activity.showLoading(false);
                        activity.setAskResult(Constant.SUCCESS);
                        activity.finish();
                    }
                });
            }
        });
    }

    public void setIndex(int i) {
        index = 0;
        activity.changeIndex(index);
    }

    public void addIndex() {
        index++;
        activity.changeIndex(index);
    }

    public void reduceIndex() {
        index--;
        activity.changeIndex(index);
    }

    public interface IView extends IBaseView {
        void finish();

        void setAskResult(Integer success);

        void alertCoinDialog(int coin, String title, String content, MyUser user);

        void changeIndex(int index);
    }
}
