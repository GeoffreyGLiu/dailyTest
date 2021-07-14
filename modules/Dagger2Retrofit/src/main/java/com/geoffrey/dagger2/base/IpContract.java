package com.geoffrey.dagger2.base;

import com.geoffrey.dagger2.bean.IpInfo;

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
public interface IpContract {

    interface IpPresenter extends BasePresenter{
        void getIpInfo(String ip);
    }

    interface IpView extends BaseView<IpPresenter>{

        void setIpInfo(IpInfo ipInfo);
        void showError();
    }
}
