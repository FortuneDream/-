package com.example.q.pocketmusic.module.home.convert.convert;

import com.example.q.pocketmusic.module.common.BasePresenter;
import com.example.q.pocketmusic.module.common.IBaseView;

/**
 * Created by 鹏君 on 2017/4/22.
 */

public class ConvertPresenter extends BasePresenter<ConvertPresenter.IView> {
    private IView activity;

    public ConvertPresenter(IView item) {
        attachView(item);
        this.activity=getIViewRef();

    }


    public interface IView extends IBaseView {


    }
}
