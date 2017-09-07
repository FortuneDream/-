package com.example.q.pocketmusic.module.user.notify;

import android.content.Intent;

import com.example.q.pocketmusic.module.common.BasePresenter;
import com.example.q.pocketmusic.module.common.IBaseView;
import com.example.q.pocketmusic.module.user.notify.gift.GiftActivity;
import com.example.q.pocketmusic.module.user.notify.help.HelpActivity;
import com.example.q.pocketmusic.module.user.notify.suggestion.SuggestionActivity;
import com.example.q.pocketmusic.module.user.notify.version.PreviewVersionActivity;

/**
 * Created by 鹏君 on 2017/5/27.
 */

public class UserNotifyPresenter extends BasePresenter<UserNotifyPresenter.IView> {
    private IView activity;

    public UserNotifyPresenter(IView activity) {
        attachView(activity);
        this.activity = getIViewRef();
    }


    public void enterUserSuggestionActivity() {
        activity.getCurrentContext().startActivity(new Intent(activity.getCurrentContext(), SuggestionActivity.class));
    }

    public void enterHelpActivity() {
        activity.getCurrentContext().startActivity(new Intent(activity.getCurrentContext(), HelpActivity.class));
    }

    public void enterPreviewActivity() {
        activity.getCurrentContext().startActivity(new Intent(activity.getCurrentContext(), PreviewVersionActivity.class));
    }

    public void enterGiftActivity() {
        activity.getCurrentContext().startActivity(new Intent(activity.getCurrentContext(), GiftActivity.class));
    }

    interface IView extends IBaseView {

    }
}
