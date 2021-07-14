package com.geoffrey.dagger2.bean;

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
public class IpInfo {

    // "ip": "117.136.12.79",
    //    "pro": "广东省",
    //    "proCode": "440000",
    //    "city": "佛山市",
    //    "cityCode": "440600",
    //    "region": "",
    //    "regionCode": "0",
    //    "addr": "广东省佛山市 移动",
    //    "regionNames": "",
    //    "err": ""
    private String ip;
    private String pro;
    private String proCode;
    private String city;
    private String cityCode;
    private String region;
    private String regionCode;
    private String addr;
    private String regionNames;
    private String err;

    @Override
    public String toString() {
        return "IpInfo{" +
                "\nip='" + ip + '\'' +
                "\n, pro='" + pro + '\'' +
                "\n, proCode='" + proCode + '\'' +
                "\n, city='" + city + '\'' +
                "\n, cityCode='" + cityCode + '\'' +
                "\n, region='" + region + '\'' +
                "\n, regionCode='" + regionCode + '\'' +
                "\n, addr='" + addr + '\'' +
                "\n, regionNames='" + regionNames + '\'' +
                "\n, err='" + err + '\'' +
                '}';
    }
}
