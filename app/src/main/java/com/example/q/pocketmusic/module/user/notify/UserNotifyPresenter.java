package com.example.q.pocketmusic.module.user.notify;

import android.content.Intent;

import com.example.q.pocketmusic.module.common.BasePresenter;
import com.example.q.pocketmusic.module.common.IBaseView;
import com.example.q.pocketmusic.module.user.notify.suggestion.SuggestionActivity;

/**
 * Created by 鹏君 on 2017/5/27.
 */

public class UserNotifyPresenter extends BasePresenter<UserNotifyPresenter.IView> {
    private IView activity;

    public UserNotifyPresenter(IView activity) {
        attachView(activity);
        this.activity = getIViewRef();
    }

    public void enterOfficialInfoActivity() {

    }

    public void enterUserSuggestionActivity() {
        activity.getCurrentContext().startActivity(new Intent(activity.getCurrentContext(), SuggestionActivity.class));
    }

    interface IView extends IBaseView {

    }
}
