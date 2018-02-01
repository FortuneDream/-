package com.example.q.pocketmusic.util.common;

import android.graphics.Rect;
import android.graphics.drawable.BitmapDrawable;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.PopupWindow;

public class GuidePopupWindow {
    private PopupWindow mBubbleWindow;
    public static final int TOP = 0;
    public static final int BOTTOM = 1;
    public static final int LEFT = 2;
    public static final int RIGHT = 3;

    private long mShowDuration;
    private int mRelatePosition;
    private float mContentAlignPosition;
    private float mTargetAlignPosition;
    private float mMargin;
    private boolean mOutSideTouchable = false;
    private boolean mFocusable = false;
    private int mAnimationStyle = -1;
    private long mDelayShow = -1;
    private boolean mUseContentAbsoluteAlign = false;
    private boolean mUseTargetAbsoluteAlign = false;
    private int mTargetAbsoluteAlignPosition;
    private int mContentAbsoluteAlignPosition;
    private boolean mPositiveContentDirection = true;
    private boolean mPositiveTargetDirection = true;
    private ShowRunnable mShowRunnable;
    private View.OnClickListener mListener;
    private PopupWindow.OnDismissListener mDismissListener;
    private PreShowHook mPreShowHook;

    /**
     * 设置相对目标view的位置，必须设置否则不会生效
     * @param position TOP,BOTTOM,LEFT,RIGHT
     */
    public GuidePopupWindow setPositionRelateToTarget(int position){
        mRelatePosition = position;
        return this;
    }

    /**
     * 设置弹窗的对齐点，该点为相对弹窗自身的百分比，
     * 比如相对位置是 TOP 则对齐点为弹窗宽度的百分比
     * @param alignPosition 取值 0-1f
     */
    public GuidePopupWindow setContentAlignPosition(float alignPosition){
        mContentAlignPosition = alignPosition;
        return this;
    }

    /**
     * 设置目标view的对齐点，该点为相对目标view自身的百分比，
     * 比如相对位置是 TOP 则对齐点为目标view宽度的百分比
     * @param alignPosition 0-1f
     */
    public GuidePopupWindow setTargetAlignPosition(float alignPosition){
        mTargetAlignPosition = alignPosition;
        return this;
    }

    /**
     * 设置绝对对齐点
     * @param alignPosition 绝对位置
     * @param direction 计算方向，true 与x or y方向一致
     */
    public GuidePopupWindow setContentAbsoluteAlignPosition(int alignPosition, boolean direction){
        mContentAbsoluteAlignPosition = alignPosition;
        mPositiveContentDirection = direction;
        mUseContentAbsoluteAlign = true;
        return this;
    }

    /**
     * 设置绝对对齐点
     * @param alignPosition 绝对位置
     * @param direction 计算方向，true 与x or y方向一致
     */
    public GuidePopupWindow setTargetAbsoluteAlignPosition(int alignPosition, boolean direction){
        mTargetAbsoluteAlignPosition = alignPosition;
        mPositiveTargetDirection = direction;
        mUseTargetAbsoluteAlign = true;
        return this;
    }

    /**
     * 距离目标view的距离，例如TOP，则margin为y方向
     */
    public GuidePopupWindow setMarginToTarget(float margin){
        mMargin = margin;
        return this;
    }

    /**
     * 自动消失时间，不设置不会自动消失
     * @param duration >0
     */
    public GuidePopupWindow setShowDuration(long duration){
        mShowDuration = duration;
        return this;
    }

    /**
     * 点击外部弹窗是否消失，true 消失，false不消失
     * 该属性只有 focus 为false 生效
     */
    public GuidePopupWindow setOutSideTouchable(boolean outSideTouchable) {
        mOutSideTouchable = outSideTouchable;
        return this;
    }

    public GuidePopupWindow setFocusable(boolean focusable) {
        mFocusable = focusable;
        return this;
    }

    /**
     * 设置点击监听
     */
    public GuidePopupWindow setOnClickListener(View.OnClickListener listener) {
        mListener = listener;
        return this;
    }

    /**
     * 设置动画
     */
    public GuidePopupWindow setAnimationStyle(int animationStyle) {
        this.mAnimationStyle = animationStyle;
        return this;
    }

    /**
     * 设置消失监听
     */
    public GuidePopupWindow setOnDismissListener(PopupWindow.OnDismissListener dismissListener) {
        this.mDismissListener = dismissListener;
        return this;
    }

    /**
     * 延迟展示
     */
    public GuidePopupWindow setDelayShow(long delay) {
        mDelayShow = delay;
        return this;
    }

    /**
     * show之前处理一些逻辑
     */
    public GuidePopupWindow setPreShowHook(PreShowHook hook) {
        mPreShowHook = hook;
        return this;
    }

    /**
     * 是否正在显示
     */
    public boolean isShowing() {
        return mBubbleWindow != null && mBubbleWindow.isShowing();
    }

    public View getContentView() {
        if (mBubbleWindow != null) {
            return mBubbleWindow.getContentView();
        }
        return null;
    }

    /**
     * 展示弹窗
     * @param targetView 目标 view
     * @param contentView 弹窗 view
     */
    public void show(View targetView, View contentView) {
        show(targetView, contentView,
                ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT);
    }

    public void show(View targetView, View contentView, int popWidth, int popHeight) {
        if (targetView == null || contentView == null) return;
        if (mBubbleWindow != null
                && mBubbleWindow.isShowing()) {
            dismiss();
        }
        if (checkParams()) return;
        mBubbleWindow = new PopupWindow(contentView,
                popWidth,
                popHeight,
                mFocusable);
        mBubbleWindow.setBackgroundDrawable(new BitmapDrawable());
        mBubbleWindow.setOutsideTouchable(mOutSideTouchable);
        mBubbleWindow.setAnimationStyle(mAnimationStyle);
        int width = 0;
        int height = 0;
        try {
            mBubbleWindow.getContentView().measure(0, 0);
            width = mBubbleWindow.getContentView().getMeasuredWidth();
            height = mBubbleWindow.getContentView().getMeasuredHeight();
        } catch (Exception e) {

        }
        if (width <= 0 || height <= 0) {
            mBubbleWindow = null;
            return;
        }
        if (mListener != null) {
            contentView.setOnClickListener(mListener);
        }
        if (mDismissListener != null) {
            mBubbleWindow.setOnDismissListener(mDismissListener);
        }
        mShowRunnable = new ShowRunnable(width, height);
        mShowRunnable.setContentView(contentView);
        mShowRunnable.setTargetView(targetView);
        if (mDelayShow > 0) {
            targetView.postDelayed(mShowRunnable, mDelayShow);
        } else {
            targetView.post(mShowRunnable);
        }
    }

    /**
     * dismiss弹窗，如果页面销毁务必调用该方法并将实例置空
     */
    public void dismiss() {
        if (mBubbleWindow != null && mBubbleWindow.isShowing()) {
            mBubbleWindow.dismiss();
        }
        mBubbleWindow = null;
        mListener = null;
        mDismissListener = null;
        releaseShowRunnable();
    }

    /**
     * 重置弹窗属性，如果一个实例展示不同的弹窗，show之前先reset
     * 否则将会持有上一个弹窗的属性
     */
    public GuidePopupWindow reset() {
        mShowDuration = -1;
        mRelatePosition = -1;
        mContentAlignPosition = 0;
        mTargetAlignPosition = 0;
        mMargin = 0;
        mListener = null;
        mDismissListener = null;
        mOutSideTouchable = false;
        mFocusable = false;
        mPreShowHook = null;
        mDelayShow = -1;
        mUseContentAbsoluteAlign = false;
        mUseTargetAbsoluteAlign = false;
        mTargetAbsoluteAlignPosition = 0;
        mContentAbsoluteAlignPosition = 0;
        mPositiveContentDirection = true;
        mPositiveTargetDirection = true;
        return this;
    }

    private void releaseShowRunnable() {
        if (mShowRunnable != null) {
            mShowRunnable.releaseShowRunnable();
        }
        mShowRunnable = null;
    }

    private boolean checkParams() {
        boolean inValidPosition = mRelatePosition < 0 || mRelatePosition > 4;
        boolean contentAlign = false;
        boolean targetAlign = false;
        if (!mUseContentAbsoluteAlign) {
            contentAlign = mContentAlignPosition < 0f || mContentAlignPosition > 1f;
        }

        if (!mUseTargetAbsoluteAlign) {
            targetAlign = mTargetAlignPosition < 0f || mTargetAlignPosition > 1f;
        }
        return inValidPosition
                || contentAlign
                || targetAlign;
    }

    private class ShowRunnable implements Runnable {
        private int width;
        private int height;
        View targetView;
        View contentView;
        DismissRunnable dismissRunnable;

        public void setTargetView(View targetView) {
            this.targetView = targetView;
        }

        public void setContentView(View contentView) {
            this.contentView = contentView;
        }

        public ShowRunnable(int width, int height) {
            this.width = width;
            this.height = height;
        }

        void releaseShowRunnable() {
            if (targetView != null) {
                targetView.removeCallbacks(this);
                targetView.removeCallbacks(dismissRunnable);
            }
            dismissRunnable = null;
        }

        @Override
        public void run() {
            if (targetView == null || targetView.getVisibility() != View.VISIBLE ) return;
            if (mPreShowHook != null && !mPreShowHook.onPreShow(mBubbleWindow)) {
                return;
            }
            Rect rect = new Rect();
            if (!targetView.getGlobalVisibleRect(rect)) {
                return;
            }
            try {
                int[] pos = calculatePositionXY(width, height);
                mBubbleWindow.showAtLocation(targetView, Gravity.LEFT | Gravity.TOP, pos[0], pos[1]);
                if (mShowDuration > 0) {
                    dismissRunnable = new DismissRunnable();
                    targetView.postDelayed(dismissRunnable, mShowDuration);
                }
            } catch (Exception e) {
                dismiss();
            }
        }

        private int[] calculatePositionXY(int width, int height) {
            int[] location = new int[2];
            targetView.getLocationOnScreen(location);
            int targetWidth = targetView.getMeasuredWidth();
            int targetHeight = targetView.getMeasuredHeight();
            int targetX = location[0];
            int targetY = location[1];
            switch (mRelatePosition) {
                case TOP:
                case BOTTOM:
                    return calculateTopOrBottomPositionXY(width, height,
                            targetWidth, targetHeight,
                            targetX, targetY);
                case LEFT:
                case RIGHT:
                    return calculateLeftOrRightPositionXY(width, height,
                            targetHeight, targetWidth,
                            targetX, targetY);
            }
            return new int[]{0,0};
        }

        private int[] calculateLeftOrRightPositionXY(int width, int height,
                                                     int targetHeight, int targetWidth,
                                                     int targetX, int targetY) {
            float targetAlignHeight = 0;
            float contentAlignHeight = 0;
            if (mUseTargetAbsoluteAlign) {
                targetAlignHeight = mPositiveTargetDirection
                        ? mTargetAbsoluteAlignPosition : targetHeight - mTargetAbsoluteAlignPosition;
            } else {
                targetAlignHeight = targetHeight * mTargetAlignPosition;
            }
            if (mUseContentAbsoluteAlign) {
                contentAlignHeight = mPositiveContentDirection
                        ? mContentAbsoluteAlignPosition : height - mContentAbsoluteAlignPosition;
            } else {
                contentAlignHeight = height * mContentAlignPosition;
            }
            int posY = (int) (targetY + (targetAlignHeight - contentAlignHeight));
            int posX = 0;
            if (mRelatePosition == LEFT) {
                posX = (int) (targetX - width - mMargin);
            } else {
                posX = (int) (targetX + targetWidth + mMargin);
            }
            return new int[]{posX, posY};
        }

        private int[] calculateTopOrBottomPositionXY(int width, int height,
                                                     int targetWidth, int targetHeight,
                                                     int targetX, int targetY) {
            float targetAlignWidth = 0;
            float contentAlignWidth = 0;
            if (mUseTargetAbsoluteAlign) {
                targetAlignWidth = mPositiveTargetDirection
                        ? mTargetAbsoluteAlignPosition : targetWidth - mTargetAbsoluteAlignPosition;
            } else {
                targetAlignWidth = targetWidth * mTargetAlignPosition;
            }
            if (mUseContentAbsoluteAlign) {
                contentAlignWidth = mPositiveContentDirection
                        ? mContentAbsoluteAlignPosition : width - mContentAbsoluteAlignPosition;
            } else {
                contentAlignWidth = width * mContentAlignPosition;
            }
            int posX = (int) (targetX + (targetAlignWidth - contentAlignWidth));
            int posY = 0;
            if (mRelatePosition == TOP) {
                posY = (int) (targetY - height - mMargin);
            } else {
                posY = (int) (targetY + targetHeight + mMargin);
            }
            return new int[]{posX, posY};
        }
    }

    private class DismissRunnable implements Runnable {

        @Override
        public void run() {
            try {
                dismiss();
            } catch (Exception e) {
                //ignore
            }

        }
    }

    /**
     * show 之前调用，可以自定义 PopupWindow 相关属性
     */
    public interface PreShowHook {
        // 返回false则不会再show
        boolean onPreShow(PopupWindow popupWindow);
    }
}
