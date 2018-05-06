package com.littleyellow.payhelper;

import android.content.Context;

import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;

import java.lang.reflect.Method;

/**
 * Created by 小黄 on 2018/4/12.
 */

public class Pay {

    private static GlobalPayInfo payInfo;

    public static IWXAPI mWXApi;

    public static void registerApp(Context context) {
        mWXApi = WXAPIFactory.createWXAPI(context, null);
        mWXApi.registerApp(payInfo.getWXAppId());
    }

    public static GlobalPayInfo getPayInfo() {
        if(null==payInfo){
            try {
                Class providerClass = Class.forName("com.littleyellow.payhelper.GlobalInfoProvider");
                Method method = providerClass.getMethod("get");
                payInfo = (GlobalPayInfo) method.invoke(providerClass);
            } catch (Exception e) {
                throw new IllegalArgumentException("GlobalPayInfo is null");
            }
        }
        return payInfo;
    }

}
