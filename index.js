/**
 * User: qijingyu
 * GitHub:https://github.com/qijingyu2003
 * Email:qijingyu2009@gmail.com
 */
import {NativeModules, Platform} from 'react-native'

export default class JYPay {

    /**
     * 支付宝Android端支付
     * @param orderInfo   订单号
     * @param callback    支付宝回调结果  详情见 https://docs.open.alipay.com/204/105301
     */
    static alipay(orderInfo, callback) {
        NativeModules.JYPay.alipay(orderInfo, callback)
    }


    /**
     * 设置微信APPID
     * @param id
     */
    static setWxId(id) {
        NativeModules.JYPay.setWxId(id);
    }

    /**
     * 设置支付宝跳转Scheme
     * @param scheme
     */
    static setAlipayScheme(scheme) {
       if (Platform.OS === 'ios')
            NativeModules.JYPay.setAlipayScheme(scheme);
    }

    /**
     * 设置支付宝沙箱环境，仅Android
     * @param isSandBox
    */
    static setAlipaySandbox(isSandBox) {
        if (Platform.OS === 'android')
            NativeModules.JYPay.setAlipaySandbox(isSandBox);
    }

    /**
     * 微信支付
     * 传入参数示例
     * {
        partnerId:data.partnerId,
        prepayId: data.prepayId,
        packageValue: data.data.packageValue,
        nonceStr: data.data.nonceStr,
        timeStamp: data.data.timeStamp,
        sign: data.data.sign,
       }
     *
     *
     * @param params  参数
     * @param callBack 回调结果码 0:支付成功,
     *                          -1:原因：支付错误,可能的原因：签名错误、未注册APPID、项目设置APPID不正确、注册的APPID与设置的不匹配、其他异常等
     *                          -2: 原因 用户取消,无需处理。发生场景：用户不支付了，点击取消，返回APP
     */
    static wxPay(params, callBack) {
        NativeModules.JYPay.wxPay(params, callBack)
    }

    /**
     * 银联支付
     *
     *
     * @param tn  参数 string
     * @param callBack 回调结果码 0:支付成功,
     *                          -1:原因：支付错误,可能的原因：签名错误、未注册APPID、项目设置APPID不正确、注册的APPID与设置的不匹配、其他异常等
     *                          -2: 原因 用户取消,无需处理。发生场景：用户不支付了，点击取消，返回APP
     */
    // static unionPay(tn, callBack) {
    //     NativeModules.JYPay.unionPay(tn, callBack)
    // }

    // static unionAliPay(tn, callBack) {
    //     NativeModules.JYPay.unionAliPay(tn, callBack)
    // }
    //
    // static unionWXPay(tn, callBack) {
    //     NativeModules.JYPay.unionWXPay(tn, callBack)
    // }
}
