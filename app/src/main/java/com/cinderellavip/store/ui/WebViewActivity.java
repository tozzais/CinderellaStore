package com.cinderellavip.store.ui;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.cinderellavip.store.AppJs;
import com.cinderellavip.store.MainActivity;
import com.cinderellavip.store.R;
import com.cinderellavip.store.bean.PayInfo;
import com.cinderellavip.store.bean.UpdateHome;
import com.cinderellavip.store.dialog.CenterDialogUtil;
import com.cinderellavip.store.listener.OnDialogClickListener;
import com.cinderellavip.store.pay.AlipayUtils;
import com.cinderellavip.store.pay.PayResultEvent;
import com.cinderellavip.store.pay.WeChatUtils;
import com.google.gson.Gson;
import com.tencent.smtt.sdk.WebChromeClient;
import com.tencent.smtt.sdk.WebView;
import com.tozzais.baselibrary.ui.BaseActivity;
import com.ycbjie.webviewlib.BridgeHandler;
import com.ycbjie.webviewlib.CallBackFunction;
import com.ycbjie.webviewlib.X5WebView;

import org.greenrobot.eventbus.EventBus;

import butterknife.BindView;
import butterknife.OnClick;
import me.jingbin.progress.WebProgress;


/**
 * Created by Administrator on 2016/9/8.
 */
public class WebViewActivity extends BaseActivity {

    @BindView(R.id.web_view)
    X5WebView web_view;

    String url = "";
    int status = 1; // 0 是无链接 1是超链接 2 是图文详情3：商品 4:商家

    public static final int GRAPHIC = 2;
    @BindView(R.id.progress)
    WebProgress mProgress;
    @BindView(R.id.title)
    TextView title;


    public static void launch(Context from,  String url) {
        Intent intent = new Intent(from, WebViewActivity.class);
        intent.putExtra("url", url);
        from.startActivity(intent);
    }





    @Override
    public int getLayoutId() {
        return R.layout.activity_webview;
    }

    @Override
    protected int getToolbarLayout() {
        return R.layout.layout_header;
    }

    @Override
    public void initView(Bundle savedInstanceState) {


    }

    @Override
    public void loadData() {
        url = getIntent().getStringExtra("url");
        status = getIntent().getIntExtra("status", 1);


        if (status == 1) {
            web_view.loadUrl(url);
            //监听WebView是否加载完成网页
            web_view.setWebChromeClient(new WebChrome());
//            setBackTitle(title);

        }

        //显示进度条
        mProgress.show();
        mProgress.setColor("#FF0000");
        web_view.getSettings().setJavaScriptCanOpenWindowsAutomatically(true);

        web_view.addJavascriptInterface(new AppJs(this), "toPay");
//        web_view.registerHandler("toPay", new BridgeHandler() {
//            @Override
//            public void handler(String data, CallBackFunction function) {
//                Log.e("TAG", "js返回：" + data);
//
//            }
//        });

    }

    @Override
    public void initListener() {

    }

    @OnClick({R.id.iv_left_back, R.id.iv_left_close})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_left_back:
                if (web_view.canGoBack()){
                    web_view.goBack();
                }else {
                    finish();
                }
                break;
            case R.id.iv_left_close:
                finish();
                break;
        }
    }

    PayInfo payInfo;
    public void pay(String data) {
        Gson gson = new Gson();
        payInfo = gson.fromJson(data, PayInfo.class);
        if (payInfo.payment == 1){
            AlipayUtils.getInstance().alipay(mActivity, payInfo.pay_string, new AlipayUtils.OnPayListener() {
                @Override
                public void onPaySuccess() {
                    paySuccess();
                }
                @Override
                public void onPayWait() {
                }
                @Override
                public void onPayFail() {

                }
            });
        }else {
            WeChatUtils.getInstance(mActivity).wechatPay(payInfo.pay_info);

        }

    }

    private void paySuccess(){
        String s = "恭喜您，成功升级为加V认证商家，享受超高曝光和平台流量以及推荐权限";
        if (payInfo.vipType == 0){
             s = "恭喜您，成功升级为加V认证商家，享受超高曝光和平台流量以及推荐权限";
        }else {
            s = "恭喜您，成功升级为品牌认证商家，享受超高曝光和平台流量以及推荐权限";
        }

        CenterDialogUtil.showSuccess(mContext, s, new OnDialogClickListener() {
            @Override
            public void onSure() {
                EventBus.getDefault().post(new UpdateHome());
                finish();


            }

            @Override
            public void onCancel() {

            }
        });
    }

   public class WebChrome extends WebChromeClient {
        @Override
        public void onProgressChanged(WebView webView, int i) {
            super.onProgressChanged(webView, i);
            mProgress.setProgress(i);
            title.setText(webView.getTitle());

            web_view.loadUrl("javascript:"+"toPay");
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        if (web_view != null) {
            web_view.getSettings().setJavaScriptEnabled(true);
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (web_view != null) {
            web_view.getSettings().setJavaScriptEnabled(false);
        }
    }

    @Override
    protected void onDestroy() {
        try {
            if (web_view != null) {
                web_view.stopLoading();
                web_view.destroy();
                web_view = null;
            }
        } catch (Exception e) {
        }
        super.onDestroy();
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && web_view.canGoBack()) {
            web_view.goBack();// 返回前一个页面
                       return true;
                  }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    public void onEvent(Object o) {
        if (o instanceof PayResultEvent) {
            PayResultEvent event = (PayResultEvent) o;
            if (event.status == 0 || event.status == 1 || event.status == 2) {
                if (event.status == 0) {
                    paySuccess();
                }
            }
        }
    }
}
