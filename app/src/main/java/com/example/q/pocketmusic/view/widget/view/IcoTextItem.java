package com.example.q.pocketmusic.view.widget.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.allenliu.badgeview.BadgeFactory;
import com.allenliu.badgeview.BadgeView;
import com.example.q.pocketmusic.R;
import com.example.q.pocketmusic.util.ConvertUtil;

/**
 * Created by YQ on 2016/10/17.
 */
//假如继承LinearLayout会不会报错？
public class IcoTextItem extends RelativeLayout {
    private TextView mTitleTv;
    private TextView mSubTv;
    private ImageView mIco;

    private String mTitle;
    private int icoId;
    private BadgeView badgeView;

    public IcoTextItem(Context context) {
        this(context, null);
    }

    public IcoTextItem(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public IcoTextItem(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(attrs);
    }


    private void init(AttributeSet attrs) {
        inflate(getContext(), R.layout.item_ico_text, this);
        mTitleTv = getView(R.id.title_tv);
        mIco = getView(R.id.ico);
        mSubTv = getView(R.id.subtitle_tv);

        TypedArray types = getContext().obtainStyledAttributes(attrs, R.styleable.IcoTextItem);
        mTitle = types.getString(R.styleable.IcoTextItem_itemTitleText);
        icoId = types.getResourceId(R.styleable.IcoTextItem_itemTitleIco, 0);
        types.recycle();

        //初始化文字
        mTitleTv.setText(mTitle);
        mIco.setImageResource(icoId);
    }

    public void setSubText(String subText) {
        mSubTv.setVisibility(VISIBLE);
        mSubTv.setText(subText);
    }

    public void bindBadge(Boolean isBind) {
        if (isBind) {
            if (badgeView == null) {
                badgeView = BadgeFactory.createDot(getContext())
                        .bind(mIco);
            }
        } else {
            if (badgeView != null) {
                badgeView.unbind();
            }
        }
    }

    public <T extends View> T getView(int id) {
        return (T) findViewById(id);
    }


}
