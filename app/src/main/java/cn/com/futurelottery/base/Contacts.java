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
    String  MOBILE                   =              "mobile";
    //amount
    String  AMOUNT                   =              "amount";
    //amount
    String  NICK                     =              "user_name";
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
    String  PHASE                    =              "phase";
    //是否通知追期
    String  IS_STOP                  =              "is_stop";
    //停止追期金额
    String  STOP_MONEY               =              "stop_money";
    //总的
    String  TOTAL                    =              "total";

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

}
