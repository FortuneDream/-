package com.example.q.pocketmusic.module.splash;

import com.example.q.pocketmusic.callback.ToastQueryListener;
import com.example.q.pocketmusic.config.constant.Constant;
import com.example.q.pocketmusic.data.BmobInfo;
import com.example.q.pocketmusic.module.common.BasePresenter;
import com.example.q.pocketmusic.module.common.IBaseView;

import java.util.List;

import cn.bmob.v3.BmobQuery;

/**
 * Created by 鹏君 on 2017/4/23.
 */

public class SplashPresenter extends BasePresenter<SplashPresenter.IView> {

    public SplashPresenter(IView activity) {
        super(activity);
    }

    public void getBmobInfo() {
        BmobQuery<BmobInfo> queryComment = new BmobQuery<>();
        queryComment.order("-createdAt");
        queryComment.setLimit(10);
        queryComment.addWhereEqualTo("type", Constant.BMOB_INFO_LABA);
        queryComment.findObjects(new ToastQueryListener<BmobInfo>() {
            @Override
            public void onSuccess(List<BmobInfo> list) {
                mView.setLaBaText(list.get(0));
            }
        });
    }


    public interface IView extends IBaseView {

        void setLaBaText(BmobInfo bmobInfo);
    }
}
