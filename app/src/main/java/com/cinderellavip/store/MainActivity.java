package com.cinderellavip.store;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.cinderellavip.store.dialog.CenterDialogUtil;
import com.cinderellavip.store.global.GlobalParam;
import com.cinderellavip.store.ui.LoginActivity;
import com.flyco.roundview.RoundTextView;
import com.tozzais.baselibrary.ui.BaseActivity;

import butterknife.BindView;
import butterknife.OnClick;

public class MainActivity extends BaseActivity {

    @BindView(R.id.ll_tip)
    LinearLayout llTip;
    @BindView(R.id.iv_image)
    ImageView ivImage;
    @BindView(R.id.tv_name)
    TextView tvName;
    @BindView(R.id.tv_content)
    TextView tvContent;
    @BindView(R.id.tv_all_order)
    RoundTextView tvAllOrder;
    @BindView(R.id.tv_return_order)
    RoundTextView tvReturnOrder;
    @BindView(R.id.tv_today_order)
    RoundTextView tvTodayOrder;
    @BindView(R.id.tv_send_order)
    RoundTextView tvSendOrder;
    @BindView(R.id.tv_order_count)
    RoundTextView tvOrderCount;
    @BindView(R.id.tv_order_money)
    RoundTextView tvOrderMoney;
    @BindView(R.id.tv_flow_count)
    RoundTextView tvFlowCount;
    @BindView(R.id.tv_mine_wallet)
    RoundTextView tvMineWallet;

    public static void launch(Activity context) {
        Intent intent = new Intent(context, MainActivity.class);
        context.startActivity(intent);
        context.finish();
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    public void initView(Bundle savedInstanceState) {
        setNotBackTitle("我的店铺");
        setRightText("退出");

    }

    @Override
    public void loadData() {

    }

    @OnClick({R.id.tv_up_grade, R.id.ll_all_order, R.id.ll_return_order, R.id.ll_today_order,
            R.id.ll_send_order, R.id.ll_order_count, R.id.ll_order_money, R.id.ll_flow_count,
            R.id.ll_mine_wallet})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_up_grade:
                break;
            case R.id.ll_all_order:
                break;
            case R.id.ll_return_order:
                break;
            case R.id.ll_today_order:
                break;
            case R.id.ll_send_order:
                break;
            case R.id.ll_order_count:
                break;
            case R.id.ll_order_money:
                break;
            case R.id.ll_flow_count:
                break;
            case R.id.ll_mine_wallet:
                break;
        }
    }

    @Override
    public void initListener() {
        super.initListener();
        tv_right.setOnClickListener(view -> {
            exit();
        });
    }

    private void exit(){
        CenterDialogUtil.showTwo(mContext,"提示","你确定要退出登录吗？","取消","确定", s->{
            if (s.equals("1")){
                GlobalParam.setUserLogin(false);
                LoginActivity.launch(mActivity);
                finish();
            }

        });
    }
}
