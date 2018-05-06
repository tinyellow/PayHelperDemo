package com.littleyellow.payhelperdemo;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import com.littleyellow.payhelper.Pay;
import com.littleyellow.payhelper.weixin.WXPay;
import com.littleyellow.payhelper.weixin.WXPayListener;


public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        TextView text = (TextView) findViewById(R.id.text);
        text.setText(Pay.getPayInfo().getAliNotifyUrl());
        WXPay.newBuilder()
                .context(this)
                .appId("")
                .nonceStr("")
                .prepayId("")
                .sign(null)
                .setOnPayListener(new WXPayListener() {
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
//        AliPay.newBuilder()
//                .activity(this)
//                .name("")
//                .detail("")
//                .money("")
//                .orderNo("")
//                .notifyUrl("")
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
//                .setOnPayListener(new AliPayListener() {
//                    @Override
//                    public void onPaySuccess(String status, String info) {
//
//                    }
//
//                    @Override
//                    public void onPayFailure(String status, String info) {
//
//                    }
//                })
//                .build()
//                .toPay();
    }
}
