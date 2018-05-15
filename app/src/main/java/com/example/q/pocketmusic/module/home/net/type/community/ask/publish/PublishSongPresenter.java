package com.example.q.pocketmusic.module.home.net.type.community.ask.publish;

import android.text.TextUtils;

import com.dell.fortune.tools.toast.ToastUtil;
import com.example.q.pocketmusic.R;
import com.example.q.pocketmusic.callback.ToastSaveListener;
import com.example.q.pocketmusic.callback.ToastUpdateListener;
import com.example.q.pocketmusic.config.constant.CoinConstant;
import com.example.q.pocketmusic.config.constant.Constant;
import com.example.q.pocketmusic.data.bean.ask.AskSongPost;
import com.example.q.pocketmusic.module.common.BaseActivity;
import com.example.q.pocketmusic.module.common.BasePresenter;
import com.example.q.pocketmusic.module.common.IBaseView;
import com.example.q.pocketmusic.util.UserUtil;


/**
 * Created by 鹏君 on 2016/11/14.
 */

public class PublishSongPresenter extends BasePresenter<PublishSongPresenter.IView> {
    private int index;
    private int typeId;

    public PublishSongPresenter(IView activity) {
        super(activity);
    }

    //指数*2+基础求谱硬币
    public void checkAsk(String title, final String content) {
        if (TextUtils.isEmpty(content) || TextUtils.isEmpty(title)) {
            ToastUtil.showToast(mView.getResString(R.string.complete_info));
            return;
        }
        int coin = CoinConstant.REDUCE_COIN_ASK + index * 2;
        if (!UserUtil.checkUserContribution((BaseActivity) mContext, coin)) {
            ToastUtil.showToast(mView.getResString(R.string.coin_not_enough));
            return;
        }
        mView.alertCoinDialog(coin, title, content);
    }


    //求谱
    public void askForSong(String title, String content) {
        final int coin = CoinConstant.REDUCE_COIN_ASK + index * 2;
        if (!UserUtil.checkUserContribution((BaseActivity) mContext, coin)) {
            ToastUtil.showToast(mView.getResString(R.string.coin_not_enough));
            return;
        }
        mView.showLoading(true);
        AskSongPost askSongPost = new AskSongPost(UserUtil.user, title, typeId, content);
        askSongPost.setIndex(index);
        askSongPost.save(new ToastSaveListener<String>() {
            @Override
            public void onSuccess(String s) {
                UserUtil.increment(-coin, new ToastUpdateListener() {
                    @Override
                    public void onSuccess() {
                        ToastUtil.showToast(mView.getResString(R.string.reduce_coin) + coin);
                        mView.showLoading(false);
                        mView.setAskResult(Constant.SUCCESS);
                        mView.finish();
                    }
                });
            }
        });
    }

    public void setIndex(int i) {
        index = i;
        mView.changeIndex(index);
    }

    public void addIndex() {
        index++;
        mView.changeIndex(index);
    }

    public void reduceIndex() {
        if (index <= 0) {
            return;
        }
        index--;
        mView.changeIndex(index);
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
