package com.geoffrey.dagger2.impl;

import com.geoffrey.dagger2.base.INetRequestCallback;
import com.geoffrey.dagger2.base.IpContract;
import com.geoffrey.dagger2.bean.IpInfo;

import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;

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
public class IpPresenter implements IpContract.IpPresenter, INetRequestCallback<IpInfo> {

    private IpInfoTask netTask;
    private IpContract.IpView view;
    private CompositeDisposable mCompositeDisposable;
    private Disposable disposable;

    public IpPresenter(IpContract.IpView view, IpInfoTask ipInfoTask) {
        this.netTask = ipInfoTask;
        this.view = view;
    }

    @Override
    public void getIpInfo(String ip) {
        disposable = netTask.onRequest(ip, this);
        subsribe();
    }

    @Override
    public void subsribe() {
        if (mCompositeDisposable == null || mCompositeDisposable.isDisposed() ) {
            mCompositeDisposable = new CompositeDisposable();
        }
        mCompositeDisposable.add(disposable);
    }

    @Override
    public void unSubscribe() {
        if (mCompositeDisposable != null && !disposable.isDisposed()) {
            mCompositeDisposable.dispose();
        }
    }

    @Override
    public void onSuccess(IpInfo ipInfo) {
        if (this.view != null) {
            view.setIpInfo(ipInfo);
        }
    }

    @Override
    public void onError(Throwable t) {
        view.showError();
    }

    @Override
    public void onStart() {

    }

    @Override
    public void onFinish() {

    }
}
