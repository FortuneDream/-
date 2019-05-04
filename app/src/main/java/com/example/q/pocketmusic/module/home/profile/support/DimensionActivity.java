package com.example.q.pocketmusic.module.home.profile.support;

import android.content.ClipboardManager;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import androidx.appcompat.app.AppCompatActivity;

import com.dell.fortune.tools.IntentUtil;
import com.dell.fortune.tools.toast.ToastUtil;
import com.example.q.pocketmusic.R;
import com.example.q.pocketmusic.util.GuidePopHelper;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class DimensionActivity extends AppCompatActivity {
    public static final String PARAM_TYPE = "param_1";//微信还是支付宝
    @BindView(R.id.content_iv)
    ImageView contentIv;
    @BindView(R.id.copy_alipay_ll)
    LinearLayout copyAlipayLl;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dimension);
        ButterKnife.bind(this);
        int type = getIntent().getIntExtra(PARAM_TYPE, 0);
        if (type == IPayType.ALIPAY) {
            contentIv.setImageResource(R.drawable.alipay);
            contentIv.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    IntentUtil.openAliPay2Pay(DimensionActivity.this);
                }
            });
            ToastUtil.showToast("支付的备注里记得写上自己昵称哦~");
            GuidePopHelper.showClickMa(copyAlipayLl);
        } else {
            contentIv.setImageResource(R.drawable.weixin);
            copyAlipayLl.setVisibility(View.GONE);
        }

    }

    public void copyAlipay() {
        ClipboardManager cm = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
        cm.setText("15123100885");
        ToastUtil.showToast("复制成功");
    }

    @OnClick(R.id.copy_alipay_ll)
    public void onViewClicked() {
        copyAlipay();
    }
}
