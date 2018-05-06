package com.littleyellow.payhelper.weixin;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import com.littleyellow.payhelper.Pay;
import com.tencent.mm.opensdk.constants.ConstantsAPI;
import com.tencent.mm.opensdk.modelbase.BaseReq;
import com.tencent.mm.opensdk.modelbase.BaseResp;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.IWXAPIEventHandler;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;

/**
 * Created by 小黄 on 2018/4/12.
 */

public class PayCallbackActivity extends Activity implements IWXAPIEventHandler {

    private IWXAPI api;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        api = WXAPIFactory.createWXAPI(this, Pay.getPayInfo().getWXAppId());
        api.handleIntent(getIntent(), this);
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        setIntent(intent);
        api.handleIntent(intent, this);
    }

    @Override
    public void onReq(BaseReq baseReq) {

    }

    @Override
    public void onResp(BaseResp resp) {
//      0	成功	展示成功页面
//      -1	错误	可能的原因：签名错误、未注册APPID、项目设置APPID不正确、注册的APPID与设置的不匹配、其他异常等。
//      -2	用户取消	无需处理。发生场景：用户不支付了，点击取消，返回APP。
        if (resp.getType() == ConstantsAPI.COMMAND_PAY_BY_WX) {
            try {
                if(null!=WXPay.WXPayListener){
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
            finish();
        }
    }
}
