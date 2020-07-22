package com.cinderellavip.store;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.cinderellavip.store.bean.HomeInfo;
import com.cinderellavip.store.bean.UpdateHome;
import com.cinderellavip.store.bean.VersionBean;
import com.cinderellavip.store.dialog.CenterDialogUtil;
import com.cinderellavip.store.global.GlobalParam;
import com.cinderellavip.store.global.ImageUtil;
import com.cinderellavip.store.http.ApiManager;
import com.cinderellavip.store.http.BaseResult;
import com.cinderellavip.store.http.HttpUrl;
import com.cinderellavip.store.http.Response;
import com.cinderellavip.store.ui.LoginActivity;
import com.cinderellavip.store.ui.WebViewActivity;
import com.cinderellavip.store.util.VersionUtil;
import com.flyco.roundview.RoundTextView;
import com.tozzais.baselibrary.http.RxHttp;
import com.tozzais.baselibrary.ui.BaseActivity;
import com.tozzais.baselibrary.ui.CheckPermissionActivity;
import com.xuexiang.xupdate.XUpdate;
import com.xuexiang.xupdate._XUpdate;
import com.xuexiang.xupdate.service.OnFileDownloadListener;
import com.xuexiang.xutil.app.PathUtils;
import com.xuexiang.xutil.display.HProgressDialogUtils;

import java.io.File;
import java.util.TreeMap;

import androidx.appcompat.app.AlertDialog;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import butterknife.BindView;
import butterknife.OnClick;

public class MainActivity extends CheckPermissionActivity {

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

    }
    @Override
    protected int getToolbarLayout() {
        return R.layout.header_main;
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
            R.id.ll_mine_wallet,R.id.tv_setting,R.id.tv_exit})
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
            case R.id.tv_setting:
                if (GlobalParam.getUserLogin())
                    WebViewActivity.launch(mActivity,HttpUrl.h5_url+"/#/set/"+GlobalParam.getUserToken());
                break;
            case R.id.tv_exit:
                exit();
                break;
        }
    }

    @Override
    public void initListener() {
        super.initListener();

        swipeLayout.setOnRefreshListener(() -> {
            loadData();
        });

        getVersion();
    }

    private void getVersion(){
        TreeMap<String, String> hashMap = new TreeMap<>();
        hashMap.put("type", 2 + "");
        new RxHttp<BaseResult<VersionBean>>().send(ApiManager.getService().getVersion(hashMap),
                new Response<BaseResult<VersionBean>>(mActivity,Response.BOTH) {
                    @Override
                    public void onSuccess(BaseResult<VersionBean> result) {
//                        LogUtil.e(result.data.toString());
                        String versionName=null;
                        try {
                            PackageManager pm = mContext.getPackageManager();
                            PackageInfo pi = pm.getPackageInfo(mContext.getPackageName(), 0);
                            versionName = pi.versionName;
                        } catch (Exception e) {
                        }
                        if (VersionUtil.isModify(versionName,result.data.version)) {
                            showDialog(result.data);
                        }
                    }
                });
    }

    private VersionBean versionBean;
    private void  showDialog(VersionBean versionBean){
        this.versionBean = versionBean;
        AlertDialog.Builder builder = new AlertDialog.Builder(mActivity);
        builder.setTitle("版本更新提示");
        builder.setMessage(versionBean.commit);
        builder.setPositiveButton("立即更新", (dialogInterface, i) -> {
            checkPermissions(needPermissions);

        });
        builder.setNegativeButton("暂不更新", null);
        builder.create().show(); //构建AlertDialog并显示

    }
    private void downFile(VersionBean versionBean){
        XUpdate.newBuild(mActivity)
                .apkCacheDir(PathUtils.getExtDownloadsPath())
                .build()
                .download(versionBean.url, new OnFileDownloadListener() {
                    @Override
                    public void onStart() {
                        HProgressDialogUtils.showHorizontalProgressDialog(mActivity, "下载进度", false);
                    }

                    @Override
                    public void onProgress(float progress, long total) {
                        HProgressDialogUtils.setProgress(Math.round(progress * 100));
                    }

                    @Override
                    public boolean onCompleted(File file) {
                        HProgressDialogUtils.cancel();
                        _XUpdate.startInstallApk(mActivity, file);
                        return false;
                    }

                    @Override
                    public void onError(Throwable throwable) {
                        HProgressDialogUtils.cancel();
                    }
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

    @Override
    public void permissionGranted() {
        downFile(versionBean);
    }

    public static String[] needPermissions = {
            Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.READ_EXTERNAL_STORAGE
    };
}
