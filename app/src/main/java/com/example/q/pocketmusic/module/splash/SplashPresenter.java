package com.example.q.pocketmusic.module.splash;

import android.content.Context;
import android.content.Intent;

import com.example.q.pocketmusic.module.common.IBaseList;
import com.example.q.pocketmusic.callback.ToastQueryListener;
import com.example.q.pocketmusic.config.BmobInfo;
import com.example.q.pocketmusic.config.Constant;
import com.example.q.pocketmusic.module.home.HomeActivity;
import com.example.q.pocketmusic.util.BmobUtil;

import java.util.List;

/**
 * Created by 鹏君 on 2017/4/23.
 */

public class SplashPresenter {
    private Context context;
    private IView activity;
    private BmobUtil bmobUtil;

    public SplashPresenter(Context context, IView activity) {
        this.context = context;
        this.activity = activity;
        bmobUtil=new BmobUtil();
    }

    public void getBmobInfo() {
        bmobUtil.getInitListWithEqual(BmobInfo.class, null, "type", Constant.BMOB_INFO_LABA, new ToastQueryListener<BmobInfo>(context,activity) {
            @Override
            public void onSuccess(List<BmobInfo> list) {
                activity.setLaBaText(list.get(0));
            }
        });
    }

    public void enterHomeActivity() {
        context.startActivity(new Intent(context, HomeActivity.class));
    }

    public interface IView extends IBaseList{

        void setLaBaText(BmobInfo bmobInfo);
    }
}
