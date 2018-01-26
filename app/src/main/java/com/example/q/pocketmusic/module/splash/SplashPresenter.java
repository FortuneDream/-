package com.example.q.pocketmusic.module.splash;

import android.content.Intent;

import com.example.q.pocketmusic.callback.ToastQueryListener;
import com.example.q.pocketmusic.data.BmobInfo;
import com.example.q.pocketmusic.config.constant.Constant;
import com.example.q.pocketmusic.module.common.BasePresenter;
import com.example.q.pocketmusic.module.common.IBaseView;
import com.example.q.pocketmusic.module.home.HomeActivity;
import com.example.q.pocketmusic.util.BmobUtil;

import java.util.List;

/**
 * Created by 鹏君 on 2017/4/23.
 */

public class SplashPresenter extends BasePresenter<SplashPresenter.IView> {
    private IView activity;
    private BmobUtil bmobUtil;

    public SplashPresenter(IView activity) {
        attachView(activity);//需要传过一个强引用
        this.activity = getIViewRef();//得到一个弱引用
        bmobUtil = new BmobUtil();
    }

    public void getBmobInfo() {
        bmobUtil.getInitListWithEqual(BmobInfo.class, null, "type", Constant.BMOB_INFO_LABA, new ToastQueryListener<BmobInfo>() {
            @Override
            public void onSuccess(List<BmobInfo> list) {
                activity.setLaBaText(list.get(0));
            }
        });
    }

    public void enterHomeActivity() {
        activity.getCurrentContext().startActivity(new Intent(activity.getCurrentContext(), HomeActivity.class));
    }

    public interface IView extends IBaseView {

        void setLaBaText(BmobInfo bmobInfo);
    }
}
