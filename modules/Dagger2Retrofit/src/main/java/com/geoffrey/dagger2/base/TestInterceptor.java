package com.geoffrey.dagger2.base;

import android.view.textclassifier.TextLinks;

import java.io.IOException;

import okhttp3.FormBody;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * -------------------------------------
 * 作者：刘京帅 sumu
 * -------------------------------------
 * 时间：4/25/21
 * -------------------------------------
 * 描述：
 * -------------------------------------
 * 备注：
 * -------------------------------------
 */
public class TestInterceptor implements Interceptor {
    @Override
    public Response intercept(Chain chain) throws IOException {

        Request.Builder builder = chain.request().newBuilder();
        builder.addHeader("Geoffrey","Geoffrey");
        if ("POST".equals(chain.request().method())) {

            RequestBody body = chain.request().body();
            if (body instanceof FormBody) {
                FormBody formBody = (FormBody) body;
                FormBody.Builder formBuilder = new FormBody.Builder();
                for (int i = 0 ;i < formBody.size();i++){
                    formBuilder.addEncoded(formBody.encodedName(i),formBody.encodedValue(i));
                }
                formBuilder.addEncoded("json", "true");
                builder.post(formBuilder.build());
            }
        }

        Request request = builder.build();
        return chain.proceed(request);
    }
}
