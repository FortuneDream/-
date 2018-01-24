package com.example.q.pocketmusic.view.widget.view;

import android.view.View;
import android.widget.PopupWindow;

/**
 * Created by 81256 on 2018/1/5.
 */

public class GuidePopupWindow {
    private PopupWindow mBubbleWindow;
    private View mTargetView;
    private int mGravity;
    private boolean mOutsideTouchable;


    public GuidePopupWindow setGravity(int gravity){
        this.mGravity=gravity;
        return this;
    }

    //设置参数
    public GuidePopupWindow reset(){
        return this;
    }
    //点击外部是否消失，true消失
    public GuidePopupWindow setOutsideTouchable(boolean outsideTouchable){
        this.mOutsideTouchable=outsideTouchable;
        return this;
    }
}
