package com.example.q.pocketmusic.view.widget.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.example.q.pocketmusic.R;
import com.example.q.pocketmusic.util.common.ConvertUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by 鹏君 on 2017/5/17.
 */
//顶部双选View
public class TopTabView extends LinearLayout  {
    private TopTabListener listener;
    private int FLAG;//当前tab
    private List<TextView> views;

    public void setListener(TopTabListener listener) {
        this.listener = listener;
    }

    public interface TopTabListener {
        void setTopTabCheck(int position);
    }

    public TopTabView(Context context) {
        this(context, null);
    }

    public TopTabView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public TopTabView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        setOrientation(HORIZONTAL);
        TypedArray array = context.obtainStyledAttributes(attrs, R.styleable.TopTabView);
        CharSequence[] s = array.getTextArray(R.styleable.TopTabView_tabs);
        if (s != null) {
            List<String> list = new ArrayList<>();
            for (CharSequence value : s) {
                list.add(value.toString());
            }
            initTextView(s.length, list);
        }
        array.recycle();
    }

    private void initTextView(int size, final List<String> list) {
        views = new ArrayList<>();
        for (int i = 0; i < size; i++) {
            TextView textView = createTextView(list.get(i));
            final int finalI = i;
            textView.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (FLAG != finalI) {
                        FLAG = finalI;
                        setCheck(finalI);
                        if (listener != null) {
                            listener.setTopTabCheck(finalI);
                        }
                    }
                }
            });
            views.add(textView);
            addView(textView);
        }
    }


    public TextView createTextView(String text) {
        TextView textView = new TextView(getContext());
        int dp_16 = ConvertUtil.Dp2Px(getContext(), 16);
        int dp_8 = ConvertUtil.Dp2Px(getContext(), 8);
        textView.setPadding(dp_16, dp_8, dp_16, dp_8);
        textView.setText(text);
        return textView;
    }

    //0或者1
    public void setCheck(int position) {
        for (int i = 0; i < views.size(); i++) {
            TextView textView = views.get(i);
            if (i == position) {
                textView.setBackgroundResource(R.drawable.shape_toolbar_tab_pressed);
                textView.setTextColor(getResources().getColor(R.color.colorPrimary));
            } else {
                textView.setBackgroundResource(R.drawable.shape_toolbar_tab_normal);
                textView.setTextColor(getResources().getColor(R.color.white));
            }
        }
    }
}
