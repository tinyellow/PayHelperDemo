package com.littleyellow.payhelperdemo;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.littleyellow.payhelper.alipay.AliPay;
import com.littleyellow.payhelper.weixin.PayListener;
import com.littleyellow.payhelper.weixin.WXPay;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        WXPay.newBuilder()
                .appId("")
                .nonceStr("")
                .prepayId("")
                .sign(null)
                .setOnPayListener(new PayListener() {
                    @Override
                    public void onPaySuccess() {

                    }

                    @Override
                    public void onPayFailure(int errorCode, String errStr) {

                    }

                    @Override
                    public void onPayCancel() {

                    }
                })
                .build()
                .toPay();
        AliPay.newBuilder()
                .name("")
                .detail("")
                .money("")
                .orderNo("")
                .notifyUrl("")
//                .setPayIntercept(new PayIntercept() {
//                    @Override
//                    public void onBuildParam(Map<String, String> params) {
//
//                    }
//
//                    @Override
//                    public void onPayResult(Map<String, String> result) {
//
//                    }
//                })
                .setOnPayListener(new com.littleyellow.payhelper.alipay.PayListener() {
                    @Override
                    public void onPaySuccess() {

                    }

                    @Override
                    public void onPayFailure(String resultStatus, String resultInfo) {

                    }
                })
                .build()
                .toPay();
    }
}