package com.example.q.pocketmusic.module.home.profile.user.other;

import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.q.pocketmusic.R;
import com.example.q.pocketmusic.config.pic.DisplayStrategy;
import com.example.q.pocketmusic.data.bean.MyUser;
import com.example.q.pocketmusic.module.common.AuthActivity;

import butterknife.BindView;
import butterknife.OnClick;

public class OtherProfileActivity extends AuthActivity<OtherProfilePresenter.IView, OtherProfilePresenter>
        implements OtherProfilePresenter.IView {
    @BindView(R.id.tab_layout)
    TabLayout tabLayout;
    @BindView(R.id.view_pager)
    ViewPager viewPager;
    @BindView(R.id.head_iv)
    ImageView headIv;
    @BindView(R.id.user_signature_tv)
    TextView userSignatureTv;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.interest_tv)
    TextView interestTv;
    private OtherAdapter adapter;
    public MyUser otherUser;
    public final static String PARAM_USER = "other_user";

    @Override
    protected OtherProfilePresenter createPresenter() {
        return new OtherProfilePresenter(this);
    }


    @Override
    public int setContentResource() {
        return R.layout.activity_other_profile;
    }

    @Override
    public void initUserView() {
        otherUser = (MyUser) getIntent().getSerializableExtra(PARAM_USER);
        initToolbar(toolbar, otherUser.getNickName());
        adapter = new OtherAdapter(getSupportFragmentManager());
        viewPager.setAdapter(adapter);
        tabLayout.setupWithViewPager(viewPager);
        userSignatureTv.setText(otherUser.getSignature());
        new DisplayStrategy().displayCircle(getCurrentContext(), otherUser.getHeadImg(), headIv);
    }


    @OnClick(R.id.interest_tv)
    public void onViewClicked() {
        presenter.interestOther(otherUser);
    }
}
