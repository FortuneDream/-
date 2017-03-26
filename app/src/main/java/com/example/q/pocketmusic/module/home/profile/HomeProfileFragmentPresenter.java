package com.example.q.pocketmusic.module.home.profile;

import android.content.Context;
import android.content.Intent;

import com.example.q.pocketmusic.callback.IBaseList;
import com.example.q.pocketmusic.callback.IBaseView;
import com.example.q.pocketmusic.callback.ToastSaveListener;
import com.example.q.pocketmusic.callback.ToastUpdateListener;
import com.example.q.pocketmusic.config.CommonString;
import com.example.q.pocketmusic.config.Constant;
import com.example.q.pocketmusic.model.bean.MyUser;

import com.example.q.pocketmusic.module.common.BasePresenter;

import com.example.q.pocketmusic.module.home.profile.collection.CollectionActivity;
import com.example.q.pocketmusic.module.home.profile.contribution.ContributionActivity;
import com.example.q.pocketmusic.module.home.profile.setting.SettingActivity;
import com.example.q.pocketmusic.module.user.suggestion.SuggestionActivity;
import com.example.q.pocketmusic.util.MyToast;
import com.example.q.pocketmusic.util.StringUtil;

import java.io.File;
import java.text.ParseException;
import java.util.Date;
import java.util.List;

import cn.bmob.v3.BmobRealTimeData;
import cn.bmob.v3.datatype.BmobFile;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.UploadFileListener;
import cn.finalteam.galleryfinal.FunctionConfig;
import cn.finalteam.galleryfinal.GalleryFinal;
import cn.finalteam.galleryfinal.model.PhotoInfo;

/**
 * Created by Cloud on 2017/1/26.
 */

public class HomeProfileFragmentPresenter extends BasePresenter {
    private Context context;
    private IView fragment;
    private MyUser user;

    public HomeProfileFragmentPresenter(Context context, IView fragment) {
        this.context = context;
        this.fragment = fragment;
    }


    //选择图片
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
                        if (e == null) {
                            //修改用户表的headIv属性
                            user.setHeadImg(bmobFile.getFileUrl());
                            user.update(new ToastUpdateListener(context, fragment) {
                                @Override
                                public void onSuccess() {
                                    fragment.showLoading(false);
                                    fragment.setHeadIvResult(picPath);
                                }
                            });
                        } else {
                            fragment.showLoading(false);
                            MyToast.showToast(context, CommonString.STR_ERROR_INFO + e.getMessage());
                        }
                    }
                });
            }

            @Override
            public void onHanlderFailure(int requestCode, String errorMsg) {
                fragment.showLoading(false);
                MyToast.showToast(context, CommonString.STR_ERROR_INFO + errorMsg);
            }
        });
    }


    public void setInstrument(final String instrumentStr) {
        fragment.showLoading(true);
        user.setInstrument(instrumentStr);
        user.update(new ToastUpdateListener(context, fragment) {
            @Override
            public void onSuccess() {
                fragment.showLoading(false);
                fragment.setInstrumentResult(instrumentStr);
            }
        });
    }

    public void setUser(MyUser user) {
        this.user = user;
    }

    //跳转到用户邮箱界面
    public void enterSuggestionActivity() {
        Intent intent = new Intent(context, SuggestionActivity.class);
        context.startActivity(intent);
    }


    //跳转到设置界面
    public void enterSettingActivity() {
        Intent intent = new Intent(context, SettingActivity.class);
        context.startActivity(intent);
    }

    public void enterCollectionActivity() {
        Intent intent = new Intent(context, CollectionActivity.class);
        context.startActivity(intent);
    }


    public void addReward(final int reward) {
        user.increment("contribution", reward);
        user.setLastSignInDate(dateFormat.format(new Date()));//设置最新签到时间
        user.update(new ToastUpdateListener(context, fragment) {
            @Override
            public void onSuccess() {
                MyToast.showToast(context, CommonString.ADD_CONTRIBUTION_BASE + reward);
                fragment.dismissSignDialog();
            }
        });
    }

    //检测是否已经签到
    public void checkHasSignIn() {
        if (user.getLastSignInDate() == null) {//之前没有这个列
            user.setLastSignInDate(dateFormat.format(new Date()));//设置当前时间为最后时间
            user.increment("contribution", 1);//第一次都加1
            user.update(new ToastUpdateListener(context, fragment) {
                @Override
                public void onSuccess() {
                    MyToast.showToast(context, "今天已签到：" + CommonString.ADD_CONTRIBUTION_BASE + 1);
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
                    MyToast.showToast(context, "24小时之内只能签到一次哦！");
                }
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }

    }

    public void enterContributionActivity() {
        Intent intent = new Intent(context, ContributionActivity.class);
        context.startActivity(intent);
    }


    public interface IView extends IBaseList {

        void setHeadIvResult(String photoPath);

        void setInstrumentResult(String instrumentStr);


        void dismissSignDialog();

        void alertSignInDialog();
    }
}
