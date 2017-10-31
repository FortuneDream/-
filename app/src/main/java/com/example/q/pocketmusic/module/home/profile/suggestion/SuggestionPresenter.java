package com.example.q.pocketmusic.module.home.profile.suggestion;

import android.text.TextUtils;

import com.example.q.pocketmusic.callback.ToastQueryListener;
import com.example.q.pocketmusic.callback.ToastSaveListener;
import com.example.q.pocketmusic.model.bean.bmob.UserSuggestion;
import com.example.q.pocketmusic.module.common.BasePresenter;
import com.example.q.pocketmusic.module.common.IBaseView;
import com.example.q.pocketmusic.util.UserUtil;
import com.example.q.pocketmusic.util.common.ToastUtil;

import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.datatype.BmobPointer;

/**
 * Created by 鹏君 on 2016/11/14.
 */

public class SuggestionPresenter extends BasePresenter<SuggestionPresenter.IView> {
    private IView activity;
    private SuggestionModel model;
    private int mPage;


    public SuggestionPresenter(IView activity) {
        attachView(activity);
        this.activity = getIViewRef();
        model = new SuggestionModel();
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
                activity.setSuggestionList(true, null);
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
                activity.setSuggestionList(isRefreshing, list);
            }
        });
    }


    public interface IView extends IBaseView {
        void setSuggestionList(boolean isRefreshing, List<UserSuggestion> list);
    }
}
