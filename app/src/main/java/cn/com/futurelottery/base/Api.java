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





}
