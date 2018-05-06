package com.littleyellow.payhelperdemo;

import com.littleyellow.pay.annotation.APPLICATION_ID;
import com.littleyellow.payhelper.GlobalPayInfo;

/**
 * Created by Administrator on 2018/5/5 0005.
 */

@APPLICATION_ID(BuildConfig.APPLICATION_ID)
public class PayInfoProvider implements GlobalPayInfo {
    @Override
    public String getWXAppId() {
        return null;
    }

    @Override
    public String getWXPartnerId() {
        return null;
    }

    @Override
    public String getAliPrivateKey() {
        return null;
    }

    @Override
    public String getAliAppId() {
        return null;
    }

    @Override
    public String getAliNotifyUrl() {
        return "ali.comx";
    }
}
