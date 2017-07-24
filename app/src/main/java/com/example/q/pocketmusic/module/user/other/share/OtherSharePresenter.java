package com.example.q.pocketmusic.module.user.other.share;

import com.example.q.pocketmusic.callback.ToastQueryListener;
import com.example.q.pocketmusic.config.Constant;
import com.example.q.pocketmusic.model.bean.MyUser;
import com.example.q.pocketmusic.model.bean.share.ShareSong;
import com.example.q.pocketmusic.module.common.BasePresenter;
import com.example.q.pocketmusic.module.common.IBaseView;

import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.datatype.BmobPointer;

/**
 * Created by 鹏君 on 2017/7/24.
 * （￣m￣）
 */

public class OtherSharePresenter extends BasePresenter<OtherSharePresenter.IView> {
    private IView fragment;

    public OtherSharePresenter(IView fragment) {
        attachView(fragment);
        this.fragment = getIViewRef();
    }

    public void getOtherShareList(MyUser other) {
        BmobQuery<ShareSong> query = new BmobQuery<>();
        query.include("user");
        query.addWhereEqualTo("user", new BmobPointer(other));
        query.order(Constant.BMOB_CREATE_AT);
        query.findObjects(new ToastQueryListener<ShareSong>() {
            @Override
            public void onSuccess(List<ShareSong> list) {
                    fragment.setOtherShareList(list);
            }
        });
    }


    public interface IView extends IBaseView {

        void setOtherShareList(List<ShareSong> list);
    }
}
