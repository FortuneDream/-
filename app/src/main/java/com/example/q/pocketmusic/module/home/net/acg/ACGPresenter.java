package com.example.q.pocketmusic.module.home.net.acg;

import com.example.q.pocketmusic.callback.ToastQueryListener;
import com.example.q.pocketmusic.model.bean.acg.ACGAlbum;
import com.example.q.pocketmusic.module.common.BasePresenter;
import com.example.q.pocketmusic.module.common.IBaseView;

import java.util.List;

import cn.bmob.v3.BmobQuery;

/**
 * Created by 鹏君 on 2017/7/18.
 * （￣m￣）
 */

public class ACGPresenter extends BasePresenter<ACGPresenter.IView> {
    private IView activity;

    public ACGPresenter(IView activity) {
        attachView(activity);
        this.activity = getIViewRef();
    }

    public void getACGAlbumList() {
        BmobQuery<ACGAlbum> query=new BmobQuery<>();
        query.findObjects(new ToastQueryListener<ACGAlbum>() {
            @Override
            public void onSuccess(List<ACGAlbum> list) {
                activity.setAlbumList(list);
            }
        });
    }

    interface IView extends IBaseView {
        void setAlbumList(List<ACGAlbum> list);
    }
}
