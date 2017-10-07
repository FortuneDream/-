package com.example.q.pocketmusic.view.widget.view;

import android.content.Context;
import android.support.annotation.DrawableRes;
import android.support.annotation.NonNull;
import android.support.v7.widget.AppCompatImageView;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.example.q.pocketmusic.R;
import com.example.q.pocketmusic.util.common.ConvertUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by 81256 on 2017/10/7.
 */

public class MorePopupWindow {
    private OnSelectedListener listener;
    private LinearLayout popupView;
    private PopupWindow popupWindow;
    private List<LinearLayout> list;
    private Context context;

    public void setListener(OnSelectedListener listener) {
        this.listener = listener;
    }

    public interface OnSelectedListener {
        void onSelected(int position);
    }

    public Context getContext() {
        return context;
    }

    public void setContext(Context context) {
        this.context = context;
    }

    public MorePopupWindow(Context context) {
        this.context = context;
        popupWindow = new PopupWindow();
        popupWindow.setWidth(LinearLayout.LayoutParams.WRAP_CONTENT);
        popupWindow.setHeight(LinearLayout.LayoutParams.WRAP_CONTENT);
        popupView = new LinearLayout(context);
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        popupView.setLayoutParams(layoutParams);
        popupView.setOrientation(LinearLayout.VERTICAL);
        popupView.setBackground(context.getResources().getDrawable(R.drawable.shape_popup_bg));
        list = new ArrayList<>();
    }

    //生成item
    public LinearLayout getContentLl(@DrawableRes int resId, String text) {
        int dp_8 = ConvertUtil.Dp2Px(context, 8);
        LinearLayout contentLl = getLinearLayout(context);
        contentLl.setGravity(Gravity.CENTER_VERTICAL);
        AppCompatImageView appCompatImageView = getAppCompatImageView(resId);
        contentLl.addView(appCompatImageView);
        TextView textView = getTextView(text, dp_8);
        contentLl.addView(textView);
        return contentLl;
    }

    @NonNull
    private LinearLayout getLinearLayout(Context context) {
        int dp_8 = ConvertUtil.Dp2Px(context, 8);
        LinearLayout contentLl = new LinearLayout(context);
        LinearLayout.LayoutParams contentLlParam = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        contentLlParam.setMargins(dp_8, dp_8, dp_8, dp_8);
        contentLl.setLayoutParams(contentLlParam);
        contentLl.setGravity(LinearLayout.VERTICAL);
        return contentLl;
    }

    @NonNull
    private TextView getTextView(String text, int dp_8) {
        TextView textView = new TextView(context);
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        layoutParams.setMargins(dp_8, 0, 0, 0);
        textView.setLayoutParams(layoutParams);
        int textColor = context.getResources().getColor(R.color.item_two_text_main);
        textView.setTextSize(14);
        textView.setTextColor(textColor);
        textView.setText(text);
        return textView;
    }

    @NonNull
    private AppCompatImageView getAppCompatImageView(@DrawableRes int resId) {
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        AppCompatImageView appCompatImageView = new AppCompatImageView(context);
        appCompatImageView.setLayoutParams(layoutParams);
        appCompatImageView.setImageResource(resId);
        return appCompatImageView;
    }

    //添加item
    public void addView(final LinearLayout linearLayout) {
        list.add(linearLayout);
        popupView.addView(linearLayout);
    }

    public void show(View view) {
        popupWindow.setContentView(popupView);
        popupWindow.setOutsideTouchable(true);
        popupWindow.showAsDropDown(view);
        for (int i = 0; i < list.size(); i++) {
            final int finalI = i;
            list.get(i).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (listener != null) {
                        listener.onSelected(finalI);
                        popupWindow.dismiss();
                    }
                }
            });
        }
    }


}
