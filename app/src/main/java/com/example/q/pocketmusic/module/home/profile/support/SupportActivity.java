package com.example.q.pocketmusic.module.home.profile.support;

import android.content.DialogInterface;
import android.support.design.widget.AppBarLayout;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.q.pocketmusic.R;
import com.example.q.pocketmusic.model.bean.MoneySupport;
import com.example.q.pocketmusic.module.common.AuthActivity;
import com.jude.easyrecyclerview.EasyRecyclerView;
import com.jude.easyrecyclerview.adapter.RecyclerArrayAdapter;

import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

public class SupportActivity extends AuthActivity<SupportPresenter.IView, SupportPresenter>
        implements SupportPresenter.IView, RecyclerArrayAdapter.OnMoreListener, SwipeRefreshLayout.OnRefreshListener {
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.app_bar)
    AppBarLayout appBar;
    @BindView(R.id.copy_alipay_tv)
    TextView copyAlipayTv;
    @BindView(R.id.recycler)
    EasyRecyclerView recycler;
    @BindView(R.id.content_ll)
    LinearLayout contentLl;
    @BindView(R.id.get_dimension_tv)
    TextView getDimensionTv;
    private MoneyAdapter mMoneyAdapter;
    private boolean isAlipayPay = true;
    private AlertDialog mPayDialog;

    @Override
    protected SupportPresenter createPresenter() {
        return new SupportPresenter(this);
    }


    @Override
    public int setContentResource() {
        return R.layout.activity_support;
    }

    @Override
    public void initUserView() {
        mMoneyAdapter = new MoneyAdapter(this);
        initToolbar(toolbar, "支持开发者");
        initRecyclerView(recycler, mMoneyAdapter, 1);
        recycler.setRefreshListener(this);
        mMoneyAdapter.setMore(R.layout.view_more, this);
        presenter.getSupportMoneyList(true);
    }

    @Override
    public void setMoneyList(boolean isRefreshing, List<MoneySupport> list) {
        if (isRefreshing) {
            mMoneyAdapter.clear();
        }
        mMoneyAdapter.addAll(list);
    }


//    private void alertSupportMeDialog() {
//        View view = View.inflate(getCurrentContext(), R.layout.dialog_support_me, null);
//        RadioGroup payWayRg = (RadioGroup) view.findViewById(R.id.pay_way_rg);
//        final TextEdit moneyTet = (TextEdit) view.findViewById(R.id.money_tet);
//        final TextEdit contentTet = (TextEdit) view.findViewById(R.id.content_tet);
//        TextView okTv = (TextView) view.findViewById(R.id.ok_tv);
//        moneyTet.setInputString(String.valueOf(3));
//        RadioButton weixinRb = (RadioButton) view.findViewById(R.id.weixin_rb);
//        RadioButton alipayRb = (RadioButton) view.findViewById(R.id.alipay_rb);
//        isAlipayPay = true;
//        alipayRb.setChecked(true);
//        contentTet.setInputString("支持口袋乐谱");
//        payWayRg.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
//            @Override
//            public void onCheckedChanged(RadioGroup group, @IdRes int checkedId) {
//                int checkButtonId = group.getCheckedRadioButtonId();
//                if (checkButtonId == R.id.alipay_rb) {
//                    isAlipayPay = true;
//                } else {
//                    isAlipayPay = false;
//                }
//            }
//        });
//
//        okTv.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                presenter.pay(isAlipayPay, moneyTet.getInputString(), contentTet.getInputString());
//                mPayDialog.dismiss();
//            }
//        });
//
//        mPayDialog = new AlertDialog.Builder(this)
//                .setView(view)
//                .show();
//    }


    @Override
    public void onMoreShow() {
        presenter.getSupportMoneyList(false);
    }

    @Override
    public void onMoreClick() {

    }

    @Override
    public void onRefresh() {
        presenter.getSupportMoneyList(true);
    }


    @OnClick({R.id.copy_alipay_tv,R.id.get_dimension_tv})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.copy_alipay_tv:
                presenter.copyAlipay();
                break;
            case R.id.get_dimension_tv:
                CharSequence[] list=new CharSequence[2];
                list[0]="支付宝";
                list[1]="微信";
                new AlertDialog.Builder(this)
                        .setTitle("选择类型")
                        .setSingleChoiceItems(list, 0, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                presenter.enterDimensionActivity(which);
                            }
                        })
                        .show();
                break;
        }
    }
}
