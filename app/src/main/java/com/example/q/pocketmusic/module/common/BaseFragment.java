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
import com.example.q.pocketmusic.util.ConvertUtil;
import com.jude.easyrecyclerview.EasyRecyclerView;
import com.jude.easyrecyclerview.adapter.RecyclerArrayAdapter;
import com.jude.easyrecyclerview.decoration.DividerDecoration;

import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by Cloud on 2017/1/16.
 */

public abstract class BaseFragment extends Fragment implements IBaseView {
    public static AlertDialog mLoadingDialog;
    public Context context;
    public final String TAG = this.getClass().getName();
    private Unbinder unbinder;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.context = getContext();
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
        initView();
        return view;
    }

    @Override
    public Context getAppContext() {
        return getContext().getApplicationContext();
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
    public void initRecyclerView(EasyRecyclerView recyclerView, RecyclerArrayAdapter<?> adapter, int dp1, boolean setEmpty) {
        initRecyclerView(recyclerView, adapter);
        int dp = ConvertUtil.Dp2Px(context, dp1);
        if (setEmpty) {
            recyclerView.setEmptyView(R.layout.view_not_found);
        }
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
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}
