/*
 * Copyright (c) 2018.
 * 版权归于Github.FortuneDream所有
 *
 */

package com.example.q.pocketmusic.module.home;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AlertDialog;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.widget.FrameLayout;

import com.dell.fortune.tools.dialog.DialogSureCancel;
import com.dell.fortune.tools.toast.ToastUtil;
import com.example.q.pocketmusic.R;
import com.example.q.pocketmusic.module.common.BaseActivity;
import com.example.q.pocketmusic.module.home.profile.support.SupportActivity;
import com.example.q.pocketmusic.util.UserUtil;
import com.example.q.pocketmusic.view.widget.view.BottomTabView;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class HomeActivity extends BaseActivity<HomePresenter.IView, HomePresenter>
        implements HomePresenter.IView {
    private static boolean isExit = false;  // 定义一个变量，来标识是否退出
    public static final String ACTION_RETURN_HOME = "action_return_home";
    @BindView(R.id.home_content)
    FrameLayout homeContent;
    @BindView(R.id.home_tab_net_tab)
    BottomTabView homeTabNetTab;
    @BindView(R.id.home_tab_local_tab)
    BottomTabView homeTabLocalTab;
    @BindView(R.id.home_tab_profile_tab)
    BottomTabView homeTabProfileTab;
    @BindView(R.id.home_tab_search_tab)
    BottomTabView homeTabSearchTab;
    private List<BottomTabView> bottomTabViews = new ArrayList<>();
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
        initBottomTabList();
        presenter.clickBottomTab(HomePresenter.TabIndex.NET_INDEX);
        presenter.checkAlertSupportDialog();
        presenter.checkSignature(getPackageManager());//签名校验
    }

    private void initBottomTabList() {
        bottomTabViews.add(HomePresenter.TabIndex.NET_INDEX, homeTabNetTab);
        bottomTabViews.add(HomePresenter.TabIndex.SEARCH_INDEX, homeTabSearchTab);
        bottomTabViews.add(HomePresenter.TabIndex.LOCAL_INDEX, homeTabLocalTab);
        bottomTabViews.add(HomePresenter.TabIndex.PROFILE_INDEX, homeTabProfileTab);
    }

    public void alertSupportDialog() {
        final DialogSureCancel dialogSureCancel = new DialogSureCancel(context);
        dialogSureCancel.getTvTitle().setText("支持开发者");
        dialogSureCancel.getTvContent().setText("如果您觉得这款应用做的不错~可以支持一下我~");
        dialogSureCancel.getTvCancel().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialogSureCancel.cancel();
            }
        });
        dialogSureCancel.getTvSure().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialogSureCancel.dismiss();
                presenter.startActivity( SupportActivity.class);
            }
        });
        dialogSureCancel.show();
    }

    @Override
    public void onSelectTabResult(int oldIndex, int index) {
        for (int i = 0; i < bottomTabViews.size(); i++) {
            if (i == index) {
                BottomTabView selectView = bottomTabViews.get(i);
                selectView.onSelect(true);
                selectAnim(selectView);
            } else {
                BottomTabView unselectView = bottomTabViews.get(i);
                unselectView.onSelect(false);
            }
        }
        if (oldIndex != HomePresenter.TabIndex.INIT_INDEX) {//初始态不变
            unselectAnim(bottomTabViews.get(oldIndex));
        }
    }


    //触发SingleTask
    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        setIntent(intent);
        if (TextUtils.equals(intent.getAction(), ACTION_RETURN_HOME)) {
            presenter.clickBottomTab(HomePresenter.TabIndex.NET_INDEX);
        }
    }

    @OnClick({R.id.home_tab_local_tab, R.id.home_tab_net_tab, R.id.home_tab_search_tab, R.id.home_tab_profile_tab})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.home_tab_local_tab://点击本地
                presenter.clickBottomTab(HomePresenter.TabIndex.LOCAL_INDEX);
                break;
            case R.id.home_tab_net_tab://点击网络
                presenter.clickBottomTab(HomePresenter.TabIndex.NET_INDEX);
                break;
            case R.id.home_tab_search_tab://点击搜索
                presenter.clickBottomTab(HomePresenter.TabIndex.SEARCH_INDEX);
                break;
            case R.id.home_tab_profile_tab:
                if (UserUtil.checkLocalUser(this)) {
                    presenter.clickBottomTab(HomePresenter.TabIndex.PROFILE_INDEX);
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
        presenter.clickBottomTab(HomePresenter.TabIndex.NET_INDEX);
    }

    //当Activity被回收，按Home键，会调用此方法，不在保存View的视图，这样就不会出现Fragment重影问题（这里适用于权限申请）
    @Override
    protected void onSaveInstanceState(Bundle outState) {
        // super.onSaveInstanceState(outState);
//        Log.e("TAG","onSaveInstanceState");
    }

    //未选中动画
    private void unselectAnim(View view) {
        ObjectAnimator scaleXAnimator = ObjectAnimator.ofFloat(view, "scaleX", 1.1f, 1f);
        ObjectAnimator scaleYAnimator = ObjectAnimator.ofFloat(view, "scaleY", 1.1f, 1f);
        AnimatorSet set = new AnimatorSet();
        List<Animator> list = new ArrayList<>();
        list.add(scaleXAnimator);
        list.add(scaleYAnimator);
        set.setDuration(200);
        set.playTogether(list);
        set.start();
    }

    //选中动画
    private void selectAnim(View view) {
        ObjectAnimator scaleXAnimator = ObjectAnimator.ofFloat(view, "scaleX", 1f, 1.1f);
        ObjectAnimator scaleYAnimator = ObjectAnimator.ofFloat(view, "scaleY", 1f, 1.1f);
        AnimatorSet set = new AnimatorSet();
        List<Animator> list = new ArrayList<>();
        list.add(scaleXAnimator);
        list.add(scaleYAnimator);
        set.setDuration(200);
        set.playTogether(list);
        set.start();
    }

}
