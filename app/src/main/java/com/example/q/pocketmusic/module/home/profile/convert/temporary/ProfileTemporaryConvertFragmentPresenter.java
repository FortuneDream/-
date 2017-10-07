package com.example.q.pocketmusic.module.home.profile.convert.temporary;

import com.example.q.pocketmusic.callback.ToastQueryListener;
import com.example.q.pocketmusic.model.bean.convert.ConvertSong;
import com.example.q.pocketmusic.module.common.BasePresenter;
import com.example.q.pocketmusic.module.common.IBaseView;

import java.util.List;

public class ProfileTemporaryConvertFragmentPresenter extends BasePresenter<ProfileTemporaryConvertFragmentPresenter.IView> {
    private IView fragment;
    private int mPage;
    private ProfileTemporaryConvertModel profileTemporaryConvertModel;

    public ProfileTemporaryConvertFragmentPresenter(IView fragment) {
        attachView(fragment);
        this.fragment = getIViewRef();
        profileTemporaryConvertModel = new ProfileTemporaryConvertModel();
    }

    public void getInitTemporaryList(final boolean isRefreshing) {
        if (isRefreshing) {
            mPage = 0;
        }
        profileTemporaryConvertModel.getList(mPage, new ToastQueryListener<ConvertSong>() {
            @Override
            public void onSuccess(List<ConvertSong> list) {
                fragment.setList(list, isRefreshing);
            }
        });
    }

    public void getMoreTemporaryList(final boolean isRefreshing) {
        mPage++;
        profileTemporaryConvertModel.getList(mPage, new ToastQueryListener<ConvertSong>() {
            @Override
            public void onSuccess(List<ConvertSong> list) {
                profileTemporaryConvertModel.getList(mPage, new ToastQueryListener<ConvertSong>() {
                    @Override
                    public void onSuccess(List<ConvertSong> list) {
                        fragment.setList(list, isRefreshing);
                    }
                });
            }
        });
    }

    interface IView extends IBaseView {


        void setList(List<ConvertSong> convertSongs, boolean isRrefreshing);
    }
}
