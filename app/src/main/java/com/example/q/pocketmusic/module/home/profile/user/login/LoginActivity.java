package com.example.q.pocketmusic.module.home.profile.user.login;

import android.content.Intent;
import android.support.design.widget.AppBarLayout;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.q.pocketmusic.R;
import com.example.q.pocketmusic.config.Constant;
import com.example.q.pocketmusic.model.bean.MyUser;
import com.example.q.pocketmusic.module.common.AuthActivity;
import com.example.q.pocketmusic.module.common.BaseActivity;
import com.example.q.pocketmusic.util.common.ToastUtil;
import com.example.q.pocketmusic.view.widget.view.TextEdit;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by 鹏君 on 2016/11/14.
 */

public class LoginActivity extends BaseActivity<LoginPresenter.IView,LoginPresenter>
        implements LoginPresenter.IView {

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.app_bar)
    AppBarLayout appBar;
    @BindView(R.id.account_tet)
    TextEdit accountTet;
    @BindView(R.id.password_tet)
    TextEdit passwordTet;
    @BindView(R.id.ok_txt)
    TextView okTxt;
    @BindView(R.id.register_txt)
    TextView registerTxt;
    @BindView(R.id.forget_password_iv)
    ImageView forgetPasswordIv;

    @Override
    public int setContentResource() {
        return R.layout.activity_login;
    }

    @Override
    public void initView() {
        initToolbar(toolbar, "用户登录");
    }



    //通过基类AuthActivity跳转来，登录成功后返回user，没有则返回null
    @Override
    public void loginToResult(Integer result, MyUser myUser) {
        Intent intent = new Intent();
        intent.putExtra(AuthActivity.RESULT_USER, myUser);
        setResult(result, intent);
    }


    @OnClick({R.id.ok_txt, R.id.register_txt,R.id.forget_password_iv})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.ok_txt://点击确定
                String account = accountTet.getInputString();
                String password = passwordTet.getInputString();
                presenter.login(account, password);
                break;
            case R.id.register_txt://跳转到注册
                presenter.enterRegisterActivity();//请求码REQUEST_REGISTER
                break;
            case R.id.forget_password_iv:
                ToastUtil.showToast("忘记密码-->请在粉丝群内@鹏君");
                break;
        }
    }

    //跳转到RegistererActivity，如果注册成功就不finish到此页
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == Constant.REQUEST_REGISTER) {
            if (resultCode == Constant.SUCCESS) {
                finish();
            }
        }
    }


    @Override
    protected LoginPresenter createPresenter() {
        return new LoginPresenter(this);
    }


}