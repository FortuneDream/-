package com.example.q.pocketmusic.module.common;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Animatable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.MenuItem;
import android.widget.ImageView;

import androidx.annotation.Nullable;
import androidx.annotation.StringRes;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.dell.fortune.tools.dialog.shapeloadingview.LoadingDialogUtil;
import com.example.q.pocketmusic.R;
import com.example.q.pocketmusic.data.event.LoadingDialogEvent;
import com.example.q.pocketmusic.util.common.ConvertUtil;
import com.jude.easyrecyclerview.EasyRecyclerView;
import com.jude.easyrecyclerview.adapter.RecyclerArrayAdapter;
import com.jude.easyrecyclerview.decoration.DividerDecoration;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;


/**
 * Created by 鹏君 on 2016/10/1.
 */

public abstract class BaseActivity<V extends IBaseView, T extends BasePresenter<V>> extends AppCompatActivity implements IBaseView {
    public Activity context;
    protected T presenter;
    public final String TAG = this.getClass().getName();
    private static final int FLAG_DISMISS_DIALOG = 2001;
    private List<Drawable> drawableList = new ArrayList<>();
    private boolean mIsViewValid = true;

    @Override
    public boolean isViewValid() {
        return mIsViewValid;
    }

    protected abstract T createPresenter();

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case FLAG_DISMISS_DIALOG:
                    LoadingDialogUtil.dismiss(context);
                    break;
            }
        }
    };

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(setContentResource());
        ButterKnife.bind(this);
        EventBus.getDefault().register(this);
        presenter = createPresenter();
        this.context = this;
        initView();
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


    public void showLoading(boolean isShow) {
        if (isShow) {
            LoadingDialogUtil.show(this);
            handler.sendEmptyMessage(FLAG_DISMISS_DIALOG);
        } else {
            LoadingDialogUtil.dismiss(this);
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


    @Subscribe(threadMode = ThreadMode.MAIN)
    public void showDialog(LoadingDialogEvent event) {
        showLoading(event.isShow());
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
        if (EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().unregister(this);
        }
        mIsViewValid = false;
        drawableList.clear();
        presenter.detachView();
        handler.removeCallbacksAndMessages(null);
    }

    @Override
    public String getResString(@StringRes int resId) {
        return getAppContext().getResources().getString(resId);
    }

}
