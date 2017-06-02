package com.example.q.pocketmusic.module.user.notify.suggestion;

import android.text.TextUtils;

import com.example.q.pocketmusic.callback.ToastQueryListener;
import com.example.q.pocketmusic.callback.ToastSaveListener;
import com.example.q.pocketmusic.model.bean.MyUser;
import com.example.q.pocketmusic.model.bean.bmob.UserSuggestion;
import com.example.q.pocketmusic.module.common.BasePresenter;
import com.example.q.pocketmusic.module.common.IBaseView;

import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.datatype.BmobPointer;

/**
 * Created by 鹏君 on 2016/11/14.
 */

public class SuggestionPresenter extends BasePresenter<SuggestionPresenter.IView> {
    private IView activity;

    private MyUser user;

    public void setUser(MyUser user) {
        this.user = user;
    }

    public SuggestionPresenter(IView activity) {
        attachView(activity);
        this.activity=getIViewRef();
    }

    public void sendSuggestion(String suggestion) {
        if (TextUtils.isEmpty(suggestion)) {
            return;
        }

        final UserSuggestion userSuggestion = new UserSuggestion(user);
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
        query.addWhereEqualTo("user", new BmobPointer(user));
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
