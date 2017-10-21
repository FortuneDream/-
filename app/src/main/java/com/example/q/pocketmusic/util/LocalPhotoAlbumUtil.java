package com.example.q.pocketmusic.util;

import com.example.q.pocketmusic.module.common.BaseModel;
import com.example.q.pocketmusic.util.common.LogUtils;

import java.util.ArrayList;
import java.util.List;

import cn.finalteam.galleryfinal.FunctionConfig;
import cn.finalteam.galleryfinal.GalleryFinal;
import cn.finalteam.galleryfinal.model.PhotoInfo;

/**
 * Created by 81256 on 2017/10/18.
 */

public class LocalPhotoAlbumUtil extends BaseModel {
    private List<String> imgUrls;

    public LocalPhotoAlbumUtil() {
        this.imgUrls =new ArrayList<>();
    }

    public interface OnLoadLocalResult{
        void onPathList(List<String> list);
    }
    //打开图片管理器
    public void getLocalPicPaths(final OnLoadLocalResult onLoadLocalResult) {
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
                    onLoadLocalResult.onPathList(imgUrls);
                }
            }

            @Override
            public void onHanlderFailure(int requestCode, String errorMsg) {

            }
        });
    }

    public List<String> getImgUrls() {
        return imgUrls;
    }

    public void checkPic(String path) {
        GalleryFinal.openEdit(2, path, new GalleryFinal.OnHanlderResultCallback() {
            @Override
            public void onHanlderSuccess(int requestCode, List<PhotoInfo> resultList) {

            }

            @Override
            public void onHanlderFailure(int requestCode, String errorMsg) {

            }
        });
    }
}
