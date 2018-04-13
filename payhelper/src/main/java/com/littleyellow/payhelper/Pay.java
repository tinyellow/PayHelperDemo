package com.littleyellow.payhelper;

import android.content.Context;

import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;

/**
 * Created by 小黄 on 2018/4/12.
 */

public class Pay {

    public static GlobalPayInfo payInfo;

    public static IWXAPI mWXApi;

    public static void init(GlobalPayInfo payInfos) {
        if(null==payInfos){
            throw new IllegalArgumentException("GlobalPayInfo is null");
        }
//        if(TextUtils.isEmpty(payInfo.getWXAppId())){
//            throw new IllegalArgumentException("WXAppId is null");
//        }
        payInfo = payInfos;
    }

    public static void registerApp(Context context) {
        mWXApi = WXAPIFactory.createWXAPI(context, null);
        mWXApi.registerApp(payInfo.getWXAppId());
    }

}
