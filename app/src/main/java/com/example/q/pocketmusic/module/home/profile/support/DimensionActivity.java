package com.example.q.pocketmusic.module.home.profile.support;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;

import com.example.q.pocketmusic.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public class DimensionActivity extends AppCompatActivity {
    public static final String PARAM_TYPE = "param_1";//微信还是支付宝
    @BindView(R.id.content_iv)
    ImageView contentIv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dimension);
        ButterKnife.bind(this);
        int type = getIntent().getIntExtra(PARAM_TYPE, 0);
        if (type == 0) {
            contentIv.setImageResource(R.drawable.alipay);
        }else {
            contentIv.setImageResource(R.drawable.weixin);
        }
    }
}
