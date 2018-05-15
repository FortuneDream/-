package com.example.q.pocketmusic.util;

import android.view.View;
import android.widget.PopupWindow;


import com.dell.fortune.tools.SharedPrefsUtil;
import com.dell.fortune.tools.view.GuidePopupWindow;
import com.example.q.pocketmusic.R;
import com.example.q.pocketmusic.config.constant.GuideConstant;


/**
 * Created by 81256 on 2018/2/1.
 */

public class GuidePopHelper {


    //求谱，第一次进入显示
    public static void showAskSongPop(View targetView) {
        if (SharedPrefsUtil.getBoolean(GuideConstant.KEY_ASK_SONG, true)) {
            View view = View.inflate(targetView.getContext(), R.layout.pop_ask_song, null);
            GuidePopupWindow popupWindow = new GuidePopupWindow();
            popupWindow.reset()
                    .setDelayShow(400)
                    .setShowDuration(2000)
                    .setPositionRelateToTarget(GuidePopupWindow.BOTTOM)
                    .setTargetAlignPosition(0.5f)
                    .setContentAlignPosition(0.9f)
                    .setOnDismissListener(new PopupWindow.OnDismissListener() {
                        @Override
                        public void onDismiss() {
                            SharedPrefsUtil.putBoolean(GuideConstant.KEY_ASK_SONG, false);
                        }
                    })
                    .show(targetView, view);
        }
    }


    //显示了求谱之后，
    public static void showShareSongPop(View targetView) {
        if (SharedPrefsUtil.getBoolean(GuideConstant.KEY_ASK_SONG, true)) {
            return;//如果求谱显示了，那就不显示分享
        }
        if (SharedPrefsUtil.getBoolean(GuideConstant.KEY_SHARE_SONG, true)) {
            View view = View.inflate(targetView.getContext(), R.layout.pop_share_song, null);
            GuidePopupWindow popupWindow = new GuidePopupWindow();
            popupWindow.reset()
                    .setDelayShow(400)
                    .setShowDuration(2000)
                    .setPositionRelateToTarget(GuidePopupWindow.BOTTOM)
                    .setTargetAlignPosition(0.5f)
                    .setContentAlignPosition(0.9f)
                    .setOnDismissListener(new PopupWindow.OnDismissListener() {
                        @Override
                        public void onDismiss() {
                            SharedPrefsUtil.putBoolean(GuideConstant.KEY_SHARE_SONG, false);
                        }
                    })
                    .show(targetView, view);
        }
    }


    //第一次进入
    public static void showGetCoinPop(View targetView) {
        if (SharedPrefsUtil.getBoolean(GuideConstant.KEY_GET_COIN, true)) {
            View view = View.inflate(targetView.getContext(), R.layout.pop_get_coin, null);
            GuidePopupWindow popupWindow = new GuidePopupWindow();
            popupWindow.reset()
                    .setDelayShow(400)
                    .setShowDuration(2000)
                    .setPositionRelateToTarget(GuidePopupWindow.BOTTOM)
                    .setTargetAlignPosition(0.5f)
                    .setContentAlignPosition(0.9f)
                    .setOnDismissListener(new PopupWindow.OnDismissListener() {
                        @Override
                        public void onDismiss() {
                            SharedPrefsUtil.putBoolean(GuideConstant.KEY_GET_COIN, false);
                        }
                    })
                    .show(targetView, view);
        }
    }


    //第一次进入显示
    public static void showHideTopBottom(View targetView) {
        if (SharedPrefsUtil.getBoolean(GuideConstant.KEY_HIDE_TOP_BOTTOM, true)) {
            View view = View.inflate(targetView.getContext(), R.layout.pop_hide_top_bottom, null);
            GuidePopupWindow popupWindow = new GuidePopupWindow();
            popupWindow.reset()
                    .setDelayShow(100)
                    .setOutSideTouchable(true)
                    .setShowDuration(1500)
                    .setPositionRelateToTarget(GuidePopupWindow.BOTTOM)
                    .setTargetAlignPosition(0.5f)
                    .setContentAlignPosition(0.9f)
                    .setOnDismissListener(new PopupWindow.OnDismissListener() {
                        @Override
                        public void onDismiss() {
                            SharedPrefsUtil.putBoolean(GuideConstant.KEY_HIDE_TOP_BOTTOM, false);
                        }
                    })
                    .show(targetView, view);
        }
    }


    //第一次进入显示,增加热点
    public static void showHot(View targetView) {
        if (SharedPrefsUtil.getBoolean(GuideConstant.KEY_HOT, true)) {
            View view = View.inflate(targetView.getContext(), R.layout.pop_hot, null);
            GuidePopupWindow popupWindow = new GuidePopupWindow();
            popupWindow.reset()
                    .setDelayShow(400)
                    .setShowDuration(2000)
                    .setPositionRelateToTarget(GuidePopupWindow.BOTTOM)
                    .setTargetAlignPosition(0.5f)
                    .setContentAlignPosition(0.9f)
                    .setOnDismissListener(new PopupWindow.OnDismissListener() {
                        @Override
                        public void onDismiss() {
                            SharedPrefsUtil.putBoolean(GuideConstant.KEY_HOT, false);
                        }
                    })
                    .show(targetView, view);
        }
    }

    //第一次进入显示
    public static void showChangeHead(View targetView) {
        if (SharedPrefsUtil.getBoolean(GuideConstant.KEY_CHANGE_HEAD, true)) {
            View view = View.inflate(targetView.getContext(), R.layout.pop_change_head, null);
            GuidePopupWindow popupWindow = new GuidePopupWindow();
            popupWindow.reset()
                    .setDelayShow(400)
                    .setShowDuration(2000)
                    .setPositionRelateToTarget(GuidePopupWindow.BOTTOM)
                    .setTargetAlignPosition(0.5f)
                    .setContentAlignPosition(0.9f)
                    .setOnDismissListener(new PopupWindow.OnDismissListener() {
                        @Override
                        public void onDismiss() {
                            SharedPrefsUtil.putBoolean(GuideConstant.KEY_CHANGE_HEAD, false);
                        }
                    })
                    .show(targetView, view);
        }
    }


    //第一次进入显示
    public static void showChangeNickName(View targetView) {
        if (SharedPrefsUtil.getBoolean(GuideConstant.KEY_CHANGE_HEAD, true)) {
            return;//如果正在提示修改头像，就不显示
        }
        if (SharedPrefsUtil.getBoolean(GuideConstant.KEY_CHANGE_NICK_NAME, true)) {
            View view = View.inflate(targetView.getContext(), R.layout.pop_change_nick_name, null);
            GuidePopupWindow popupWindow = new GuidePopupWindow();
            popupWindow.reset()
                    .setDelayShow(400)
                    .setShowDuration(2000)
                    .setPositionRelateToTarget(GuidePopupWindow.BOTTOM)
                    .setTargetAlignPosition(0.5f)
                    .setOnDismissListener(new PopupWindow.OnDismissListener() {
                        @Override
                        public void onDismiss() {
                            SharedPrefsUtil.putBoolean(GuideConstant.KEY_CHANGE_NICK_NAME, false);
                        }
                    })
                    .show(targetView, view);
        }
    }

    //第一次进入显示
    public static void showLeadSong(View targetView) {
        if (SharedPrefsUtil.getBoolean(GuideConstant.KEY_LEAD_SONG, true)) {
            View view = View.inflate(targetView.getContext(), R.layout.pop_lead_song, null);
            GuidePopupWindow popupWindow = new GuidePopupWindow();
            popupWindow.reset()
                    .setDelayShow(400)
                    .setOutSideTouchable(true)
                    .setShowDuration(2000)
                    .setPositionRelateToTarget(GuidePopupWindow.BOTTOM)
                    .setTargetAlignPosition(0.5f)
                    .setContentAlignPosition(0.9f)
                    .setOnDismissListener(new PopupWindow.OnDismissListener() {
                        @Override
                        public void onDismiss() {
                            SharedPrefsUtil.putBoolean(GuideConstant.KEY_LEAD_SONG, false);
                        }
                    })
                    .show(targetView, view);
        }
    }


    //每次都提示,点击二维码
    public static void showClickMa(View targetView) {
        View view = View.inflate(targetView.getContext(), R.layout.pop_click_ma, null);
        GuidePopupWindow popupWindow = new GuidePopupWindow();
        popupWindow.reset()
                .setDelayShow(400)
                .setOutSideTouchable(true)
                .setShowDuration(2000)
                .setPositionRelateToTarget(GuidePopupWindow.BOTTOM)
                .setTargetAlignPosition(0.5f)
                .setContentAlignPosition(0.9f)
                .show(targetView, view);
    }
}
