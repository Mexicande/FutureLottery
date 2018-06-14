package com.xinhe.haoyuncaipiao.base;

/**
 *
 * @author apple
 * @date 2018/4/11
 */

public   interface    Contacts {

    String   UMENG_KEY                 ="5ad03215a40fa366560000eb";
    String   BUGLY_KEY                  ="f21ec220a3";

    String LEAN_KEY="1Xractc2sikvNt5m6r9GP7sK";
    String LEAN_ID="9zeHS4F8GAHi97Fkg0p80FYV-gzGzoHsz";


    String  fileprovider                    =              BaseApplication.getInstance().getPackageName()+".provider";



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
        //排列3
        String  P3name                  =                 "排列3";
        String  P3                  =                 "p3";
        //排列5
        String  P5name                  =                 "排列5";
        String  P5                  =                 "p5";
        //福彩3d
        String  D3name                  =                 "3D";
        String  D3                  =                 "3d";
    }

    /**
     * 排3、5
     * 3d
     */
    interface Arrange{
        //个
        String  individual                  =                 "individual";
        //十
        String  ten                  =                 "ten";
        //百
        String  hundred                  =                 "hundred";
        //千
        String  thousand                  =                 "thousand";
        //万
        String  absolutely                  =                 "absolutely";
    }


    //双色球列表缓存
    String  doubleBallSave                 =              "doubleBalls";
    //大乐透列表缓存
    String  superLottoSave                 =              "superLottos";
    //排列5列表缓存
    String  line5                 =              "line5";
    //排列3列表缓存
    String  line3                 =              "line3";
    //3D列表缓存
    String  D3                 =              "3D";


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
     * h5页面
     */
    interface H5{
        String H5="http://m.lottery.anwenqianbao.com/#";
        //关于
        String about= H5+"/about";
        //服务
        String service= H5+"/service";
        //大乐透走势图
        String dlt=H5+"/zst/dlt";
        //双色球走势图
        String ssq=H5+"/zst/ssq";
        //排列3走势图
        String p3=H5+"/zst/p3";
        //排列5走势图
        String p5=H5+"/zst/p5";
        //3D走势图
        String fc3d=H5+"/zst/fc3d";
    }

    //余额不足时支付成功
    String INTENT_EXTRA_PAY_SUCESS = "com.xinhe.intent.action.PAY_SUCESS";
    //充值成功
    String INTENT_EXTRA_RECHARGE_SUCESS = "com.xinhe.intent.action.RECHARGE_SUCESS";
    //登录成功
    String INTENT_EXTRA_LOGIN_SUCESS = "com.xinhe.intent.action.LOGIN_SUCESS";

}
