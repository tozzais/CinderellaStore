package com.cinderellavip.store.ui;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.cinderellavip.store.R;
import com.itheima.view.BridgeWebView;
import com.tozzais.baselibrary.ui.BaseActivity;
import com.tozzais.baselibrary.util.NetworkUtil;

import butterknife.BindView;
import me.jingbin.progress.WebProgress;


/**
 * Created by Administrator on 2016/9/8.
 */
public class WebViewActivity extends BaseActivity {

    @BindView(R.id.web_view)
    BridgeWebView web_view;

    String url = "";
    int status = 1; // 0 是无链接 1是超链接 2 是图文详情3：商品 4:商家

    public static final int GRAPHIC = 2;
    @BindView(R.id.progress)
    WebProgress mProgress;


    public static void launch(Context from, String title, String url) {
        Intent intent = new Intent(from, WebViewActivity.class);
        intent.putExtra("title", title);
        intent.putExtra("url", url);
        from.startActivity(intent);
    }


    public static void launch(Context from, String title, String url, int status) {
        Intent intent = new Intent(from, WebViewActivity.class);
        intent.putExtra("title", title);
        intent.putExtra("url", url);
        intent.putExtra("status", status);
        from.startActivity(intent);
    }


    @Override
    public int getLayoutId() {
        return R.layout.activity_webview;
    }

    @Override
    public void initView(Bundle savedInstanceState) {


    }

    @Override
    public void loadData() {
        String title = getIntent().getStringExtra("title");
        url = getIntent().getStringExtra("url");
        status = getIntent().getIntExtra("status", 1);


        WebSettings webSettings = web_view.getSettings();
        webSettings.setJavaScriptEnabled(true);
        webSettings.setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK);
        webSettings.setDomStorageEnabled(true);
        if (status == 1) {
            web_view.loadUrl(url);
            //监听WebView是否加载完成网页
//            web_view.setWebViewClient(new WebViewClient() {
//                @Override
//                public void onPageFinished(WebView view, String url) {
//                    super.onPageFinished(view, url);
//                    setBackTitle(view.getTitle());
//                }
//
//            });
            setBackTitle(title);

        } else if (status == GRAPHIC) {
            setBackTitle(title);
//            String data = Html.fromHtml(url).toString();
            String varjs = "<script type='text/javascript'> \nwindow.onload = function()\n{var $img = document.getElementsByTagName('img');for(var p in  $img){$img[p].style.width = '100%'; $img[p].style.height ='auto'}}</script>";
            web_view.loadDataWithBaseURL("", varjs + url, "text/html", "UTF-8", null);
//            String html = "<html><body><img src=" + url + " width=100% height=100%/></body></html>";
//            web_view.loadData(Html.fromHtml(url).toString(), "text/html", "UTF-8");
//            web_view.loadData(url, "text/html",  "UTF-8");

            web_view.setWebViewClient(new WebViewClient() {
                @Override
                public void onPageFinished(WebView view, String url) {
                    // html加载完成之后，无网隐藏进度条
                    if (!NetworkUtil.isNetworkAvailable(mActivity)) {
                        mProgress.hide();
                    }
                    super.onPageFinished(view, url);
                }


            });
            web_view.setWebChromeClient(new WebChromeClient());
        }

        //显示进度条
        mProgress.show();
        mProgress.setColor("#FF0000");


    }

    @Override
    public void initListener() {

    }

    public class WebChromeClient extends android.webkit.WebChromeClient {
        @Override
        public void onProgressChanged(WebView view, int newProgress) {
            super.onProgressChanged(view, newProgress);
            mProgress.setWebProgress(newProgress);
        }

    }


    @Override
    protected void onDestroy() {

        DestoryWebview();
        super.onDestroy();


    }

    private void DestoryWebview() {
        if (web_view != null) {
            ViewGroup parent = (ViewGroup) web_view.getParent();
            if (parent != null) {
                parent.removeView(web_view);
            }
            web_view.removeAllViews();
            web_view.destroy();
        }
    }

    public class ResizeImgWebviewClient extends WebViewClient {
        @Override
        public void onPageFinished(WebView view, String url) {
            super.onPageFinished(view, url);
//            resizeImg(view);
//            addImgClickEvent(view);
//            view.loadUrl("javascript:ResizeImages();");
            loadJS();
        }

        /**
         * 添加图片点击事件
         *
         * @param view
         */
        private void addImgClickEvent(WebView view) {
            view.loadUrl("javascript:(function(){" +
                    "var objs = document.getElementsByTagName(\"img\"); " +
                    "for(var i=0;i<objs.length;i++)  " +
                    "{"
                    + "    objs[i].onclick=function()  " +
                    "    {  "
                    + "        window.JsBridge.openImage(this.src);  " +
                    "    }  " +
                    "}" +
                    "})()");
        }

        /**
         * 重新调整图片宽高
         *
         * @param view
         */
        private void resizeImg(WebView view) {
            view.loadUrl("javascript:(function(){" +
                    "var objs = document.getElementsByTagName('img'); " +
                    "for(var i=0;i<objs.length;i++)  " +
                    "{"
                    + "var img = objs[i];   " +
                    "    img.style.maxWidth = '100%'; img.style.height = 'auto';  " +
                    "}" +
                    "})()");
        }

    }

    private void loadJS() {
        web_view.loadUrl("javascript:(function(){"
                //将DIV元素中的外边距和内边距设置为零，防止网页左右有空隙
                + " var divs = document.getElementsByTagName(\"div\");"
                + " for(var j=0;j<divs.length;j++){"
                + "   divs[j].style.margin=\"0px\";"
                + "   divs[j].style.padding=\"0px\";"
                + "   divs[j].style.width=document.body.clientWidth;"
                + " }"

                + " var imgs = document.getElementsByTagName(\"img\"); "
                + "   for(var i=0;i<imgs.length;i++)  "
                + "       {"
                //过滤掉GIF图片，防止过度放大后，GIF失真
                + "    var vkeyWords=/.gif$/;"
                + "        if(!vkeyWords.test(imgs[i].src)){"
                + "         var hRatio=" + getScreenWidthPX() + "/objs[i].width;"
                + "         objs[i].height= objs[i].height*hRatio;"//通过缩放比例来设置图片的高度
                + "         objs[i].width=" + getScreenWidthPX() + ";"//设置图片的宽度
                + "        }"
                + "}"
                + "})()");

    }

    /**
     * 获取屏幕的宽度（单位：像素PX）
     *
     * @return
     */
    private int getScreenWidthPX() {
        WindowManager wm = (WindowManager) this.getSystemService(Context.WINDOW_SERVICE);
        DisplayMetrics dm = new DisplayMetrics();
        if (wm != null && wm.getDefaultDisplay() != null) {
            wm.getDefaultDisplay().getMetrics(dm);
            return px2dip(dm.widthPixels);
        } else {
            return 0;
        }
    }

    /**
     * 像素转DP
     *
     * @param pxValue
     * @return
     */
    public int px2dip(float pxValue) {
        float scale = this.getResources().getDisplayMetrics().density;
        return (int) (pxValue / scale);
    }



}
