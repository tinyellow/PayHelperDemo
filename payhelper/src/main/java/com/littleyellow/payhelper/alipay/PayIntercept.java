package com.littleyellow.payhelper.alipay;

import java.util.Map;

/**
 * Created by 小黄 on 2018/4/13.
 */

public interface PayIntercept {

    void onBuildParam(Map<String, String> params);

    void onPayResult(Map<String, String> result);

}
