package cn.com.futurelottery.base;

/**
 * Created by apple on 2018/4/8.
 *
 */

public interface Api {

    String HOST="http://test.api.lottery.anwenqianbao.com/v1/";

    /**
     * 弹窗广告
     */
     String GET_POPUP=HOST+"home/popup";
    /**
     * 首页bannner
     */
     String GET_BANNER=HOST+"home/banner";

    /**
     * 首页消息
     */
    String GET_NOTIFITION=HOST+"home/notice";
    /**
     * 首页彩种
     */
    String GET_LOTTERY=HOST+"home/list";

    interface Double_Ball{
        /**
         * 双色球下注
         */
        String POST_DOUBLE_BALL     =       HOST+"ssq/order";
        /**
         * 双色球截止时间
         */
        String GET_BYTIME           =       HOST+"ssq/purchase";
        /**
         * 双色球往期
         */
         String GET_DROP            =       HOST+"ssq/drop";
        /**
         * 遗漏
         */
        String GET_MISS             =       HOST+"ssq/miss";
        /**
         * 多期机选
         */
        String POST_MULTI             =       HOST+"ssq/multi";

    }
    interface Super_Lotto{
        /**
         * 大乐透下单
         */
        String POST_DOUBLE_BALL     =       HOST+"dlt/order";
        /**
         * 大乐透截止时间
         */
        String GET_BYTIME           =       HOST+"dlt/purchase";
        /**
         * 大乐透往期
         */
         String GET_DROP            =       HOST+"dlt/drop";
        /**
         * 遗漏
         */
        String GET_MISS             =       HOST+"dlt/miss";
        /**
         * 多期机选
         */
        String POST_MULTI             =       HOST+"dlt/multi";

    }

    interface Login{
        /**
         * 验证码获取
         */
        String GET_CODE               =         HOST+"sms/getcode";
        /**
         * 登陆
         */
        String LOGIN               =            HOST+"login/login";
        /**
         * 获取余额
         */
        String balance               =            HOST+"user/balance";
    }
    interface  FOOTBALL{

        String  PASS_RULE                   =           "pass_rules";
        String  PLAY_RULE                =               "play_rules";

        //胜平负
        String  FT001           =           "FT001";
        //让球胜平负
        String  FT006           =           "FT006";
        //比分
        String  FT002           =           "FT002";
        //总进球
        String  FT003           =           "FT003";
        //半全场
        String  FT004           =           "FT004";
        //单关
        String pass_rules_o        =            "0";
        //过关
        String pass_rules_1        =            "1";

    }

    class FootBall_Api{

       public static final String  FOOTBALL_LSIT  =   HOST+"football/list";
       public static final String  Payment  =   HOST+"football/confirmThePayment";
        //赛事列表
        public static final String  PayList  =   HOST+"football/league";




    }


    interface Pay{
        //余额不足时支付
        String pay   =      HOST+"pay/pay";
        //充值
        String recharge   =      HOST+"recharge/pay";
    }

    //特殊code码
    interface Special_Code{
        int notEnoughMoney   =      1004;
    }

    //开奖
    interface Open{
        //总的开奖信息
        String open   =      HOST+"open/open";
        //二级页面
        String openssq   =      HOST+"open/openssq";
    }
    //订单
    interface Order{
        //全部订单
        String whole   =      HOST+"order/whole";
        //追号订单
        String chase   =      HOST+"order/chase";
        //中奖订单
        String winning   =      HOST+"order/winning";
        //待开奖订单
        String wait   =      HOST+"order/wait";
        //订单详情
        String deteails   =      HOST+"order/deteails";
        //删除订单
        String del   =      HOST+"order/del";
    }
    //提现
    interface Withdraw{
        //绑定银行卡状态
        String binding   =      HOST+"extract/binding";
        //检验验证码
        String checkCode   =      HOST+"sms/checkCode";
        //提交信息
        String idcard   =      HOST+"extract/idcard";
        //提现信息
        String amount   =      HOST+"extract/amount";
        //提现
        String take   =      HOST+"extract/take";
    }

}
