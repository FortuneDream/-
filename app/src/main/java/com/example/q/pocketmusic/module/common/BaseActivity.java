package com.example.q.pocketmusic.module.common;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.example.q.pocketmusic.R;
import com.example.q.pocketmusic.util.ConvertUtil;
import com.jude.easyrecyclerview.EasyRecyclerView;
import com.jude.easyrecyclerview.adapter.RecyclerArrayAdapter;
import com.jude.easyrecyclerview.decoration.DividerDecoration;

import java.lang.reflect.Method;

import butterknife.ButterKnife;
import butterknife.Unbinder;


/**
 * Created by YQ on 2016/10/1.
 */

public abstract class BaseActivity<V, T extends BasePresenter<V>> extends AppCompatActivity implements IBaseView {
    public Activity context;
    protected T presenter;
    public final String TAG = this.getClass().getName();
    public AlertDialog mLoadingDialog;//这个dialog一般在上传，下载，的时候才会用到

    protected abstract T createPresenter();

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
        recyclerView.addItemDecoration(new DividerDecoration(ContextCompat.getColor(context, R.color.setting_divider), 1, dp, 1));
    }

    //无分割线
    public void initRecyclerView(EasyRecyclerView recyclerView, RecyclerArrayAdapter<?> adapter) {
        recyclerView.setLayoutManager(new LinearLayoutManager(context));
        recyclerView.setRefreshingColorResources(R.color.colorAccent);
        recyclerView.setAdapter(adapter);
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

    //反射显示图标
    @Override
    protected boolean onPrepareOptionsPanel(View view, Menu menu) {
        if (menu != null) {
            if (menu.getClass().getSimpleName().equals("MenuBuilder")) {
                try {
                    Method m = menu.getClass().getDeclaredMethod("setOptionalIconsVisible", Boolean.TYPE);
                    m.setAccessible(true);
                    m.invoke(menu, true);
                } catch (Exception e) {
                    Log.e(getClass().getSimpleName(), "onMenuOpened...unable to set icons for overflow menu", e);
                }
            }
        }
        return super.onPrepareOptionsPanel(view, menu);
    }

    //直接重启应用，cleartop
    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        Intent i = getBaseContext().getPackageManager()
                .getLaunchIntentForPackage(getBaseContext().getPackageName());
        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(i);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        mLoadingDialog.dismiss();
        presenter.detachView();
    }

}
