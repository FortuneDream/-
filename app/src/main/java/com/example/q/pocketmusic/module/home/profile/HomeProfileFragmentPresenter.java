package com.example.q.pocketmusic.module.home.profile;

import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;

import com.example.q.pocketmusic.R;
import com.example.q.pocketmusic.callback.ToastUpdateListener;
import com.example.q.pocketmusic.config.constant.CoinConstant;
import com.example.q.pocketmusic.module.common.BaseActivity;
import com.example.q.pocketmusic.module.common.BaseFragment;
import com.example.q.pocketmusic.module.common.BasePresenter;
import com.example.q.pocketmusic.module.common.IBaseView;
import com.example.q.pocketmusic.module.home.profile.gift.GiftActivity;
import com.example.q.pocketmusic.module.home.profile.interest.UserInterestActivity;
import com.example.q.pocketmusic.util.LocalPhotoAlbumUtil;
import com.example.q.pocketmusic.util.UserUtil;
import com.example.q.pocketmusic.util.common.IntentUtil;
import com.example.q.pocketmusic.util.common.ToastUtil;

import java.io.File;
import java.text.ParseException;
import java.util.Calendar;
import java.util.Date;

import cn.bmob.v3.datatype.BmobFile;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.UploadFileListener;

/**
 * Created by 鹏君 on 2017/1/26.
 */

public class HomeProfileFragmentPresenter extends BasePresenter<HomeProfileFragmentPresenter.IView> {

    public HomeProfileFragmentPresenter(IView fragment) {
        super(fragment);
    }

    //选择头像
    public void setHeadIv() {
        new LocalPhotoAlbumUtil().getSingleLocalPhoto(mView, new LocalPhotoAlbumUtil.OnLoadSingleResult() {
            @Override
            public void onSinglePath(final String picPath) {
                //图片上传至Bmob
                final BmobFile bmobFile = new BmobFile(new File(picPath));
                bmobFile.upload(new UploadFileListener() {
                    @Override
                    public void done(BmobException e) {
                        if (e != null) {
                            mView.showLoading(false);
                            ToastUtil.showToast(mView.getResString(R.string.send_error) + e.getMessage());
                            return;
                        }
                        //修改用户表的headIv属性
                        UserUtil.user.setHeadImg(bmobFile.getFileUrl());
                        UserUtil.user.update(new ToastUpdateListener(mView) {
                            @Override
                            public void onSuccess() {
                                mView.showLoading(false);
                                mView.setHeadIvResult(picPath);
                            }
                        });

                    }
                });
            }
        });
    }


    //签到
    public void addCoin(final int coin) {
        UserUtil.increment(coin, new ToastUpdateListener() {
            @Override
            public void onSuccess() {
                UserUtil.user.setLastSignInDate(dateFormat.format(new Date()));//设置最新签到时间
                UserUtil.user.update(new ToastUpdateListener() {
                    @Override
                    public void onSuccess() {
                        ToastUtil.showToast(mView.getResString(R.string.add_coin) + coin);
                    }
                });
            }
        });
    }

    //检测是否已经签到
    public boolean isSignIn() {
        if (UserUtil.checkLocalUser((BaseFragment) mView) && UserUtil.user.getLastSignInDate() == null) {//之前没有这个列,可以签到
            return false;
        } else {
            String lastSignIn = UserUtil.user.getLastSignInDate();
            try {
                Date last = dateFormat.parse(lastSignIn);
                Date now = new Date();
                Calendar cd = Calendar.getInstance();
                cd.setTime(last);
                int lastDay = cd.get(Calendar.DAY_OF_YEAR);
                int lastYear = cd.get(Calendar.YEAR);
                cd.setTime(now);
                int nowDay = cd.get(Calendar.DAY_OF_YEAR);
                int nowYear = cd.get(Calendar.YEAR);
                if (nowYear > lastYear) {
                    return false;//没有签到
                } else {
                    if (nowDay > lastDay) {
                        return false;//没有签到
                    } else {
                        return true;//已经签到
                    }
                }
            } catch (ParseException e) {
                e.printStackTrace();
                return true;
            }
        }
    }

    //检测是否已经签到
    public void SignIn() {
        if (UserUtil.user.getLastSignInDate() == null) {//之前没有这个列
            final int coin = CoinConstant.ADD_COIN_SIGN_DEFAULT;
            UserUtil.user.setLastSignInDate(dateFormat.format(new Date()));//设置当前时间为最后时间
            UserUtil.increment(coin, new ToastUpdateListener() {
                @Override
                public void onSuccess() {
                    ToastUtil.showToast(mView.getResString(R.string.add_coin) + coin);
                }
            });//第一次都加5
        } else {
            mView.alertSignInDialog();
        }
    }

    //分享apk
    public void shareApp() {
        IntentUtil.shareText(mView.getCurrentContext(), "推荐一款app:" + "<口袋乐谱>" + "---官网地址：" + "http://pocketmusic.bmob.site/");
    }


    //App商店
    public void grade() {
        IntentUtil.enterAppStore(mView.getCurrentContext());
    }


    public void setSignature(final String signature) {
        UserUtil.user.setSignature(signature);
        UserUtil.user.update(new ToastUpdateListener() {
            @Override
            public void onSuccess() {
                ToastUtil.showToast("已修改签名~");
                mView.setSignature(signature);

            }
        });
    }

    //设置用户属于哪个版本
    public void setUserBelongToVersion() {
        try {
            PackageManager pm = mView.getCurrentContext().getPackageManager();
            PackageInfo pi = pm.getPackageInfo(mView.getCurrentContext().getPackageName(), PackageManager.GET_ACTIVITIES);
            UserUtil.user.setVersion(pi.versionName);
            UserUtil.user.update(new ToastUpdateListener() {
                @Override
                public void onSuccess() {

                }
            });
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
    }

    //修改昵称
    public void setNickName(final String nickName) {
        boolean isEnough = UserUtil.checkUserContribution((BaseActivity) mView.getCurrentContext(), CoinConstant.REDUCE_COIN_CHANG_NICK_NAME);
        if (isEnough) {
            UserUtil.user.setNickName(nickName);
            UserUtil.user.update(new ToastUpdateListener() {
                @Override
                public void onSuccess() {
                    UserUtil.increment(-CoinConstant.REDUCE_COIN_CHANG_NICK_NAME, new ToastUpdateListener() {
                        @Override
                        public void onSuccess() {
                            ToastUtil.showToast(mView.getResString(R.string.reduce_coin) + CoinConstant.REDUCE_COIN_CHANG_NICK_NAME);
                            mView.setNickName(nickName);
                        }
                    });
                }
            });
        } else {
            ToastUtil.showToast(mView.getResString(R.string.coin_not_enough));
        }
    }


    public void enterGiftActivity() {
        mView.getCurrentContext().startActivity(new Intent(mView.getCurrentContext(), GiftActivity.class));
    }

    public void enterInterestActivity() {
        mView.getCurrentContext().startActivity(new Intent(mView.getCurrentContext(), UserInterestActivity.class));
    }


    public interface IView extends IBaseView {

        void setHeadIvResult(String photoPath);

        void alertSignInDialog();

        void setSignature(String signature);

        void setNickName(String nickName);
    }
}
