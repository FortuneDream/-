package com.example.q.pocketmusic.view.widget.view; /**
 * Created by 鹏君 on 2016/9/10.
 */


import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.q.pocketmusic.R;
import com.zcw.togglebutton.ToggleButton;

public class SwitchSettingItem extends RelativeLayout {
    private TextView mTitleTv;
    private TextView mSubtitleTv;
    private ToggleButton mToggleBtn;

    private String mTitle;
    private String mSubtitleIsFalse;
    private String mSubtitleIsTrue;
    private boolean isInvisible;

    public OnSelectListener onSelectListener;

    public OnSelectListener getOnSelectListener() {
        return onSelectListener;
    }

    public void setOnSelectListener(OnSelectListener onSelectListener) {
        this.onSelectListener = onSelectListener;
    }

    public interface OnSelectListener {
        void onSelected(boolean isSelect);
    }

    public SwitchSettingItem(Context context) {
        this(context, null);
    }

    public SwitchSettingItem(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public SwitchSettingItem(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(attrs);
    }

    private void init(AttributeSet attrs) {
        inflate(getContext(),R.layout.item_setting,this);
        mToggleBtn=getView(R.id.toggle_btn);
        mTitleTv=getView(R.id.title_tv);
        mSubtitleTv=getView(R.id.subtitle_tv);

        TypedArray types = getContext().obtainStyledAttributes(attrs, R.styleable.SwitchSettingItem);
        mTitle = types.getString(R.styleable.SwitchSettingItem_itemTitle);
        mSubtitleIsFalse = types.getString(R.styleable.SwitchSettingItem_itemSubtitleIsFalse);
        mSubtitleIsTrue = types.getString(R.styleable.SwitchSettingItem_itemSubtitleIsTrue);
        isInvisible = types.getBoolean(R.styleable.SwitchSettingItem_itemCbVisible, true);
        types.recycle();


        //初始化文字,cb状态
        mTitleTv.setText(mTitle);
        mSubtitleTv.setText(mSubtitleIsFalse);//默认false


        mToggleBtn.setToggleOff();

        //是否有switch
        if (isInvisible) {
            mToggleBtn.setVisibility(VISIBLE);
        } else {
            mToggleBtn.setVisibility(INVISIBLE);
        }

        mToggleBtn.setOnToggleChanged(new ToggleButton.OnToggleChanged() {
            @Override
            public void onToggle(boolean on) {
                if (onSelectListener != null) {
                    onSelectListener.onSelected(on);
                    if (on) {
                        mSubtitleTv.setText(mSubtitleIsTrue);
                    } else {
                        mSubtitleTv.setText(mSubtitleIsFalse);
                    }
                }
            }
        });
    }

    public void setChecked(boolean isChecked) {
        if (isChecked) {
            mToggleBtn.setToggleOn();
            mSubtitleTv.setText(mSubtitleIsTrue);
        } else {
            mSubtitleTv.setText(mSubtitleIsFalse);
            mToggleBtn.setToggleOff();
        }
    }

    public void setSubtitleVisible(int Visible){
        mSubtitleTv.setVisibility(Visible);
    }

    public <T extends View> T getView(int id) {
        return (T) findViewById(id);
    }
}
