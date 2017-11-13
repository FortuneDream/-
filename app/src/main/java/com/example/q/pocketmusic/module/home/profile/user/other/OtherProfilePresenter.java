package com.example.q.pocketmusic.module.home.profile.user.other;

import com.example.q.pocketmusic.callback.ToastUpdateListener;
import com.example.q.pocketmusic.data.bean.MyUser;
import com.example.q.pocketmusic.module.common.BasePresenter;
import com.example.q.pocketmusic.module.common.IBaseView;
import com.example.q.pocketmusic.util.UserUtil;
import com.example.q.pocketmusic.util.common.ToastUtil;

import cn.bmob.v3.datatype.BmobRelation;

/**
 * Created by 鹏君 on 2017/7/24.
 * （￣m￣）
 */

public class OtherProfilePresenter extends BasePresenter<OtherProfilePresenter.IView> {
    private IView activity;

    public OtherProfilePresenter(IView activity) {
        attachView(activity);
        this.activity = getIViewRef();
    }

    public void interestOther(MyUser otherUser) {
        BmobRelation relation = new BmobRelation();
        relation.add(otherUser);
        UserUtil.user.setConverts(relation);
        UserUtil.user.update(new ToastUpdateListener() {
            @Override
            public void onSuccess() {
                ToastUtil.showToast("已关注");
            }
        });
    }

    interface IView extends IBaseView {

    }
}
