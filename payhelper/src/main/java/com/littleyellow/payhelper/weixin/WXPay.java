package com.littleyellow.payhelper.weixin;

import android.app.Activity;
import android.text.TextUtils;

import com.tencent.mm.opensdk.constants.Build;
import com.tencent.mm.opensdk.modelpay.PayReq;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;

import static com.littleyellow.payhelper.Pay.payInfo;

/**
 * Created by 小黄 on 2018/4/12.
 */

public class WXPay {

    private Activity activity;

    public static WXPayListener WXPayListener;

    //微信支付AppID
    private String appId;
    //微信支付商户号
    private String partnerId;
    //预支付码（重要）
    private String prepayId;
    //"Sign=WXPay"
    private String packageValue;
    //随机字符串
    private String nonceStr;
    //时间戳
    private String timeStamp;
    //签名
    private String sign;

    //微信支付核心api
    IWXAPI mWXApi;

    private WXPay(Builder builder) {
        sign = builder.sign;
        appId = builder.appId;
        partnerId = builder.partnerId;
        prepayId = builder.prepayId;
        packageValue = builder.packageValue;
        nonceStr = builder.nonceStr;
        timeStamp = builder.timeStamp;
        WXPayListener = builder.mWXPayListener;
    }

    public static Builder newBuilder() {
        return new Builder();
    }

    public boolean toPay(){
        mWXApi = WXAPIFactory.createWXAPI(activity, null);
        mWXApi.registerApp(this.appId);

        if (!check()) {
            if (WXPayListener != null) {
                WXPayListener.onPayFailure(-1,"未安装微信或微信版本过低");
                WXPayListener = null;
            }
            return false;
        }

        PayReq request = new PayReq();
        request.appId = TextUtils.isEmpty(this.appId)?payInfo.getWXAppId():this.appId;
        request.partnerId = TextUtils.isEmpty(this.partnerId)?payInfo.getWXPartnerId():this.partnerId;
        request.packageValue = TextUtils.isEmpty(this.packageValue)? "Sign=WXPay":this.packageValue;
        request.prepayId = this.prepayId;
        request.nonceStr = this.nonceStr;
        request.timeStamp = this.timeStamp;
        request.sign = this.sign;

        return mWXApi.sendReq(request);
    }

    //检测是否支持微信支付
    private boolean check() {
        return mWXApi.isWXAppInstalled() && mWXApi.getWXAppSupportAPI() >= Build.PAY_SUPPORTED_SDK_INT;
    }


    public static final class Builder {
        private String sign;
        private String appId;
        private String partnerId;
        private String prepayId;
        private String packageValue;
        private String nonceStr;
        private String timeStamp;
        private WXPayListener mWXPayListener;

        private Builder() {
        }

        public Builder sign(String sign) {
            this.sign = sign;
            return this;
        }

        public Builder appId(String appId) {
            this.appId = appId;
            return this;
        }

        public Builder partnerId(String partnerId) {
            this.partnerId = partnerId;
            return this;
        }

        public Builder prepayId(String prepayId) {
            this.prepayId = prepayId;
            return this;
        }

        public Builder packageValue(String packageValue) {
            this.packageValue = packageValue;
            return this;
        }

        public Builder nonceStr(String nonceStr) {
            this.nonceStr = nonceStr;
            return this;
        }

        public Builder timeStamp(String timeStamp) {
            this.timeStamp = timeStamp;
            return this;
        }

        public Builder setOnPayListener(WXPayListener mWXPayListener) {
            this.mWXPayListener = mWXPayListener;
            return this;
        }

        public WXPay build() {
            return new WXPay(this);
        }
    }
}
