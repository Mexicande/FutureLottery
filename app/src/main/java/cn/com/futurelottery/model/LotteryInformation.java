package cn.com.futurelottery.model;

import java.io.Serializable;
import java.util.List;

/**
 * Created by tantan on 2018/4/24.
 */

public class LotteryInformation implements Serializable{

    /**
     * code : 200
     * message : OK
     * data : [{"end_time":"2018-04-19","lotid":"ssq","phase":"2018032","bonuscode":"05,06,07,08,20,17,04","week":"周四"},{"end_time":"2018-04-23","lotid":"dlt","phase":"18046","bonuscode":"01,02,03,04,05,06,07","week":"周一"},{"end_time":"2018-04-23","lotid":"dlt","phase":"18046","bonuscode":"01,02,03,04,05,06,07","week":"周一"},{"end_time":"2018-04-23","lotid":"dlt","phase":"18046","bonuscode":"01,02,03,04,05,06,07","week":"周一"},{"end_time":"2018-04-23","lotid":"p3","phase":"18106","bonuscode":"1,2,3","week":"周一"},{"end_time":"2018-04-23","lotid":"p3","phase":"18106","bonuscode":"1,2,3","week":"周一"},{"end_time":"2018-04-23","lotid":"p3","phase":"18106","bonuscode":"1,2,3","week":"周一"},{"end_time":"2018-04-23","lotid":"p5","phase":"18106","bonuscode":"1,2,3,4,5","week":"周一"},{"end_time":"2018-04-23","lotid":"p5","phase":"18106","bonuscode":"1,2,3,4,5","week":"周一"},{"end_time":"2018-04-23","lotid":"p5","phase":"18106","bonuscode":"1,2,3,4,5","week":"周一"},{"end_time":"2018-04-23","lotid":"dlc","phase":"18042384","bonuscode":"01,02,03,04,05","week":"周一"},{"end_time":"2018-04-24","lotid":"JXK3","phase":"180424036","bonuscode":"1,2,3","week":"周二"},{"end_time":"2018-04-24","lotid":"D11","phase":"18042439","bonuscode":"01,02,03,04,05","week":"周二"},{"front":"1登博思","result":"1:2","daytime":"2018-04-23","after":"埃门"}]
     * error_code : 0
     * error_message :
     * time : 2018-04-24 15:04:22
     */

    private int code;
    private String message;
    private int error_code;
    private String error_message;
    private String time;
    private List<DataProduct> data;

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

    public List<DataProduct> getData() {
        return data;
    }

    public void setData(List<DataProduct> data) {
        this.data = data;
    }

    public static class DataProduct {
        /**
         * end_time : 2018-04-19
         * lotid : ssq
         * phase : 2018032
         * bonuscode : 05,06,07,08,20,17,04
         * week : 周四
         * front : 1登博思
         * result : 1:2
         * daytime : 2018-04-23
         * after : 埃门
         */

        private String end_time;
        private String lotid;
        private String phase;
        private String bonuscode;
        private String week;
        private String front;
        private String result;
        private String daytime;
        private String after;

        public String getEnd_time() {
            return end_time;
        }

        public void setEnd_time(String end_time) {
            this.end_time = end_time;
        }

        public String getLotid() {
            return lotid;
        }

        public void setLotid(String lotid) {
            this.lotid = lotid;
        }

        public String getPhase() {
            return phase;
        }

        public void setPhase(String phase) {
            this.phase = phase;
        }

        public String getBonuscode() {
            return bonuscode;
        }

        public void setBonuscode(String bonuscode) {
            this.bonuscode = bonuscode;
        }

        public String getWeek() {
            return week;
        }

        public void setWeek(String week) {
            this.week = week;
        }

        public String getFront() {
            return front;
        }

        public void setFront(String front) {
            this.front = front;
        }

        public String getResult() {
            return result;
        }

        public void setResult(String result) {
            this.result = result;
        }

        public String getDaytime() {
            return daytime;
        }

        public void setDaytime(String daytime) {
            this.daytime = daytime;
        }

        public String getAfter() {
            return after;
        }

        public void setAfter(String after) {
            this.after = after;
        }
    }
}
