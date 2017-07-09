package com.example.q.pocketmusic.module.user.notify;

import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.example.q.pocketmusic.R;
import com.example.q.pocketmusic.module.common.AuthActivity;
import com.example.q.pocketmusic.module.common.BaseActivity;
import com.example.q.pocketmusic.view.widget.view.IcoTextItem;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class UserNotifyActivity extends BaseActivity<UserNotifyPresenter.IView, UserNotifyPresenter>
        implements UserNotifyPresenter.IView {

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.app_bar)
    AppBarLayout appBar;
    @BindView(R.id.preview_version_item)
    IcoTextItem previewVersionItem;
    @BindView(R.id.usually_help_item)
    IcoTextItem usuallyHelpItem;
    @BindView(R.id.user_suggestion_item)
    IcoTextItem userSuggestionItem;

    @Override
    protected UserNotifyPresenter createPresenter() {
        return new UserNotifyPresenter(this);
    }

    @Override
    public int setContentResource() {
        return R.layout.activity_user_notify;
    }

    @Override
    public void initView() {
        initToolbar(toolbar,"Tips");
    }

    @OnClick({R.id.preview_version_item, R.id.usually_help_item, R.id.user_suggestion_item})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.preview_version_item:
                presenter.enterPreviewActivity();
                break;
            case R.id.usually_help_item:
                presenter.enterHelpActivity();
                break;
            case R.id.user_suggestion_item:
                presenter.enterUserSuggestionActivity();
                break;
        }
    }
}
