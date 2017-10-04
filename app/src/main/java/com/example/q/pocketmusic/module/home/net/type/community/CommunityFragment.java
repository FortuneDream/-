package com.example.q.pocketmusic.module.home.net.type.community;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;

import com.example.q.pocketmusic.R;
import com.example.q.pocketmusic.module.common.BaseFragment;
import com.example.q.pocketmusic.module.home.net.type.SongTypeActivity;
import com.example.q.pocketmusic.view.widget.view.TopTabView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * Created by 鹏君 on 2017/5/16.
 */

public class CommunityFragment extends BaseFragment<CommunityPresenter.IView, CommunityPresenter> implements CommunityPresenter.IView, TopTabView.TopTabListener {

    @BindView(R.id.top_tab_view)
    TopTabView topTabView;
    @BindView(R.id.seek_content)
    FrameLayout seekContent;
    @BindView(R.id.publish_ask_iv)
    ImageView publishAskIv;
    Unbinder unbinder;
    private int typeId;


    @Override
    protected CommunityPresenter createPresenter() {
        return new CommunityPresenter(this);
    }


    @Override
    public void finish() {
        getActivity().finish();
    }

    @Override
    public int setContentResource() {
        return R.layout.fragment_community;
    }

    @Override
    public void initView() {
        presenter.setFragmentManager(getChildFragmentManager());
        presenter.initFragment(typeId);
        topTabView.setListener(this);
        topTabView.setCheck(0);
        presenter.clickAsk();
    }

    @Override
    public void onSelectAsk() {
        topTabView.setCheck(0);
    }


    @Override
    public void onSelectShare() {
        topTabView.setCheck(1);
    }

    @OnClick({R.id.publish_ask_iv})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.publish_ask_iv:
                presenter.enterPublishAskActivity();
                break;
        }
    }

    @Override
    public void setTopTabCheck(int position) {
        if (position == 0) {
            presenter.clickAsk();
        } else {
            presenter.clickShare();
        }
    }

    public static CommunityFragment getInstance(int typeId) {
        CommunityFragment fragment = new CommunityFragment();
        Bundle args = new Bundle();
        args.putInt(SongTypeActivity.PARAM_POSITION, typeId);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.typeId = getArguments().getInt(SongTypeActivity.PARAM_POSITION);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // TODO: inflate a fragment view
        View rootView = super.onCreateView(inflater, container, savedInstanceState);
        unbinder = ButterKnife.bind(this, rootView);
        return rootView;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}
