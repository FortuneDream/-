package com.example.q.pocketmusic.module.home.net.type.community.state;

import com.example.q.pocketmusic.callback.ToastQueryListener;
import com.example.q.pocketmusic.data.bean.CommunityState;
import com.example.q.pocketmusic.data.model.UserCommunityStateModel;
import com.example.q.pocketmusic.module.common.BasePresenter;
import com.example.q.pocketmusic.module.common.IBaseView;

import java.util.List;

public class CommunityStateFragmentPresenter extends BasePresenter<CommunityStateFragmentPresenter.IView> {
    private IView fragment;
    private int mPage;
    private int type;
    private UserCommunityStateModel model;

    public CommunityStateFragmentPresenter(IView fragment) {
        attachView(fragment);
        this.fragment = getIViewRef();
        this.mPage = 0;
        model=new UserCommunityStateModel();
    }

    public void getList(final boolean isRefreshing) {
        mPage++;
        if (isRefreshing) {
            mPage = 0;
        }
        model.getList(type, mPage, new ToastQueryListener<CommunityState>() {
            @Override
            public void onSuccess(List<CommunityState> list) {
                fragment.setList(isRefreshing,list);
            }
        });
    }

    public void setType(int type) {
        this.type = type;
    }

    interface IView extends IBaseView {

        void setList(boolean isRefreshing, List<CommunityState> list);
    }
}
