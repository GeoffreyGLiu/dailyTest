package com.geoffrey.hybridtest;

import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.text.Editable;
import android.view.KeyEvent;
import android.view.View;
import android.webkit.JsPromptResult;
import android.webkit.JsResult;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import utils.CommonUtils;
import utils.log.Logger;

import static android.view.KeyEvent.KEYCODE_BACK;

/**
 * -------------------------------------
 * 作者：刘京帅 sumu
 * -------------------------------------
 * 时间：6/7/21
 * -------------------------------------
 * 描述：
 * -------------------------------------
 * 备注：
 * -------------------------------------
 */
public class WebViewActivity extends AppCompatActivity implements View.OnClickListener {

    private final String TAG = "WebViewActivity";
    private final int SC_NOT_FOUND = 404;
    private ConstraintLayout cl_webview;
    private EditText et_input;
    private TextView tv_go;
    private ProgressBar pb_hybrid;
    private WebView wv_hybrid;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_webview);
        initView();
        initListener();
        initWebView();
    }

    private void initView() {
        cl_webview = findViewById(R.id.cl_webview);
        et_input = findViewById(R.id.et_input);
        tv_go = findViewById(R.id.tv_go);
        pb_hybrid = findViewById(R.id.pb_hybrid);
        wv_hybrid = findViewById(R.id.wv_hybrid);

    }

    private void initListener() {
        tv_go.setOnClickListener(this);
    }

    private void initWebView() {
        WebSettings settings = wv_hybrid.getSettings();
        //支持JavaScript交互
        settings.setJavaScriptEnabled(true);
        //支持图片调整到适合WebView的大小
        settings.setUseWideViewPort(true);
        //缩放至屏幕的大小
        settings.setLoadWithOverviewMode(true);
        //缩放操作
        //支持缩放
        settings.setSupportZoom(true);
        //支持内置的缩放控件,false,则该WebView不可缩放
        settings.setBuiltInZoomControls(true);
        //缩放控件隐藏
        settings.setDisplayZoomControls(false);
        //其他操作
        //设置WebView中的缓存
        //缓存模式如下：
        //LOAD_CACHE_ONLY: 不使用网络，只读取本地缓存数据
        //LOAD_DEFAULT: （默认）根据cache-control决定是否从网络上取数据。
        //LOAD_NO_CACHE: 不使用缓存，只从网络获取数据.
        //LOAD_CACHE_ELSE_NETWORK，只要本地有，无论是否过期，或者no-cache，都使用缓存中的数据。
        if (CommonUtils.isNetConnected(WebViewActivity.this)) {
            settings.setCacheMode(WebSettings.LOAD_DEFAULT);
        } else {
            settings.setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK);
        }
        //可以访问文件
        settings.setAllowFileAccess(true);
        //支持通过JS打开窗口
        settings.setJavaScriptCanOpenWindowsAutomatically(true);
        //支持自动加载图片
        settings.setLoadsImagesAutomatically(true);
        //支持编码格式
        settings.setDefaultTextEncodingName("utf-8");

        //android 5.0 以上是禁止了http和https混用的,这是打开方式
        settings.setMixedContentMode(WebSettings.MIXED_CONTENT_ALWAYS_ALLOW);

        //处理各种通知 & 请求事件
        //复写shouldOverrideUrlLoading,使得打开页面不掉用系统浏览器,而是在Webview中显示
        wv_hybrid.setWebViewClient(new WebViewClient() {

            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                if (url != null && (url.startsWith("https") || url.startsWith("http"))) {
                    view.loadUrl(url);
                    return false;
                }
                return true;
            }

            //页面刚开始调用的时候
            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                super.onPageStarted(view, url, favicon);
                Toast.makeText(WebViewActivity.this, "页面加载中....", Toast.LENGTH_SHORT).show();
                //开始加载,进度条置零
                pb_hybrid.setProgress(0);
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                Toast.makeText(WebViewActivity.this, "页面加载完成....", Toast.LENGTH_SHORT).show();
                //加载完成,进度条置零
                pb_hybrid.post(new Runnable() {
                    @Override
                    public void run() {
                        pb_hybrid.setProgress(0);
                    }
                });
            }

            @Override
            public void onLoadResource(WebView view, String url) {
                super.onLoadResource(view, url);
//                Logger.e(TAG, "加载的Url = " + url);
            }

            @Override
            public void onReceivedError(WebView view, int errorCode, String description, String failingUrl) {
                Toast.makeText(WebViewActivity.this, "errcode = " + errorCode, Toast.LENGTH_SHORT).show();
//              //https://ai.renren.com/ 404网站
//                wv_hybrid.loadUrl("file:///android_asset/test.html");
            }
        });

        //辅助WebView处理JavaScript对话框,网站图标,网站标题等
        wv_hybrid.setWebChromeClient(new WebChromeClient() {

            @Override
            public void onProgressChanged(WebView view, int newProgress) {
                Logger.e(TAG, "progress = " + newProgress);
                if (newProgress <= 100) {
                    pb_hybrid.setProgress(newProgress);
                }
            }

            @Override
            public void onReceivedTitle(WebView view, String title) {
                //仅限WebView加载的一级页面
                tv_go.setText(title);
            }

            @Override
            public boolean onJsAlert(WebView view, String url, String message, final JsResult result) {
                new AlertDialog.Builder(WebViewActivity.this)
                        .setTitle("JsAlert")
                        .setMessage(message)
                        .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                result.confirm();
                            }
                        })
                        .setCancelable(false)
                        .show();
                return true;
            }

            @Override
            public boolean onJsConfirm(WebView view, String url, String message, final JsResult result) {
                new AlertDialog.Builder(WebViewActivity.this)
                        .setTitle("JsConfirm")
                        .setMessage(message)
                        .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                result.confirm();
                            }
                        })
                        .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                result.cancel();
                            }
                        })
                        .setCancelable(false)
                        .show();
                //返回boolean值,判断点击时确认还是取消
                //true表示确认,false表示点击取消.
                return true;
            }

            @Override
            public boolean onJsPrompt(WebView view, String url, String message, String defaultValue, final JsPromptResult result) {
                final EditText et = new EditText(WebViewActivity.this);
                et.setText(defaultValue);
                new AlertDialog.Builder(WebViewActivity.this)
                        .setTitle(message)
                        .setView(et)
                        .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                result.confirm(et.getText().toString());
                            }
                        })
                        .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                result.cancel();
                            }
                        })
                        .setCancelable(false)
                        .show();
                return true;
            }
        });

    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (id == R.id.tv_go) {
            //click,请求网络
            Editable editable = et_input.getText();
            if (editable != null) {
                String urlTemp = editable.toString();
                wv_hybrid.loadUrl("https://" + urlTemp);
            }
            //加载本地html
//            wv_hybrid.loadUrl("file:///android_asset/test.html");

        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {

        if (keyCode == KEYCODE_BACK && wv_hybrid.canGoBack()) {
            wv_hybrid.goBack();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        cl_webview.removeView(wv_hybrid);
        wv_hybrid.destroy();
    }
}
