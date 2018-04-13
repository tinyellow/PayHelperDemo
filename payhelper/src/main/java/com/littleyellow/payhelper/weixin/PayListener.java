package com.littleyellow.payhelper.weixin;

/**
 * Created by 小黄 on 2018/4/12.
 */

public interface PayListener {

    void onPaySuccess();

    void onPayFailure(int errorCode, String errStr);

    void onPayCancel();
}
