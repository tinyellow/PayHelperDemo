# PayHelperDemo
简单封装微信支付宝支付,统一调用风格和回调方式。
## Setup

要使用这个库 `minSdkVersion`  >= 14. 库引用的微信sdk:5.1.6,支付宝sdk:20180403

```gradle
allprojects {
    repositories {
        ...
        jcenter()//一般android studio新建项目都会自动加这行引进这个仓库的
    }
}

dependencies {
    implementation 'com.littleyellow:payhelper:1.0.5'
    annotationProcessor 'com.littleyellow:payhelper-compiler:1.0.2'
}
```

## Usage
新建一个配置类继承GlobalPayInfo，类名随意写,添加注解@APPLICATION_ID("你应用的id,强烈建议直接写BuildConfig.APPLICATION_ID")
```
@APPLICATION_ID(BuildConfig.APPLICATION_ID)
public class PayInfoProvider extends GlobalPayInfo {

    @Override
    public String getWXAppId() {
        return null;
    }

    @Override
    public String getWXPartnerId() {
        return null;
    }

    @Override
    public String getWXSecret() {
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
        return null;
    }
}
```
上面实现的方法说明

- 支付宝：如果只用一个支付宝收款账号且要求在前端做签名操作时可以在这里设置(正常安全考虑都是后台签名的)，不填就在支付时动态设置，都设置了优先使用动态设置的参数。
- 微信对付： 直接不用管，直接返回null，支付时动态设置参数。暂时不支付前端进行统一订单和订单签名操作，应该由后台处理返回。我有时间后面版本可能会添加(因为有些后台好懒）
（如果项目有特殊要求要全局监听支付回调可在上面重写onResp()方法进行监听）

支付宝支付时调用
```
AliPay.newBuilder()
    .activity(this)
    //.serviceSign(bean.getPrepayid())//如果后台进行签名加密会返回一条签名信息(包括全部支付信息的)，只要设置这个参数其他的支付信息都忽略
    //.setPayIntercept()    //有特殊情况要添加自定义支付字段时，可这里拦截设置
    .appId()
    .privateKey()
    .orderNo()
    .name()
    .money()
    .detail()
    .notifyUrl()
    .timestamp()
    .setOnPayListener(new AliPayListener() {
        @Override
        public void onPaySuccess(String status, String info) {
        }

        @Override
        public void onPayFailure(String status, String info) {
        }
    })
    .build()
    .toPay();
```

微信支付时调用
```
WXPay.newBuilder()
    .appId(appid)
    .partnerId(partnerId)
    .prepayId(prepayId)
    .nonceStr(nonceStr)
    .timeStamp(timeStamp)
    .sign(sign)
    //.intercept() //调试可这里拦截查看上报微信前的参数,可直接修改(不建议)
    .setOnPayListener(new WXPayListener() {
        @Override
        public void onPaySuccess() {
        }

        @Override
        public void onPayFailure(int errorCode, String errStr) {
            if ("未安装微信或微信版本过低".equals(errStr)){
                Toast.makeText(RechargeActivity.this,"请先安装微信在进行支付",Toast.LENGTH_SHORT).show();
            }
        }

        @Override
        public void onPayCancel() {

        }
    })
    .build()
    .toPay(this);

```

如果项目加混淆，添加下面一行规则（支付宝微信到各自开发文档上查看并添加）
```
-keep public class com.littleyellow.payhelper.GlobalInfoProvider{ * ; }
```

# License

```
Copyright (C) 2019, 小黄
  
   Licensed under the Apache License, Version 2.0 (the "License");
   you may not use this file except in compliance with the License.
   You may obtain a copy of the License at 
 
       http://www.apache.org/licenses/LICENSE-2.0 

   Unless required by applicable law or agreed to in writing, software
   distributed under the License is distributed on an "AS IS" BASIS,
   WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
   See the License for the specific language governing permissions and
   limitations under the License.
```








