package com.example.q.pocketmusic.module.home.net.type.community.ask.publish;

import android.text.TextUtils;

import com.example.q.pocketmusic.callback.ToastSaveListener;
import com.example.q.pocketmusic.callback.ToastUpdateListener;
import com.example.q.pocketmusic.config.BmobConstant;
import com.example.q.pocketmusic.config.CommonString;
import com.example.q.pocketmusic.config.Constant;
import com.example.q.pocketmusic.model.bean.MyUser;
import com.example.q.pocketmusic.model.bean.ask.AskSongPost;
import com.example.q.pocketmusic.module.common.BaseActivity;
import com.example.q.pocketmusic.module.common.BasePresenter;
import com.example.q.pocketmusic.module.common.IBaseView;
import com.example.q.pocketmusic.util.UserUtil;
import com.example.q.pocketmusic.util.common.ToastUtil;

/**
 * Created by 鹏君 on 2016/11/14.
 */

public class PublishSongPresenter extends BasePresenter<PublishSongPresenter.IView> {
    private IView activity;
    private int index;
    private int typeId;

    public PublishSongPresenter(IView activity) {
        attachView(activity);
        this.activity = getIViewRef();
    }

    //指数*2+基础求谱硬币
    public void checkAsk(String title, final String content) {
        if (TextUtils.isEmpty(content) || TextUtils.isEmpty(title)) {
            ToastUtil.showToast(CommonString.STR_COMPLETE_INFO);
            return;
        }
        int coin = Constant.REDUCE_ASK + index * 2;
        if (!UserUtil.checkUserContribution((BaseActivity) activity.getCurrentContext(), coin)) {
            ToastUtil.showToast(CommonString.STR_NOT_ENOUGH_COIN);
            return;
        }
        activity.alertCoinDialog(coin, title, content);
    }


    //求谱
    public void askForSong(String title, String content) {
        final int coin = Constant.REDUCE_ASK + index * 2;
        if (!UserUtil.checkUserContribution((BaseActivity) activity.getCurrentContext(), coin)) {
            ToastUtil.showToast(CommonString.STR_NOT_ENOUGH_COIN);
            return;
        }
        activity.showLoading(true);
        AskSongPost askSongPost = new AskSongPost(UserUtil.user, title, typeId, content);
        askSongPost.setIndex(index);
        askSongPost.save(new ToastSaveListener<String>(activity) {
            @Override
            public void onSuccess(String s) {
                UserUtil.increment(-coin, new ToastUpdateListener() {
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
        if (index <= 0) {
            return;
        }
        index--;
        activity.changeIndex(index);
    }

    public void setTypeId(int typeId) {
        this.typeId = typeId;
    }

    public interface IView extends IBaseView {
        void finish();

        void setAskResult(Integer success);

        void alertCoinDialog(int coin, String title, String content);

        void changeIndex(int index);
    }
}
