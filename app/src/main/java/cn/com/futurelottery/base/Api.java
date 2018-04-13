package cn.com.futurelottery.base;

/**
 * Created by apple on 2018/4/8.
 *
 */

class Api {

    private static final String HOST="http://test.api.lottery.anwenqianbao.com/v1/";

    //弹窗广告
    public static final String GET_POPUP=HOST+"home/popup";

    //首页bannner
    public static final String GET_BANNER=HOST+"home/banner";

    //双色球下注
    public static final String POST_DOUBLE_BALL=HOST+"ssq/order";

    //往期中奖号码-截止时间
    public static final String GET_BYTIME=HOST+"ssq/purchase";




}
