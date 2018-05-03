package cn.com.futurelottery.base;

/**
 *
 * @author apple
 * @date 2018/4/11
 */

public   interface    Contacts {

    String   UMENG_KEY                 ="5ad03215a40fa366560000eb";
    String   BUGLY_KEY                  ="f21ec220a3";

    int TOP_RIGHT=              1;
    int TOP_MENU=               2;


    //token
    String  TOKEN                    =              "token";
    //手机号
    String  MOBILE                   =              "mobile";
    //账户余额
    String  AMOUNT                   =              "amount";
    //用户名
    String  NICK                     =              "user_name";
    //积分
    String  INTEGRAL                     =              "integral";
    //注数
    String  NOTES                    =              "notes";
    //钱数
    String  MONEY                    =              "money";
    //期数
    String  PERIODS                  =              "periods";
    //倍数
    String  MULTIPLE                 =              "multiple";
    //红球
    String  RED                      =              "red";
    //拖号
    String  TUO                      =              "tuo";
    //绿号
    String  BLU                      =              "blue";
    //方式
    String  TYPE                     =              "type";
    //用户下注的奖期
    String  PHASE                 =              "phase";
    //用户下注的奖期的截止日期
    String  END_TIME                 =              "end_time";
    //是否通知追期
    String  IS_STOP                  =              "is_stop";
    //停止追期金额
    String  STOP_MONEY               =              "stop_money";
    //总的
    String  TOTAL                 =              "total";
    //大乐透里的红拖
    String  RED_TUO                 =              "red_tuo";
    //大乐透里的蓝拖
    String  BLUE_TUO                 =              "blue_tuo";
    //大乐透里是否追加
    String  IS_ADD                 =              "is_add";

    /**
     * 彩种
     */
    interface Lottery{
        //双色球
        String  SSQ                  =                 "ssq";
        //大乐透
        String  DIL                  =                 "dlt";
        //竞彩足球
        String  FTB                  =                 "ftb";
    }


    //双色球列表缓存
    String  doubleBallSave                 =              "doubleBalls";
    //大乐透列表缓存
    String  superLottoSave                 =              "superLottos";


    // 双色球下单到选球的请求码
    int REQUEST_CODE_PAYMENT_TO_CHOOSE = 101;
    // 大乐透下单到选球的请求码
    int REQUEST_CODE_SL_PAYMENT_TO_CHOOSE = 102;
    // 到付款页面的请求码
    int REQUEST_CODE_TO_PAY = 103;




    class Times {
        /** 启动页显示时间 **/
        public static final int LAUCHER_DIPLAY_MILLIS = 2000;
        /** 倒计时时间 **/
        public static final int MILLIS_IN_TOTAL = 60000;
        /** 时间间隔 **/
        public static final int COUNT_DOWN_INTERVAL = 1000;
    }

    /**
     * 竞彩足球
     */
    class FootBall{
        //胜平负
        public static final String FTOO1    =       "FT001";
        //让分胜平负
        public static final String FTOO6    =       "FTOO6";
        //比分
        public static final String FT002    =       "FT002";
        //总进
        public static final String FT003    =       "FT003";
        //半场胜平负
        public static final String FT004    =       "FT004";



    }
    /**
     * 订单
     */
    interface Order{
        //订单id
        String ORDERID    =       "orderid";
        //订单钱数
        String MONEY    =       "money";
        //付款类型
        String TYPE    =       "type";

    }

    /**
     * 微信
     */
    interface WeChat{
        /*微信支付*/

        //微信的APP_ID
        String WX_APP_ID       = "wxe90a89b17657d1ae";
        //商户号
        String WX_PARTNER_ID   = "";
        //secret
        String SECRET   = "391890c4186f9d9ce2ae91aa8f17b0c6";
}
    /**
     * 支付宝
     */
    interface Alipay{

        String ALI_PID         = "2018041602568031";
        String SELLER = "inkey@gmail.com";

        String RSA_PRIVATE ="dsdsdsds+iQAorePmYvdq0x+wew/ivXc1ofgo4S88/rere/Qo9jw+oZUdxLc/K76PhT4R9gpk/C/L09x8sLun8Ym6vRWOUzX4AIEkxOOKA9zzHNzpHYsioDG8D6/1pyHhHlI0On82mUoqIZ6wvXWb8pSOuByClvkQiwzH21ajnuYSZm2PiIWk0eb/G/OqRgCb0zJokMVnCA8K0nINUOx4uIF2UnMyO0no/zLd8IY9FBncknQ8Ha8Uqwd1uahm/05cLAgMBAAECggEAKfgAHcjPqnle/37TUOxImZEPDwHPn3wCM705CwWkn+MlwFObrghUIZEhF/vas2SrbhT0d4DcX/ciLmxRMUAx08iqJyUEoRUDh3lA2Ad7gnZ1WOvaEqLj9NVowLGVv6X9SqzDeYAkU7JMcQQ2HiPNiB9W7QypBI3uhCHwaE4WH7GWnysT3+okPX8oxoAJ2AMTnwKsx1d1Bru7MUMlybPb/DVQdMF5Xz0JBr6XL0UN/sXlWv8muxMtK1xSHkwwNCI98dDU81BWpeQITcqhrvL3gSaOjEQkRAERLbN9zF+kDmT9MoWuLHAWm+gop8NxnWiXCyaHDmrfeaRAkavj3ZjmYQKBgQD9uYPQlIoBAWrF8geS/0zZsszAlEE89r2EjX/0JJF2CJxm1yZsErBSpzgIYJWHsshm0NeT6WbDPo7pMqMRP/L7lED8wQmy4pa62uqbxAkgUkTBxcawBHWG8XMmA874ZyIFKrS74n93cIzyztYZMTQj0S8XCFCVuzT8EQrhwxLgIwKBgQDQkME/DztKDk319X1qSW4i21iWNlbop2eVDw+qrESi5N2CVXNWZ3O7DvHh9C14eeyqYIEhVjh8HBXbg1D7J9Mdx3bS8b0iNdKVwpLCq6sAjY74NqeadibRZFYzuJh3i1TG24SLnGOTQmbQQrTUEJ+3kYEWuRNz/sYiAr7PJGjn+QKBgQCnC9jpHqKtfs1ZfbvW4BcszTdZI0LAbILiHnc7uke6uniejNBTHwrPoCCYA17vdJnUd3M3gteRfEKC7j/GP6AuWrFet4k3KU+rvi3wV+TaTs/N8/SYNpdaPOVfBeriSKRxWmibusfJT2S9B8iV1XtKiZLDS0ojCpobyuyrLs/imQKBgAX2va/h7Z4WfqAz3tw1MVMJ1qSk9SM99jZK8fW9a1EFHlhlvSCRYLvOl5gQdWRmiB/vFoIvhvn1i6J1RNGJt/24AGvTwcbCpzzlDXFHm2IuqhHq7iv6iO2f4t8jvihc8ZWmHUootKg3U91MqwMNXqPPI1PVAvtkI6JBVddygRVxAoGADKwbOKWGjsZNrOMvP3uGT5CZYrfNxs2rTfL4LmvhnSTa98JrePffmDJl4E19MmjjnHt9HhaNsuSlC2al5cKrg9N6YwOIVyvOy0bpwTXHO+xbuD62FjFeTfVAmsb2We5eK3wYKysl7FbpAsLVRIva3jOOJ/gYrhvqPqtWuoRqVNQ=";

    }

    //余额不足时支付成功
    String INTENT_EXTRA_PAY_SUCESS = "com.xinhe.intent.action.PAY_SUCESS";
    //充值成功
    String INTENT_EXTRA_RECHARGE_SUCESS = "com.xinhe.intent.action.RECHARGE_SUCESS";
    //登录成功
    String INTENT_EXTRA_LOGIN_SUCESS = "com.xinhe.intent.action.LOGIN_SUCESS";

}
