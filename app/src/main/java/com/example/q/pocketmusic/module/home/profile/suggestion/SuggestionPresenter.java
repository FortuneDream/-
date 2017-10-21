package com.example.q.pocketmusic.module.home.profile.suggestion;

import android.text.TextUtils;

import com.example.q.pocketmusic.callback.ToastQueryListener;
import com.example.q.pocketmusic.callback.ToastSaveListener;
import com.example.q.pocketmusic.model.bean.bmob.UserSuggestion;
import com.example.q.pocketmusic.module.common.BasePresenter;
import com.example.q.pocketmusic.module.common.IBaseView;
import com.example.q.pocketmusic.util.UserUtil;

import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.datatype.BmobPointer;

/**
 * Created by 鹏君 on 2016/11/14.
 */

public class SuggestionPresenter extends BasePresenter<SuggestionPresenter.IView> {
    private IView activity;


    public SuggestionPresenter(IView activity) {
        attachView(activity);
        this.activity=getIViewRef();
    }

    public void sendSuggestion(String suggestion) {
        if (TextUtils.isEmpty(suggestion)) {
            return;
        }

        final UserSuggestion userSuggestion = new UserSuggestion(UserUtil.user);
        userSuggestion.setSuggestion(suggestion);
        userSuggestion.save(new ToastSaveListener<String>(activity) {
            @Override
            public void onSuccess(String s) {
                activity.sendSuggestionResult(userSuggestion);
            }
        });
    }

    public void getSuggestionList(final boolean isRefreshing) {
        BmobQuery<UserSuggestion> query = new BmobQuery<>();
        query.addWhereEqualTo("user", new BmobPointer(UserUtil.user));
        query.findObjects(new ToastQueryListener<UserSuggestion>(activity) {
            @Override
            public void onSuccess(final List<UserSuggestion> list) {
                if (!isRefreshing){
                    activity.getSuggestionListResult(list);
                }else {
                    activity.getSuggestionListResultWithRefreshing(list);
                }

            }
        });
    }


    public interface IView extends IBaseView {

        void sendSuggestionResult(UserSuggestion userSuggestion);

        void getSuggestionListResult(List<UserSuggestion> list);

        void getSuggestionListResultWithRefreshing(List<UserSuggestion> list);
    }
}