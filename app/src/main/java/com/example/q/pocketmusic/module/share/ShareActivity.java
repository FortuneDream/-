package com.example.q.pocketmusic.module.share;

import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.q.pocketmusic.R;
import com.example.q.pocketmusic.model.bean.local.LocalSong;
import com.example.q.pocketmusic.module.common.AuthActivity;
import com.example.q.pocketmusic.view.widget.view.TextEdit;
import com.jude.easyrecyclerview.EasyRecyclerView;
import com.jude.easyrecyclerview.adapter.RecyclerArrayAdapter;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ShareActivity extends AuthActivity implements SharePresenter.IView, RecyclerArrayAdapter.OnItemClickListener {

    public static final String LOCAL_SONG = "LOCAL_SONG";
    @BindView(R.id.name_tet)
    TextEdit nameTet;
    @BindView(R.id.content_tet)
    TextEdit contentTet;
    @BindView(R.id.pic_number_tv)
    TextView picNumberTv;
    @BindView(R.id.add_pic_iv)
    ImageView addPicIv;
    @BindView(R.id.recycler)
    EasyRecyclerView recycler;
    @BindView(R.id.upload_txt)
    TextView uploadTxt;
    @BindView(R.id.activity_upload)
    LinearLayout activityUpload;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.app_bar)
    AppBarLayout appBar;
    private SharePresenter presenter;
    private SmallPicAdapter adapter;

    @Override
    public int setContentResource() {
        return R.layout.activity_share;
    }



    @Override
    public void initUserView() {
        adapter = new SmallPicAdapter(this);
        adapter.setOnItemClickListener(this);

        presenter = new SharePresenter( this, user);
        initToolbar(toolbar, "上传曲谱");
        LocalSong localSong = (LocalSong) getIntent().getSerializableExtra(LOCAL_SONG);
        presenter.getPicAndName(localSong);
        recycler.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        recycler.setAdapter(adapter);
    }




    @OnClick({R.id.add_pic_iv, R.id.upload_txt})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.add_pic_iv:
                presenter.openPicture();
                break;
            case R.id.upload_txt:
                String name = nameTet.getInputString();
                String author = presenter.getUser().getNickName();
                String content = contentTet.getInputString();
                presenter.upLoad(name, author, content);
                break;
        }
    }


    @Override
    public void setSelectPicResult(int mNumberPic, String[] filePaths, String name) {
        if (name != null) {
            nameTet.setInputString(name);
        }
        adapter.clear();
        picNumberTv.setText("目前已添加 " + mNumberPic + " 张");
        adapter.addAll(filePaths);
    }


    //查看图片
    @Override
    public void onItemClick(int position) {
        presenter.checkPic(adapter.getItem(position));
    }

    @Override
    public void showRefreshing(boolean isShow) {
        recycler.setRefreshing(isShow);
    }
}
