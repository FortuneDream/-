package com.example.q.pocketmusic.view.widget.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.support.annotation.IdRes;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import com.example.q.pocketmusic.R;
import com.example.q.pocketmusic.util.common.ConvertUtil;

/**
 * Created by 鹏君 on 2017/3/5.
 */

public class GuaGuaKa extends View {
    private Paint mMainPaint;//主要
    private Paint mTextPaint;//底部文字
    private Path path;
    private Bitmap bitmap;//表面
    private Canvas mCanvas;//缓冲画布
    private String mText;//底部文字
    private int mTextSize;//文字大小
    private int mTextColor;//文字颜色
    private int mEraserSize;//橡皮擦大小
    private Rect mTextBound = new Rect();
    private PorterDuffXfermode duffXfermode = new PorterDuffXfermode(PorterDuff.Mode.DST_OUT);
    private int measureWidth;
    private int measureHeight;
    private boolean isComplete;
    private final int DEFAULT_ERASER_SIZE = ConvertUtil.Dp2Px(getContext(), 16);
    private final int DEFAULT_TEXT_SIZE = ConvertUtil.Dp2Px(getContext(), 20);
    private final int DEFAULT_TEXT_COLOR = getResources().getColor(R.color.colorAccent);
    private OnCompleteListener onCompleteListener;

    public void setOnCompleteListener(OnCompleteListener onCompleteListener) {
        this.onCompleteListener = onCompleteListener;
    }

    public GuaGuaKa(Context context) {
        this(context, null);
    }

    public GuaGuaKa(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public interface OnCompleteListener{
        void onComplete();
    }


    public GuaGuaKa(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs);
        path = new Path();
        initPaint();
    }

    private void init(Context context, AttributeSet attributeSet) {
        TypedArray array = context.obtainStyledAttributes(attributeSet, R.styleable.GuaGuaKa);
        mText = array.getString(R.styleable.GuaGuaKa_Reward_Text);
        if (mText == null) {
            mText = "";
        }
        mTextSize = array.getDimensionPixelSize(R.styleable.GuaGuaKa_Reward_Eraser_Size, DEFAULT_TEXT_SIZE);
        mTextColor = array.getColor(R.styleable.GuaGuaKa_Reward_Text_Color, DEFAULT_TEXT_COLOR);
        mEraserSize = array.getDimensionPixelSize(R.styleable.GuaGuaKa_Reward_Eraser_Size, DEFAULT_ERASER_SIZE);
        array.recycle();


    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        measureWidth = setDefaultMeasureSpec(200, widthMeasureSpec);
        measureHeight = setDefaultMeasureSpec(200, heightMeasureSpec);
        setMeasuredDimension(measureWidth, measureHeight);
        //图层灰色
        //在这里创建，不会创建多次重新绘制
        bitmap = Bitmap.createBitmap(measureWidth, measureHeight, Bitmap.Config.ARGB_8888);
        mCanvas = new Canvas(bitmap);
        mCanvas.drawColor(Color.GRAY);
    }

    //设置图片
    private Bitmap resizeBitmap(@IdRes int id) {
        BitmapFactory.Options options = new BitmapFactory.Options();
        bitmap = BitmapFactory.decodeResource(getResources(), id, options);
        int scaleHeight = measureHeight / options.outHeight;
        int scaleWidth = measureWidth / options.outWidth;
        Matrix matrix = new Matrix();
        matrix.postScale(scaleWidth, scaleHeight);
        bitmap = Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), matrix, true);
        return bitmap;
    }


    private int setDefaultMeasureSpec(int defaultSize, int measureSpec) {
        int result = defaultSize;
        int specMode = MeasureSpec.getMode(measureSpec);
        int specSize = MeasureSpec.getSize(measureSpec);
        switch (specMode) {
            case MeasureSpec.EXACTLY:
                result = specSize;//match_parent或者80dp
                break;
            case MeasureSpec.AT_MOST:
            case MeasureSpec.UNSPECIFIED:
                result = defaultSize;//wrap_content默认
                break;
        }
        return result;
    }

    //初始画笔
    private void initPaint() {
        setMainPaint();
        setTextPaint();
    }

    //橡皮擦
    private void setMainPaint() {
        mMainPaint = new Paint();
        mMainPaint.setStrokeWidth(mEraserSize);
        mMainPaint.setStyle(Paint.Style.STROKE);
        mMainPaint.setAntiAlias(true);
        mMainPaint.setStrokeJoin(Paint.Join.ROUND);
        mMainPaint.setStrokeCap(Paint.Cap.ROUND);
    }

    //底部文字
    private void setTextPaint() {
        mTextPaint = new Paint();
        mTextPaint.setStyle(Paint.Style.STROKE);
        mTextPaint.setTextSize(mTextSize);
        mTextPaint.setColor(mTextColor);
        mTextPaint.getTextBounds(mText, 0, mText.length(), mTextBound);//得到文字的Rect
    }


    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawText(mText, getWidth() / 2 - mTextBound.width() / 2, getHeight() / 2 - mTextBound.height() / 2, mTextPaint);//先绘制底部文字
        if (!isComplete) {//擦除完成
            drawPath();
            canvas.drawBitmap(bitmap, 0, 0, null);//把Bitmap放入画布
        }
    }

    private void drawPath() {
        mMainPaint.setXfermode(duffXfermode);//取下层绘制非交集部分
        mCanvas.drawPath(path, mMainPaint);//新建一个Canvas，在上面设置bitmap，只有的画笔操作都在这个Canvas上，也是bitmap
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        float x = event.getX();
        float y = event.getY();
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                path.moveTo(x, y);
                break;
            case MotionEvent.ACTION_MOVE:
                path.lineTo(x, y);
                break;
            case MotionEvent.ACTION_UP:
                new Thread(runnable).start();
                break;
        }
        invalidate();
        return true;
    }

    //设置底部文字
    public void setAwardText(String mText) {
        this.mText = mText;
        setTextPaint();
    }


    //检测擦除百分比
    private Runnable runnable = new Runnable() {
        private int[] mPixels;

        @Override
        public void run() {
            int w = getWidth();
            int h = getHeight();
            float wipeArea = 0;
            float totalArea = w * h;
            mPixels = new int[w * h];
            bitmap.getPixels(mPixels, 0, w, 0, 0, w, h);
            for (int i = 0; i < w; i++) {
                for (int j = 0; j < h; j++) {
                    int index = i + j * w;
                    if (mPixels[index] == 0) {
                        wipeArea++;
                    }
                }
            }
            if (wipeArea > 0 && totalArea > 0) {
                int percent = (int) (wipeArea * 100 / totalArea);
                if (percent > 50) {
                    isComplete = true;
                    post(new Runnable() {
                        @Override
                        public void run() {
                            onCompleteListener.onComplete();
                            bitmap.recycle();//回收
                        }
                    });//切换到主线程
                    postInvalidate();
                }
            }
        }
    };
}
