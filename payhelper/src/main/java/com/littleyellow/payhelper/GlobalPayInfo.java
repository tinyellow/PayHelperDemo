package com.littleyellow.payhelper;

/**
 * Created by 小黄 on 2018/4/12.
 */

public interface GlobalPayInfo {

    //微信支付App号
    String getWXAppId();

    //微信支付商户号
    String getWXPartnerId();

    //微信支付商户号key
    String getWXAppKey();

    //支付宝
    String getAliPrivateKey();

    String getAliPartner();

    String getAliSellerId();

    String getAliNotifyUrl();

}
