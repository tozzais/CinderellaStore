package com.cinderellavip.store.ui;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.TextView;

import com.cinderellavip.store.R;
import com.cinderellavip.store.http.ApiManager;
import com.cinderellavip.store.http.BaseResult;
import com.cinderellavip.store.http.Response;
import com.tencent.smtt.sdk.WebChromeClient;
import com.tencent.smtt.sdk.WebView;
import com.tozzais.baselibrary.http.RxHttp;
import com.tozzais.baselibrary.ui.BaseActivity;
import com.ycbjie.webviewlib.X5WebView;

import butterknife.BindView;
import butterknife.OnClick;
import me.jingbin.progress.WebProgress;


/**
 * Created by Administrator on 2016/9/8.
 */
public class AgreementWebViewActivity extends BaseActivity {

    @BindView(R.id.web_view)
    X5WebView web_view;
    @BindView(R.id.progress)
    WebProgress mProgress;
    @BindView(R.id.title)
    TextView title;


    public static void launch(Context from,  String  type) {
        Intent intent = new Intent(from, AgreementWebViewActivity.class);
        intent.putExtra("type", type);
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

        web_view.loadUrl(getIntent().getStringExtra("type"));


        web_view.setWebChromeClient(new WebChrome());
        //显示进度条
        mProgress.show();
        mProgress.setColor("#FF0000");


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

    public class WebChrome extends WebChromeClient {
        @Override
        public void onProgressChanged(WebView webView, int i) {
            super.onProgressChanged(webView, i);
            mProgress.setProgress(i);
            title.setText(webView.getTitle());
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
}
