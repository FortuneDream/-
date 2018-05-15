package com.example.q.pocketmusic.module.song.bottom;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.AppCompatImageView;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.LinearLayout;

import com.dell.fortune.tools.dialog.DialogEditSureCancel;
import com.dell.fortune.tools.toast.ToastUtil;
import com.example.q.pocketmusic.R;
import com.example.q.pocketmusic.config.constant.CoinConstant;
import com.example.q.pocketmusic.config.constant.Constant;
import com.example.q.pocketmusic.module.common.BaseFragment;
import com.example.q.pocketmusic.module.home.HomeActivity;
import com.example.q.pocketmusic.view.dialog.CoinDialogBuilder;
import com.example.q.pocketmusic.view.widget.net.ConfettiUtil;
import com.example.q.pocketmusic.view.widget.net.SnackBarUtil;

import butterknife.BindView;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * Created by 鹏君 on 2017/5/31.
 */

public class SongMenuFragment extends BaseFragment<SongMenuPresenter.IView, SongMenuPresenter>
        implements SongMenuPresenter.IView {
    @BindView(R.id.agree_iv)
    AppCompatImageView agreeIv;
    @BindView(R.id.agree_ll)
    LinearLayout agreeLl;
    @BindView(R.id.download_iv)
    AppCompatImageView downloadIv;
    @BindView(R.id.download_ll)
    LinearLayout downloadLl;
    @BindView(R.id.collection_iv)
    AppCompatImageView collectionIv;
    @BindView(R.id.collection_ll)
    LinearLayout collectionLl;
    @BindView(R.id.share_iv)
    AppCompatImageView shareIv;
    @BindView(R.id.share_ll)
    LinearLayout shareLl;
    @BindView(R.id.content_ll)
    LinearLayout contentLl;
    Unbinder unbinder;
    private DialogEditSureCancel editSureCancel;
    private static final String PARAM_Intent = "param_1";


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
            case Constant.MENU_DOWNLOAD_COLLECTION_AGREE_SHARE://下载，收藏，点赞，分享
                break;
            case Constant.MENU_DOWNLOAD_COLLECTION_SHARE://下载和收藏，分享
                agreeLl.setVisibility(View.GONE);
                break;
            case Constant.MENU_DOWNLOAD_SHARE://下载，分享
                agreeLl.setVisibility(View.GONE);
                collectionLl.setVisibility(View.GONE);
                break;
        }
    }

    @Override
    protected SongMenuPresenter createPresenter() {
        return new SongMenuPresenter(this);
    }

    @OnClick({R.id.download_ll, R.id.agree_ll, R.id.collection_ll, R.id.share_ll})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.download_ll:
                alertCheckDownloadDialog(CoinConstant.REDUCE_COIN_DOWNLOAD);
                break;
            case R.id.agree_ll:
                agreeLl.setEnabled(false);//点赞之后就不可再次点击
                agreeIv.setImageResource(R.drawable.ic_vec_song_bottom_agree_press);
                presenter.agree();
                break;
            case R.id.collection_ll:
                alertCheckCollectionDialog(CoinConstant.REDUCE_COIN_COLLECTION);
                break;
            case R.id.share_ll:
                presenter.share();
                break;
        }
    }


    public void alertCheckCollectionDialog(int coin) {
        new CoinDialogBuilder(getCurrentContext(), coin)
                .setPositiveButton(new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        collectionLl.setEnabled(false);//点击收藏之后不可再次点击
                        collectionIv.setImageResource(R.drawable.ic_vec_song_bottom_collection_press);//改变状态
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


    @Override
    public void addCollectionResult() {
        if (getActivity() != null) {
            Activity activity = getActivity();
            if (activity.getWindow() != null) {
                Window window = activity.getWindow();
                if (window.getDecorView() != null) {
                    View view = window.getDecorView();
                    if (view instanceof ViewGroup) {
                        ConfettiUtil.getCommonConfetti((ViewGroup) view);
                    }
                }
            }
        }
    }

    public void alertCheckDownloadDialog(int coin) {
        new CoinDialogBuilder(getCurrentContext(), coin)
                .setPositiveButton(new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        alertDownloadDialog();
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

    private void alertDownloadDialog() {
        editSureCancel = new DialogEditSureCancel(context);
        editSureCancel.getTvTitle().setText("保存曲谱名");
        editSureCancel.getEditText().setText(presenter.getSong().getName());
        editSureCancel.getTvSure().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = editSureCancel.getEditText().getText().toString();
                if (TextUtils.isEmpty(name)) {
//                    Toasts.error(context, "名字不能为空哦~", Toast.LENGTH_SHORT,true).show();
                    return;
                }
                editSureCancel.dismiss();
                ToastUtil.showToast("后台下载中~");
                downloadLl.setEnabled(false);//下载键
                presenter.download(name);
            }
        });
        editSureCancel.getTvCancel().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editSureCancel.cancel();
            }
        });
        editSureCancel.show();
    }

    @Override
    public void dismissEditDialog() {
        editSureCancel.dismiss();
    }
}
