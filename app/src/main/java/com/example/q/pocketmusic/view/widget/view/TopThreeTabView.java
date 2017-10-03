package com.example.q.pocketmusic.view.widget.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;
import android.widget.LinearLayout;

import com.example.q.pocketmusic.R;

/**
 * Created by 81256 on 2017/10/3.
 */
//3个tab
public class TopThreeTabView extends LinearLayout {
    public TopThreeTabView(Context context) {
        this(context, null);
    }

    public TopThreeTabView(Context context, @Nullable AttributeSet attrs) {
        this(context, null, 0);
    }

    public TopThreeTabView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        inflate(getContext(), R.layout.view_top_tab, this);
        TypedArray array = context.obtainStyledAttributes(attrs, R.styleable.TopTwoTabView);
        String leftStr = array.getString(R.styleable.TopTwoTabView_Left_Text);
        String rightStr = array.getString(R.styleable.TopTwoTabView_Right_Text);
        array.recycle();
//        mLeftTv = getView(R.id.left_tv);
//        mRightTv = getView(R.id.right_tv);
//
//        mLeftTv.setText(leftStr);
//        mRightTv.setText(rightStr);
//
//        mLeftTv.setOnClickListener(new OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                if (listener != null) {
//                    listener.setTopTabCheck(0);
//                }
//            }
//        });
//
//        mRightTv.setOnClickListener(new OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                if (listener != null) {
//                    listener.setTopTabCheck(1);
//                }
//            }
//        });
    }
}
