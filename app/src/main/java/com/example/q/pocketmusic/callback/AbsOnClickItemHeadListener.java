package com.example.q.pocketmusic.callback;

import android.content.Context;
import android.content.Intent;

import com.example.q.pocketmusic.data.bean.MyUser;
import com.example.q.pocketmusic.module.home.profile.user.other.OtherProfileActivity;

/**
 * Created by 鹏君 on 2017/7/25.
 * （￣m￣）
 */

public abstract class AbsOnClickItemHeadListener {
//    //点击头像
//    void onClickHead(int position);集成在Adapter
    //点击item
   public abstract void onClickItem(int position);

    public void onClickHead(Context context,MyUser other){
        Intent intent = new Intent(context, OtherProfileActivity.class);
        intent.putExtra(OtherProfileActivity.PARAM_USER, other);
        context.startActivity(intent);
    }
}
