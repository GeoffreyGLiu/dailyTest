package com.geoffrey.hybridtest;

import android.content.DialogInterface;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.webkit.JsPromptResult;
import android.webkit.JsResult;
import android.webkit.ValueCallback;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceRequest;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Set;

/**
 * -------------------------------------
 * 作者：刘京帅 sumu
 * -------------------------------------
 * 时间：6/22/21
 * -------------------------------------
 * 描述：
 * -------------------------------------
 * 备注：
 * -------------------------------------
 */
public class HybridActivity extends AppCompatActivity implements View.OnClickListener {

    private final String TAG = "HybridActivity";

    private WebView wv_hybrid_one;
    private Button btn_hybrid_one, btn_hybrid_result, btn_hybrid_prompt;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hybrid);
        initView();
        initWebView();
        initListener();
    }

    private void initView() {
        wv_hybrid_one = findViewById(R.id.wv_hybrid_one);
        btn_hybrid_one = findViewById(R.id.btn_hybrid_one);
        btn_hybrid_result = findViewById(R.id.btn_hybrid_result);
        btn_hybrid_prompt = findViewById(R.id.btn_hybrid_prompt);
    }

    private void initWebView() {
        WebSettings settings = wv_hybrid_one.getSettings();
        //允许与js交互权限
        settings.setJavaScriptEnabled(true);
        //设置允许js弹窗
        settings.setJavaScriptCanOpenWindowsAutomatically(true);
        //-------------------------android调用JS代码开始-------------------------
        //载入js代码,根据功能选择
        //js调用android的方法
        //wv_hybrid_one.loadUrl("file:///android_asset/callAndroid.html");
        //android调用js的方法
        //wv_hybrid_one.loadUrl("file:///android_asset/callJS.html");
        //android拦截js的输入框
//        wv_hybrid_one.loadUrl("file:///android_asset/prompt.html");
        wv_hybrid_one.setWebChromeClient(new WebChromeClient() {

            @Override
            public boolean onJsAlert(WebView view, String url, String message, final JsResult result) {
                AlertDialog.Builder buidler = new AlertDialog.Builder(HybridActivity.this);
                buidler.setTitle("Alert");
                buidler.setMessage(message);
                buidler.setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        result.confirm();
                    }
                });
                buidler.setCancelable(false);
                buidler.create().show();
                return true;
            }

            @Override
            public boolean onJsConfirm(WebView view, String url, String message, final JsResult result) {
                //根据协议判断是否是需要拦截的url
                Uri uri = Uri.parse(message);
                if ("js".equals(uri.getScheme())) {
                    if ("prompt".equals(uri.getAuthority())) {
                        new AlertDialog.Builder(HybridActivity.this)
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
                }
                return super.onJsConfirm(view, url, message, result);
            }

            @Override
            public boolean onJsPrompt(WebView view, String url, String message, String defaultValue, JsPromptResult result) {
                //根据协议判断是否是需要拦截的url
                Uri uri = Uri.parse(message);
                //如果scheme符合,就判断authority
                if ("js".equals(uri.getScheme())) {
                    if ("prompt".equals(uri.getAuthority())) {
                        //符合协议的url
                        StringBuilder builder = new StringBuilder();
                        builder.append("JS调用了android的方法");
                        builder.append("\r\n");
                        Set<String> queryNames = uri.getQueryParameterNames();
                        String value = null;
                        for (String s : queryNames) {
                            value = uri.getQueryParameter(s);
                            if (!TextUtils.isEmpty(value)) {
                                builder.append(s);
                                builder.append("=");
                                builder.append(value);
                                builder.append("\r\n");
                            }
                        }
                        result.confirm("JS调用了android的方法成功了~");
                        return true;
                    }
                }
                return super.onJsPrompt(view, url, message, defaultValue, result);
            }
        });
        //-------------------------android调用JS代码结束-------------------------


        //-------------------------JS调用android代码开始-------------------------
        wv_hybrid_one.addJavascriptInterface(new AndroidtoJS(), "test");
        wv_hybrid_one.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
                //根据协议判断是否是需要拦截的url
                //根据schema协议格式,判断前两个参数
                //比如传入的是url = "javascript://webview?arg1=111&arg2=222"
                Uri uri = request.getUrl();

                if ("js".equals(uri.getScheme())) {
                    //如果传入的url协议 = 预先约定的js协议,就往下解析参数
                    if ("webview".equals(uri.getAuthority())) {
                        //拦截,执行js需要调用的逻辑
                        //也可以在协议上带有参数传递到android上
                        StringBuilder builder = new StringBuilder();
                        builder.append("JS调用了android的方法");
                        builder.append("\r\n");
                        Set<String> queryNames = uri.getQueryParameterNames();
                        String value = null;
                        for (String s : queryNames) {
                            value = uri.getQueryParameter(s);
                            if (!TextUtils.isEmpty(value)) {
                                builder.append(s);
                                builder.append("=");
                                builder.append(value);
                                builder.append("\r\n");
                            }
                        }
                        Log.e(TAG, builder.toString());
                    }
                    return true;
                }
                return super.shouldOverrideUrlLoading(view, request);
            }
        });
        //-------------------------JS调用android代码结束-------------------------
    }

    private void initListener() {
        btn_hybrid_one.setOnClickListener(this);
        btn_hybrid_result.setOnClickListener(this);
        btn_hybrid_prompt.setOnClickListener(this);
    }


    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (id == R.id.btn_hybrid_one) {
            //需要在点击事件前加载,点击方能一次生效
            wv_hybrid_one.loadUrl("file:///android_asset/callJS.html");
            //android调用js代码
            //click 点击于此
            wv_hybrid_one.post(new Runnable() {
                @Override
                public void run() {
                    //注意调用的js方法名称要准确
                    //调用js中的callJS()方法
//                    wv_hybrid_one.loadUrl("javascript:callJS()");
                    //调用第二种方式evaluateJavaScript
                    wv_hybrid_one.evaluateJavascript("javascript:callJS()", new ValueCallback<String>() {
                        @Override
                        public void onReceiveValue(String value) {
                            Log.e(TAG, "value = " + value);
                        }
                    });
                }
            });
        } else if (id == R.id.btn_hybrid_result) {
            //需要在点击事件前加载,点击方能一次生效
            wv_hybrid_one.loadUrl("file:///android_asset/callAndroid.html");
            wv_hybrid_one.post(new Runnable() {
                @Override
                public void run() {
                    //注意调用的js方法名称要准确
                    //调用js中的callJS()方法
                    String temp = "JS调用了Android的方法";
                    String script = "javascript:getResult('" + temp + "')";
                    wv_hybrid_one.evaluateJavascript(script, new ValueCallback<String>() {
                        @Override
                        public void onReceiveValue(String value) {
                            Log.e(TAG, "value = " + value);
                        }
                    });
                }
            });
        } else if (id == R.id.btn_hybrid_prompt) {
            wv_hybrid_one.loadUrl("file:///android_asset/prompt.html");
        }
    }
}
