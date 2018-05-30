package com.xinhe.haoyuncaipiao.base;

/**
 * Created by apple on 2018/4/8.
 *
 */

public interface Api {

    //正式线
    String HOST="http://api.lottery.anwenqianbao.com/v1/";
//    //测试线
//    String HOST="http://test.api.lottery.anwenqianbao.com/v1/";

    /**
     * 版本更新
     */
     String VERSION=HOST+"user/version";
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
        String GET_CODE                  =         HOST+"sms/getcode";
        /**
         * 登录
         */
        String LOGIN                    =            HOST+"login/login";
        /**
         * 获取余额
         */
        String balance               =            HOST+"user/balance";
        /**
         * 微信登录
         */
        String wlogin               =            HOST+"login/wlogin";
        /**
         * 微信登录绑定手机号
         */
        String wbind               =            HOST+"login/wbind";
    }
    interface  FOOTBALL{

        String  PASS_RULE                   =           "pass_rules";
        String  PLAY_RULE                    =               "play_rules";

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
        String PASS_RULES_O =            "0";
        //过关
        String PASS_RULES_1 =            "1";
        //混合
        String  FT005           =           "FT005";

    }

    class FootBall_Api{

       public static final String  FOOTBALL_LSIT  =   HOST+"football/list";
       public static final String  Payment  =   HOST+"football/confirmThePayment";
       //混合下单
       public static final String  blend  =   HOST+"football/blend";
        /**
         * 赛事列表
         */
        public static final String PAY_LIST =   HOST+"football/league";
        /**
         * 赛事筛选
         */
        public static final String PAY_SCREEN =   HOST+"football/screen";



    }


    interface Pay{
        //余额不足时支付
        String pay   =      HOST+"pay/pay";
        //充值
        String recharge   =      HOST+"recharge/pay";
        //充值方式
        String mode   =      HOST+"recharge/mode";
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

        String FOOT_RUN   =     HOST+"football/result";

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
        //追号二级页
        String chasing   =      HOST+"order/chasing";
        //订单详情
        String deteails   =      HOST+"order/deteails";
        //足球混合订单详情
        String footDeteails   =      HOST+"order/footDeteails";
        //删除订单
        String del   =      HOST+"order/del";
        //合买订单
        String documentary   =      HOST+"order/documentary";
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
        //获取银行列表
        String opening   =      HOST+"extract/opening";
    }

    //排列3,5
    interface Line{
        //排列3 5购买的奖期以及截止时间
        String purchase   =      HOST+"arrange/purchase";
        //往期中奖
        String drop   =      HOST+"arrange/drop";
        //遗漏
        String miss   =      HOST+"arrange/miss";
        //排5下单
        String five   =      HOST+"arrange/five";
        //排3下单
        String order   =      HOST+"arrange/order";
    }
    //3D
    interface Lottery3D{
        //排列3 5购买的奖期以及截止时间
        String purchase   =      HOST+"3d/purchase";
        //往期中奖
        String drop   =      HOST+"3d/drop";
        //遗漏
        String miss   =      HOST+"3d/miss";
        //下单
        String order   =      HOST+"3d/order";
    }
    //合买
    interface Together{
        //合买顶部接口
        String top   =      HOST+"together/top";
        //合买列表
        String list   =      HOST+"together/list";
        //合买详情
        String details   =      HOST+"together/details";
        //合买跟单
        String documentary   =      HOST+"together/documentary";
    }
    //红包
    interface RedPacket{
        //用户红包
        String envelopes   =      HOST+"user/envelopes";
        //可用列表
        String rightoff   =      HOST+"user/rightoff";
        //邀请码
        String code   =      HOST+"user/code";
    }
    //浏览统计
    interface record{
        //浏览统计
        String envelopes   =      HOST+"StatisticalColor/statistical";
    }
    //用户
    interface user{
        //修改昵称
        String name   =      HOST+"user/name";
        //修改头像
        String avatar   =      HOST+"user/avatar";
        //分享内容
        String share   =      HOST+"user/share";
    }

}
