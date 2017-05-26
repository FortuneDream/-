package com.example.q.pocketmusic.module.home.profile.setting;

import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

import com.example.q.pocketmusic.R;
import com.example.q.pocketmusic.module.common.AuthActivity;
import com.example.q.pocketmusic.util.MyToast;
import com.example.q.pocketmusic.view.widget.view.IcoTextItem;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;


/**
 * Created by Cloud on 2016/11/14.
 */

public class SettingActivity extends AuthActivity<SettingPresenter.IView, SettingPresenter>
        implements SettingPresenter.IView {

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.app_bar)
    AppBarLayout appBar;
    @BindView(R.id.version_tv)
    TextView versionTv;
    @BindView(R.id.update_item)
    IcoTextItem updateItem;
    @BindView(R.id.share_item)
    IcoTextItem shareItem;
    @BindView(R.id.logout_item)
    IcoTextItem logoutItem;

    @Override
    public int setContentResource() {
        return R.layout.activity_setting;
    }


    @Override
    public void initUserView() {
        initToolbar(toolbar, "设置");
        presenter.checkUpdate(false);//检测更新
        setVersion();
    }


    private void setVersion() {
        try {
            versionTv.setText("当前版本：" + getPackageManager().getPackageInfo(
                    getPackageName(), 0).versionName);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }

    }

    @OnClick({R.id.update_item, R.id.logout_item, R.id.share_item})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.update_item://检测更新
                presenter.checkUpdate(true);
                break;
            case R.id.logout_item://退出登录
                alertLogoutDialog();
                break;
            case R.id.share_item://分享app
                presenter.shareApp();
                break;
        }
    }

    private void alertLogoutDialog() {
        new AlertDialog.Builder(this)
                .setTitle("退出登录")
                .setIcon(R.drawable.ico_setting_error)
                .setMessage("确定?")
                .setNegativeButton("否", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                })
                .setPositiveButton("是", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        presenter.logOut();
                    }
                })
                .show();
    }

    @Override
    public void setCheckUpdateResult(boolean hasUpdate, boolean showToast) {
        updateItem.bindBadge(hasUpdate);
        if (!hasUpdate && showToast) {
            MyToast.showToast(context, "当前已是最新版本~");
        }
    }

    @Override
    protected SettingPresenter createPresenter() {
        return new SettingPresenter(this);
    }

}