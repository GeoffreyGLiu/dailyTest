package com.geoffrey.hybridtest;

import android.webkit.JavascriptInterface;

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
public class AndroidtoJS extends Object {

    //定义JS需要调用的方法
    @JavascriptInterface
    public void hello(String msg){
        System.out.println("JS调用Android的hello方法");
    }
}
