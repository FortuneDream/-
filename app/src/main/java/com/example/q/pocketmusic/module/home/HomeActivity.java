package com.example.q.pocketmusic.module.home;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AlertDialog;
import android.view.KeyEvent;
import android.view.View;
import android.widget.FrameLayout;

import com.example.q.pocketmusic.R;
import com.example.q.pocketmusic.module.common.BaseActivity;

import com.example.q.pocketmusic.util.UserUtil;
import com.example.q.pocketmusic.util.common.ToastUtil;
import com.example.q.pocketmusic.view.widget.view.BottomTabView;

import butterknife.BindView;
import butterknife.OnClick;

public class HomeActivity extends BaseActivity<HomePresenter.IView, HomePresenter>
        implements HomePresenter.IView {
    @BindView(R.id.home_content)
    FrameLayout homeContent;
    @BindView(R.id.home_tab_local_tab)
    BottomTabView homeTabLocalTab;
    @BindView(R.id.home_tab_net_tab)
    BottomTabView homeTabNetTab;
    @BindView(R.id.home_tab_ask_tab)
    BottomTabView homeTabAskTab;
    @BindView(R.id.home_tab_profile_tab)
    BottomTabView homeTabProfileTab;

    private static boolean isExit = false;  // 定义一个变量，来标识是否退出
    public static final String ACTION_RETURN_HOME = "action_return_home";
    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            isExit = false;
        }
    };

    @Override
    public int setContentResource() {
        return R.layout.activity_home;
    }

    @Override
    public void initView() {
        presenter.setFragmentManager(getSupportFragmentManager());
        presenter.checkVersion();
        presenter.clickNet();
        presenter.checkAlertSupportDialog();
    }

    public void alertSupportDialog() {
        new AlertDialog.Builder(getCurrentContext())
                .setTitle("支持开发者")
                .setMessage("如果您觉得这款应用做的不错~可以支持一下我~")
                .setPositiveButton("好的", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        presenter.enterSupportActivity();
                    }
                })
                .setNegativeButton("算了", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                })
                .show();
    }


    //触发SingleTask
    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        setIntent(intent);
        if (intent.getAction() == ACTION_RETURN_HOME) {
            presenter.clickLocal();
        }
    }

    @OnClick({R.id.home_tab_local_tab, R.id.home_tab_net_tab, R.id.home_tab_ask_tab, R.id.home_tab_profile_tab})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.home_tab_local_tab://点击本地
                presenter.clickLocal();
                break;
            case R.id.home_tab_net_tab://点击网络
                presenter.clickNet();
                break;
            case R.id.home_tab_ask_tab://点击求谱
                presenter.clickAsk();
                break;
            case R.id.home_tab_profile_tab:
                UserUtil.checkLocalUser(this);
                if (UserUtil.user != null) {
                    presenter.clickProfile();
                }
                break;
        }
    }


    //捕获back，设置按第二次退出
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {

        if (keyCode == KeyEvent.KEYCODE_BACK) {
            exit();
            return false;
        }
        return super.onKeyDown(keyCode, event);
    }

    private void exit() {
        if (!isExit) {
            isExit = true;
            ToastUtil.showToast("再按一次退出程序");
            // 利用handler延迟发送更改状态信息
            mHandler.sendEmptyMessageDelayed(0, 2000);
        } else {
            finish();
            System.exit(0);
        }
    }

    @Override
    protected HomePresenter createPresenter() {
        return new HomePresenter(this);
    }

    //恢复视图，在onStart之后，这里默认点击local
    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        presenter.clickLocal();
    }

    //当Activity被回收，按Home键，会调用此方法，不在保存View的视图，这样就不会出现Fragment重影问题（这里适用于权限申请）
    @Override
    protected void onSaveInstanceState(Bundle outState) {
        // super.onSaveInstanceState(outState);
//        Log.e("TAG","onSaveInstanceState");
    }

    //选择本地tab
    @Override
    public void onSelectLocal() {
        homeTabLocalTab.onSelect(true);
        homeTabNetTab.onSelect(false);
        homeTabAskTab.onSelect(false);
        homeTabProfileTab.onSelect(false);
    }

    //选择网络tab
    @Override
    public void onSelectNet() {
        homeTabLocalTab.onSelect(false);
        homeTabNetTab.onSelect(true);
        homeTabAskTab.onSelect(false);
        homeTabProfileTab.onSelect(false);
    }

    @Override
    public void onSelectAsk() {
        homeTabLocalTab.onSelect(false);
        homeTabNetTab.onSelect(false);
        homeTabAskTab.onSelect(true);
        homeTabProfileTab.onSelect(false);
    }

    @Override
    public void onSelectProfile() {

        homeTabLocalTab.onSelect(false);
        homeTabNetTab.onSelect(false);
        homeTabAskTab.onSelect(false);
        homeTabProfileTab.onSelect(true);
    }
}
