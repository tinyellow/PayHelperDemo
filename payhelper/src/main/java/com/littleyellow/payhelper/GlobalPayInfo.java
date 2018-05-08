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

    //      0	成功	展示成功页面
    //      -1	错误	可能的原因：签名错误、未注册APPID、项目设置APPID不正确、注册的APPID与设置的不匹配、其他异常等。
    //      -2	用户取消	无需处理。发生场景：用户不支付了，点击取消，返回APP。
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
