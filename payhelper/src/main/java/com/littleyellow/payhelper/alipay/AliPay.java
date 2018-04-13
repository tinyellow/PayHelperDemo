package com.littleyellow.payhelper.alipay;

import android.app.Activity;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.text.TextUtils;

import com.alipay.sdk.app.PayTask;
import com.littleyellow.payhelper.alipay.util.OrderInfoUtil2_0;

import java.util.Map;

import static com.littleyellow.payhelper.Pay.payInfo;


/**
 * Created by 小黄 on 2018/4/12.
 */

public class AliPay {

    private  final int SDK_PAY_FLAG = 1;

    private Activity activity;

    private boolean isRsa2;

    private String privateKey;

    private String partner;

    private String sellerId;

    //
    private String orderNo;

    private String money;

    private String name;

    private String detail;

    private String notifyUrl;

    private PayListener payListener;

    private PayIntercept payIntercept;

    private Handler mHandler = new Handler(Looper.getMainLooper()) {

        public void handleMessage(Message msg) {
            switch (msg.what) {
                case SDK_PAY_FLAG:
                    if(null == payListener){
                        return;
                    }
                    PayResult payResult = new PayResult((Map<String, String>) msg.obj);
                    /**
                     对于支付结果，请商户依赖服务端的异步通知结果。同步通知结果，仅作为支付结束的通知。
                     */
                    String resultInfo = payResult.getResult();// 同步返回需要验证的信息
                    String resultStatus = payResult.getResultStatus();
                    // 判断resultStatus 为9000则代表支付成功
                    if (TextUtils.equals(resultStatus, "9000")) {
                        // 该笔订单是否真实支付成功，需要依赖服务端的异步通知。
                        payListener.onPaySuccess();
                    } else {
                        // 该笔订单真实的支付结果，需要依赖服务端的异步通知。
                        payListener.onPayFailure(resultStatus,resultInfo);
                    }
                    break;
                default:
                    break;
            }
        }
    };

    private AliPay(Builder builder) {
        isRsa2 = builder.isRsa2;
        orderNo = builder.orderNo;
        money = builder.money;
        name = builder.name;
        detail = builder.detail;
        payIntercept = builder.payIntercept;
        payListener = builder.payListener;

        partner = TextUtils.isEmpty(builder.partner)? payInfo.getAliPartner():builder.partner;
        sellerId = TextUtils.isEmpty(builder.sellerId)?payInfo.getAliSellerId():builder.sellerId;
        privateKey = TextUtils.isEmpty(builder.privateKey)?payInfo.getAliPrivateKey():builder.privateKey;
        notifyUrl = TextUtils.isEmpty(builder.notifyUrl)?payInfo.getAliNotifyUrl():builder.notifyUrl;
    }

    public static Builder newBuilder() {
        return new Builder();
    }

    /**
     * 这里只是为了方便直接向商户展示支付宝的整个支付流程；所以Demo中加签过程直接放在客户端完成；
     * 真实App里，privateKey等数据严禁放在客户端，加签过程务必要放在服务端完成；
     * 防止商户私密数据泄露，造成不必要的资金损失，及面临各种安全风险；
     *
     * orderInfo的获取必须来自服务端；
     */
    public void toPay(){
        Runnable payRunnable = new Runnable() {

            @Override
            public void run() {

                Map<String, String> params = OrderInfoUtil2_0.buildOrderParamMap(partner,
                        sellerId,orderNo,name,detail,money,notifyUrl);
                if(null!=payIntercept){
                    payIntercept.onBuildParam(params);
                }

                String orderParam = OrderInfoUtil2_0.buildOrderParam(params);
                String sign = OrderInfoUtil2_0.getSign(params, privateKey, isRsa2);
                final String orderInfo = orderParam + "&" + sign;

                PayTask alipay = new PayTask(activity);
                Map<String, String> result = alipay.payV2(orderInfo, true);
                if(null!=payIntercept){
                    payIntercept.onPayResult(result);
                }

                Message msg = new Message();
                msg.what = SDK_PAY_FLAG;
                msg.obj = result;
                mHandler.sendMessage(msg);
            }
        };

        Thread payThread = new Thread(payRunnable);
        payThread.start();
    }


    public static final class Builder {
        private PayIntercept payIntercept;
        private boolean isRsa2;
        private String privateKey;
        private String partner;
        private String sellerId;
        private String orderNo;
        private String money;
        private String name;
        private String detail;
        private String notifyUrl;
        private PayListener payListener;

        private Builder() {
        }

        public Builder setPayIntercept(PayIntercept payIntercept) {
            this.payIntercept = payIntercept;
            return this;
        }

        public Builder isRsa2(boolean isRsa2) {
            this.isRsa2 = isRsa2;
            return this;
        }

        public Builder privateKey(String privateKey) {
            this.privateKey = privateKey;
            return this;
        }

        public Builder partner(String partner) {
            this.partner = partner;
            return this;
        }

        public Builder sellerId(String sellerId) {
            this.sellerId = sellerId;
            return this;
        }

        public Builder orderNo(String orderNo) {
            this.orderNo = orderNo;
            return this;
        }

        public Builder money(String money) {
            this.money = money;
            return this;
        }

        public Builder name(String name) {
            this.name = name;
            return this;
        }

        public Builder detail(String detail) {
            this.detail = detail;
            return this;
        }

        public Builder notifyUrl(String notifyUrl) {
            this.notifyUrl = notifyUrl;
            return this;
        }

        public Builder setOnPayListener(PayListener payListener) {
            this.payListener = payListener;
            return this;
        }

        public AliPay build() {
            return new AliPay(this);
        }
    }
}
