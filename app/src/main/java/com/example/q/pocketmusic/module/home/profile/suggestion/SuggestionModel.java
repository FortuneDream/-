package com.example.q.pocketmusic.module.home.profile.suggestion;

import android.text.TextUtils;

import com.example.q.pocketmusic.callback.ToastQueryListener;
import com.example.q.pocketmusic.callback.ToastSaveListener;
import com.example.q.pocketmusic.model.bean.bmob.UserSuggestion;
import com.example.q.pocketmusic.module.common.BaseModel;
import com.example.q.pocketmusic.util.UserUtil;

import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.datatype.BmobPointer;

/**
 * Created by 81256 on 2017/10/25.
 */

public class SuggestionModel extends BaseModel<UserSuggestion> {

    //提出建议
    public void sendSuggestion(String suggestion, ToastSaveListener<String> listener) {
        UserSuggestion userSuggestion = new UserSuggestion(UserUtil.user);
        userSuggestion.setSuggestion(suggestion);
        userSuggestion.save(listener);
    }

    @Override
    public void getList(int page, ToastQueryListener<UserSuggestion> listener) {
        super.getList(page, listener);
        BmobQuery<UserSuggestion> query = new BmobQuery<>();
        initDefaultListQuery(query,page);
        query.addWhereEqualTo("user", new BmobPointer(UserUtil.user));
        query.findObjects(listener);

    }

}
