package com.cinderellavip.store;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.cinderellavip.store.bean.HomeInfo;
import com.cinderellavip.store.bean.UpdateHome;
import com.cinderellavip.store.dialog.CenterDialogUtil;
import com.cinderellavip.store.global.GlobalParam;
import com.cinderellavip.store.global.ImageUtil;
import com.cinderellavip.store.http.ApiManager;
import com.cinderellavip.store.http.BaseResult;
import com.cinderellavip.store.http.HttpUrl;
import com.cinderellavip.store.http.Response;
import com.cinderellavip.store.ui.LoginActivity;
import com.cinderellavip.store.ui.WebViewActivity;
import com.flyco.roundview.RoundTextView;
import com.tozzais.baselibrary.http.RxHttp;
import com.tozzais.baselibrary.ui.BaseActivity;
import com.tozzais.baselibrary.util.log.LogUtil;

import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import butterknife.BindView;
import butterknife.OnClick;

public class MainActivity extends BaseActivity {

    @BindView(R.id.ll_tip)
    LinearLayout llTip;
    @BindView(R.id.iv_image)
    ImageView ivImage;
    @BindView(R.id.iv_image_flag)
    ImageView iv_image_flag;
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
    @BindView(R.id.swipeLayout)
    SwipeRefreshLayout swipeLayout;

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
        new RxHttp<BaseResult<HomeInfo>>().send(ApiManager.getService().getHome(),
                new Response<BaseResult<HomeInfo>>(isLoad,mActivity) {
                    @Override
                    public void onSuccess(BaseResult<HomeInfo> result) {
                        HomeInfo homeInfo = result.data;
                        ImageUtil.loadNet(mContext,ivImage,homeInfo.logo);
                        if (homeInfo.type == 1){
                            llTip.setVisibility(View.VISIBLE);
                            iv_image_flag.setVisibility(View.GONE);
                        }else if (homeInfo.type == 2){
                            llTip.setVisibility(View.GONE);
                            iv_image_flag.setVisibility(View.VISIBLE);
                            iv_image_flag.setImageResource(R.mipmap.icon_store_flag1);
                        }else if (homeInfo.type == 3){
                            llTip.setVisibility(View.GONE);
                            iv_image_flag.setVisibility(View.VISIBLE);
                            iv_image_flag.setImageResource(R.mipmap.icon_store_flag2);
                        }
                        tvName.setText(homeInfo.name);
                        tvContent.setText("商品："+homeInfo.products+"件     总销量："+homeInfo.sale+"件");
                        if (homeInfo.refund>0){
                            tvReturnOrder.setText(homeInfo.refund+"");
                            tvReturnOrder.setVisibility(View.VISIBLE);
                        }else {
                            tvReturnOrder.setVisibility(View.GONE);
                        }
                        if (homeInfo.today>0){
                            tvTodayOrder.setText(homeInfo.today+"");
                            tvTodayOrder.setVisibility(View.VISIBLE);
                        }else {
                            tvTodayOrder.setVisibility(View.GONE);
                        }
                        if (homeInfo.send>0){
                            tvSendOrder.setText(homeInfo.send+"");
                            tvSendOrder.setVisibility(View.VISIBLE);
                        }else {
                            tvSendOrder.setVisibility(View.GONE);
                        }
                    }

                    @Override
                    public void onCompleted() {
                        super.onCompleted();
                        swipeLayout.setRefreshing(false);
                    }
                });

    }

    @OnClick({R.id.tv_up_grade, R.id.ll_all_order, R.id.ll_return_order, R.id.ll_today_order,
            R.id.ll_send_order, R.id.ll_order_count, R.id.ll_order_money, R.id.ll_flow_count,
            R.id.ll_mine_wallet})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_up_grade:

                if (GlobalParam.getUserLogin())
                    WebViewActivity.launch(mActivity,HttpUrl.h5_url+"/#/pay/"+GlobalParam.getUserToken());
                break;
            case R.id.ll_all_order:
                if (GlobalParam.getUserLogin())
                WebViewActivity.launch(mActivity,HttpUrl.h5_url+"/#/order/0/"+GlobalParam.getUserToken());
                break;
            case R.id.ll_return_order:
                if (GlobalParam.getUserLogin())
                    WebViewActivity.launch(mActivity,HttpUrl.h5_url+"/#/refundOrder/"+GlobalParam.getUserToken());
                break;
            case R.id.ll_today_order:
                if (GlobalParam.getUserLogin())
                    WebViewActivity.launch(mActivity,HttpUrl.h5_url+"/#/order/2/"+GlobalParam.getUserToken());
                break;
            case R.id.ll_send_order:
                if (GlobalParam.getUserLogin())
                    WebViewActivity.launch(mActivity,HttpUrl.h5_url+"/#/order/3/"+GlobalParam.getUserToken());
                break;
            case R.id.ll_order_count:
                if (GlobalParam.getUserLogin())
                    WebViewActivity.launch(mActivity,HttpUrl.h5_url+"/#/statistics/"+GlobalParam.getUserToken());
                break;
            case R.id.ll_order_money:
                if (GlobalParam.getUserLogin())
                    WebViewActivity.launch(mActivity,HttpUrl.h5_url+"/#/volumeBusiness/"+GlobalParam.getUserToken());
                break;
            case R.id.ll_flow_count:
                if (GlobalParam.getUserLogin())
                    WebViewActivity.launch(mActivity,HttpUrl.h5_url+"/#/flow/"+GlobalParam.getUserToken());
                break;
            case R.id.ll_mine_wallet:
                if (GlobalParam.getUserLogin())
                    WebViewActivity.launch(mActivity,HttpUrl.h5_url+"/#/wallet/"+GlobalParam.getUserToken());
                break;
        }
    }

    @Override
    public void initListener() {
        super.initListener();
        tv_right.setOnClickListener(view -> {
            exit();
        });
        swipeLayout.setOnRefreshListener(() -> {
            loadData();
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

    @Override
    public void onEvent(Object o) {
        super.onEvent(o);
        if (o instanceof UpdateHome){
            loadData();
        }
    }
}
