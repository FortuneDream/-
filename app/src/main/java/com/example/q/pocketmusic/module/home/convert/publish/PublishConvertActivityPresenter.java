package com.example.q.pocketmusic.module.home.convert.publish;

import android.text.TextUtils;
import android.util.Log;

import com.example.q.pocketmusic.callback.ToastQueryListListener;
import com.example.q.pocketmusic.callback.ToastSaveListener;
import com.example.q.pocketmusic.callback.ToastUpdateListener;
import com.example.q.pocketmusic.config.BmobConstant;
import com.example.q.pocketmusic.config.CommonString;
import com.example.q.pocketmusic.config.Constant;
import com.example.q.pocketmusic.model.bean.convert.ConvertPost;
import com.example.q.pocketmusic.model.bean.convert.ConvertPostPic;
import com.example.q.pocketmusic.module.common.BaseActivity;
import com.example.q.pocketmusic.module.common.BasePresenter;
import com.example.q.pocketmusic.module.common.IBaseView;
import com.example.q.pocketmusic.util.UserUtil;
import com.example.q.pocketmusic.util.common.LogUtils;
import com.example.q.pocketmusic.util.common.ToastUtil;

import java.util.ArrayList;
import java.util.List;

import cn.bmob.v3.BmobBatch;
import cn.bmob.v3.BmobObject;
import cn.bmob.v3.datatype.BatchResult;
import cn.bmob.v3.datatype.BmobFile;
import cn.bmob.v3.listener.UploadBatchListener;
import cn.finalteam.galleryfinal.FunctionConfig;
import cn.finalteam.galleryfinal.GalleryFinal;
import cn.finalteam.galleryfinal.model.PhotoInfo;


public class PublishConvertActivityPresenter extends BasePresenter<PublishConvertActivityPresenter.IView> {
    private IView activity;
    private List<String> imgUrls;
    private int coin = Constant.REDUCE_BASE_CONVERT;

    public PublishConvertActivityPresenter(IView activity) {
        attachView(activity);
        this.activity = getIViewRef();
        imgUrls = new ArrayList<>();
    }

    //打开图片管理器
    public void openPicture() {
        FunctionConfig config = new FunctionConfig.Builder()
                .setMutiSelectMaxSize(8)
                .build();
        GalleryFinal.openGalleryMuti(1, config, new GalleryFinal.OnHanlderResultCallback() {
            @Override
            public void onHanlderSuccess(int reqeustCode, List<PhotoInfo> resultList) {
                if (reqeustCode == 1) {
                    imgUrls.clear();
                    for (PhotoInfo info : resultList) {
                        String url = info.getPhotoPath();
                        imgUrls.add(url);
                    }
                    activity.showSmallPic(imgUrls);//返回图片地址
                }
            }

            @Override
            public void onHanlderFailure(int requestCode, String errorMsg) {

            }
        });
    }


    public void checkPublishConvert(String title, String content) {
        if (TextUtils.isEmpty(title) || TextUtils.isEmpty(content)) {
            ToastUtil.showToast("标题或描述不能为空");
            return;
        } else if (imgUrls.size() <= 0) {
            ToastUtil.showToast("请添加图片");
            return;
        }
        if (!UserUtil.checkUserContribution((BaseActivity) activity.getCurrentContext(), coin)) {
            ToastUtil.showToast(CommonString.STR_NOT_ENOUGH_COIN);
            return;
        }
        activity.alertCoinDialog(coin, title, content);

    }

    public void checkPic(String path) {
        LogUtils.e(TAG, path);
        GalleryFinal.openEdit(2, path, new GalleryFinal.OnHanlderResultCallback() {
            @Override
            public void onHanlderSuccess(int requestCode, List<PhotoInfo> resultList) {

            }

            @Override
            public void onHanlderFailure(int requestCode, String errorMsg) {

            }
        });
    }


    //增加硬币
    public void addCoin() {
        coin++;
        activity.changeCoin(coin - Constant.REDUCE_BASE_CONVERT);
    }

    //减少硬币
    public void reduceCoin() {
        if (coin <= Constant.REDUCE_BASE_CONVERT) {
            return;
        }
        coin--;
        activity.changeCoin(coin - Constant.REDUCE_BASE_CONVERT);
    }

    //上传图片
    public void uploadConvertPic(final String title, final String content) {
//        LogUtils.e(TAG, String.valueOf(imgUrls.size()));
        activity.showLoading(true);
        BmobFile.uploadBatch(imgUrls.toArray(new String[imgUrls.size()]), new UploadBatchListener() {
            @Override
            public void onSuccess(List<BmobFile> list, List<String> list1) {
                //文件成功之后再添加数据
                if (imgUrls.size() == list1.size()) {
                    addConvertPostPic(title, content, list1);
                }
            }

            @Override
            public void onProgress(int i, int i1, int i2, int i3) {
                //批量上传中
            }

            @Override
            public void onError(int i, String s) {
                activity.showLoading(false);
                ToastUtil.showToast(CommonString.STR_ERROR_INFO + "第" + i + "张：" + s);
            }
        });
    }

    //
    private void addConvertPostPic(String title, String content, final List<String> list1) {
        final ConvertPost convertPost = new ConvertPost();
        convertPost.setUser(UserUtil.user);
        convertPost.setTitle(title);
        convertPost.setCoin(coin);
        convertPost.setCommentNum(0);
        convertPost.setContent(content);
        convertPost.save(new ToastSaveListener<String>() {
            @Override
            public void onSuccess(String s) {
                List<BmobObject> convertPostPics = new ArrayList<BmobObject>();
                for (int i = 0; i < list1.size(); i++) {
                    ConvertPostPic convertPostPic = new ConvertPostPic();
                    convertPostPic.setPost(convertPost);
                    convertPostPic.setUrl(list1.get(i));
                    convertPostPics.add(convertPostPic);
                }
                new BmobBatch().insertBatch(convertPostPics).doBatch(new ToastQueryListListener<BatchResult>(activity) {
                    @Override
                    public void onSuccess(List<BatchResult> list) {
                        UserUtil.increment(-coin, new ToastUpdateListener() {
                            @Override
                            public void onSuccess() {
                                ToastUtil.showToast(CommonString.REDUCE_COIN_BASE + (coin));
                                activity.showLoading(false);
                                activity.returnActivity();
                            }
                        });
                    }
                });
            }
        });
    }

    interface IView extends IBaseView {
        void showSmallPic(List<String> imgUrls);

        void returnActivity();

        void changeCoin(int coin);

        void alertCoinDialog(int coin, String title, String content);
    }
}
