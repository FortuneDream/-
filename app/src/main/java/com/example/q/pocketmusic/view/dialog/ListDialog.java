package com.example.q.pocketmusic.view.dialog;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;


import com.example.q.pocketmusic.R;

import com.example.q.pocketmusic.module.home.profile.InstrumentAdapter;
import com.example.q.pocketmusic.util.common.ConvertUtil;
import com.jude.easyrecyclerview.EasyRecyclerView;
import com.jude.easyrecyclerview.adapter.RecyclerArrayAdapter;
import com.jude.easyrecyclerview.decoration.DividerDecoration;

import java.util.List;

/**
 * Created by 鹏君 on 2017/1/13.
 */

public class ListDialog {
    private EasyRecyclerView recycler;
    private TextView titleTv;
    private Button cancelBtn;
    private AlertDialog listDialog;

    private ListDialog(final Builder builder) {
        View view = View.inflate(builder.getContext(), R.layout.dialog_list, null);
        listDialog = new AlertDialog.Builder(builder.getContext())
                .setView(view)
                .setCancelable(false)
                .create();
        recycler = (EasyRecyclerView) view.findViewById(R.id.dialog_recycler);
        titleTv = (TextView) view.findViewById(R.id.title_tv);
        cancelBtn = (Button) view.findViewById(R.id.cancel_btn);
        if (!TextUtils.isEmpty(builder.getTitleStr())) {
            titleTv.setText(builder.getTitleStr());
        } else {
            titleTv.setText("无标题");
        }
        final InstrumentAdapter adapter = new InstrumentAdapter(builder.getContext());
        recycler.setAdapter(adapter);
        recycler.setLayoutManager(new LinearLayoutManager(builder.getContext()));
        recycler.addItemDecoration(new DividerDecoration(Color.GRAY, 1, ConvertUtil.Dp2Px(builder.getContext(),48), ConvertUtil.Dp2Px(builder.getContext(),48)));

        //加载数据
        adapter.addAll(builder.getList());
        adapter.setOnItemClickListener(new RecyclerArrayAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                if (builder.getOnItemSelectListener() != null) {
                    builder.getOnItemSelectListener().onSelect(position,adapter.getItem(position));
                }
            }
        });
        cancelBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
    }

    public ListDialog show() {
        listDialog.show();
        return this;
    }

    public void dismiss() {
        listDialog.dismiss();
    }

    public static class Builder {
        private Context context;
        private String titleStr;//标题
        private List<String> list;//列表
        private OnItemSelectListener onItemSelectListener;

        public OnItemSelectListener getOnItemSelectListener() {
            return onItemSelectListener;
        }

        public Builder setOnItemSelectListener(OnItemSelectListener onItemSelectListener) {
            this.onItemSelectListener = onItemSelectListener;
            return this;
        }

        public interface OnItemSelectListener {
            void onSelect(int position,String str);
        }

        public Context getContext() {
            return context;
        }


        public Builder(Context context) {
            this.context = context;
        }

        public String getTitleStr() {
            return titleStr;
        }

        public Builder setTitleStr(String titleStr) {
            this.titleStr = titleStr;
            return this;
        }

        public List<String> getList() {
            return list;
        }

        public Builder setList(List<String> list) {
            this.list = list;
            return this;
        }

        //构建
        public ListDialog build() {
            return new ListDialog(this);
        }
    }


}
