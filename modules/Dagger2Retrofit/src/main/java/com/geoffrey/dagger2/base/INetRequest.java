package com.geoffrey.dagger2.base;

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
public interface INetRequest<T, K> {

    Disposable onRequest(T data, INetRequestCallback<K> netRequestCallback);
}
