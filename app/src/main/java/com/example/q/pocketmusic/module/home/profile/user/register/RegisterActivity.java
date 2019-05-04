package com.example.q.pocketmusic.module.home.profile.user.register;

import android.view.View;
import android.widget.TextView;

import androidx.appcompat.widget.Toolbar;

import com.example.q.pocketmusic.R;
import com.example.q.pocketmusic.module.common.BaseActivity;
import com.example.q.pocketmusic.view.widget.view.TextEdit;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by 鹏君 on 2016/11/14.
 */

public class RegisterActivity extends BaseActivity<RegisterPresenter.IView,RegisterPresenter>
        implements RegisterPresenter.IView {

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.account_tet)
    TextEdit accountTet;
    @BindView(R.id.nick_name_tet)
    TextEdit nickNameTet;
    @BindView(R.id.password_tet)
    TextEdit passwordTet;
    @BindView(R.id.confirm_password_tet)
    TextEdit confirmPasswordTet;
    @BindView(R.id.ok_txt)
    TextView okTxt;

    @Override
    public int setContentResource() {
        return R.layout.activity_register;
    }

    @Override
    public void initView() {
        initToolbar(toolbar, "用户注册");
    }


    @OnClick(R.id.ok_txt)
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.ok_txt:
                String account = accountTet.getInputString();
                String password = passwordTet.getInputString();
                String confirmPassword = confirmPasswordTet.getInputString();
                String nickName = nickNameTet.getInputString();
                presenter.register(account, password, confirmPassword, nickName);
                break;
        }
    }




    @Override
    protected RegisterPresenter createPresenter() {
        return new RegisterPresenter(this);
    }
}