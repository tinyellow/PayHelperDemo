package com.littleyellow.payhelperdemo;

import android.app.Application;

import com.littleyellow.payhelper.GlobalPayInfo;
import com.littleyellow.payhelper.Pay;

/**
 * Created by 小黄 on 2018/4/12.
 */

public class App extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        Pay.init(new GlobalPayInfo() {
            @Override
            public String getWXAppId() {
                return null;
            }

            @Override
            public String getWXPartnerId() {
                return null;
            }

            @Override
            public String getWXAppKey() {
                return null;
            }

            @Override
            public String getAliPrivateKey() {
                return null;
            }

            @Override
            public String getAliPartner() {
                return null;
            }

            @Override
            public String getAliSellerId() {
                return null;
            }

            @Override
            public String getAliNotifyUrl() {
                return null;
            }
        });
    }
}
