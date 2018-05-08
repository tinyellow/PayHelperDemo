package com.littleyellow.payhelper;

import android.content.Context;

import com.littleyellow.payhelper.weixin.WXPay;
import com.tencent.mm.opensdk.modelbase.BaseResp;

/**
 * Created by 小黄 on 2018/4/12.
 */

public abstract class GlobalPayInfo {

    //微信支付App号
    public abstract String getWXAppId();

    //微信支付支付商户号
    public abstract String getWXPartnerId();

    //微信支付商户号key,支付暂无用到
    public abstract String getWXSecret();

    //支付宝
    public abstract String getAliPrivateKey();

    public abstract String getAliAppId();

    public abstract String getAliNotifyUrl();

    public void onResp(Context context,BaseResp resp){
        try {
            if(null!= WXPay.WXPayListener){
                if (resp.errCode == BaseResp.ErrCode.ERR_OK) {
                    WXPay.WXPayListener.onPaySuccess();
                } else if(resp.errCode == BaseResp.ErrCode.ERR_USER_CANCEL){
                    WXPay.WXPayListener.onPayCancel();
                }else{
                    WXPay.WXPayListener.onPayFailure(resp.errCode,resp.errStr);
                }
                WXPay.WXPayListener = null;//防止内存泄漏
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
