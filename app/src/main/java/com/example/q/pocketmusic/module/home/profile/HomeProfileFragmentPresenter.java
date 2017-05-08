package com.example.q.pocketmusic.module.home.profile;

import android.content.Context;
import android.content.Intent;

import com.example.q.pocketmusic.module.common.IBaseList;
import com.example.q.pocketmusic.callback.ToastUpdateListener;
import com.example.q.pocketmusic.config.CommonString;
import com.example.q.pocketmusic.model.bean.MyUser;
import com.example.q.pocketmusic.module.common.BasePresenter;
import com.example.q.pocketmusic.module.home.profile.collection.CollectionActivity;
import com.example.q.pocketmusic.module.home.profile.contribution.ContributionActivity;
import com.example.q.pocketmusic.module.home.profile.post.UserPostActivity;
import com.example.q.pocketmusic.module.home.profile.setting.SettingActivity;
import com.example.q.pocketmusic.module.home.profile.setting.help.HelpActivity;
import com.example.q.pocketmusic.module.user.suggestion.SuggestionActivity;
import com.example.q.pocketmusic.util.MyToast;

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
 * Created by Cloud on 2017/1/26.
 */

public class HomeProfileFragmentPresenter extends BasePresenter<HomeProfileFragmentPresenter.IView> {
    private IView fragment;
    private MyUser user;

    public HomeProfileFragmentPresenter(IView fragment) {
        attachView(fragment);
        this.fragment=getIViewRef();
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
                            MyToast.showToast(fragment.getCurrentContext(), CommonString.STR_ERROR_INFO + e.getMessage());
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
                MyToast.showToast(fragment.getCurrentContext(), CommonString.STR_ERROR_INFO + errorMsg);
            }
        });


    }


    public void setUser(MyUser user) {
        this.user = user;
    }

    //跳转到用户邮箱界面
    public void enterSuggestionActivity() {
        fragment.getCurrentContext().startActivity(new Intent(fragment.getCurrentContext(), SuggestionActivity.class));
    }


    //跳转到设置界面
    public void enterSettingActivity() {
        fragment.getCurrentContext().startActivity(new Intent(fragment.getCurrentContext(), SettingActivity.class));
    }

    //调转到收藏界面
    public void enterCollectionActivity() {
        fragment.getCurrentContext().startActivity(new Intent(fragment.getCurrentContext(), CollectionActivity.class));
    }


    //签到
    public void addReward(final int reward) {
        user.increment("contribution", reward);
        user.setLastSignInDate(dateFormat.format(new Date()));//设置最新签到时间
        user.update(new ToastUpdateListener(fragment) {
            @Override
            public void onSuccess() {
                MyToast.showToast(fragment.getCurrentContext(), CommonString.ADD_COIN_BASE + reward);
            }
        });
    }

    //检测是否已经签到
    public void checkHasSignIn() {
        if (user.getLastSignInDate() == null) {//之前没有这个列
            user.setLastSignInDate(dateFormat.format(new Date()));//设置当前时间为最后时间
            user.increment("contribution", 5);//第一次都加5
            user.update(new ToastUpdateListener(fragment) {
                @Override
                public void onSuccess() {
                    MyToast.showToast(fragment.getCurrentContext(), "今天已签到：" + CommonString.ADD_COIN_BASE + 1);
                }
            });
        } else {
            String lastSignIn = user.getLastSignInDate();
            try {
                Date last = dateFormat.parse(lastSignIn);
                Date date = new Date();
                long remainTime = date.getTime() - last.getTime();
                if (remainTime > 24 * 60 * 60 * 1000) {//距离上次签到已经超过24小时
                    fragment.alertSignInDialog();
                } else {
                    MyToast.showToast(fragment.getCurrentContext(), "24小时之内只能签到一次哦！");
                }
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }

    }

    //进入硬币榜
    public void enterContributionActivity() {
        fragment.getCurrentContext().startActivity(new Intent(fragment.getCurrentContext(), ContributionActivity.class));
    }

    //进入帮助
    public void enterHelpActivity() {
        fragment.getCurrentContext().startActivity(new Intent(fragment.getCurrentContext(), HelpActivity.class));
    }

    public void enterUserPostActivity() {
        fragment.getCurrentContext().startActivity(new Intent(fragment.getCurrentContext(), UserPostActivity.class));
    }


    public interface IView extends IBaseList {

        void setHeadIvResult(String photoPath);


        void alertSignInDialog();

    }
}
