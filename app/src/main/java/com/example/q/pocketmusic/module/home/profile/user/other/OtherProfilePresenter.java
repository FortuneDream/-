package com.example.q.pocketmusic.module.home.profile.user.other;

import android.text.TextUtils;

import com.dell.fortune.tools.toast.ToastUtil;
import com.example.q.pocketmusic.R;
import com.example.q.pocketmusic.callback.ToastUpdateListener;
import com.example.q.pocketmusic.data.bean.MyUser;
import com.example.q.pocketmusic.module.common.BasePresenter;
import com.example.q.pocketmusic.module.common.IBaseView;
import com.example.q.pocketmusic.util.UserUtil;

import cn.bmob.v3.datatype.BmobRelation;

/**
 * Created by 鹏君 on 2017/7/24.
 * （￣m￣）
 */

public class OtherProfilePresenter extends BasePresenter<OtherProfilePresenter.IView> {

    public OtherProfilePresenter(IView activity) {
        super(activity);
    }

    public void interestOther(MyUser otherUser) {
        if (TextUtils.equals(otherUser.getObjectId(), UserUtil.user.getObjectId())) {
            ToastUtil.showToast(mView.getResString(R.string.no_interest_self));
            return;
        }
        BmobRelation relation = new BmobRelation();
        relation.add(otherUser);
        UserUtil.user.setInterests(relation);
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
