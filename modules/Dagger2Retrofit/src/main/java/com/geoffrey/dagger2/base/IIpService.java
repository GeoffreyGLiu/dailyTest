package com.geoffrey.dagger2.base;

import com.geoffrey.dagger2.bean.IpInfo;

import io.reactivex.Observable;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

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
public interface IIpService {

    @FormUrlEncoded
    @POST("ipJson.jsp")
    Observable<IpInfo> getIpInfo(@Field("ip") String ip);
}
