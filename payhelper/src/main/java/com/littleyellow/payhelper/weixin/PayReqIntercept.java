package com.littleyellow.payhelper.weixin;

import com.tencent.mm.opensdk.modelpay.PayReq;

/**
 * Created by 小黄 on 2018/4/13.
 */

public interface PayReqIntercept {

    void onSendReq(PayReq request);

}
