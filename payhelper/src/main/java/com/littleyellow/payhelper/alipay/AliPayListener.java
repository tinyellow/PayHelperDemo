package com.littleyellow.payhelper.alipay;

/**
 * Created by 小黄 on 2018/4/12.
 */

public interface AliPayListener {

    void onPaySuccess(String status, String info);

    void onPayFailure(String status, String info);

}
