package com.example.q.pocketmusic.module.home.net;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.example.q.pocketmusic.R;

/**
 * Created by 鹏君 on 2017/8/23.
 * （￣m￣）
 */

public class NetFragmentDecoration extends RecyclerView.ItemDecoration {
    private Paint mTextPaint;
    private Paint mDividerPaint;
    private int mDividerHeight = 1;
    private String hot = " - 热门谱单 - ";
    private String instrument = " - 我的乐器 - ";
    private float hotTextLength;
    private float instrumentTextLength;
    private Rect mTextRect;


    public NetFragmentDecoration(Context context) {
        mTextPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mTextPaint.setColor(context.getResources().getColor(R.color.md_grey_500));
        mTextPaint.setTextSize(context.getResources().getDimensionPixelSize(R.dimen.net_fragment_text));

        mDividerPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mDividerPaint.setColor(context.getResources().getColor(R.color.divider));

        mTextRect = new Rect();
        hotTextLength = mTextPaint.measureText(hot);
        instrumentTextLength = mTextPaint.measureText(instrument);
        mTextPaint.getTextBounds(hot, 0, hot.length(), mTextRect);
    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        super.getItemOffsets(outRect, view, parent, state);
        int count = parent.getChildCount();
        for (int i = 0; i < count; i++) {
            View childView = parent.getChildAt(i);
            int type = parent.getAdapter().getItemViewType(parent.getChildAdapterPosition(childView));//重点，这样可以得到整个列表，而不是可见区域
            if (type == NetFragmentAdapter.BANNER || type == NetFragmentAdapter.TYPE_SONG) {
                outRect.bottom = mTextRect.height();
            } else {
                outRect.bottom = mDividerHeight;
            }
        }
    }

    @Override
    public void onDraw(Canvas c, RecyclerView parent, RecyclerView.State state) {
        super.onDraw(c, parent, state);
        int childCount = parent.getChildCount();
        for (int i = 0; i < childCount - 1; i++) {
            View childView = parent.getChildAt(i);
            int type = parent.getAdapter().getItemViewType(parent.getChildAdapterPosition(childView));
            if (type == NetFragmentAdapter.BANNER) {//在Banner下
                int left = (int) (childView.getWidth() / 2 - instrumentTextLength / 2);
                int bottom = childView.getBottom() + mTextRect.height();
                c.drawText(instrument, left, bottom, mTextPaint);
            } else if (type == NetFragmentAdapter.TYPE_SONG) {//在乐器下
                int left = (int) (childView.getWidth() / 2 - hotTextLength / 2);
                int bottom = childView.getBottom() + mTextRect.height();
                c.drawText(hot, left, bottom, mTextPaint);
            } else {
                float top = childView.getBottom();
                float bottom = top + mDividerHeight;
                int left = parent.getPaddingLeft();
                int right = parent.getWidth() - parent.getPaddingRight();
                c.drawRect(left, top, right, bottom, mDividerPaint);
            }
        }
    }
}
