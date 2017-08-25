package com.example.q.pocketmusic.module.user.notify.suggestion;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.example.q.pocketmusic.R;

/**
 * Created by 鹏君 on 2017/8/23.
 * （￣m￣）
 */
//左右交叉的Decoration
public class LeftAndRightTagDecoration extends RecyclerView.ItemDecoration {
    private int tagWidth;
    private Paint leftPaint;
    private Paint rightPaint;

    public LeftAndRightTagDecoration(Context context) {
        leftPaint = new Paint();
        leftPaint.setColor(context.getResources().getColor(R.color.md_green_400));
        rightPaint = new Paint();
        rightPaint.setColor(context.getResources().getColor(R.color.md_orange_400));
        this.tagWidth = 20;
    }


    @Override
    public void onDrawOver(Canvas c, RecyclerView parent, RecyclerView.State state) {
        super.onDrawOver(c, parent, state);
        int childCount = parent.getChildCount();
        for (int i = 0; i < childCount; i++) {
            View childView = parent.getChildAt(i);
            int position = parent.getChildAdapterPosition(childView);
            boolean isLeft;
            if (position % 2 == 0) {
                isLeft = true;
            } else {
                isLeft = false;
            }
            if (isLeft) {
                float left = childView.getLeft();
                float top = childView.getTop();
                float right = left + tagWidth;
                float bottom = childView.getBottom();
                c.drawRect(left, top, right, bottom, leftPaint);
            } else {
                float left = childView.getRight() - tagWidth;
                float top = childView.getTop();
                float right = childView.getRight();
                float bottom = childView.getBottom();
                c.drawRect(left, top, right, bottom, rightPaint);
            }
        }
    }
}
