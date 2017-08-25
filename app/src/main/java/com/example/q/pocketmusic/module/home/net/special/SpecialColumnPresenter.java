package com.example.q.pocketmusic.module.home.net.special;

import com.example.q.pocketmusic.callback.ToastQueryListener;
import com.example.q.pocketmusic.model.bean.special.SpecialColumn;
import com.example.q.pocketmusic.module.common.BasePresenter;
import com.example.q.pocketmusic.module.common.IBaseView;

import java.util.List;

import cn.bmob.v3.BmobQuery;

/**
 * Created by 鹏君 on 2017/7/18.
 * （￣m￣）
 */

public class SpecialColumnPresenter extends BasePresenter<SpecialColumnPresenter.IView> {
    private IView activity;

    public SpecialColumnPresenter(IView activity) {
        attachView(activity);
        this.activity = getIViewRef();
    }

    public void getACGAlbumList() {
        BmobQuery<SpecialColumn> query=new BmobQuery<>();
        query.findObjects(new ToastQueryListener<SpecialColumn>() {
            @Override
            public void onSuccess(List<SpecialColumn> list) {
                activity.setAlbumList(list);
            }
        });
    }

    interface IView extends IBaseView {
        void setAlbumList(List<SpecialColumn> list);
    }
}
