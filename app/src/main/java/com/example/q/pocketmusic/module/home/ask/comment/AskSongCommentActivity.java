package com.example.q.pocketmusic.module.home.ask.comment;

import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.q.pocketmusic.R;
import com.example.q.pocketmusic.model.bean.Song;
import com.example.q.pocketmusic.model.bean.ask.AskSongComment;
import com.example.q.pocketmusic.model.bean.ask.AskSongPost;
import com.example.q.pocketmusic.module.common.AuthActivity;
import com.example.q.pocketmusic.util.MyToast;
import com.example.q.pocketmusic.view.dialog.PicDialog;
import com.jude.easyrecyclerview.EasyRecyclerView;
import com.jude.easyrecyclerview.adapter.RecyclerArrayAdapter;

import java.util.List;

import butterknife.BindView;

/**
 * Created by Cloud on 2016/11/14.
 */

public class AskSongCommentActivity extends AuthActivity<AskSongCommentPresenter.IView, AskSongCommentPresenter>
        implements AskSongCommentPresenter.IView, View.OnClickListener, RecyclerArrayAdapter.OnItemClickListener, SwipeRefreshLayout.OnRefreshListener {

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.add_pic)
    ImageView addPic;
    @BindView(R.id.user_input_edt)
    EditText userInputEdt;
    @BindView(R.id.send_comment_btn)
    Button sendCommentBtn;
    @BindView(R.id.input_ll)
    LinearLayout inputLl;
    @BindView(R.id.recycler)
    EasyRecyclerView recycler;
    @BindView(R.id.number_pic_tv)
    TextView numberPicTv;
    private AskSongCommentAdapter adapter;
    private PicDialog picDialog;
    public static final String PARAM_POST = "param_post";

    @Override
    public int setContentResource() {
        return R.layout.activity_ask_song_comment;
    }


    @Override
    public void initUserView() {
        //各种监听
        adapter = new AskSongCommentAdapter(AskSongCommentActivity.this);
        recycler.setRefreshListener(this);
        sendCommentBtn.setOnClickListener(this);
        addPic.setOnClickListener(this);
        adapter.setOnItemClickListener(this);
        //数据初始化
        final AskSongPost post = (AskSongPost) getIntent().getSerializableExtra(PARAM_POST);
        presenter.setPost(post);
        presenter.setUser(user);
        initToolbar(toolbar, presenter.getPost().getTitle());
        initRecyclerView(recycler, adapter);
        adapter.addHeader(new PostHeadView(context,
                presenter.getPost().getContent(),
                presenter.getPost().getUser().getNickName(),
                presenter.getPost().getTitle(),
                presenter.getPost().getUser().getHeadImg(),
                presenter.getPost().getCreatedAt()));
        onRefresh();
    }


    //加载评论列表
    @Override
    public void setCommentList(List<AskSongComment> list) {
        adapter.addAll(list);
    }

    //发送评论返回
    @Override
    public void sendCommentResult(String s, AskSongComment askSongComment) {
        adapter.add(askSongComment);
        numberPicTv.setText(0 + " 张");
    }


    //添加图片返回,最好有个列表可以展示
    @Override
    public void addPicResult(List<String> picUrls) {
        MyToast.showToast(context, "已经添加" + picUrls.size());
        numberPicTv.setText(picUrls.size() + " 张");
    }

    //发送评论后，输入框为空
    @Override
    public void setCommentInput(String s) {
        userInputEdt.setText(s);
    }

    @Override
    public void showPicDialog(final Song song, final AskSongComment askSongComment) {
        picDialog = new PicDialog.Builder(context)
                .setList(song.getIvUrl())
                .setOnSelectListener(new PicDialog.Builder.OnSelectListener() {
                    @Override
                    public void onSelectOk() {
                        //进入SongActivity
                        presenter.enterSongActivity(song, askSongComment);
                    }
                })
                .create();
        picDialog.show();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.send_comment_btn://发送评论
                String comment = userInputEdt.getText().toString().trim();
                presenter.sendComment(comment);
                break;
            case R.id.add_pic://添加图片
                presenter.addPic();
                break;
        }
    }

    @Override
    public void onItemClick(int position) {
        presenter.alertPicDialog(adapter.getItem(position));  //弹出简略图
    }

    @Override
    public void onRefresh() {
        adapter.clear();
        presenter.getInitCommentList();
    }

    @Override
    public void showRefreshing(boolean isShow) {
        recycler.setRefreshing(isShow);
    }

    @Override
    protected AskSongCommentPresenter createPresenter() {
        return new AskSongCommentPresenter();
    }
}