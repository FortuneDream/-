package com.example.q.pocketmusic.module.home.local.lead;

import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.q.pocketmusic.R;
import com.example.q.pocketmusic.module.common.BaseActivity;
import com.example.q.pocketmusic.module.home.net.type.community.share.publish.SmallPicAdapter;
import com.example.q.pocketmusic.view.widget.view.TextEdit;
import com.google.android.material.appbar.AppBarLayout;
import com.jude.easyrecyclerview.EasyRecyclerView;
import com.jude.easyrecyclerview.adapter.RecyclerArrayAdapter;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

//本地导入
public class LeadSongActivity extends BaseActivity<LeadSongPresenter.IView, LeadSongPresenter>
        implements LeadSongPresenter.IView, RecyclerArrayAdapter.OnItemClickListener {
    public static int REQUEST_LEAD = 1001;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.app_bar)
    AppBarLayout appBar;
    @BindView(R.id.name_tet)
    TextEdit nameTet;
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
        return R.layout.activity_lead_song;
    }

    @Override
    public void initView() {
        adapter = new SmallPicAdapter(this);
        adapter.setOnItemClickListener(this);
        initToolbar(toolbar, getResString(R.string.title_lead_song));
        recycler.setLayoutManager(new LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false));
        recycler.setAdapter(adapter);
    }

    @Override
    public void showSmallPic(List<String> imgUrls) {
        adapter.clear();
        adapter.addAll(imgUrls);
        addPicTv.setText("目前已添加 " + imgUrls.size() + " 张");
    }

    @Override
    public void returnActivity() {
        setResult(LeadSongActivity.RESULT_OK);
        finish();
    }

    @OnClick({R.id.add_pic_tv, R.id.upload_txt})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.add_pic_tv:
                presenter.openPicture();
                break;
            case R.id.upload_txt:
                String name = nameTet.getInputString();
                presenter.leadSong(name);
                break;
        }
    }

    @Override
    public void onItemClick(int position) {
        presenter.checkPic(adapter.getItem(position));
    }

    @Override
    protected LeadSongPresenter createPresenter() {
        return new LeadSongPresenter(this);
    }
}
