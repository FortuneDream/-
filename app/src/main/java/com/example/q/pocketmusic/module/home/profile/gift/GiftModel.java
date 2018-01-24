package com.example.q.pocketmusic.module.home.profile.gift;

import com.example.q.pocketmusic.callback.ToastQueryListener;
import com.example.q.pocketmusic.callback.ToastSaveListener;
import com.example.q.pocketmusic.data.bean.MyUser;
import com.example.q.pocketmusic.data.bean.bmob.Gift;
import com.example.q.pocketmusic.module.common.BaseModel;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.datatype.BmobPointer;

/**
 * Created by 81256 on 2017/9/6.
 */
//礼物模块
public class GiftModel extends BaseModel {
    public interface TYPE {
        int COMMENT = 1;
        int SHARE = 2;
    }

    public void getGiftList(MyUser user, int mPage, ToastQueryListener<Gift> listener) {
        BmobQuery<Gift> query = new BmobQuery<>();
        initDefaultListQuery(query, mPage);
        query.addWhereEqualTo("isGet", false);
        query.addWhereEqualTo("user", new BmobPointer(user));
        query.findObjects(listener);
    }

    public void addGift(MyUser user, final int coin, MyUser other, int type, ToastSaveListener<String> listener) {
        Gift gift = new Gift();
        gift.setUser(user);
        gift.setCoin(coin);
        gift.setContent(other.getNickName() + " 点赞了你 " + getSource(type));
        gift.setGet(false);
        gift.save(listener);
    }

    private String getSource(int type) {
        switch (type) {
            case TYPE.COMMENT:
                return "求谱评论发所发的曲谱";
            case TYPE.SHARE:
                return "分享的曲谱";
        }
        return "未知";
    }


}
