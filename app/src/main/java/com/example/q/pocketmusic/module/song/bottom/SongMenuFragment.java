package com.example.q.pocketmusic.module.song.bottom;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.example.q.pocketmusic.R;
import com.example.q.pocketmusic.config.Constant;
import com.example.q.pocketmusic.model.bean.Song;
import com.example.q.pocketmusic.module.common.BaseFragment;
import com.example.q.pocketmusic.module.home.HomeActivity;
import com.example.q.pocketmusic.module.song.SongActivity;
import com.example.q.pocketmusic.util.MyToast;
import com.example.q.pocketmusic.view.dialog.EditDialog;
import com.example.q.pocketmusic.view.widget.net.ConfettiUtil;
import com.example.q.pocketmusic.view.widget.net.SnackBarUtil;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * Created by 鹏君 on 2017/5/31.
 */

public class SongMenuFragment extends BaseFragment<SongMenuPresenter.IView, SongMenuPresenter>
        implements SongMenuPresenter.IView {
    @BindView(R.id.download_iv)
    ImageView downloadIv;
    @BindView(R.id.agree_iv)
    ImageView agreeIv;
    @BindView(R.id.collection_iv)
    ImageView collectionIv;
    @BindView(R.id.share_iv)
    ImageView shareIv;
    private static final String PARAM_Intent = "intent";
    @BindView(R.id.content_ll)
    LinearLayout contentLl;
    private EditDialog editDialog;//编辑框


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        presenter.setIntent((Intent) getArguments().getParcelable(PARAM_Intent));
    }

    public static final SongMenuFragment newInstance(Intent intent) {
        SongMenuFragment fragment = new SongMenuFragment();
        Bundle bundle = new Bundle();
        bundle.putParcelable(PARAM_Intent, intent);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public int setContentResource() {
        return R.layout.fragment_song_menu;
    }

    @Override
    public void initView() {
        showBottomMenu();//显示不同的底部按钮
        presenter.init();
    }

    private void showBottomMenu() {
        switch (presenter.getShowMenuFlag()) {
            case Constant.SHOW_ALL_MENU://下载，收藏，点赞，分享
                break;
            case Constant.SHOW_COLLECTION_MENU://下载和收藏，分享
                agreeIv.setVisibility(View.GONE);
                break;
            case Constant.SHOW_ONLY_DOWNLOAD://下载，分享
                agreeIv.setVisibility(View.GONE);
                collectionIv.setVisibility(View.GONE);
                break;
        }
    }

    @Override
    protected SongMenuPresenter createPresenter() {
        return new SongMenuPresenter(this);
    }

    @OnClick({R.id.download_iv, R.id.agree_iv, R.id.collection_iv, R.id.share_iv})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.download_iv:
                setDownloadDialog();
                break;
            case R.id.agree_iv:
                presenter.agree();
                view.setEnabled(false);//点赞之后就不可再次点击
                break;
            case R.id.collection_iv:
                presenter.addCollection();
                break;
            case R.id.share_iv:
                presenter.share();
                break;
        }
    }

    //设置下载弹窗框
    private void setDownloadDialog() {
        //编辑框
        editDialog = new EditDialog.Builder(getActivity())
                .setEditStr(presenter.getSong().getName())
                .setListener(new EditDialog.Builder.OnSelectedListener() {
                    @Override
                    public void onSelectedOk(String str) {
                        MyToast.showToast(getCurrentContext(), "后台下载中~");
                        presenter.download(str);
                    }

                    @Override
                    public void onSelectedCancel() {
                        editDialog.dismiss();
                    }
                })
                .create();
        editDialog.show();
    }

    //下载结果
    @Override
    public void downloadResult(Integer result, String info) {
        if (result.equals(Constant.FAIL)) {
            MyToast.showToast(getCurrentContext(), info);
        } else {
            //撒花
            if (getActivity() == null) {
                return;
            }
            View view = getActivity().getWindow().getDecorView();
            ConfettiUtil.getCommonConfetti((ViewGroup) view);
            //返回主页
            SnackBarUtil.IndefiniteSnackbar(view, "下载成功\\(^o^)/，是否立即查看", 4000, R.color.black, SnackBarUtil.green).setAction("是", new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(getCurrentContext(), HomeActivity.class);
                    intent.setAction(HomeActivity.ACTION_RETURN_HOME);
                    startActivity(intent);
                }
            }).setActionTextColor(ContextCompat.getColor(getCurrentContext(), R.color.white)).show();
        }
    }

    @Override
    public void dismissEditDialog() {
        editDialog.dismiss();
    }

}
