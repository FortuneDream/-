package com.example.q.pocketmusic.module.home.local.lead;

import android.text.TextUtils;

import com.example.q.pocketmusic.data.db.LocalSongDao;
import com.example.q.pocketmusic.module.common.BasePresenter;
import com.example.q.pocketmusic.module.common.IBaseView;
import com.example.q.pocketmusic.util.LocalPhotoAlbumUtil;
import com.example.q.pocketmusic.util.common.LogUtils;
import com.example.q.pocketmusic.util.common.ToastUtil;

import java.util.List;

/**
 * Created by 鹏君 on 2017/4/1.
 */

public class LeadSongPresenter extends BasePresenter<LeadSongPresenter.IView> {
    private IView activity;
    private LocalSongDao localSongDao;
    private LocalPhotoAlbumUtil localPhotoAlbumUtil;

    public LeadSongPresenter(IView activity) {
        attachView(activity);
        this.activity = getIViewRef();
        localSongDao = new LocalSongDao(activity.getAppContext());
        localPhotoAlbumUtil = new LocalPhotoAlbumUtil();
    }

    //打开图片管理器,得到选择的图片返回地址
    public void openPicture() {
        localPhotoAlbumUtil.getLocalPicPaths(new LocalPhotoAlbumUtil.OnLoadMutiLocalResult() {
            @Override
            public void onPathList(List<String> list) {
                activity.showSmallPic(list);//返回图片地址
            }
        });
    }

    //本地导入乐谱
    public void leadSong(String name) {
        if (TextUtils.isEmpty(name)) {
            ToastUtil.showToast("名字不能为空");
            return;
        } else if (localPhotoAlbumUtil.getImgUrls().size() <= 0) {
            ToastUtil.showToast("请添加图片");
            return;
        }
        localSongDao.saveLocalSong(name, localPhotoAlbumUtil.getImgUrls());
        activity.returnActivity();
    }

    public void checkPic(String path) {
        LogUtils.e(TAG, path);
        localPhotoAlbumUtil.checkPic(path);
    }

    interface IView extends IBaseView {

        void showSmallPic(List<String> imgUrls);

        void finish();

        void returnActivity();
    }
}
