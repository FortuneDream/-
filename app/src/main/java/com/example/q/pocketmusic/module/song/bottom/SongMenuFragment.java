package com.example.q.pocketmusic.module.song.bottom;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.AppCompatImageView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.example.q.pocketmusic.R;
import com.example.q.pocketmusic.config.Constant;
import com.example.q.pocketmusic.module.common.BaseFragment;
import com.example.q.pocketmusic.module.home.HomeActivity;
import com.example.q.pocketmusic.util.common.ToastUtil;
import com.example.q.pocketmusic.view.dialog.CoinDialogBuilder;
import com.example.q.pocketmusic.view.dialog.EditDialog;
import com.example.q.pocketmusic.view.widget.net.ConfettiUtil;
import com.example.q.pocketmusic.view.widget.net.SnackBarUtil;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by 鹏君 on 2017/5/31.
 */

public class SongMenuFragment extends BaseFragment<SongMenuPresenter.IView, SongMenuPresenter>
        implements SongMenuPresenter.IView {
    @BindView(R.id.content_ll)
    LinearLayout contentLl;
    @BindView(R.id.download_iv)
    AppCompatImageView downloadIv;
    @BindView(R.id.agree_iv)
    AppCompatImageView agreeIv;
    @BindView(R.id.collection_iv)
    AppCompatImageView collectionIv;
    @BindView(R.id.share_iv)
    AppCompatImageView shareIv;
    private EditDialog editDialog;//编辑框
    private static final String PARAM_Intent = "intent";


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        presenter.setIntent((Intent) getArguments().getParcelable(PARAM_Intent));
    }

    public static SongMenuFragment newInstance(Intent intent) {
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
                alertCheckDownloadDialog(view, Constant.REDUCE_DOWNLOAD);
                break;
            case R.id.agree_iv:
                view.setEnabled(false);//点赞之后就不可再次点击
                ((AppCompatImageView) view).setImageResource(R.drawable.ic_vec_song_bottom_agree_press);
                presenter.agree();
                break;
            case R.id.collection_iv:
                alertCheckCollectionDialog(view, Constant.REDUCE_CONTRIBUTION_COLLECTION);
                break;
            case R.id.share_iv:
                presenter.share();
                break;
        }
    }


    public void alertCheckCollectionDialog(final View view, int coin) {
        new CoinDialogBuilder(getCurrentContext(), coin)
                .setPositiveButton(new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        view.setEnabled(false);//点击收藏之后不可再次点击
                        ((AppCompatImageView) view).setImageResource(R.drawable.ic_vec_song_bottom_collection_press);//改变状态
                        presenter.addCollection();
                    }
                })
                .setNegativeButton(new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                })
                .show();
    }

    //下载结果
    @Override
    public void downloadResult(Integer result, String info) {
        if (result.equals(Constant.FAIL)) {
            ToastUtil.showToast(info);
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

    public void alertCheckDownloadDialog(final View view, int coin) {
        new CoinDialogBuilder(getCurrentContext(), coin)
                .setPositiveButton(new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        alertDownloadDialog(view);
                    }
                })
                .setNegativeButton(new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                })
                .show();
    }

    private void alertDownloadDialog(final View view) {
        editDialog = new EditDialog.Builder(getActivity())
                .setEditStr(presenter.getSong().getName())
                .setListener(new EditDialog.Builder.OnSelectedListener() {
                    @Override
                    public void onSelectedOk(String str) {
                        ToastUtil.showToast("后台下载中~");
                        view.setEnabled(false);//下载键
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

    @Override
    public void dismissEditDialog() {
        editDialog.dismiss();
    }

}
