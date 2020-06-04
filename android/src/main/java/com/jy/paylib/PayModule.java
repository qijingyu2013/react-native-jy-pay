package com.jy.paylib;

import com.alipay.sdk.app.PayTask;
import com.alipay.sdk.app.EnvUtils;
import com.facebook.react.bridge.Arguments;
import com.facebook.react.bridge.Callback;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactContextBaseJavaModule;
import com.facebook.react.bridge.ReactMethod;
import com.facebook.react.bridge.ReadableMap;
import com.facebook.react.bridge.WritableMap;
import com.tencent.mm.opensdk.modelpay.PayReq;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;

import java.util.Map;

public class PayModule extends ReactContextBaseJavaModule {

//     private ReactApplicationContext reactContext;
    public static String WX_APPID = "";

    public PayModule(ReactApplicationContext reactContext) {
        super(reactContext);
//         this.reactContext = reactContext;
//         this.reactContext.addActivityEventListener(this);
    }

    @Override
    public String getName() {
        return "JYPay";
    }

//     private Promise mPromise;

    @ReactMethod
    public void setAlipaySandbox(Boolean isSandbox) {
        if(isSandbox){
            EnvUtils.setEnv(EnvUtils.EnvEnum.SANDBOX);
        }else {
            EnvUtils.setEnv(EnvUtils.EnvEnum.ONLINE);
        }
    }

    @ReactMethod
    public void alipay(final String orderInfo, final Callback promise) {
        Runnable payRunnable = new Runnable() {
            @Override
            public void run() {
                PayTask alipay = new PayTask(getCurrentActivity());
                Map<String, String> result = alipay.payV2(orderInfo, true);
                WritableMap map = Arguments.createMap();
                map.putString("memo", result.get("memo"));
                map.putString("result", result.get("result"));
                map.putString("resultStatus", result.get("resultStatus"));
                promise.invoke(map);
            }
        };
        // 必须异步调用
        Thread payThread = new Thread(payRunnable);
        payThread.start();
    }

//     @ReactMethod
//     public void unionPay(String tn, final Callback promise) {
//         // 云闪付
//         Log.d("test","云闪付支付 tn = " + tn);
//         UPPayAssistEx.startPay(getCurrentActivity(), null, null, tn, "00");
//     }

//     @ReactMethod
//     public void unionAliPay(String qrCode, final Callback promise) {
//         // 银联-支付宝
//         Log.d("test","银联-支付宝 tn = " + qrCode);
//         UPPayAssistEx.startPay(getCurrentActivity(), null, null, qrCode, "00");
//     }
//
//     @ReactMethod
//     public void unionWXPay(String tn, final Callback promise) {
//         // 银联-微信
//         Log.d("test","银联-微信 tn = " + tn);
//         UPPayAssistEx.startPay(getCurrentActivity(), null, null, tn, "00");
//
//     }

    @ReactMethod
    public void setWxId(String id) {
        WX_APPID = id;
    }

    @ReactMethod
    public void wxPay(ReadableMap params, final Callback callback) {
        IWXAPI api = WXAPIFactory.createWXAPI(getCurrentActivity(), WX_APPID);
        //data  根据服务器返回的json数据创建的实体类对象
        PayReq req = new PayReq();
        req.appId = WX_APPID;
        req.partnerId = params.getString("partnerId");
        req.prepayId = params.getString("prepayId");
        req.packageValue = params.getString("packageValue");
        req.nonceStr = params.getString("nonceStr");
        req.timeStamp = params.getString("timeStamp");
        req.sign = params.getString("sign");
        api.registerApp(WX_APPID);
        JYWXPayEntryActivity.callback = new WXPayCallBack() {
            @Override
            public void callBack(WritableMap result) {
                callback.invoke(result);
            }
        };
        //发起请求
        api.sendReq(req);
    }

//     @Override
//     public void onActivityResult(Activity activity, int requestCode, int resultCode, Intent data) {
//         // 处理银联手机支付控件返回的支付结果
//         if (data == null) {
//             return;
//         }
//         WritableMap response = Arguments.createMap();
//         String msg = "";
//         /*
//          * 支付控件返回字符串:success、fail、cancel 分别代表支付成功，支付失败，支付取消
//          */
//         String str = data.getExtras().getString("pay_result");
//         if (str.equalsIgnoreCase("success")) {
//             // 如果想对结果数据验签，可使用下面这段代码，但建议不验签，直接去商户后台查询交易结果
//             // result_data结构见c）result_data参数说明
//             if (data.hasExtra("result_data")) {
//                 String result = data.getExtras().getString("result_data");
//                 try {
//                     JSONObject resultJson = new JSONObject(result);
//                     String sign = resultJson.getString("sign");
//                     String dataOrg = resultJson.getString("data");
//                     response.putString("sign", sign);
//                     response.putString("data", dataOrg);
//                     /*
//                     // 此处的verify建议送去商户后台做验签
//                     // 如要放在手机端验，则代码必须支持更新证书
//                     boolean ret = verify(dataOrg, sign, mMode);
//                     if (ret) {
//                         // 验签成功，显示支付结果
//                         msg = "支付成功！";
//                     } else {
//                         // 验签失败
//                         msg = "支付失败！";
//                     }
//                     */
//                 } catch (JSONException e) {
//                 }
//             }
//             // 结果result_data为成功时，去商户后台查询一下再展示成功
//             msg = "支付成功！";
//         } else if (str.equalsIgnoreCase("fail")) {
//             msg = "支付失败！";
//         } else if (str.equalsIgnoreCase("cancel")) {
//             msg = "用户取消了支付";
//         }
//         response.putString("pay_result", str);
//         response.putString("msg", msg);
//         reactContext.getJSModule(DeviceEventManagerModule.RCTDeviceEventEmitter.class).emit("UnionPayResponse", response);
//     }

//     @Override
//     public void onNewIntent(Intent intent) {
//         if(intent != null){
//             String str = intent.getExtras().getString("pay_result");
//         }
//     }

    /**
     * 检查是否安装云闪付客户端的接口
     *
     * @param promise
     */
//     @ReactMethod
//     public void checkWalletInstalled(Promise promise) {
//         boolean res = UPPayAssistEx.checkWalletInstalled(getCurrentActivity());
//         promise.resolve(res);
//     }

}
