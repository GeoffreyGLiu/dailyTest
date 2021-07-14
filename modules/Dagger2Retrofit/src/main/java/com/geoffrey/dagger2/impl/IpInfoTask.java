package com.geoffrey.dagger2.impl;

import com.geoffrey.dagger2.base.IIpService;
import com.geoffrey.dagger2.base.INetRequest;
import com.geoffrey.dagger2.base.INetRequestCallback;
import com.geoffrey.dagger2.base.TestInterceptor;
import com.geoffrey.dagger2.bean.IpInfo;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * -------------------------------------
 * 作者：刘京帅 sumu
 * -------------------------------------
 * 时间：4/23/21
 * -------------------------------------
 * 描述：
 * -------------------------------------
 * 备注：
 * -------------------------------------
 */
public class IpInfoTask implements INetRequest<String,IpInfo> {

    private String baseUrl = "http://whois.pconline.com.cn/";
    private Retrofit retrofit;

    private IpInfoTask() {
        initRetrofit();
    }

    public static IpInfoTask getInstance() {
        return IpInfoTaskInner.INSTANCE;
    }

    @Override
    public Disposable onRequest(String data, final INetRequestCallback<IpInfo> netRequestCallback) {

        IIpService iIpService = retrofit.create(IIpService.class);
        Disposable disposable = iIpService.getIpInfo(data).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<IpInfo>() {
                    @Override
                    public void accept(IpInfo ipInfo) throws Exception {
                        if (netRequestCallback != null){
                            netRequestCallback.onSuccess(ipInfo);
                        }
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        if (netRequestCallback != null){
                            netRequestCallback.onError(throwable);
                        }
                    }
                });
        return disposable;
    }

    private static class IpInfoTaskInner {
        private static IpInfoTask INSTANCE = new IpInfoTask();
    }

    private void initRetrofit() {
        OkHttpClient client = new OkHttpClient();
        OkHttpClient.Builder builder = client.newBuilder();
        builder.addInterceptor(new TestInterceptor());
        retrofit = new Retrofit.Builder()
                .baseUrl(baseUrl)
                .client(builder.build())
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();
    }
}
