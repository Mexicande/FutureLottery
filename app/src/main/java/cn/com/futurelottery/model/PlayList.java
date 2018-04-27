package cn.com.futurelottery.model;

import java.io.Serializable;
import java.util.List;

/**
 * Created by apple on 2018/4/27.
 * 赛事列表
 */

public class PlayList implements Serializable {


    /**
     * code : 200
     * message : OK
     * data : [{"league":"俄超","count":4},{"league":"墨超","count":2},{"league":"巴甲","count":8},{"league":"德乙","count":1},{"league":"德甲","count":2},{"league":"意甲","count":2},{"league":"挪超","count":7},{"league":"智利甲","count":4},{"league":"比甲","count":3},{"league":"法甲","count":2},{"league":"瑞超","count":5},{"league":"美职足","count":2},{"league":"英超","count":2},{"league":"英足总杯","count":1},{"league":"荷兰杯","count":1},{"league":"葡超","count":5},{"league":"西甲","count":4},{"league":"阿甲","count":7}]
     * error_code : 0
     * error_message :
     * time : 2018-04-22 21:00:53
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

    public static class DataBean {
        /**
         * league : 俄超
         * count : 4
         */

        private String league;
        private int count;

        public String getLeague() {
            return league;
        }

        public void setLeague(String league) {
            this.league = league;
        }

        public int getCount() {
            return count;
        }

        public void setCount(int count) {
            this.count = count;
        }
    }
}

