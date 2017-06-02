package com.example.q.pocketmusic.module.home.seek;

import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;

import com.example.q.pocketmusic.R;
import com.example.q.pocketmusic.module.common.BaseFragment;
import com.example.q.pocketmusic.view.widget.view.TopTabView;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by 鹏君 on 2017/5/16.
 */

public class HomeSeekFragment extends BaseFragment<HomeSeekPresenter.IView, HomeSeekPresenter> implements HomeSeekPresenter.IView, TopTabView.TopTabListener {


    @BindView(R.id.publish_iv)
    ImageView publishIv;
    @BindView(R.id.top_tab_view)
    TopTabView topTabView;
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

    @OnClick({R.id.publish_iv,R.id.search_iv})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.publish_iv:
                presenter.enterAskSongActivity();
                break;
            case R.id.search_iv:
                presenter.enterSearchMainActivity();
                break;
        }
    }

    @Override
    public void setTopTabCheck(int position) {
        if (position==0){
            presenter.clickAsk();
        }else {
            presenter.clickShare();
        }
    }
}
