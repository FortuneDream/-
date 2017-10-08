package com.example.q.pocketmusic.module.home.profile;

import android.animation.ValueAnimator;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.q.pocketmusic.R;
import com.example.q.pocketmusic.config.Constant;
import com.example.q.pocketmusic.config.pic.DisplayStrategy;
import com.example.q.pocketmusic.module.common.AuthFragment;
import com.example.q.pocketmusic.util.UserUtil;
import com.example.q.pocketmusic.util.common.ToastUtil;
import com.example.q.pocketmusic.view.dialog.CoinDialogBuilder;
import com.example.q.pocketmusic.view.widget.view.GuaGuaKa;
import com.example.q.pocketmusic.view.widget.view.IcoTextItem;

import java.util.Random;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;


/**
 * Created by 鹏君 on 2017/1/26.
 */

public class HomeProfileFragment extends AuthFragment<HomeProfileFragmentPresenter.IView, HomeProfileFragmentPresenter>
        implements HomeProfileFragmentPresenter.IView {

    @BindView(R.id.setting_item)
    ImageView settingItem;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.head_iv)
    ImageView headIv;
    @BindView(R.id.user_signature_tv)
    TextView userSignatureTv;
    @BindView(R.id.sign_in_btn)
    Button signInBtn;
    @BindView(R.id.interest_item)
    LinearLayout interestItem;
    @BindView(R.id.post_item)
    LinearLayout postItem;
    @BindView(R.id.collection_item)
    LinearLayout collectionItem;
    @BindView(R.id.share_item)
    LinearLayout shareItem;
    @BindView(R.id.convert_item)
    LinearLayout convertItem;
    @BindView(R.id.coin_item)
    IcoTextItem coinItem;
    @BindView(R.id.gift_item)
    IcoTextItem giftItem;
    @BindView(R.id.support_me_item)
    IcoTextItem supportMeItem;
    @BindView(R.id.grade_item)
    IcoTextItem gradeItem;
    @BindView(R.id.share_app_item)
    IcoTextItem shareAppItem;
    @BindView(R.id.content_ll)
    LinearLayout contentLl;
    Unbinder unbinder;
    private AlertDialog signInDialog;


    @Override
    public int setContentResource() {
        return R.layout.fragment_home_profile;
    }


    @Override
    public void onResume() {
        super.onResume();
        initProfileView();
    }

    private void initProfileView() {
        if (UserUtil.user != null) {
            //设置昵称
            initToolbar(toolbar, UserUtil.user.getNickName());
            //设置头像
            new DisplayStrategy().displayCircle(context, UserUtil.user.getHeadImg(), headIv);
            //设置签名
            if (UserUtil.user.getSignature() == null) {
                userSignatureTv.setText("这个人没有签名~");
            } else {
                userSignatureTv.setText(UserUtil.user.getSignature());
            }
            checkIsSign();
            //点击标题栏修改NickName
            toolbar.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    alertNickNameDialog();
                }
            });
            presenter.setUserBelongToVersion();//用户属于哪个版本
        }
    }

    //修改昵称
    private void alertNickNameDialog() {
        final EditText inputServer = new EditText(getCurrentContext());
        AlertDialog.Builder builder = new AlertDialog.Builder(getCurrentContext());
        builder.setTitle("修改昵称").setIcon(R.drawable.ico_signature).setView(inputServer)
                .setNegativeButton("算了", null);
        builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {

            public void onClick(DialogInterface dialog, int which) {
                if (TextUtils.isEmpty(inputServer.getText().toString())) {
                    ToastUtil.showToast("不能为空");
                    return;
                }
                new CoinDialogBuilder(getCurrentContext(), Constant.REDUCE_CHANG_NICK_NAME)
                        .setPositiveButton(new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                String text = inputServer.getText().toString();
                                presenter.setNickName(text);
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
        });
        builder.show();
    }

    //检测是否已经签到
    private void checkIsSign() {
        boolean isSignIn = presenter.isSignIn();
        if (isSignIn) {
            signInBtn.setVisibility(View.GONE);
        } else {
            signInBtn.setVisibility(View.VISIBLE);
            startSignInAnimator();
        }
    }

    //执行可签到的动画,横向滑动
    private void startSignInAnimator() {
        ValueAnimator animator = ValueAnimator.ofFloat(0, 1);
        animator.setDuration(1500);
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                float percent = (float) animation.getAnimatedValue();
                signInBtn.setTranslationX(-100 * percent);
            }
        });
        animator.start();
    }


    @OnClick({R.id.head_iv, R.id.setting_item, R.id.grade_item,
            R.id.collection_item, R.id.coin_item, R.id.sign_in_btn,
            R.id.post_item, R.id.share_app_item, R.id.support_me_item, R.id.share_item,
            R.id.user_signature_tv, R.id.convert_item, R.id.gift_item})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.support_me_item:
                presenter.enterSupportActivity();
                break;
            case R.id.head_iv://设置头像
                presenter.setHeadIv();
                break;
            case R.id.grade_item://评分
                presenter.grade();
                break;
            case R.id.share_item://我的分享
                presenter.enterUserShareActivity();
                break;
            case R.id.post_item://用户求谱帖子
                presenter.enterUserPostActivity();
                break;
            case R.id.setting_item://设置界面
                presenter.enterSettingActivity();
                break;
            case R.id.collection_item://进入收藏列表界面
                presenter.enterCollectionActivity();
                break;
            case R.id.coin_item://进入ContributionActivity
                presenter.enterContributionActivity();
                break;
            case R.id.sign_in_btn://签到
                presenter.SignIn();
                break;
            case R.id.share_app_item://分享app
                presenter.shareApp();
                break;
            case R.id.user_signature_tv:
                alertSignatureDialog();//修改签名
                break;
            case R.id.convert_item:
                presenter.enterConvertListActivity();//转谱
                break;
            case R.id.gift_item:
                presenter.enterGiftActivity();//进入礼包中心
                break;
            case R.id.interest_item://进入关注列表
                presenter.enterInterestActivity();
                break;
        }
    }

    //设置签名dialog
    private void alertSignatureDialog() {
        final EditText inputServer = new EditText(getCurrentContext());
        AlertDialog.Builder builder = new AlertDialog.Builder(getCurrentContext());
        builder.setTitle("设置签名").setIcon(R.drawable.ico_signature).setView(inputServer)
                .setNegativeButton("算了", null);
        builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {

            public void onClick(DialogInterface dialog, int which) {
                String text = inputServer.getText().toString();
                presenter.setSignature(text);
            }
        });
        builder.show();
    }

    //签到Dialog
    public void alertSignInDialog() {
        //签到Btn消失
        signInBtn.setVisibility(View.GONE);
        //随机签到
        Random random = new Random();
        final int reward = random.nextInt(8) + 1;//随机1--8点
        View view = View.inflate(getContext(), R.layout.dialog_sign_in, null);
        final TextView titleTv = (TextView) view.findViewById(R.id.title_tv);
        GuaGuaKa guaGuaKa = (GuaGuaKa) view.findViewById(R.id.gua_gua_ka);
        guaGuaKa.setAwardText(String.valueOf(reward) + " 枚硬币");
        final Button getRewardBtn = (Button) view.findViewById(R.id.get_reward_btn);
        signInDialog = new AlertDialog.Builder(getContext())
                .setCancelable(false)
                .setView(view)
                .create();

        guaGuaKa.setOnCompleteListener(new GuaGuaKa.OnCompleteListener() {
            @Override
            public void onComplete() {
                titleTv.setText("(ฅ´ω`ฅ)");
                getRewardBtn.setVisibility(View.VISIBLE);
            }
        });
        getRewardBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                presenter.addCoin(reward);
                signInDialog.dismiss();
            }
        });
        signInDialog.show();
    }

    @Override
    public void setSignature(String signature) {
        userSignatureTv.setText(signature);
    }

    @Override
    public void setNickName(String nickName) {
        initToolbar(toolbar, UserUtil.user.getNickName());
    }


    @Override
    public void setHeadIvResult(String photoPath) {
        new DisplayStrategy().displayCircle(context, photoPath, headIv);
    }


    @Override
    public void finish() {
        getActivity().finish();
    }


    @Override
    protected HomeProfileFragmentPresenter createPresenter() {
        return new HomeProfileFragmentPresenter(this);
    }


}
