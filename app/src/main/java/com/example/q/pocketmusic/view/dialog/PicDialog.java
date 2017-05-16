package com.example.q.pocketmusic.view.dialog;

import android.content.Context;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.example.q.pocketmusic.R;
import com.example.q.pocketmusic.module.home.seek.ask.comment.PicAdapter;
import com.jude.easyrecyclerview.EasyRecyclerView;

import java.util.List;

/**
 * Created by Cloud on 2017/2/5.
 */

public class PicDialog {
    private EasyRecyclerView recycler;
    private Button enterBtn;
    private ImageView closeIv;
    private PicAdapter picAdapter;
    private AlertDialog dialog;


    private PicDialog(final Builder builder) {
        View view = View.inflate(builder.getContext(), R.layout.dialog_pic, null);
        dialog = new AlertDialog.Builder(builder.getContext())
                .setView(view)
                .create();
        recycler = (EasyRecyclerView) view.findViewById(R.id.dialog_recycler);
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

        picAdapter = new PicAdapter(builder.getContext());
        recycler.setLayoutManager(new LinearLayoutManager(builder.getContext()));
        picAdapter.addAll(builder.getList());
        recycler.setAdapter(picAdapter);
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
        private List<String> list;

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

        public List<String> getList() {
            return list;
        }

        public Builder setList(List<String> list) {
            this.list = list;
            return this;
        }

        public PicDialog create() {
            return new PicDialog(this);
        }
    }
}
