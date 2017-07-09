package com.example.q.pocketmusic.module.home.profile.support;

import com.example.q.pocketmusic.callback.ToastQueryListener;
import com.example.q.pocketmusic.config.BmobInfo;
import com.example.q.pocketmusic.config.Constant;
import com.example.q.pocketmusic.model.bean.MoneySupport;
import com.example.q.pocketmusic.module.common.BasePresenter;
import com.example.q.pocketmusic.module.common.IBaseView;

import java.util.List;

import c.b.BP;
import c.b.PListener;
import cn.bmob.v3.BmobQuery;

/**
 * Created by 鹏君 on 2017/7/8.
 * （￣m￣）
 */

public class SupportPresenter extends BasePresenter<SupportPresenter.IView> {
    private IView activity;

    public SupportPresenter(IView activity) {
        attachView(activity);
        this.activity = getIViewRef();
    }



    public void getMoneyList() {
        BmobQuery<MoneySupport> query=new BmobQuery<>();
        query.findObjects(new ToastQueryListener<MoneySupport>() {
            @Override
            public void onSuccess(List<MoneySupport> list) {
                activity.setMoneyList(list);
            }
        });
    }

    public void alipay() {
        BP.pay("捐赠","支持口袋乐谱的发展",0.02,true, new PListener() {
            @Override
            public void orderId(String s) {

            }

            @Override
            public void succeed() {

            }

            @Override
            public void fail(int i, String s) {

            }

            @Override
            public void unknow() {

            }
        });
    }

    public interface IView extends IBaseView {


        void setMoneyList(List<MoneySupport> list);
    }
}
