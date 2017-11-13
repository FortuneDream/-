package com.example.q.pocketmusic.module.home.net.help;

import com.example.q.pocketmusic.callback.ToastQueryListener;
import com.example.q.pocketmusic.config.Constant;
import com.example.q.pocketmusic.data.BmobInfo;
import com.example.q.pocketmusic.module.common.BasePresenter;

import java.util.ArrayList;
import java.util.List;

import cn.bmob.v3.BmobQuery;

/**
 * Created by 鹏君 on 2017/5/8.
 */

public class HelpPresenter extends BasePresenter<HelpPresenter.IView> {
    private IView activity;

    public HelpPresenter(IView activity) {
        attachView(activity);
        this.activity = getIViewRef();
    }

    public void getList() {
        BmobQuery<BmobInfo> query = new BmobQuery<>();
        query.order("-createdAt");//逆序
        query.addWhereEqualTo("type", Constant.BMOB_INFO_HELP);
        query.findObjects(new ToastQueryListener<BmobInfo>() {
            @Override
            public void onSuccess(List<BmobInfo> list) {
                convertToHelp(list);
            }

            private void convertToHelp(List<BmobInfo> list) {
                List<String> strings=new ArrayList<String>();
                for (BmobInfo bmobInfo:list){
                    strings.add(bmobInfo.getContent());
                }
                activity.setList(strings);
            }
        });
    }

    interface IView {

        void setList(List<String> strings);
    }
}
