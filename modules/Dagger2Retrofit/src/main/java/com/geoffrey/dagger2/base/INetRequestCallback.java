package com.geoffrey.dagger2.base;

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
public interface INetRequestCallback<T> {

    /**
     * 请求成功返回
     */
    void onSuccess(T t);

    /**
     * 请求异常返回
     *
     * @param t
     */
    void onError(Throwable t);

    /**
     * 请求开始
     */
    void onStart();

    /**
     * 请求完成
     */
     void onFinish();
}
