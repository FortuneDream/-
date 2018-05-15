package com.example.q.pocketmusic.module.common;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.dell.fortune.tools.toast.ToastUtil;
import com.example.q.pocketmusic.R;
import com.example.q.pocketmusic.view.widget.view.TopTabView;

import butterknife.BindView;
import butterknife.ButterKnife;

//此Activity用户测试各种UI效果
public class TestModuleActivity extends AppCompatActivity implements TopTabView.TopTabListener {

    @BindView(R.id.top_tab_view)
    TopTabView topTabView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_module);
        ButterKnife.bind(this);
        topTabView.setListener(this);
        topTabView.setCheck(0);
    }

    @Override
    public void setTopTabCheck(int position) {
        ToastUtil.showToast(String.valueOf(position));
    }
}
