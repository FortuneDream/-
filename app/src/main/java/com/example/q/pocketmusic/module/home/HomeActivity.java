package com.example.q.pocketmusic.module.home;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.KeyEvent;
import android.view.View;
import android.widget.FrameLayout;

import com.example.q.pocketmusic.R;
import com.example.q.pocketmusic.module.common.BaseActivity;
import com.example.q.pocketmusic.module.common.TestActivity;
import com.example.q.pocketmusic.util.CheckUserUtil;
import com.example.q.pocketmusic.util.MyToast;
import com.example.q.pocketmusic.view.widget.view.TabView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class HomeActivity extends BaseActivity implements HomePresenter.IView {

    @BindView(R.id.home_content)
    FrameLayout homeContent;
    @BindView(R.id.home_tab_local_tab)
    TabView homeTabLocalTab;
    @BindView(R.id.home_tab_net_tab)
    TabView homeTabNetTab;
    @BindView(R.id.home_tab_ask_tab)
    TabView homeTabAskTab;
    @BindView(R.id.home_tab_profile_tab)
    TabView homeTabProfileTab;
    private HomePresenter presenter;
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
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        ButterKnife.bind(this);
        presenter = new HomePresenter(this, this, getSupportFragmentManager());
        presenter.checkVersion();
        presenter.clickLocal();
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
                if (CheckUserUtil.checkLocalUser(this) != null) {//检验是否登录
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
            MyToast.showToast(this, "再按一次退出程序");
            // 利用handler延迟发送更改状态信息
            mHandler.sendEmptyMessageDelayed(0, 2000);
        } else {
            finish();
            System.exit(0);
        }
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
