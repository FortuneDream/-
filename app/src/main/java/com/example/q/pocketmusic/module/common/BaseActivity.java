package com.example.q.pocketmusic.module.common;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Animatable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.IdRes;
import android.support.annotation.Nullable;
import android.support.annotation.RestrictTo;
import android.support.annotation.StringRes;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;

import com.example.q.pocketmusic.R;
import com.example.q.pocketmusic.util.common.ConvertUtil;
import com.jude.easyrecyclerview.EasyRecyclerView;
import com.jude.easyrecyclerview.adapter.RecyclerArrayAdapter;
import com.jude.easyrecyclerview.decoration.DividerDecoration;

import java.lang.ref.WeakReference;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;


/**
 * Created by 鹏君 on 2016/10/1.
 */

public abstract class BaseActivity<V, T extends BasePresenter<V>> extends AppCompatActivity implements IBaseView {
    public Activity context;
    protected T presenter;
    public final String TAG = this.getClass().getName();
    private static final int FLAG_DISMISS_DIALOG = 2001;
    public AlertDialog mLoadingDialog;//这个dialog一般在上传，下载，的时候才会用到
    private List<Drawable> drawableList = new ArrayList<>();
    private boolean mIsViewValid = true;

    @Override
    public boolean isViewValid() {
        return mIsViewValid;
    }

    protected abstract T createPresenter();

    private Handler handler = new UIHandler(this);

    //防止dialog内存泄漏
    private static class UIHandler extends Handler {
        WeakReference<BaseActivity> softActivity;

        UIHandler(BaseActivity baseActivity) {
            softActivity = new WeakReference<>(baseActivity);//使用弱引用+static 一是为了Activity可以在只有Handler引用的情况下立即被清除，而是为了可以调用activity的方法。
        }

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case FLAG_DISMISS_DIALOG:
                    BaseActivity activity = softActivity.get();
                    if (activity == null) {
                        return;
                    }
                    if (activity.mLoadingDialog != null && activity.mLoadingDialog.isShowing()) {
                        activity.mLoadingDialog.dismiss();
                    }
                    break;
            }
        }
    }


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(setContentResource());
        ButterKnife.bind(this);
        presenter = createPresenter();
        this.context = this;
        initView();
        initLoadingView();
    }


    @Override
    public Context getAppContext() {
        return getApplicationContext();
    }

    @Override
    public Context getCurrentContext() {
        return this;
    }

    public void initToolbar(Toolbar toolbar, String titleName) {
        toolbar.setTitle(titleName);
        toolbar.setTitleTextColor(ContextCompat.getColor(context, R.color.colorTitle));
        toolbar.setBackgroundColor(ContextCompat.getColor(context, R.color.colorPrimary));
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    //dp1=paddingLeft
    public void initRecyclerView(EasyRecyclerView recyclerView, RecyclerArrayAdapter<?> adapter, int dp1) {
        initRecyclerView(recyclerView, adapter);
        int dp = ConvertUtil.Dp2Px(context, dp1);
        recyclerView.addItemDecoration(new DividerDecoration(ContextCompat.getColor(context, R.color.divider_deep), 1, dp, 1));
    }

    //无分割线
    public void initRecyclerView(EasyRecyclerView recyclerView, RecyclerArrayAdapter<?> adapter) {
        recyclerView.setLayoutManager(new LinearLayoutManager(context));
        recyclerView.setRefreshingColorResources(R.color.colorAccent);
        recyclerView.setEmptyView(R.layout.view_empty);
        recyclerView.setAdapter(adapter);
    }

    //绑定动画Drawable
    public void bindAnimatableDrawble(List<ImageView> imageViews) {
        for (ImageView imageView : imageViews) {
            Drawable drawable = imageView.getDrawable();
            drawableList.add(drawable);
        }
    }


    private void initLoadingView() {
        //初始化一个LoadingDialog
        mLoadingDialog = new AlertDialog.Builder(this)
                .setView(R.layout.view_loading_wait)
                .setCancelable(false)
                .create();
    }

    public void showLoading(boolean isShow) {
        if (isShow) {
            mLoadingDialog.show();
            handler.sendEmptyMessageDelayed(FLAG_DISMISS_DIALOG, 5 * 1000);
        } else {
            mLoadingDialog.dismiss();
        }
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

//    //反射显示图标
//    @Override
//    protected boolean onPrepareOptionsPanel(View view, Menu menu) {
//        if (menu != null) {
//            if (menu.getClass().getSimpleName().equals("MenuBuilder")) {
//                try {
//                    Method m = menu.getClass().getDeclaredMethod("setOptionalIconsVisible", Boolean.TYPE);
//                    m.setAccessible(true);
//                    m.invoke(menu, true);
//                } catch (Exception e) {
//                    Log.e(getClass().getSimpleName(), "onMenuOpened...unable to set icons for overflow menu", e);
//                }
//            }
//        }
//        return super.onPrepareOptionsPanel(view, menu);
//    }

    //直接重启应用，cleartop
    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        Intent i = getBaseContext().getPackageManager()
                .getLaunchIntentForPackage(getBaseContext().getPackageName());
        if (i != null) {
            i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(i);
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
    }


    @Override
    protected void onResume() {
        super.onResume();
        startVectorAnim();
    }

    //开始属性动画
    private void startVectorAnim() {
        if (drawableList != null && drawableList.size() > 0) {
            for (Drawable drawable : drawableList) {
                ((Animatable) drawable).start();
            }
        }
    }


    @Override
    protected void onPause() {
        super.onPause();
        stopVectorAnim();
    }

    //停止属性动画
    private void stopVectorAnim() {
        if (drawableList != null && drawableList.size() > 0) {
            for (Drawable drawable : drawableList) {
                if (((Animatable) drawable).isRunning()) {
                    ((Animatable) drawable).stop();
                }
            }
        }
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        mIsViewValid = false;
        drawableList.clear();
        mLoadingDialog.dismiss();
        presenter.detachView();
    }

    @Override
    public String getResString(@StringRes int resId){
        return getAppContext().getResources().getString(resId);
    }

}
