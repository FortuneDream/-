package com.example.q.pocketmusic.module.home.profile.contribution;

import com.example.q.pocketmusic.callback.ToastSaveListener;
import com.example.q.pocketmusic.callback.ToastUpdateListener;
import com.example.q.pocketmusic.config.BmobConstant;
import com.example.q.pocketmusic.model.bean.MyUser;
import com.example.q.pocketmusic.model.bean.bmob.Gift;
import com.example.q.pocketmusic.module.common.BaseModel;

/**
 * Created by 81256 on 2017/9/7.
 */
//硬币模块
public class ContributionModel extends BaseModel {

    public void addGift(MyUser user, final int coin, ToastSaveListener<String> listener){
        Gift gift =new Gift();
        gift.setUser(user);
        gift.setCoin(coin);
        gift.setContent("有小伙伴点赞你的求谱图片~");
        gift.setGet(false);
        gift.save(listener);
    }
}
