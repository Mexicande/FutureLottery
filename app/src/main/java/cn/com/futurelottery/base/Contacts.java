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
    //注数
    String  NOTES                    =              "notes";
    //钱数
    String  MONEY                    =              "money";
    //期数
    String  PERIODS                  =              "periods";
    //倍数
    String  MULTIPLE                 =              "multiple";

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

}
