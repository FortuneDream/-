package com.example.q.pocketmusic.view.widget.net;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.ColorInt;
import androidx.annotation.DrawableRes;
import androidx.annotation.StringRes;
import androidx.annotation.StyleRes;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;

import com.example.q.pocketmusic.R;

/**
 * Created by 鹏君 on 2016/10/9.
 */

public class TitleBar extends Toolbar {
    //中心标题
    private TextView mCenterTitle;
    //中心icon
    private ImageView mCenterIcon;
    //左侧文字
    private TextView mNavigationText;
    //右侧文字
    private TextView mSettingText;
    //右侧按钮
    private ImageButton mSettingIcon;

    public TitleBar(Context context) {
        super(context);
    }

    public TitleBar(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public TitleBar(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    /**
     * 左侧文字
     *
     * @param Rid
     */
    public void setMyNavigationText(@StringRes int Rid) {
        setMyNavigationText(this.getContext().getText(Rid));
    }

    /**
     * ToolBar左侧有contentInsetStart 16Dp的空白，若需要可自己定义style修改
     * 详情请见 http://my.oschina.net/yaly/blog/502471
     *
     * @param text
     */
    public void setMyNavigationText(CharSequence text) {
        Context context = this.getContext();
        if (this.mNavigationText == null) {
            this.mNavigationText = new TextView(context);
            this.mNavigationText.setGravity(Gravity.CENTER_VERTICAL);
            this.mNavigationText.setSingleLine();
            this.mNavigationText.setEllipsize(TextUtils.TruncateAt.END);
            setMyNavigationTextAppearance(getContext(), R.style.TextAppearance_TitleBar_subTitle);
            //textView in left
            this.addMyView(this.mNavigationText, Gravity.START);
        }
        mNavigationText.setText(text);
    }

    public void setMyNavigationTextAppearance(Context context, @StyleRes int resId) {
        if (this.mNavigationText != null) {
            this.mNavigationText.setTextAppearance(context, resId);
        }
    }

    public void setMyNavigationTextColor(@ColorInt int color) {
        if (this.mNavigationText != null) {
            this.mNavigationText.setTextColor(color);
        }
    }

    public void setNavigationTextOnClickListener(OnClickListener listener) {
        if (mNavigationText != null) {
            mNavigationText.setOnClickListener(listener);
        }
    }

    /**
     * 居中标题
     *
     * @param Rid
     */
    public void setMyCenterTitle(@StringRes int Rid) {
        setMyCenterTitle(this.getContext().getText(Rid));
    }

    public void setMyCenterTitle(CharSequence text) {
        Context context = this.getContext();
        if (this.mCenterTitle == null) {
            this.mCenterTitle = new TextView(context);
            this.mCenterTitle.setGravity(Gravity.CENTER);
            this.mCenterTitle.setSingleLine();
            this.mCenterTitle.setEllipsize(TextUtils.TruncateAt.END);
            setMyCenterTextAppearance(getContext(), R.style.TextAppearance_TitleBar_Title);
            //textView in center
            this.addMyView(this.mCenterTitle, Gravity.CENTER);
        } else {
            if (this.mCenterTitle.getVisibility() != VISIBLE) {
                mCenterTitle.setVisibility(VISIBLE);
            }
        }
        if (mCenterIcon != null && mCenterIcon.getVisibility() != GONE) {
            mCenterIcon.setVisibility(GONE);
        }
        //隐藏toolbar自带的标题
        setTitle("");
        mCenterTitle.setText(text);
    }

    public void setMyCenterTextAppearance(Context context, @StyleRes int resId) {
        if (this.mCenterTitle != null) {
            this.mCenterTitle.setTextAppearance(context, resId);
        }
    }

    public void setMyCenterTextColor(@ColorInt int color) {
        if (this.mCenterTitle != null) {
            this.mCenterTitle.setTextColor(color);
        }
    }

    /**
     * 居中图标
     *
     * @param resId
     */
    public void setMyCenterIcon(@DrawableRes int resId) {
        setMyCenterIcon(ContextCompat.getDrawable(this.getContext(), resId));
    }

    public void setMyCenterIcon(Drawable drawable) {
        Context context = this.getContext();
        if (this.mCenterIcon == null) {
            this.mCenterIcon = new ImageView(context);
            this.mCenterIcon.setScaleType(ImageView.ScaleType.CENTER);
            //textView in center
            this.addMyView(this.mCenterIcon, Gravity.CENTER);
        } else {
            if (mCenterIcon.getVisibility() != VISIBLE) {
                mCenterIcon.setVisibility(VISIBLE);
            }
        }
        if (mCenterTitle != null && mCenterTitle.getVisibility() != GONE) {
            mCenterTitle.setVisibility(GONE);
        }
        //隐藏toolbar自带的标题
        setTitle("");
        mCenterIcon.setImageDrawable(drawable);
    }

    /**
     * 右侧文字
     *
     * @param Rid
     */
    public void setMySettingText(@StringRes int Rid) {
        setMySettingText(this.getContext().getText(Rid));
    }

    public void setMySettingText(CharSequence text) {
        Context context = this.getContext();
        if (this.mSettingText == null) {
            this.mSettingText = new TextView(context);
            this.mSettingText.setGravity(Gravity.CENTER);
            this.mSettingText.setSingleLine();
            this.mSettingText.setEllipsize(TextUtils.TruncateAt.END);
            setMySettingTextAppearance(getContext(), R.style.TextAppearance_TitleBar_subTitle);
            //textView in center
            int padding = (int) this.getContext().getResources().getDimension(R.dimen.title_right_margin);
            this.mSettingText.setPadding(padding, 0, padding, 0);

            this.addMyView(this.mSettingText, Gravity.END);
        } else {
            if (mSettingText.getVisibility() != VISIBLE) {
                mSettingText.setVisibility(VISIBLE);
            }
        }
        if (mSettingIcon != null && mSettingIcon.getVisibility() != GONE) {
            mSettingIcon.setVisibility(GONE);
        }
        mSettingText.setText(text);
    }

    public void setMySettingTextAppearance(Context context, @StyleRes int resId) {
        if (mSettingText != null) {
            mSettingText.setTextAppearance(context, resId);
        }
    }

    public void setMySettingTextColor(@ColorInt int color) {
        if (mSettingText != null) {
            mSettingText.setTextColor(color);
        }
    }

    public void setSettingTextOnClickListener(OnClickListener listener) {
        if (mSettingText != null) {
            mSettingText.setOnClickListener(listener);
        }
    }

    /**
     * 右侧图标
     *
     * @param resId
     */
    public void setMySettingIcon(@DrawableRes int resId) {
        setMySettingIcon(ContextCompat.getDrawable(this.getContext(), resId));
        //获取系统判定的最低华东距离
//        ViewConfiguration.get(this.getContext()).getScaledTouchSlop();
    }

    public void setMySettingIcon(Drawable drawable) {
        Context context = this.getContext();
        if (this.mSettingIcon == null) {
            this.mSettingIcon = new ImageButton(context);
            this.mSettingIcon.setId(R.id.title_bar_setting_icon);
            this.mSettingIcon.setBackground(null);
            //保持点击区域
            int padding = (int) this.getContext().getResources().getDimension(R.dimen.title_right_margin);
            this.mSettingIcon.setPadding(padding, 0, padding, 0);

            this.mSettingIcon.setScaleType(ImageView.ScaleType.CENTER);
            //textView in center
            this.addMyView(this.mSettingIcon, Gravity.END);
        } else {
            if (mSettingIcon.getVisibility() != VISIBLE) {
                mSettingIcon.setVisibility(VISIBLE);
            }
        }
        if (mSettingText != null && mSettingText.getVisibility() != GONE) {
            mSettingText.setVisibility(GONE);
        }
        mSettingIcon.setImageDrawable(drawable);
    }

    public void setSettingIconOnClickListener(OnClickListener listener) {
        if (mSettingIcon != null) {
            mSettingIcon.setOnClickListener(listener);
        }
    }

    /**
     * @param v
     * @param gravity
     */
    private void addMyView(View v, int gravity) {
        addMyView(v, gravity, 0, 0, 0, 0);
    }

    private void addMyView(View v, int gravity, int left, int top, int right, int bottom) {
        LayoutParams lp = new LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT, gravity);
        lp.setMargins(left, top, right, bottom);
        this.addView(v, lp);
    }

}
