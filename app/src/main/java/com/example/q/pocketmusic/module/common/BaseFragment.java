package com.example.q.pocketmusic.module.common;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.q.pocketmusic.R;
import com.example.q.pocketmusic.data.event.LoadingDialogEvent;
import com.example.q.pocketmusic.util.common.ConvertUtil;
import com.jude.easyrecyclerview.EasyRecyclerView;
import com.jude.easyrecyclerview.adapter.RecyclerArrayAdapter;
import com.jude.easyrecyclerview.decoration.DividerDecoration;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by 鹏君 on 2017/1/16.
 */

public abstract class BaseFragment<V extends IBaseView, T extends BasePresenter<V>> extends Fragment implements IBaseView {
    protected T presenter;
    public static AlertDialog mLoadingDialog;
    public Context context;
    public final String TAG = this.getClass().getName();
    Unbinder unbinder;
    private boolean isViewValid;

    @Override
    public boolean isViewValid() {
        return isViewValid;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        isViewValid = true;
    }

    protected abstract T createPresenter();

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.context = getContext();
        presenter = createPresenter();
        if (mLoadingDialog == null) {
            mLoadingDialog = new AlertDialog.Builder(getActivity())
                    .setView(R.layout.view_loading_wait)
                    .setCancelable(false)
                    .create();
        }
    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(setContentResource(), container, false);
        unbinder = ButterKnife.bind(this, view);
        EventBus.getDefault().register(this);
        initView();
        return view;
    }


    @Override
    public Context getAppContext() {
        return getContext() != null ? getContext().getApplicationContext() : null;
    }

    @Override
    public Context getCurrentContext() {
        return getContext();
    }

    public void showLoading(boolean isShow) {
        if (isShow) {
            mLoadingDialog.show();
        } else {
            mLoadingDialog.dismiss();
        }
    }

    //有分割线三个参数为1
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

    public void initToolbar(Toolbar toolbar, String title) {
        toolbar.setTitle(title);
        toolbar.setTitleTextColor(ContextCompat.getColor(context, R.color.colorTitle));
        toolbar.setBackgroundColor(ContextCompat.getColor(context, R.color.colorPrimary));
    }

    @Override
    public void finish() {
        getActivity().finish();
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void showDialog(LoadingDialogEvent event){
        showLoading(event.isShow());
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if (EventBus.getDefault().isRegistered(this)){
            EventBus.getDefault().unregister(this);
        }
        isViewValid = false;
        unbinder.unbind();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        isViewValid = false;
        presenter.detachView();
    }

    @Override
    public String getResString(int resId) {
        if (getContext() == null) {
            return "";
        }
        return getContext().getResources().getString(resId);
    }
}
