package com.example.q.pocketmusic.module.user.notify.version;

import com.example.q.pocketmusic.callback.ToastQueryListener;
import com.example.q.pocketmusic.model.BmobInfo;
import com.example.q.pocketmusic.config.Constant;
import com.example.q.pocketmusic.module.common.BasePresenter;
import com.example.q.pocketmusic.module.common.IBaseView;

import java.util.List;

import cn.bmob.v3.BmobQuery;

/**
 * Created by 鹏君 on 2017/7/9.
 * （￣m￣）
 */

public class PreviewVersionPresenter extends BasePresenter<PreviewVersionPresenter.IView> {
    private IView activity;

    public PreviewVersionPresenter(IView activity) {
        attachView(activity);
        this.activity = getIViewRef();
    }

    public void getDevPlanList() {
        BmobQuery<BmobInfo> query=new BmobQuery<>();
        query.addWhereEqualTo("type", Constant.BMOB_INFO_PLAN);
        query.findObjects(new ToastQueryListener<BmobInfo>() {
            @Override
            public void onSuccess(List<BmobInfo> list) {
                activity.setDevPlanList(list);
            }
        });
    }

    interface IView extends IBaseView {

        void setDevPlanList(List<BmobInfo> list);
    }
}
