package com.example.q.pocketmusic.module.home.profile.suggestion;

import android.text.TextUtils;

import com.example.q.pocketmusic.callback.ToastQueryListener;
import com.example.q.pocketmusic.callback.ToastSaveListener;
import com.example.q.pocketmusic.data.bean.bmob.UserSuggestion;
import com.example.q.pocketmusic.data.model.UserSuggestionModel;
import com.example.q.pocketmusic.module.common.BasePresenter;
import com.example.q.pocketmusic.module.common.IBaseView;
import com.example.q.pocketmusic.util.common.ToastUtil;

import java.util.List;

/**
 * Created by 鹏君 on 2016/11/14.
 */

public class SuggestionPresenter extends BasePresenter<SuggestionPresenter.IView> {
    private UserSuggestionModel model;
    private int mPage;


    public SuggestionPresenter(IView activity) {
        super(activity);
        model = new UserSuggestionModel();
        this.mPage = 0;
    }

    public void sendSuggestion(String suggestion) {
        if (TextUtils.isEmpty(suggestion)) {
            ToastUtil.showToast("不能为空哦~");
            return;
        }
        model.sendSuggestion(suggestion, new ToastSaveListener<String>() {
            @Override
            public void onSuccess(String s) {
                mView.setSuggestionList(true, null);
            }
        });
    }

    public void getSuggestionList(final boolean isRefreshing) {
        mPage++;
        if (isRefreshing) {
            mPage = 0;
        }
        model.getList(mPage, new ToastQueryListener<UserSuggestion>() {
            @Override
            public void onSuccess(List<UserSuggestion> list) {
                mView.setSuggestionList(isRefreshing, list);
            }
        });
    }


    public interface IView extends IBaseView {
        void setSuggestionList(boolean isRefreshing, List<UserSuggestion> list);
    }
}
