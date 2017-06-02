package com.example.q.pocketmusic.module.user.notify;

import android.support.design.widget.AppBarLayout;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.example.q.pocketmusic.R;
import com.example.q.pocketmusic.module.common.AuthActivity;
import com.example.q.pocketmusic.view.widget.view.IcoTextItem;

import butterknife.BindView;
import butterknife.OnClick;

public class UserNotifyActivity extends AuthActivity<UserNotifyPresenter.IView, UserNotifyPresenter>
        implements UserNotifyPresenter.IView {

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.app_bar)
    AppBarLayout appBar;
    @BindView(R.id.official_info_item)
    IcoTextItem officialInfoItem;
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
    public void initUserView() {

    }

    @OnClick({R.id.official_info_item, R.id.user_suggestion_item})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.official_info_item:
                presenter.enterOfficialInfoActivity();
                break;
            case R.id.user_suggestion_item:
                presenter.enterUserSuggestionActivity();
                break;
        }
    }
}
