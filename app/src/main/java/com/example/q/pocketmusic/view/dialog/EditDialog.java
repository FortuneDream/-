package com.example.q.pocketmusic.view.dialog;

import android.content.Context;
import android.support.v7.app.AlertDialog;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.example.q.pocketmusic.R;
import com.example.q.pocketmusic.util.common.ToastUtil;

/**
 * Created by 鹏君 on 2016/11/22.
 */

//Builder设计模式
public class EditDialog {
    private TextView okTxt;
    private TextView cancelTxt;
    private EditText hintEdit;
    private TextView titleTxt;
    private String mTitle;
    private String mHitStr;
    private String mEditStr;
    private String mOkStr;
    private String mCancelStr;
    private Builder.OnSelectedListener listener;
    private AlertDialog dialog;

    private EditDialog(Builder builder) {
        AlertDialog.Builder alertBuilder = new AlertDialog.Builder(builder.getContext());
        View view = View.inflate(builder.getContext(), R.layout.dialog_edit, null);
        okTxt = (TextView) view.findViewById(R.id.ok);
        cancelTxt = (TextView) view.findViewById(R.id.cancel);
        hintEdit = (EditText) view.findViewById(R.id.name_edt);
        titleTxt = (TextView) view.findViewById(R.id.title_tv);
        alertBuilder.setView(view).setCancelable(false);//要用同一个View

        //通过Builder设置
        this.mTitle = builder.getTitle();
        this.mOkStr = builder.getOkStr();
        this.mCancelStr = builder.getCancelStr();
        this.mHitStr = builder.getEditHint();
        this.mEditStr = builder.getEditStr();
        this.listener = builder.getListener();

        titleTxt.setText(mTitle);
        hintEdit.setHint(mHitStr);
        hintEdit.setText(mEditStr);
        okTxt.setText(mOkStr);
        cancelTxt.setText(mCancelStr);

        okTxt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (listener != null) {
                    String str = hintEdit.getText().toString().trim();
                    if (TextUtils.isEmpty(str)) {
                        ToastUtil.showToast( "不能为空哦~");
                    } else {
                        listener.onSelectedOk(str);
                        dismiss();
                    }
                }
            }
        });

        cancelTxt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (listener != null) {
                    listener.onSelectedCancel();
                }
                dismiss();
            }
        });

        dialog = alertBuilder.create();
    }

    public EditDialog show() {
        dialog.show();
        return this;
    }

    public void dismiss() {
        dialog.dismiss();
    }

    public static class Builder {
        private String mTitle = "请输入文件夹名字";
        private String mEditHint = "...";
        private String mEditStr = "";
        private String mOkStr = "确定";
        private String mCancelStr = "取消";
        private Context context;
        private OnSelectedListener listener;

        public OnSelectedListener getListener() {
            return listener;
        }

        public Builder setListener(OnSelectedListener listener) {
            this.listener = listener;
            return this;
        }

        public interface OnSelectedListener {
            void onSelectedOk(String str);

            void onSelectedCancel();
        }


        public Builder(Context context) {
            this.context = context;
        }


        /**
         * 设置
         */
        //title
        public Builder setTitle(String title) {
            this.mTitle = title;
            return this;
        }

        //hint
        public Builder setHint(String hint) {
            this.mEditHint = hint;
            return this;
        }

        //editText
        public Builder setEditStr(String editStr) {
            this.mEditStr = editStr;
            return this;
        }

        //okStr
        public Builder setOkStr(String okStr) {
            this.mOkStr = okStr;
            return this;
        }

        //cancelStr
        public Builder setCancelStr(String cancelStr) {
            this.mCancelStr = cancelStr;
            return this;
        }


        /**
         * 返回
         */

        public String getTitle() {
            return mTitle;
        }

        public String getEditHint() {
            return this.mEditHint;
        }

        public String getOkStr() {
            return this.mOkStr;
        }

        public String getCancelStr() {
            return this.mCancelStr;
        }

        public String getEditStr() {
            return this.mEditStr;
        }

        public Context getContext() {
            return context;
        }

        //构建
        public EditDialog create() {
            return new EditDialog(this);
        }


    }
}
