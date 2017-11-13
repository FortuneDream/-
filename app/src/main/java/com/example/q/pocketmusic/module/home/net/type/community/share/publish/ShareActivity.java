package com.example.q.pocketmusic.module.home.net.type.community.share.publish;

import android.content.DialogInterface;
import android.support.design.widget.AppBarLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.q.pocketmusic.R;
import com.example.q.pocketmusic.data.bean.local.LocalSong;
import com.example.q.pocketmusic.module.common.AuthActivity;
import com.example.q.pocketmusic.util.InstrumentFlagUtil;
import com.example.q.pocketmusic.view.widget.view.TextEdit;
import com.jude.easyrecyclerview.EasyRecyclerView;
import com.jude.easyrecyclerview.adapter.RecyclerArrayAdapter;

import butterknife.BindView;
import butterknife.OnClick;

//分享具体曲谱
public class ShareActivity extends AuthActivity<SharePresenter.IView, SharePresenter>
        implements SharePresenter.IView, RecyclerArrayAdapter.OnItemClickListener {
    public static final String PARAM_LOCAL_SONG = "param_1";
    public static final String PARAM_TYPE_ID = "param_2";
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.app_bar)
    AppBarLayout appBar;
    @BindView(R.id.name_tet)
    TextEdit nameTet;
    @BindView(R.id.content_tet)
    TextEdit contentTet;
    @BindView(R.id.add_pic_tv)
    TextView addPicTv;
    @BindView(R.id.recycler)
    EasyRecyclerView recycler;
    @BindView(R.id.upload_txt)
    TextView uploadTxt;
    @BindView(R.id.activity_upload)
    LinearLayout activityUpload;
    private SmallPicAdapter adapter;


    @Override
    public int setContentResource() {
        return R.layout.activity_share;
    }


    @Override
    public void initUserView() {
        adapter = new SmallPicAdapter(this);
        adapter.setOnItemClickListener(this);
        initToolbar(toolbar, "上传曲谱");
        LocalSong localSong = (LocalSong) getIntent().getSerializableExtra(PARAM_LOCAL_SONG);
        int typeId = getIntent().getIntExtra(PARAM_TYPE_ID, 0);
        presenter.setTypeId(typeId);
        presenter.getPicAndName(localSong);
        recycler.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        recycler.setAdapter(adapter);
    }


    @OnClick({R.id.add_pic_tv, R.id.upload_txt})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.add_pic_tv:
                presenter.openPicture();
                break;
            case R.id.upload_txt:
                String name = nameTet.getInputString();
                String content = contentTet.getInputString();
                presenter.upLoad(name, content);
                break;
        }
    }

    @Override
    public void alertSelectCommunityDialog(final String name, final String content) {
        new AlertDialog.Builder(getCurrentContext())
                .setTitle("选择圈子")
                .setSingleChoiceItems(InstrumentFlagUtil.typeNames, 0, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
//                        LogUtils.e("which:"+which);
                        dialog.dismiss();
                        presenter.checkHasSong(name, content,which);
                    }
                })
                .show();
    }


    @Override
    public void setSelectPicResult(int mNumberPic, String[] filePaths, String name) {
        if (name != null) {
            nameTet.setInputString(name);
        }
        adapter.clear();
        addPicTv.setText("目前已添加 " + mNumberPic + " 张");
        adapter.addAll(filePaths);
    }


    //查看图片
    @Override
    public void onItemClick(int position) {
        presenter.checkPic(adapter.getItem(position));
    }


    @Override
    protected SharePresenter createPresenter() {
        return new SharePresenter(this);
    }

}
