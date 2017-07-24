package com.example.q.pocketmusic.module.user.other.collection;

import com.example.q.pocketmusic.callback.ToastQueryListener;
import com.example.q.pocketmusic.config.Constant;
import com.example.q.pocketmusic.model.bean.MyUser;
import com.example.q.pocketmusic.model.bean.collection.CollectionSong;
import com.example.q.pocketmusic.model.bean.share.ShareSong;
import com.example.q.pocketmusic.module.common.BasePresenter;
import com.example.q.pocketmusic.module.common.IBaseView;

import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.datatype.BmobPointer;
import cn.bmob.v3.datatype.BmobRelation;

/**
 * Created by 鹏君 on 2017/7/24.
 * （￣m￣）
 */

public class OtherCollectionPresenter extends BasePresenter<OtherCollectionPresenter.IView> {
    private IView fragment;

    public OtherCollectionPresenter(IView fragment) {
        attachView(fragment);
        this.fragment = getIViewRef();
    }

    public void getOtherCollectionList(MyUser other) {
        BmobQuery<CollectionSong> query = new BmobQuery<>();
        query.addWhereRelatedTo("collections", new BmobPointer(other));
        query.order(Constant.BMOB_CREATE_AT);
        query.findObjects(new ToastQueryListener<CollectionSong>() {
            @Override
            public void onSuccess(List<CollectionSong> list) {
                fragment.setOtherCollectionList(list);
            }
        });

    }

    public interface IView extends IBaseView {

        void setOtherCollectionList(List<CollectionSong> list);
    }
}
