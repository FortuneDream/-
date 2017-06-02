package com.example.q.pocketmusic.view.dialog;

import android.content.Context;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.example.q.pocketmusic.R;
import com.example.q.pocketmusic.util.DisplayStrategy;

/**
 * Created by 鹏君 on 2017/2/5.
 */

public class PicDialog {
    private Button enterBtn;
    private ImageView closeIv;
    private ImageView firstIv;
    private AlertDialog dialog;


    private PicDialog(final Builder builder) {
        View view = View.inflate(builder.getContext(), R.layout.dialog_pic, null);
        dialog = new AlertDialog.Builder(builder.getContext())
                .setView(view)
                .create();
        firstIv= (ImageView) view.findViewById(R.id.first_iv);
        enterBtn = (Button) view.findViewById(R.id.enter_btn);
        closeIv = (ImageView) view.findViewById(R.id.close_iv);
        enterBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (builder.getOnSelectListener() != null) {
                    builder.getOnSelectListener().onSelectOk();
                }

            }
        });
        closeIv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
        new DisplayStrategy().display(builder.getContext(),builder.getFirstPic(),firstIv);
    }

    public PicDialog show() {
        dialog.show();
        return this;
    }

    public void dismiss() {
        dialog.dismiss();
    }

    public static class Builder {
        private OnSelectListener onSelectListener;
        private Context context;
        private String firstPic;

        public OnSelectListener getOnSelectListener() {
            return onSelectListener;
        }

        public Builder setOnSelectListener(OnSelectListener onSelectListener) {
            this.onSelectListener = onSelectListener;
            return this;
        }

        public Context getContext() {
            return context;
        }

        public interface OnSelectListener {
            void onSelectOk();
        }

        public Builder(Context context) {
            this.context = context;
        }

        public String getFirstPic() {
            return firstPic;
        }

        public Builder setFirstPath(String firstPic) {
            this.firstPic = firstPic;
            return this;
        }

        public PicDialog create() {
            return new PicDialog(this);
        }
    }
}
