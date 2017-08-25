package com.example.q.pocketmusic.module.common;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.q.pocketmusic.R;
import com.example.q.pocketmusic.util.common.ConvertUtil;
import com.jude.easyrecyclerview.EasyRecyclerView;
import com.jude.easyrecyclerview.adapter.RecyclerArrayAdapter;
import com.jude.easyrecyclerview.decoration.DividerDecoration;

import butterknife.ButterKnife;

/**
 * Created by 鹏君 on 2017/1/16.
 */

public abstract class BaseFragment<V, T extends BasePresenter<V>> extends Fragment implements IBaseView {
    protected T presenter;
    public static AlertDialog mLoadingDialog;
    public Context context;
    public final String TAG = this.getClass().getName();


    protected abstract T createPresenter();

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.context = getContext();
        presenter = createPresenter();
        presenter.attachView((V) this);
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
        ButterKnife.bind(this, view);
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
        recyclerView.addItemDecoration(new DividerDecoration(ContextCompat.getColor(context, R.color.setting_divider), 1, dp, 1));
    }

    //无分割线
    public void initRecyclerView(EasyRecyclerView recyclerView, RecyclerArrayAdapter<?> adapter) {
        recyclerView.setLayoutManager(new LinearLayoutManager(context));
        recyclerView.setRefreshingColorResources(R.color.colorAccent);
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

    @Override
    public void onDestroy() {
        super.onDestroy();
        presenter.detachView();
    }
}
