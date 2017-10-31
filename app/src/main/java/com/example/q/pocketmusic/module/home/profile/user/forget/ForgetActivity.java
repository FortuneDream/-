package com.example.q.pocketmusic.module.home.profile.user.forget;

import android.support.design.widget.AppBarLayout;
import android.support.v7.widget.Toolbar;
import android.widget.TextView;

import com.example.q.pocketmusic.R;
import com.example.q.pocketmusic.module.common.BaseActivity;
import com.example.q.pocketmusic.view.widget.view.TextEdit;

import butterknife.BindView;
import butterknife.OnClick;

public class ForgetActivity extends BaseActivity<ForgetPresenter.IView,ForgetPresenter>
        implements ForgetPresenter.IView {


    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.app_bar)
    AppBarLayout appBar;
    @BindView(R.id.account_tet)
    TextEdit accountTet;
    @BindView(R.id.nick_name_tet)
    TextEdit nickNameTet;
    @BindView(R.id.new_password_tet)
    TextEdit newPasswordTet;
    @BindView(R.id.confirm_password_tet)
    TextEdit confirmPasswordTet;
    @BindView(R.id.ok_txt)
    TextView okTxt;

    @Override
    public int setContentResource() {
        return R.layout.activity_forget;
    }

    @Override
    public void initView() {
        initToolbar(toolbar, "忘记密码");
    }


    @OnClick(R.id.ok_txt)
    public void onViewClicked() {
        String account = accountTet.getInputString();
        String nickName = nickNameTet.getInputString();
        String newPassword = newPasswordTet.getInputString();
        String confirmPassword = confirmPasswordTet.getInputString();
        presenter.checkInfo(account, nickName, newPassword, confirmPassword);
    }


    @Override
    protected ForgetPresenter createPresenter() {
        return new ForgetPresenter(this);
    }
}
