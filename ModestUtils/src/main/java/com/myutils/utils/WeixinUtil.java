//package com.myutils.utils;
//
//
//public class WeixinUtil {
//    /**
//     * appid是微信公众账号或开放平台APP的唯一标识，在公众平台申请公众账号或者在开放平台申请APP账号后，微信会自动分配对应
//     * 的appid，用于标识该应用。可在微信公众平台-->开发-->基本配置里面查看，商户的微信支付审核通过邮件中也会包含该字段值。
//     */
//    public static String APP_ID;
//    /**
//     * AppSecret是APPID对应的接口密码，用于获取接口调用凭证access_token时使用。在微信支付中，先通过OAuth2.0接口获取用户
//     * openid，此openid用于微信内网页支付模式下单接口使用。可登录公众平台-->微信支付，获取AppSecret（需成为开发者且帐号
//     * 没有异常状态）。
//     */
//    public static String APP_SECRET;
//    /**
//     * 商户申请微信支付后，由微信支付分配的商户收款账号。
//     */
//    public static String MCH_ID;
//    /**
//     * 交易过程生成签名的密钥，仅保留在商户系统和微信支付后台，不会在网络中传播。商户妥善保管该Key，切勿在网络中传输，不
//     * 能在其他客户端中存储，保证key不会被泄漏。商户可根据邮件提示登录微信商户平台进行设置。也可按以下路径设置：微信商户
//     * 平台(pay.weixin.qq.com)-->账户中心-->账户设置-->API安全-->密钥设置
//     */
//    public static String API_KEY;
//    /**
//     * 异步接收微信支付结果通知的回调地址，通知url必须为外网可访问的url，不能携带参数。
//     */
//    public static String NOTIFY_URL;
//    /**
//     * 服务器域名
//     */
//    public static String DOMAIN;
//    /**
//     * 服务器外网IP
//     */
//    public static String SERVER_IP;
//
//    //微信部分常用url
//    //1、统一下单
//    public static String WXPAY_URL_UNIFIEDORDER = "https://api.mch.weixin.qq.com/pay/unifiedorder";
//    //2、查询订单
//    public static String WXPAY_URL_ORDERQUERY = "https://api.mch.weixin.qq.com/pay/orderquery";
//    //3、关闭订单
//    public static String WXPAY_URL_CLOSEORDER = "https://api.mch.weixin.qq.com/pay/closeorder";
//    //4、申请退款
//    public static String WXPAY_URL_REFUND = "https://api.mch.weixin.qq.com/secapi/pay/refund";
//    //5、查询退款
//    public static String WXPAY_URL_REFUNDQUERY = "https://api.mch.weixin.qq.com/pay/refundquery";
//    //6、下载对账单
//    public static String WXPAY_URL_DOWNLOADBILL = "https://api.mch.weixin.qq.com/pay/downloadbill";
//    //7、支付结果通知(当前服务器的微信支付接受后台通知地址)
//    //8、交易保障(得到微信支付返回的相关信息以及获得整个接口的响应时间)
//    public static String WXPAY_URL_REPORT = "https://api.mch.weixin.qq.com/payitil/report";
//    //9、转短链接
//    public static String WXPAY_URL_SHORTURL = "https://api.mch.weixin.qq.com/tools/shorturl";
//
//    //交易超时时间，单位分钟
//    public static int TIMEOUT = 30;
//
//    public static void setWeixinConfig(WeixinConfig config) {
//        WeixinUtil.APP_ID = config.getAppId();
//        WeixinUtil.APP_SECRET = config.getAppSecret();
//        WeixinUtil.MCH_ID = config.getMchId();
//        WeixinUtil.API_KEY = config.getApiKey();
//        WeixinUtil.NOTIFY_URL = config.getNotifyUrl();
//        WeixinUtil.DOMAIN = config.getDomain();
//        WeixinUtil.SERVER_IP = config.getServerIp();
//    }
//}
