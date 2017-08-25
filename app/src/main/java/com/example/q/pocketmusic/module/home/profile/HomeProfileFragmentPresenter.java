package com.example.q.pocketmusic.module.home.profile;

import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;

import com.example.q.pocketmusic.callback.ToastUpdateListener;
import com.example.q.pocketmusic.config.BmobConstant;
import com.example.q.pocketmusic.config.CommonString;
import com.example.q.pocketmusic.config.Constant;
import com.example.q.pocketmusic.model.bean.MyUser;
import com.example.q.pocketmusic.module.common.BasePresenter;
import com.example.q.pocketmusic.module.common.IBaseView;
import com.example.q.pocketmusic.module.home.profile.collection.UserCollectionActivity;
import com.example.q.pocketmusic.module.home.profile.contribution.ContributionActivity;
import com.example.q.pocketmusic.module.home.profile.post.UserPostActivity;
import com.example.q.pocketmusic.module.home.profile.setting.SettingActivity;
import com.example.q.pocketmusic.module.home.profile.share.UserShareActivity;
import com.example.q.pocketmusic.module.home.profile.support.SupportActivity;
import com.example.q.pocketmusic.util.common.IntentUtil;
import com.example.q.pocketmusic.util.common.ToastUtil;

import java.io.File;
import java.text.ParseException;
import java.util.Date;
import java.util.List;

import cn.bmob.v3.datatype.BmobFile;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.UploadFileListener;
import cn.finalteam.galleryfinal.FunctionConfig;
import cn.finalteam.galleryfinal.GalleryFinal;
import cn.finalteam.galleryfinal.model.PhotoInfo;

/**
 * Created by 鹏君 on 2017/1/26.
 */

public class HomeProfileFragmentPresenter extends BasePresenter<HomeProfileFragmentPresenter.IView> {
    private IView fragment;
    private MyUser user;
    private static boolean mShowSignInToast = true;

    public HomeProfileFragmentPresenter(IView fragment) {
        attachView(fragment);
        this.fragment = getIViewRef();
    }


    //选择头像
    public void setHeadIv() {
        final FunctionConfig config = new FunctionConfig.Builder()
                .setMutiSelectMaxSize(1)
                .build();
        GalleryFinal.openGallerySingle(2, config, new GalleryFinal.OnHanlderResultCallback() {
            @Override
            public void onHanlderSuccess(int reqeustCode, List<PhotoInfo> resultList) {
                fragment.showLoading(true);
                PhotoInfo photoInfo = resultList.get(0);
                //图片上传至Bmob
                final String picPath = photoInfo.getPhotoPath();
                final BmobFile bmobFile = new BmobFile(new File(picPath));
                bmobFile.upload(new UploadFileListener() {
                    @Override
                    public void done(BmobException e) {
                        if (e != null) {
                            fragment.showLoading(false);
                            ToastUtil.showToast(CommonString.STR_ERROR_INFO + e.getMessage());
                            return;
                        }
                        //修改用户表的headIv属性
                        user.setHeadImg(bmobFile.getFileUrl());
                        user.update(new ToastUpdateListener(fragment) {
                            @Override
                            public void onSuccess() {
                                fragment.showLoading(false);
                                fragment.setHeadIvResult(picPath);
                            }
                        });

                    }
                });
            }

            @Override
            public void onHanlderFailure(int requestCode, String errorMsg) {
                fragment.showLoading(false);
                ToastUtil.showToast(CommonString.STR_ERROR_INFO + errorMsg);
            }
        });


    }


    public void setUser(MyUser user) {
        this.user = user;
    }


    //跳转到设置界面
    public void enterSettingActivity() {
        fragment.getCurrentContext().startActivity(new Intent(fragment.getCurrentContext(), SettingActivity.class));
    }

    //调转到收藏界面
    public void enterCollectionActivity() {
        fragment.getCurrentContext().startActivity(new Intent(fragment.getCurrentContext(), UserCollectionActivity.class));
    }


    //签到
    public void addCoin(final int coin) {
        user.increment("contribution", coin);
        user.setLastSignInDate(dateFormat.format(new Date()));//设置最新签到时间
        user.update(new ToastUpdateListener() {
            @Override
            public void onSuccess() {
                ToastUtil.showToast(CommonString.ADD_COIN_BASE + coin);
            }
        });
    }

    //检测是否已经签到
    public boolean isSignIn() {
        if (user.getLastSignInDate() == null) {//之前没有这个列,可以签到
            return false;
        } else {
            String lastSignIn = user.getLastSignInDate();
            try {
                Date last = dateFormat.parse(lastSignIn);
                Date now = new Date();
                long remainTime = now.getTime() - last.getTime();  //11   10,10  10
                if (remainTime > 18 * 60 * 60 * 1000) {//距离上次签到已经超过18小时
                    return false;
                } else {
                    long hour = 18 - remainTime / 1000 / 60 / 60;
                    if (mShowSignInToast) {
                        ToastUtil.showToast("距离下次签到还剩:" + hour + "小时");
                        mShowSignInToast = false;
                    }

                    return true;
                }
            } catch (ParseException e) {
                e.printStackTrace();
                return true;
            }
        }
    }

    //检测是否已经签到
    public void SignIn() {
        if (user.getLastSignInDate() == null) {//之前没有这个列
            final int coin = 5;
            user.setLastSignInDate(dateFormat.format(new Date()));//设置当前时间为最后时间
            user.increment("contribution", coin);//第一次都加5
            user.update(new ToastUpdateListener() {
                @Override
                public void onSuccess() {
                    ToastUtil.showToast("今天已签到：" + CommonString.ADD_COIN_BASE + coin);
                }
            });
        } else {
            fragment.alertSignInDialog();
        }
    }

    //分享apk
    public void shareApp() {
        IntentUtil.shareText(fragment.getCurrentContext(), "推荐一款app:" + "<口袋乐谱>" + "---官网地址：" + "http://pocketmusic.bmob.site/");
    }

    //进入硬币榜
    public void enterContributionActivity() {
        fragment.getCurrentContext().startActivity(new Intent(fragment.getCurrentContext(), ContributionActivity.class));
    }


    public void enterUserPostActivity() {
        fragment.getCurrentContext().startActivity(new Intent(fragment.getCurrentContext(), UserPostActivity.class));
    }

    //App商店
    public void grade() {
        IntentUtil.enterAppStore(fragment.getCurrentContext());
    }

    public void enterUserShareActivity() {
        fragment.getCurrentContext().startActivity(new Intent(fragment.getCurrentContext(), UserShareActivity.class));
    }

    public void enterSupportActivity() {
        fragment.getCurrentContext().startActivity(new Intent(fragment.getCurrentContext(), SupportActivity.class));
    }

    public void setSignature(final String signature) {
        user.setSignature(signature);
        user.update(new ToastUpdateListener() {
            @Override
            public void onSuccess() {
                ToastUtil.showToast("已修改签名~");
                fragment.setSignature(signature);

            }
        });
    }

    //设置用户属于哪个版本
    public void setUserBelongToVersion() {
        try {
            PackageManager pm = fragment.getCurrentContext().getPackageManager();
            PackageInfo pi = pm.getPackageInfo(fragment.getCurrentContext().getPackageName(), PackageManager.GET_ACTIVITIES);
            user.setVersion(pi.versionName);
            user.update(new ToastUpdateListener() {
                @Override
                public void onSuccess() {

                }
            });
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
    }

    public void setNickName(final String nickName) {
        user.setNickName(nickName);
        user.update(new ToastUpdateListener() {
            @Override
            public void onSuccess() {
                user.increment(BmobConstant.BMOB_COIN, -Constant.REDUCE_CHANG_NICK_NAME);
                user.update(new ToastUpdateListener() {
                    @Override
                    public void onSuccess() {
                        ToastUtil.showToast(CommonString.REDUCE_COIN_BASE + Constant.REDUCE_CHANG_NICK_NAME);
                        fragment.setNickName(nickName);
                    }
                });
            }
        });
    }


    public interface IView extends IBaseView {

        void setHeadIvResult(String photoPath);

        void alertSignInDialog();


        void setSignature(String signature);

        void setNickName(String nickName);
    }
}
