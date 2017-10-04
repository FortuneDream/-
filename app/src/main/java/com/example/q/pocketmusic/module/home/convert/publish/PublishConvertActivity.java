package com.example.q.pocketmusic.module.home.convert.publish;


import android.content.DialogInterface;
import android.support.design.widget.AppBarLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.q.pocketmusic.R;
import com.example.q.pocketmusic.module.common.AuthActivity;
import com.example.q.pocketmusic.module.share.SmallPicAdapter;
import com.example.q.pocketmusic.view.dialog.CoinDialogBuilder;
import com.example.q.pocketmusic.view.widget.view.TextEdit;
import com.jude.easyrecyclerview.EasyRecyclerView;
import com.jude.easyrecyclerview.adapter.RecyclerArrayAdapter;

import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;


public class PublishConvertActivity extends AuthActivity<PublishConvertActivityPresenter.IView, PublishConvertActivityPresenter>
        implements PublishConvertActivityPresenter.IView, RecyclerArrayAdapter.OnItemClickListener {

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.app_bar)
    AppBarLayout appBar;
    @BindView(R.id.title_tet)
    TextEdit titleTet;
    @BindView(R.id.add_coin_iv)
    ImageView addCoinIv;
    @BindView(R.id.extra_coin_tv)
    TextView extraCoinTv;
    @BindView(R.id.reduce_coin_iv)
    ImageView reduceCoinIv;
    @BindView(R.id.pic_number_tv)
    TextView picNumberTv;
    @BindView(R.id.add_pic_iv)
    ImageView addPicIv;
    @BindView(R.id.recycler)
    EasyRecyclerView recycler;
    @BindView(R.id.ok_txt)
    TextView okTxt;
    private SmallPicAdapter adapter;


    @Override
    public int setContentResource() {
        return R.layout.activity_publish_convert;
    }

    @Override
    public void initUserView() {
        adapter = new SmallPicAdapter(this);
        adapter.setOnItemClickListener(this);
        initToolbar(toolbar, "发布转谱");
        recycler.setLayoutManager(new LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false));
        recycler.setAdapter(adapter);
    }

    @Override
    public void showSmallPic(List<String> imgUrls) {
        adapter.clear();
        adapter.addAll(imgUrls);
        picNumberTv.setText("目前已添加 " + imgUrls.size() + " 张");
    }

    @Override
    public void returnActivity() {
        setResult(PublishConvertActivity.RESULT_OK);
        finish();
    }

    @Override
    public void changeCoin(int coin) {
        extraCoinTv.setText(String.valueOf(coin));
    }

    @Override
    public void alertCoinDialog(int coin, final String name) {
        new CoinDialogBuilder(getCurrentContext(), coin)
                .setPositiveButton(new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        presenter.uploadConvertPic(name);//上传
                    }
                })
                .show();
    }

    @OnClick({R.id.add_coin_iv, R.id.reduce_coin_iv, R.id.add_pic_iv, R.id.ok_txt})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.add_coin_iv:
                presenter.addCoin();
                break;
            case R.id.reduce_coin_iv:
                presenter.reduceCoin();
                break;
            case R.id.add_pic_iv:
                presenter.openPicture();
                break;
            case R.id.ok_txt:
                String name = titleTet.getInputString();
                presenter.checkPublishConvert(name);
                break;
        }
    }
    @Override
    public void onItemClick(int position) {
        presenter.checkPic(adapter.getItem(position));
    }


    @Override
    protected PublishConvertActivityPresenter createPresenter() {
        return new PublishConvertActivityPresenter(this);
    }
}