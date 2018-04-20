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
    //mobile
    String  MOBILE                    =              "mobile";
    //amount
    String  AMOUNT                    =              "amount";
    //amount
    String  NICK                    =              "user_name";
    //注数
    String  NOTES                    =              "notes";
    //钱数
    String  MONEY                    =              "money";
    //期数
    String  PERIODS                  =              "periods";
    //倍数
    String  MULTIPLE                 =              "multiple";
    //红球
    String  RED                 =              "red";
    //拖号
    String  TUO                 =              "tuo";
    //绿号
    String  BLU                 =              "blue";
    //方式
    String  TYPE                 =              "type";
    //用户下注的奖期
    String  PHASE                 =              "phase";
    //用户下注的奖期的截止日期
    String  END_TIME                 =              "end_time";
    //是否通知追期
    String  IS_STOP                 =              "is_stop";
    //停止追期金额
    String  STOP_MONEY                 =              "stop_money";
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




    class Times {
        /** 启动页显示时间 **/
        public static final int LAUCHER_DIPLAY_MILLIS = 2000;
        /** 倒计时时间 **/
        public static final int MILLIS_IN_TOTAL = 60000;
        /** 时间间隔 **/
        public static final int COUNT_DOWN_INTERVAL = 1000;
    }

}
