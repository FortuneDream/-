package com.example.q.pocketmusic.module.home.net.type.community.share.publish;

import android.database.SQLException;
import android.text.TextUtils;

import com.example.q.pocketmusic.R;
import com.example.q.pocketmusic.callback.ToastQueryListListener;
import com.example.q.pocketmusic.callback.ToastQueryListener;
import com.example.q.pocketmusic.callback.ToastSaveListener;
import com.example.q.pocketmusic.callback.ToastUpdateListener;
import com.example.q.pocketmusic.config.constant.CoinConstant;
import com.example.q.pocketmusic.data.bean.local.Img;
import com.example.q.pocketmusic.data.bean.local.LocalSong;
import com.example.q.pocketmusic.data.bean.share.SharePic;
import com.example.q.pocketmusic.data.bean.share.ShareSong;
import com.example.q.pocketmusic.data.db.LocalSongDao;
import com.example.q.pocketmusic.module.common.BaseActivity;
import com.example.q.pocketmusic.module.common.BasePresenter;
import com.example.q.pocketmusic.module.common.IBaseView;
import com.example.q.pocketmusic.util.UserUtil;
import com.example.q.pocketmusic.util.common.LogUtils;
import com.example.q.pocketmusic.util.common.ToastUtil;
import com.j256.ormlite.dao.CloseableIterator;
import com.j256.ormlite.dao.ForeignCollection;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import cn.bmob.v3.BmobBatch;
import cn.bmob.v3.BmobObject;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.datatype.BatchResult;
import cn.bmob.v3.datatype.BmobFile;
import cn.bmob.v3.listener.UploadBatchListener;
import cn.finalteam.galleryfinal.FunctionConfig;
import cn.finalteam.galleryfinal.GalleryFinal;
import cn.finalteam.galleryfinal.model.PhotoInfo;

/**
 * Created by 鹏君 on 2016/11/10.
 */

public class SharePresenter extends BasePresenter<SharePresenter.IView> {
    private IView activity;
    private int mNumberPic;//图片数量
    private String[] filePaths;//本地图片路径
    private int typeId;


    public SharePresenter(IView activity) {
       super(activity);
    }


    //打开图片浏览器，存储数量和本地路径
    public void openPicture() {
        FunctionConfig config = new FunctionConfig.Builder()
                .setMutiSelectMaxSize(8)
                .build();
        GalleryFinal.openGalleryMuti(1, config, new GalleryFinal.OnHanlderResultCallback() {
            @Override
            public void onHanlderSuccess(int reqeustCode, List<PhotoInfo> resultList) {
                //初始化
                mNumberPic = 0;
                int size = resultList.size();
                if (reqeustCode == 1) {
                    filePaths = new String[size];
                    //得到所有的本地曲谱路径
                    for (int i = 0; i < resultList.size(); i++) {
                        String url = resultList.get(i).getPhotoPath();
                        filePaths[i] = url;
                        mNumberPic++;
                        LogUtils.e(TAG, url);
                    }
                    activity.setSelectPicResult(mNumberPic, filePaths, null);
                }
            }

            @Override
            public void onHanlderFailure(int requestCode, String errorMsg) {

            }
        });
    }

    public void upLoad(final String name, final String content) {

        if ((!TextUtils.isEmpty(name)) && (!TextUtils.isEmpty(content)) && (mNumberPic > 0) && UserUtil.checkLocalUser((BaseActivity) activity.getCurrentContext())) {
            //上传服务器,弹出Dialog，成功后finish，弹出Toast;
            activity.alertSelectCommunityDialog(name, content);
            //先检查是否已经存在相同的曲谱
        } else {
            ToastUtil.showToast(activity.getResString(R.string.complete_info));
        }
    }

    //检查是否已经存在
    public void checkHasSong(final String name, final String content, final int community) {
        activity.showLoading(true);
        BmobQuery<ShareSong> query = new BmobQuery<>();
        query.addWhereEqualTo("name", name);
        query.findObjects(new ToastQueryListener<ShareSong>() {
            @Override
            public void onSuccess(List<ShareSong> list) {
                Boolean flag = true;
                if (list == null || list.size() == 0) {
                    flag = false;//没有相同的额曲谱
                }
                if (flag) {
                    activity.showLoading(false);
                    ToastUtil.showToast("已经存在相同曲谱~");
                } else {
                    LogUtils.e(TAG, "开始批量上传");
                    //批量上传文件
                    uploadBatch(name, content, community);
                }
            }
        });
    }

    //批量上传文件
    private void uploadBatch(final String name, final String content, final int community) {
        BmobFile.uploadBatch(filePaths, new UploadBatchListener() {
            @Override
            public void onSuccess(List<BmobFile> list, List<String> list1) {
                //文件成功之后再添加数据
                if (filePaths.length == list1.size()) {
                    shareSong(name, content, list1, community);
                }
            }

            @Override
            public void onProgress(int i, int i1, int i2, int i3) {
                //批量上传中
            }

            @Override
            public void onError(int i, String s) {
                activity.showLoading(false);
                ToastUtil.showToast(activity.getResString(R.string.send_error) + "第" + i + "张：" + s);
            }
        });
    }

    //上传/分享乐曲
    private void shareSong(String name, final String content, final List<String> list1, int community) {
        final ShareSong shareSong = new ShareSong(UserUtil.user, name, content);
        shareSong.setInstrument(community);
        //添加分享曲谱记录
        shareSong.save(new ToastSaveListener<String>() {
            @Override
            public void onSuccess(String s) {
                List<BmobObject> sharePics = new ArrayList<>();
                for (int i = 0; i < list1.size(); i++) {
                    SharePic sharePic = new SharePic(shareSong, list1.get(i));
                    sharePics.add(sharePic);
                }
                //批量添加分享图片记录
                new BmobBatch().insertBatch(sharePics).doBatch(new ToastQueryListListener<BatchResult>() {

                    @Override
                    public void onSuccess(List<BatchResult> list) {
                        UserUtil.increment(CoinConstant.ADD_COIN_UPLOAD, new ToastUpdateListener() {
                            @Override
                            public void onSuccess() {
                                ToastUtil.showToast(activity.getResString(R.string.add_coin) + (CoinConstant.ADD_COIN_UPLOAD));
                                activity.showLoading(false);
                                activity.finish();
                            }
                        });
                    }
                });
            }
        });
    }


    public void getPicAndName(LocalSong localSong) {
        LocalSongDao localSongDao = new LocalSongDao(activity.getAppContext());
        ForeignCollection<Img> imgs = localSongDao.findBySongId(localSong.getId()).getImgs();
        CloseableIterator<Img> iterator = imgs.closeableIterator();
        int i = 0;
        filePaths = new String[imgs.size()];
        try {
            while (iterator.hasNext()) {
                Img img = iterator.next();
                filePaths[i++] = img.getUrl();
            }
            mNumberPic = filePaths.length;
            activity.setSelectPicResult(filePaths.length, filePaths, localSong.getName());
        } finally {
            try {
                iterator.close();
            } catch (SQLException | IOException e) {
                e.printStackTrace();
            }
        }
    }

    //查看图片
    public void checkPic(String path) {
        LogUtils.e(TAG, path);//22  15  1500  1.5  1.2  3-4  16 6
        GalleryFinal.openEdit(2, path, new GalleryFinal.OnHanlderResultCallback() {
            @Override
            public void onHanlderSuccess(int requestCode, List<PhotoInfo> resultList) {

            }

            @Override
            public void onHanlderFailure(int requestCode, String errorMsg) {

            }
        });
    }

    public void setTypeId(int typeId) {
        this.typeId = typeId;
    }

    public int getTypeId() {
        return typeId;
    }


    public interface IView extends IBaseView {

        void finish();

        void setSelectPicResult(int mNumberPic, String[] filePaths, String name);

        void showLoading(boolean isShow);

        void alertSelectCommunityDialog(String name, String content);
    }
}
