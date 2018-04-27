package cn.com.futurelottery.model;

import com.chad.library.adapter.base.entity.AbstractExpandableItem;
import com.chad.library.adapter.base.entity.MultiItemEntity;

import java.io.Serializable;
import java.util.List;

import cn.com.futurelottery.ui.adapter.football.WinAndLoseAdapter;

/**
 * Created by apple on 2018/4/19.
 */

public class FootBallList implements Serializable, MultiItemEntity {

    /**
     * code : 200
     * message : OK
     * data : [{"match":[{"id":"554","match_id":"20180419*4*005","daytime":"20180419","weekid":"4","teamid":"005","league":"西甲","team":"阿拉维斯:赫罗纳","endtime":"23:56","matchtime":"2018-04-20 01:30:00","flage":"0","wtype":"1","champion":"","unsupport_type":"FT006,0&FT001,0","created_at":"2018-04-17 21:22:46","homeTeam":"阿拉维斯","awayTeam":"赫罗纳","week":"星期四","odds":[{"odds":"-1.00","describe":"篮球让分，足球让球数"},{"odds":"4.70","describe":"胜奖金/赔率"},{"odds":"3.65","describe":"平奖金/赔率"},{"odds":"1.56","describe":"负奖金/赔率"}]},{"id":"555","match_id":"20180419*4*006","daytime":"20180419","weekid":"4","teamid":"006","league":"西甲","team":"皇家社会:马德里竞技","endtime":"23:56","matchtime":"2018-04-20 01:30:00","flage":"0","wtype":"1","champion":"","unsupport_type":"FT006,0&FT001,0","created_at":"2018-04-17 21:22:46","homeTeam":"皇家社会","awayTeam":"马德里竞技","week":"星期四","odds":[{"odds":"1.00","describe":"篮球让分，足球让球数"},{"odds":"1.61","describe":"胜奖金/赔率"},{"odds":"3.60","describe":"平奖金/赔率"},{"odds":"4.35","describe":"负奖金/赔率"}]},{"id":"556","match_id":"20180419*4*008","daytime":"20180419","weekid":"4","teamid":"008","league":"英超","team":"伯恩利:切尔西","endtime":"23:56","matchtime":"2018-04-20 02:45:00","flage":"0","wtype":"1","champion":"","unsupport_type":"FT001,0&FT006,0","created_at":"2018-04-17 21:22:47","homeTeam":"伯恩利","awayTeam":"切尔西","week":"星期四","odds":[{"odds":"1.00","describe":"篮球让分，足球让球数"},{"odds":"1.88","describe":"胜奖金/赔率"},{"odds":"3.35","describe":"平奖金/赔率"},{"odds":"3.35","describe":"负奖金/赔率"}]},{"id":"557","match_id":"20180419*4*009","daytime":"20180419","weekid":"4","teamid":"009","league":"英超","team":"莱切斯特城:南安普敦","endtime":"23:56","matchtime":"2018-04-20 02:45:00","flage":"0","wtype":"1","champion":"","unsupport_type":"FT006,0","created_at":"2018-04-17 21:22:47","homeTeam":"莱切斯特城","awayTeam":"南安普敦","week":"星期四","odds":[{"odds":"-1.00","describe":"篮球让分，足球让球数"},{"odds":"4.55","describe":"胜奖金/赔率"},{"odds":"3.85","describe":"平奖金/赔率"},{"odds":"1.54","describe":"负奖金/赔率"}]},{"id":"558","match_id":"20180419*4*012","daytime":"20180419","weekid":"4","teamid":"012","league":"西甲","team":"皇家贝蒂斯:拉斯帕尔马斯","endtime":"23:56","matchtime":"2018-04-20 03:30:00","flage":"0","wtype":"1","champion":"","unsupport_type":"FT001,0&FT006,0","created_at":"2018-04-17 21:22:47","homeTeam":"皇家贝蒂斯","awayTeam":"拉斯帕尔马斯","week":"星期四","odds":[{"odds":"-1.00","describe":"篮球让分，足球让球数"},{"odds":"1.72","describe":"胜奖金/赔率"},{"odds":"3.90","describe":"平奖金/赔率"},{"odds":"3.45","describe":"负奖金/赔率"}]},{"id":"559","match_id":"20180419*4*013","daytime":"20180419","weekid":"4","teamid":"013","league":"西甲","team":"莱万特:马拉加","endtime":"23:56","matchtime":"2018-04-20 03:30:00","flage":"0","wtype":"1","champion":"","unsupport_type":"FT006,0&FT001,0","created_at":"2018-04-17 21:22:47","homeTeam":"莱万特","awayTeam":"马拉加","week":"星期四","odds":[{"odds":"-1.00","describe":"篮球让分，足球让球数"},{"odds":"3.20","describe":"胜奖金/赔率"},{"odds":"3.25","describe":"平奖金/赔率"},{"odds":"1.97","describe":"负奖金/赔率"}]},{"id":"560","match_id":"20180419*4*001","daytime":"20180419","weekid":"4","teamid":"001","league":"荷甲","team":"乌德勒支:格罗宁根","endtime":"23:56","matchtime":"2018-04-20 00:30:00","flage":"0","wtype":"1","champion":"","unsupport_type":"FT006,0&FT001,0","created_at":"2018-04-17 21:22:47","homeTeam":"乌德勒支","awayTeam":"格罗宁根","week":"星期四","odds":[{"odds":"-1.00","describe":"篮球让分，足球让球数"},{"odds":"2.27","describe":"胜奖金/赔率"},{"odds":"3.60","describe":"平奖金/赔率"},{"odds":"2.43","describe":"负奖金/赔率"}]},{"id":"561","match_id":"20180419*4*002","daytime":"20180419","weekid":"4","teamid":"002","league":"瑞超","team":"哈马比:北雪平","endtime":"23:56","matchtime":"2018-04-20 01:00:00","flage":"0","wtype":"1","champion":"","unsupport_type":"FT006,0&FT001,0","created_at":"2018-04-17 21:22:47","homeTeam":"哈马比","awayTeam":"北雪平","week":"星期四","odds":[{"odds":"-1.00","describe":"篮球让分，足球让球数"},{"odds":"4.55","describe":"胜奖金/赔率"},{"odds":"4.00","describe":"平奖金/赔率"},{"odds":"1.52","describe":"负奖金/赔率"}]},{"id":"562","match_id":"20180419*4*003","daytime":"20180419","weekid":"4","teamid":"003","league":"瑞超","team":"布鲁马波卡纳:埃尔夫斯堡","endtime":"23:56","matchtime":"2018-04-20 01:00:00","flage":"0","wtype":"1","champion":"","unsupport_type":"FT001,0&FT006,0","created_at":"2018-04-17 21:22:47","homeTeam":"布鲁马波卡纳","awayTeam":"埃尔夫斯堡","week":"星期四","odds":[{"odds":"1.00","describe":"篮球让分，足球让球数"},{"odds":"1.60","describe":"胜奖金/赔率"},{"odds":"3.75","describe":"平奖金/赔率"},{"odds":"4.20","describe":"负奖金/赔率"}]},{"id":"563","match_id":"20180419*4*004","daytime":"20180419","weekid":"4","teamid":"004","league":"瑞超","team":"IFK哥德堡:达尔库尔德人","endtime":"23:56","matchtime":"2018-04-20 01:00:00","flage":"0","wtype":"1","champion":"","unsupport_type":"FT001,0&FT006,0","created_at":"2018-04-17 21:22:47","homeTeam":"IFK哥德堡","awayTeam":"达尔库尔德人","week":"星期四","odds":[{"odds":"-1.00","describe":"篮球让分，足球让球数"},{"odds":"3.37","describe":"胜奖金/赔率"},{"odds":"3.40","describe":"平奖金/赔率"},{"odds":"1.86","describe":"负奖金/赔率"}]},{"id":"564","match_id":"20180419*4*007","daytime":"20180419","weekid":"4","teamid":"007","league":"比甲","team":"布鲁日:沙勒罗瓦","endtime":"23:56","matchtime":"2018-04-20 02:30:00","flage":"0","wtype":"1","champion":"","unsupport_type":"FT006,0&FT001,0","created_at":"2018-04-17 21:22:47","homeTeam":"布鲁日","awayTeam":"沙勒罗瓦","week":"星期四","odds":[{"odds":"-1.00","describe":"篮球让分，足球让球数"},{"odds":"2.10","describe":"胜奖金/赔率"},{"odds":"3.45","describe":"平奖金/赔率"},{"odds":"2.75","describe":"负奖金/赔率"}]},{"id":"565","match_id":"20180419*4*010","daytime":"20180419","weekid":"4","teamid":"010","league":"英甲","team":"布莱克本:彼得堡联","endtime":"23:56","matchtime":"2018-04-20 02:45:00","flage":"0","wtype":"1","champion":"","unsupport_type":"FT001,0&FT006,0","created_at":"2018-04-17 21:22:47","homeTeam":"布莱克本","awayTeam":"彼得堡联","week":"星期四","odds":[{"odds":"-1.00","describe":"篮球让分，足球让球数"},{"odds":"2.30","describe":"胜奖金/赔率"},{"odds":"3.40","describe":"平奖金/赔率"},{"odds":"2.50","describe":"负奖金/赔率"}]},{"id":"566","match_id":"20180419*4*011","daytime":"20180419","weekid":"4","teamid":"011","league":"荷甲","team":"阿贾克斯:芬洛","endtime":"23:56","matchtime":"2018-04-20 02:45:00","flage":"0","wtype":"1","champion":"","unsupport_type":"FT006,0&FT001,0&FT001,1","created_at":"2018-04-17 21:22:47","homeTeam":"阿贾克斯","awayTeam":"芬洛","week":"星期四","odds":[{"odds":"-2.00","describe":"篮球让分，足球让球数"},{"odds":"1.75","describe":"胜奖金/赔率"},{"odds":"4.15","describe":"平奖金/赔率"},{"odds":"3.15","describe":"负奖金/赔率"}]},{"id":"567","match_id":"20180419*4*014","daytime":"20180419","weekid":"4","teamid":"014","league":"解放者杯","team":"竞技俱乐部:瓦斯科达伽马","endtime":"23:56","matchtime":"2018-04-20 06:15:00","flage":"0","wtype":"1","champion":"","unsupport_type":"FT001,0&FT006,0","created_at":"2018-04-17 21:22:47","homeTeam":"竞技俱乐部","awayTeam":"瓦斯科达伽马","week":"星期四","odds":[{"odds":"-1.00","describe":"篮球让分，足球让球数"},{"odds":"2.15","describe":"胜奖金/赔率"},{"odds":"3.35","describe":"平奖金/赔率"},{"odds":"2.74","describe":"负奖金/赔率"}]},{"id":"568","match_id":"20180419*4*015","daytime":"20180419","weekid":"4","teamid":"015","league":"巴西杯","team":"圣保罗:巴拉纳竞技","endtime":"23:56","matchtime":"2018-04-20 06:15:00","flage":"0","wtype":"1","champion":"","unsupport_type":"FT006,0&FT001,0","created_at":"2018-04-17 21:22:47","homeTeam":"圣保罗","awayTeam":"巴拉纳竞技","week":"星期四","odds":[{"odds":"-1.00","describe":"篮球让分，足球让球数"},{"odds":"2.86","describe":"胜奖金/赔率"},{"odds":"3.25","describe":"平奖金/赔率"},{"odds":"2.12","describe":"负奖金/赔率"}]},{"id":"569","match_id":"20180419*4*016","daytime":"20180419","weekid":"4","teamid":"016","league":"巴西杯","team":"维多利亚:巴西国际","endtime":"23:56","matchtime":"2018-04-20 06:15:00","flage":"0","wtype":"1","champion":"","unsupport_type":"FT006,0&FT001,0","created_at":"2018-04-17 21:22:47","homeTeam":"维多利亚","awayTeam":"巴西国际","week":"星期四","odds":[{"odds":"-1.00","describe":"篮球让分，足球让球数"},{"odds":"5.00","describe":"胜奖金/赔率"},{"odds":"3.75","describe":"平奖金/赔率"},{"odds":"1.51","describe":"负奖金/赔率"}]},{"id":"570","match_id":"20180419*4*017","daytime":"20180419","weekid":"4","teamid":"017","league":"解放者杯","team":"利马联盟:巴兰基亚青年","endtime":"23:56","matchtime":"2018-04-20 08:30:00","flage":"0","wtype":"1","champion":"","unsupport_type":"FT001,0&FT006,0","created_at":"2018-04-17 21:22:47","homeTeam":"利马联盟","awayTeam":"巴兰基亚青年","week":"星期四","odds":[{"odds":"1.00","describe":"篮球让分，足球让球数"},{"odds":"1.88","describe":"胜奖金/赔率"},{"odds":"3.25","describe":"平奖金/赔率"},{"odds":"3.45","describe":"负奖金/赔率"}]},{"id":"571","match_id":"20180419*4*018","daytime":"20180419","weekid":"4","teamid":"018","league":"解放者杯","team":"埃梅莱克:河床","endtime":"23:56","matchtime":"2018-04-20 08:30:00","flage":"0","wtype":"1","champion":"","unsupport_type":"FT001,0&FT006,0","created_at":"2018-04-17 21:22:47","homeTeam":"埃梅莱克","awayTeam":"河床","week":"星期四","odds":[{"odds":"1.00","describe":"篮球让分，足球让球数"},{"odds":"1.41","describe":"胜奖金/赔率"},{"odds":"3.90","describe":"平奖金/赔率"},{"odds":"6.10","describe":"负奖金/赔率"}]},{"id":"572","match_id":"20180419*4*019","daytime":"20180419","weekid":"4","teamid":"019","league":"解放者杯","team":"智利大学:克鲁塞罗","endtime":"23:56","matchtime":"2018-04-20 08:30:00","flage":"0","wtype":"1","champion":"","unsupport_type":"FT006,0&FT001,0","created_at":"2018-04-17 21:22:47","homeTeam":"智利大学","awayTeam":"克鲁塞罗","week":"星期四","odds":[{"odds":"-1.00","describe":"篮球让分，足球让球数"},{"odds":"5.80","describe":"胜奖金/赔率"},{"odds":"3.95","describe":"平奖金/赔率"},{"odds":"1.42","describe":"负奖金/赔率"}]}],"date":"2018-04-19","week":"星期四","count":19},{"match":[{"id":"576","match_id":"20180420*5*001","daytime":"20180420","weekid":"5","teamid":"001","league":"西甲","team":"埃瓦尔:阿拉维斯","endtime":"17:56","matchtime":"2018-04-20 12:00:00","flage":"0","wtype":"1","champion":"","unsupport_type":"","created_at":"2018-04-19 00:00:00","homeTeam":"埃瓦尔","awayTeam":"阿拉维斯","week":"星期五","odds":[{"odds":"-1.00","describe":"篮球让分，足球让球数"},{"odds":"4.85","describe":"胜奖金/赔率"},{"odds":"3.90","describe":"平奖金/赔率"},{"odds":"1.50","describe":"负奖金/赔率"}]}],"date":"2018-04-20","week":"星期五","count":1}]
     * error_code : 0
     * error_message :
     * time : 2018-04-19 12:25:55
     */

    private int code;
    private String message;
    private int error_code;
    private String error_message;
    private String time;
    private List<DataBean> data;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public int getError_code() {
        return error_code;
    }

    public void setError_code(int error_code) {
        this.error_code = error_code;
    }

    public String getError_message() {
        return error_message;
    }

    public void setError_message(String error_message) {
        this.error_message = error_message;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public List<DataBean> getData() {
        return data;
    }

    public void setData(List<DataBean> data) {
        this.data = data;
    }

    @Override
    public int getItemType() {
        return 0;
    }

    public static class DataBean  extends AbstractExpandableItem<FootBallList.DataBean.MatchBean> implements MultiItemEntity ,Serializable{
        /**
         * match : [{"id":"554","match_id":"20180419*4*005","daytime":"20180419","weekid":"4","teamid":"005","league":"西甲","team":"阿拉维斯:赫罗纳","endtime":"23:56","matchtime":"2018-04-20 01:30:00","flage":"0","wtype":"1","champion":"","unsupport_type":"FT006,0&FT001,0","created_at":"2018-04-17 21:22:46","homeTeam":"阿拉维斯","awayTeam":"赫罗纳","week":"星期四","odds":[{"odds":"-1.00","describe":"篮球让分，足球让球数"},{"odds":"4.70","describe":"胜奖金/赔率"},{"odds":"3.65","describe":"平奖金/赔率"},{"odds":"1.56","describe":"负奖金/赔率"}]},{"id":"555","match_id":"20180419*4*006","daytime":"20180419","weekid":"4","teamid":"006","league":"西甲","team":"皇家社会:马德里竞技","endtime":"23:56","matchtime":"2018-04-20 01:30:00","flage":"0","wtype":"1","champion":"","unsupport_type":"FT006,0&FT001,0","created_at":"2018-04-17 21:22:46","homeTeam":"皇家社会","awayTeam":"马德里竞技","week":"星期四","odds":[{"odds":"1.00","describe":"篮球让分，足球让球数"},{"odds":"1.61","describe":"胜奖金/赔率"},{"odds":"3.60","describe":"平奖金/赔率"},{"odds":"4.35","describe":"负奖金/赔率"}]},{"id":"556","match_id":"20180419*4*008","daytime":"20180419","weekid":"4","teamid":"008","league":"英超","team":"伯恩利:切尔西","endtime":"23:56","matchtime":"2018-04-20 02:45:00","flage":"0","wtype":"1","champion":"","unsupport_type":"FT001,0&FT006,0","created_at":"2018-04-17 21:22:47","homeTeam":"伯恩利","awayTeam":"切尔西","week":"星期四","odds":[{"odds":"1.00","describe":"篮球让分，足球让球数"},{"odds":"1.88","describe":"胜奖金/赔率"},{"odds":"3.35","describe":"平奖金/赔率"},{"odds":"3.35","describe":"负奖金/赔率"}]},{"id":"557","match_id":"20180419*4*009","daytime":"20180419","weekid":"4","teamid":"009","league":"英超","team":"莱切斯特城:南安普敦","endtime":"23:56","matchtime":"2018-04-20 02:45:00","flage":"0","wtype":"1","champion":"","unsupport_type":"FT006,0","created_at":"2018-04-17 21:22:47","homeTeam":"莱切斯特城","awayTeam":"南安普敦","week":"星期四","odds":[{"odds":"-1.00","describe":"篮球让分，足球让球数"},{"odds":"4.55","describe":"胜奖金/赔率"},{"odds":"3.85","describe":"平奖金/赔率"},{"odds":"1.54","describe":"负奖金/赔率"}]},{"id":"558","match_id":"20180419*4*012","daytime":"20180419","weekid":"4","teamid":"012","league":"西甲","team":"皇家贝蒂斯:拉斯帕尔马斯","endtime":"23:56","matchtime":"2018-04-20 03:30:00","flage":"0","wtype":"1","champion":"","unsupport_type":"FT001,0&FT006,0","created_at":"2018-04-17 21:22:47","homeTeam":"皇家贝蒂斯","awayTeam":"拉斯帕尔马斯","week":"星期四","odds":[{"odds":"-1.00","describe":"篮球让分，足球让球数"},{"odds":"1.72","describe":"胜奖金/赔率"},{"odds":"3.90","describe":"平奖金/赔率"},{"odds":"3.45","describe":"负奖金/赔率"}]},{"id":"559","match_id":"20180419*4*013","daytime":"20180419","weekid":"4","teamid":"013","league":"西甲","team":"莱万特:马拉加","endtime":"23:56","matchtime":"2018-04-20 03:30:00","flage":"0","wtype":"1","champion":"","unsupport_type":"FT006,0&FT001,0","created_at":"2018-04-17 21:22:47","homeTeam":"莱万特","awayTeam":"马拉加","week":"星期四","odds":[{"odds":"-1.00","describe":"篮球让分，足球让球数"},{"odds":"3.20","describe":"胜奖金/赔率"},{"odds":"3.25","describe":"平奖金/赔率"},{"odds":"1.97","describe":"负奖金/赔率"}]},{"id":"560","match_id":"20180419*4*001","daytime":"20180419","weekid":"4","teamid":"001","league":"荷甲","team":"乌德勒支:格罗宁根","endtime":"23:56","matchtime":"2018-04-20 00:30:00","flage":"0","wtype":"1","champion":"","unsupport_type":"FT006,0&FT001,0","created_at":"2018-04-17 21:22:47","homeTeam":"乌德勒支","awayTeam":"格罗宁根","week":"星期四","odds":[{"odds":"-1.00","describe":"篮球让分，足球让球数"},{"odds":"2.27","describe":"胜奖金/赔率"},{"odds":"3.60","describe":"平奖金/赔率"},{"odds":"2.43","describe":"负奖金/赔率"}]},{"id":"561","match_id":"20180419*4*002","daytime":"20180419","weekid":"4","teamid":"002","league":"瑞超","team":"哈马比:北雪平","endtime":"23:56","matchtime":"2018-04-20 01:00:00","flage":"0","wtype":"1","champion":"","unsupport_type":"FT006,0&FT001,0","created_at":"2018-04-17 21:22:47","homeTeam":"哈马比","awayTeam":"北雪平","week":"星期四","odds":[{"odds":"-1.00","describe":"篮球让分，足球让球数"},{"odds":"4.55","describe":"胜奖金/赔率"},{"odds":"4.00","describe":"平奖金/赔率"},{"odds":"1.52","describe":"负奖金/赔率"}]},{"id":"562","match_id":"20180419*4*003","daytime":"20180419","weekid":"4","teamid":"003","league":"瑞超","team":"布鲁马波卡纳:埃尔夫斯堡","endtime":"23:56","matchtime":"2018-04-20 01:00:00","flage":"0","wtype":"1","champion":"","unsupport_type":"FT001,0&FT006,0","created_at":"2018-04-17 21:22:47","homeTeam":"布鲁马波卡纳","awayTeam":"埃尔夫斯堡","week":"星期四","odds":[{"odds":"1.00","describe":"篮球让分，足球让球数"},{"odds":"1.60","describe":"胜奖金/赔率"},{"odds":"3.75","describe":"平奖金/赔率"},{"odds":"4.20","describe":"负奖金/赔率"}]},{"id":"563","match_id":"20180419*4*004","daytime":"20180419","weekid":"4","teamid":"004","league":"瑞超","team":"IFK哥德堡:达尔库尔德人","endtime":"23:56","matchtime":"2018-04-20 01:00:00","flage":"0","wtype":"1","champion":"","unsupport_type":"FT001,0&FT006,0","created_at":"2018-04-17 21:22:47","homeTeam":"IFK哥德堡","awayTeam":"达尔库尔德人","week":"星期四","odds":[{"odds":"-1.00","describe":"篮球让分，足球让球数"},{"odds":"3.37","describe":"胜奖金/赔率"},{"odds":"3.40","describe":"平奖金/赔率"},{"odds":"1.86","describe":"负奖金/赔率"}]},{"id":"564","match_id":"20180419*4*007","daytime":"20180419","weekid":"4","teamid":"007","league":"比甲","team":"布鲁日:沙勒罗瓦","endtime":"23:56","matchtime":"2018-04-20 02:30:00","flage":"0","wtype":"1","champion":"","unsupport_type":"FT006,0&FT001,0","created_at":"2018-04-17 21:22:47","homeTeam":"布鲁日","awayTeam":"沙勒罗瓦","week":"星期四","odds":[{"odds":"-1.00","describe":"篮球让分，足球让球数"},{"odds":"2.10","describe":"胜奖金/赔率"},{"odds":"3.45","describe":"平奖金/赔率"},{"odds":"2.75","describe":"负奖金/赔率"}]},{"id":"565","match_id":"20180419*4*010","daytime":"20180419","weekid":"4","teamid":"010","league":"英甲","team":"布莱克本:彼得堡联","endtime":"23:56","matchtime":"2018-04-20 02:45:00","flage":"0","wtype":"1","champion":"","unsupport_type":"FT001,0&FT006,0","created_at":"2018-04-17 21:22:47","homeTeam":"布莱克本","awayTeam":"彼得堡联","week":"星期四","odds":[{"odds":"-1.00","describe":"篮球让分，足球让球数"},{"odds":"2.30","describe":"胜奖金/赔率"},{"odds":"3.40","describe":"平奖金/赔率"},{"odds":"2.50","describe":"负奖金/赔率"}]},{"id":"566","match_id":"20180419*4*011","daytime":"20180419","weekid":"4","teamid":"011","league":"荷甲","team":"阿贾克斯:芬洛","endtime":"23:56","matchtime":"2018-04-20 02:45:00","flage":"0","wtype":"1","champion":"","unsupport_type":"FT006,0&FT001,0&FT001,1","created_at":"2018-04-17 21:22:47","homeTeam":"阿贾克斯","awayTeam":"芬洛","week":"星期四","odds":[{"odds":"-2.00","describe":"篮球让分，足球让球数"},{"odds":"1.75","describe":"胜奖金/赔率"},{"odds":"4.15","describe":"平奖金/赔率"},{"odds":"3.15","describe":"负奖金/赔率"}]},{"id":"567","match_id":"20180419*4*014","daytime":"20180419","weekid":"4","teamid":"014","league":"解放者杯","team":"竞技俱乐部:瓦斯科达伽马","endtime":"23:56","matchtime":"2018-04-20 06:15:00","flage":"0","wtype":"1","champion":"","unsupport_type":"FT001,0&FT006,0","created_at":"2018-04-17 21:22:47","homeTeam":"竞技俱乐部","awayTeam":"瓦斯科达伽马","week":"星期四","odds":[{"odds":"-1.00","describe":"篮球让分，足球让球数"},{"odds":"2.15","describe":"胜奖金/赔率"},{"odds":"3.35","describe":"平奖金/赔率"},{"odds":"2.74","describe":"负奖金/赔率"}]},{"id":"568","match_id":"20180419*4*015","daytime":"20180419","weekid":"4","teamid":"015","league":"巴西杯","team":"圣保罗:巴拉纳竞技","endtime":"23:56","matchtime":"2018-04-20 06:15:00","flage":"0","wtype":"1","champion":"","unsupport_type":"FT006,0&FT001,0","created_at":"2018-04-17 21:22:47","homeTeam":"圣保罗","awayTeam":"巴拉纳竞技","week":"星期四","odds":[{"odds":"-1.00","describe":"篮球让分，足球让球数"},{"odds":"2.86","describe":"胜奖金/赔率"},{"odds":"3.25","describe":"平奖金/赔率"},{"odds":"2.12","describe":"负奖金/赔率"}]},{"id":"569","match_id":"20180419*4*016","daytime":"20180419","weekid":"4","teamid":"016","league":"巴西杯","team":"维多利亚:巴西国际","endtime":"23:56","matchtime":"2018-04-20 06:15:00","flage":"0","wtype":"1","champion":"","unsupport_type":"FT006,0&FT001,0","created_at":"2018-04-17 21:22:47","homeTeam":"维多利亚","awayTeam":"巴西国际","week":"星期四","odds":[{"odds":"-1.00","describe":"篮球让分，足球让球数"},{"odds":"5.00","describe":"胜奖金/赔率"},{"odds":"3.75","describe":"平奖金/赔率"},{"odds":"1.51","describe":"负奖金/赔率"}]},{"id":"570","match_id":"20180419*4*017","daytime":"20180419","weekid":"4","teamid":"017","league":"解放者杯","team":"利马联盟:巴兰基亚青年","endtime":"23:56","matchtime":"2018-04-20 08:30:00","flage":"0","wtype":"1","champion":"","unsupport_type":"FT001,0&FT006,0","created_at":"2018-04-17 21:22:47","homeTeam":"利马联盟","awayTeam":"巴兰基亚青年","week":"星期四","odds":[{"odds":"1.00","describe":"篮球让分，足球让球数"},{"odds":"1.88","describe":"胜奖金/赔率"},{"odds":"3.25","describe":"平奖金/赔率"},{"odds":"3.45","describe":"负奖金/赔率"}]},{"id":"571","match_id":"20180419*4*018","daytime":"20180419","weekid":"4","teamid":"018","league":"解放者杯","team":"埃梅莱克:河床","endtime":"23:56","matchtime":"2018-04-20 08:30:00","flage":"0","wtype":"1","champion":"","unsupport_type":"FT001,0&FT006,0","created_at":"2018-04-17 21:22:47","homeTeam":"埃梅莱克","awayTeam":"河床","week":"星期四","odds":[{"odds":"1.00","describe":"篮球让分，足球让球数"},{"odds":"1.41","describe":"胜奖金/赔率"},{"odds":"3.90","describe":"平奖金/赔率"},{"odds":"6.10","describe":"负奖金/赔率"}]},{"id":"572","match_id":"20180419*4*019","daytime":"20180419","weekid":"4","teamid":"019","league":"解放者杯","team":"智利大学:克鲁塞罗","endtime":"23:56","matchtime":"2018-04-20 08:30:00","flage":"0","wtype":"1","champion":"","unsupport_type":"FT006,0&FT001,0","created_at":"2018-04-17 21:22:47","homeTeam":"智利大学","awayTeam":"克鲁塞罗","week":"星期四","odds":[{"odds":"-1.00","describe":"篮球让分，足球让球数"},{"odds":"5.80","describe":"胜奖金/赔率"},{"odds":"3.95","describe":"平奖金/赔率"},{"odds":"1.42","describe":"负奖金/赔率"}]}]
         * date : 2018-04-19
         * week : 星期四
         * count : 19
         */

        private String date;
        private String week;
        private int count;
        private List<MatchBean> match;

        public String getDate() {
            return date;
        }

        public void setDate(String date) {
            this.date = date;
        }

        public String getWeek() {
            return week;
        }

        public void setWeek(String week) {
            this.week = week;
        }

        public int getCount() {
            return count;
        }

        public void setCount(int count) {
            this.count = count;
        }

        public List<MatchBean> getMatch() {
            return match;
        }

        public void setMatch(List<MatchBean> match) {
            this.match = match;
        }
        @Override
        public int getLevel() {
            return 0;
        }

        @Override
        public int getItemType() {
            return WinAndLoseAdapter.TYPE_LEVEL_0;
        }

        @Override
        public String toString() {
            return "DataBean{" +
                    "date='" + date + '\'' +
                    ", week='" + week + '\'' +
                    ", count=" + count +
                    ", match=" + match +
                    '}';
        }

        public static class MatchBean implements MultiItemEntity,Serializable {
            /**
             * id : 554
             * match_id : 20180419*4*005
             * daytime : 20180419
             * weekid : 4
             * teamid : 005
             * league : 西甲
             * team : 阿拉维斯:赫罗纳
             * endtime : 23:56
             * matchtime : 2018-04-20 01:30:00
             * flage : 0
             * wtype : 1
             * champion :
             * unsupport_type : FT006,0&FT001,0
             * created_at : 2018-04-17 21:22:46
             * homeTeam : 阿拉维斯
             * awayTeam : 赫罗纳
             * week : 星期四
             * odds : [{"odds":"-1.00","describe":"篮球让分，足球让球数"},{"odds":"4.70","describe":"胜奖金/赔率"},{"odds":"3.65","describe":"平奖金/赔率"},{"odds":"1.56","describe":"负奖金/赔率"}]
             */
            private int homeType;
            private int vsType;
            private int awayType;

            private int fistfrom;
            private int secondfrom;
            private int thirdfrom;
            private int fourthfrom;
            private int fifthfrom;
            private int sixthfrom;
            private int seventhfrom;
            private int eighthfrom;
            private String select;

            public int getFistfrom() {
                return fistfrom;
            }

            public void setFistfrom(int fistfrom) {
                this.fistfrom = fistfrom;
            }

            public int getSecondfrom() {
                return secondfrom;
            }

            public void setSecondfrom(int secondfrom) {
                this.secondfrom = secondfrom;
            }

            public int getThirdfrom() {
                return thirdfrom;
            }

            public void setThirdfrom(int thirdfrom) {
                this.thirdfrom = thirdfrom;
            }

            public int getFourthfrom() {
                return fourthfrom;
            }

            public void setFourthfrom(int fourthfrom) {
                this.fourthfrom = fourthfrom;
            }

            public int getFifthfrom() {
                return fifthfrom;
            }

            public void setFifthfrom(int fifthfrom) {
                this.fifthfrom = fifthfrom;
            }

            public int getSixthfrom() {
                return sixthfrom;
            }

            public void setSixthfrom(int sixthfrom) {
                this.sixthfrom = sixthfrom;
            }

            public int getSeventhfrom() {
                return seventhfrom;
            }

            public void setSeventhfrom(int seventhfrom) {
                this.seventhfrom = seventhfrom;
            }

            public int getEighthfrom() {
                return eighthfrom;
            }

            public void setEighthfrom(int eighthfrom) {
                this.eighthfrom = eighthfrom;
            }

            public String getSelect() {
                return select;
            }

            public void setSelect(String select) {
                this.select = select;
            }

            private String id;
            private String match_id;
            private String daytime;
            private String weekid;
            private String teamid;
            private String league;
            private String team;
            private String endtime;
            private String matchtime;
            private String flage;
            private String wtype;
            private String champion;
            private String unsupport_type;
            private String created_at;
            private String homeTeam;
            private String awayTeam;
            private String week;
            private List<OddsBean> odds;

            public int getHomeType() {
                return homeType;
            }

            public void setHomeType(int homeType) {
                this.homeType = homeType;
            }

            public int getVsType() {
                return vsType;
            }

            public void setVsType(int vsType) {
                this.vsType = vsType;
            }

            public int getAwayType() {
                return awayType;
            }

            public void setAwayType(int awayType) {
                this.awayType = awayType;
            }

            public String getId() {
                return id;
            }

            public void setId(String id) {
                this.id = id;
            }

            public String getMatch_id() {
                return match_id;
            }

            public void setMatch_id(String match_id) {
                this.match_id = match_id;
            }

            public String getDaytime() {
                return daytime;
            }

            public void setDaytime(String daytime) {
                this.daytime = daytime;
            }

            public String getWeekid() {
                return weekid;
            }

            public void setWeekid(String weekid) {
                this.weekid = weekid;
            }

            public String getTeamid() {
                return teamid;
            }

            public void setTeamid(String teamid) {
                this.teamid = teamid;
            }

            public String getLeague() {
                return league;
            }

            public void setLeague(String league) {
                this.league = league;
            }

            public String getTeam() {
                return team;
            }

            public void setTeam(String team) {
                this.team = team;
            }

            public String getEndtime() {
                return endtime;
            }

            public void setEndtime(String endtime) {
                this.endtime = endtime;
            }

            public String getMatchtime() {
                return matchtime;
            }

            public void setMatchtime(String matchtime) {
                this.matchtime = matchtime;
            }

            public String getFlage() {
                return flage;
            }

            public void setFlage(String flage) {
                this.flage = flage;
            }

            public String getWtype() {
                return wtype;
            }

            public void setWtype(String wtype) {
                this.wtype = wtype;
            }

            public String getChampion() {
                return champion;
            }

            public void setChampion(String champion) {
                this.champion = champion;
            }

            public String getUnsupport_type() {
                return unsupport_type;
            }

            public void setUnsupport_type(String unsupport_type) {
                this.unsupport_type = unsupport_type;
            }

            public String getCreated_at() {
                return created_at;
            }

            public void setCreated_at(String created_at) {
                this.created_at = created_at;
            }

            public String getHomeTeam() {
                return homeTeam;
            }

            public void setHomeTeam(String homeTeam) {
                this.homeTeam = homeTeam;
            }

            public String getAwayTeam() {
                return awayTeam;
            }

            public void setAwayTeam(String awayTeam) {
                this.awayTeam = awayTeam;
            }

            public String getWeek() {
                return week;
            }

            public void setWeek(String week) {
                this.week = week;
            }

            public List<OddsBean> getOdds() {
                return odds;
            }

            public void setOdds(List<OddsBean> odds) {
                this.odds = odds;
            }

            @Override
            public int getItemType() {
                return WinAndLoseAdapter.TYPE_LEVEL_1;
            }

            @Override
            public String toString() {
                return "MatchBean{" +
                        "homeType=" + homeType +
                        ", vsType=" + vsType +
                        ", awayType=" + awayType +
                        '}';
            }

            public static class OddsBean implements Serializable{
                /**
                 * odds : 18.00
                 * name : 0
                 * describe : 进0球奖金(赔率)
                 */
                private int type;
                private String odds;
                private String name;
                private String describe;

                public int getType() {
                    return type;
                }

                public void setType(int type) {
                    this.type = type;
                }

                public String getOdds() {
                    return odds;
                }

                public void setOdds(String odds) {
                    this.odds = odds;
                }

                public String getName() {
                    return name;
                }

                public void setName(String name) {
                    this.name = name;
                }

                public String getDescribe() {
                    return describe;
                }

                public void setDescribe(String describe) {
                    this.describe = describe;
                }
            }

        }
    }
}
