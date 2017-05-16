package com.example.q.pocketmusic.module.home.seek;

import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.q.pocketmusic.R;
import com.example.q.pocketmusic.module.common.BaseFragment;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by 鹏君 on 2017/5/16.
 */

public class HomeSeekFragment extends BaseFragment<HomeSeekPresenter.IView, HomeSeekPresenter> implements HomeSeekPresenter.IView {
    @BindView(R.id.publish_iv)
    ImageView publishIv;
    @BindView(R.id.ask_list_iv)
    TextView askListIv;
    @BindView(R.id.share_list_iv)
    TextView shareListIv;
    @BindView(R.id.search_iv)
    ImageView searchIv;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.seek_content)
    FrameLayout seekContent;

    @Override
    protected HomeSeekPresenter createPresenter() {
        return new HomeSeekPresenter(this);
    }


    @Override
    public void finish() {
        getActivity().finish();
    }

    @Override
    public int setContentResource() {
        return R.layout.fragment_home_seek;
    }

    @Override
    public void initView() {
        presenter.setFragmentManager(getChildFragmentManager());
        presenter.clickAsk();
    }

    @Override
    public void onSelectAsk() {
        askListIv.setBackgroundResource(R.drawable.shape_toolbar_tab_pressed);
        askListIv.setTextColor(getResources().getColor(R.color.colorPrimary));
        shareListIv.setBackgroundResource(R.drawable.shape_toolbar_tab_normal);
        shareListIv.setTextColor(getResources().getColor(R.color.white));
    }


    @Override
    public void onSelectShare() {
        shareListIv.setBackgroundResource(R.drawable.shape_toolbar_tab_pressed);
        shareListIv.setTextColor(getResources().getColor(R.color.colorPrimary));
        askListIv.setBackgroundResource(R.drawable.shape_toolbar_tab_normal);
        askListIv.setTextColor(getResources().getColor(R.color.white));
    }

    @OnClick({R.id.publish_iv, R.id.ask_list_iv, R.id.share_list_iv, R.id.search_iv})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.publish_iv:
                presenter.enterAskSongActivity();
                break;
            case R.id.ask_list_iv:
                presenter.clickAsk();
                break;
            case R.id.share_list_iv:
                presenter.clickShare();
                break;
            case R.id.search_iv:
                presenter.enterSearchMainActivity();
                break;
        }
    }
}
