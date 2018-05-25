package com.littleyellow.payhelper.weixin;

import android.content.Context;
import android.text.TextUtils;

import com.littleyellow.payhelper.Pay;
import com.tencent.mm.opensdk.constants.Build;
import com.tencent.mm.opensdk.modelpay.PayReq;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;

import static android.text.TextUtils.isEmpty;

/**
 * Created by 小黄 on 2018/4/12.
 */

public class WXPay {

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

    private PayReqIntercept intercept;

    //微信支付核心api
    private IWXAPI wxapi;

    private WXPay(Builder builder) {
        sign = builder.sign;
        prepayId = builder.prepayId;
        nonceStr = builder.nonceStr;
        timeStamp = builder.timeStamp;
        intercept = builder.intercept;
        WXPayListener = builder.mWXPayListener;
        appId = TextUtils.isEmpty(builder.appId)? Pay.getPayInfo().getWXAppId():builder.appId;
        partnerId = isEmpty(builder.partnerId)?Pay.getPayInfo().getWXPartnerId():builder.partnerId;
        packageValue = isEmpty(builder.packageValue)? "Sign=WXPay":builder.packageValue;
        wxapi = builder.wxapi;
    }

    public static Builder newBuilder() {
        return new Builder();
    }

    public boolean toPay(Context context){
        if(null == wxapi){
            wxapi = WXAPIFactory.createWXAPI(context, null);
            wxapi.registerApp(this.appId);
        }
        if (!check()) {
            if (WXPayListener != null) {
                WXPayListener.onPayFailure(-1,"未安装微信或微信版本过低");
                WXPayListener = null;
            }
            return false;
        }

        PayReq request = new PayReq();
        request.appId = appId;
        request.partnerId = partnerId;
        request.packageValue = packageValue;
        request.prepayId = this.prepayId;
        request.nonceStr = this.nonceStr;
        request.timeStamp = this.timeStamp;
        request.sign = this.sign;
        if(null != intercept){
            intercept.onSendReq(request);
        }
        return wxapi.sendReq(request);
    }

    //检测是否支持微信支付
    private boolean check() {
        return wxapi.isWXAppInstalled() && wxapi.getWXAppSupportAPI() >= Build.PAY_SUPPORTED_SDK_INT;
    }


    public static final class Builder {
        private String sign;
        private String appId;
        private String partnerId;
        private String prepayId;
        private String packageValue;
        private String nonceStr;
        private String timeStamp;
        private IWXAPI wxapi;
        private WXPayListener mWXPayListener;
        private PayReqIntercept intercept;

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

        public Builder intercept(PayReqIntercept intercept) {
            this.intercept = intercept;
            return this;
        }

        public Builder wxapi(IWXAPI wxapi) {
            this.wxapi = wxapi;
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
