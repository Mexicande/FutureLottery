package com.xinhe.haoyuncaipiao.model;

import java.io.Serializable;
import java.util.List;

/**
 * Created by tantan on 2018/5/2.
 */

public class FootBallOrder implements Serializable{


    /**
     * code : 200
     * message : OK
     * data : [{"id":"3054","thirdorder_id":"0","user_id":"13","internal_id":"1","order_id":"1526024078297702144","goal":"0","openmatch":"3","opendate":"1970-01-02 00:00:00","selected":"0","ball":"0","strand":"0","odd":"0","status":"4","lotid":"FT005","data":"0","play_type":"ftb","notes":"21","multiple":"10","pay_money":"0","phase":"","tc_order_num":"","message":"","is_chasing":"0","code":"11111","order_time":"1970-01-02 00:00:00","created_at":"1970-01-02 00:00:00","arr":[{"week":"4","team_id":"014","team":"萨尔茨堡:马赛*1:1","selected":"平12.0*FT001*1:1*12.0*FT002*平12.0*FT001","result":"平","letpoint":0},{"week":"1","team_id":"011","team":"沙佩科恩斯:巴拉纳*1:0","selected":"3:1*12.0*FT002*主胜12.0*FT001","result":"主胜","letpoint":0}],"mess":"中奖","count":1,"logo":"http://or2eh71ll.bkt.clouddn.com/152463163583863.png","name":"竞彩足球混合串关玩法","winning_money":10000}]
     * error_code : 0
     * error_message :
     * time : 2018-05-13 17:04:04
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

    public static class DataProduct implements Serializable{
        /**
         * id : 3054
         * thirdorder_id : 0
         * user_id : 13
         * internal_id : 1
         * order_id : 1526024078297702144
         * goal : 0
         * openmatch : 3
         * opendate : 1970-01-02 00:00:00
         * selected : 0
         * ball : 0
         * strand : 0
         * odd : 0
         * status : 4
         * lotid : FT005
         * data : 0
         * play_type : ftb
         * notes : 21
         * multiple : 10
         * pay_money : 0
         * phase :
         * tc_order_num :
         * message :
         * is_chasing : 0
         * code : 11111
         * order_time : 1970-01-02 00:00:00
         * created_at : 1970-01-02 00:00:00
         * arr : [{"week":"4","team_id":"014","team":"萨尔茨堡:马赛*1:1","selected":"平12.0*FT001*1:1*12.0*FT002*平12.0*FT001","result":"平","letpoint":0},{"week":"1","team_id":"011","team":"沙佩科恩斯:巴拉纳*1:0","selected":"3:1*12.0*FT002*主胜12.0*FT001","result":"主胜","letpoint":0}]
         * mess : 中奖
         * count : 1
         * logo : http://or2eh71ll.bkt.clouddn.com/152463163583863.png
         * name : 竞彩足球混合串关玩法
         * winning_money : 10000
         */

        private String id;
        private String thirdorder_id;
        private String user_id;
        private String internal_id;
        private String order_id;
        private String goal;
        private String openmatch;
        private String opendate;
        private String selected;
        private String ball;
        private String strand;
        private String odd;
        private String status;
        private String lotid;
        private String data;
        private String play_type;
        private String notes;
        private String multiple;
        private String pay_money;
        private String phase;
        private String tc_order_num;
        private String message;
        private String is_chasing;
        private String code;
        private String order_time;
        private String created_at;
        private String mess;
        private String count;
        private String logo;
        private String name;
        private String winning_money;
        private List<ArrProduct> arr;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getThirdorder_id() {
            return thirdorder_id;
        }

        public void setThirdorder_id(String thirdorder_id) {
            this.thirdorder_id = thirdorder_id;
        }

        public String getUser_id() {
            return user_id;
        }

        public void setUser_id(String user_id) {
            this.user_id = user_id;
        }

        public String getInternal_id() {
            return internal_id;
        }

        public void setInternal_id(String internal_id) {
            this.internal_id = internal_id;
        }

        public String getOrder_id() {
            return order_id;
        }

        public void setOrder_id(String order_id) {
            this.order_id = order_id;
        }

        public String getGoal() {
            return goal;
        }

        public void setGoal(String goal) {
            this.goal = goal;
        }

        public String getOpenmatch() {
            return openmatch;
        }

        public void setOpenmatch(String openmatch) {
            this.openmatch = openmatch;
        }

        public String getOpendate() {
            return opendate;
        }

        public void setOpendate(String opendate) {
            this.opendate = opendate;
        }

        public String getSelected() {
            return selected;
        }

        public void setSelected(String selected) {
            this.selected = selected;
        }

        public String getBall() {
            return ball;
        }

        public void setBall(String ball) {
            this.ball = ball;
        }

        public String getStrand() {
            return strand;
        }

        public void setStrand(String strand) {
            this.strand = strand;
        }

        public String getOdd() {
            return odd;
        }

        public void setOdd(String odd) {
            this.odd = odd;
        }

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }

        public String getLotid() {
            return lotid;
        }

        public void setLotid(String lotid) {
            this.lotid = lotid;
        }

        public String getData() {
            return data;
        }

        public void setData(String data) {
            this.data = data;
        }

        public String getPlay_type() {
            return play_type;
        }

        public void setPlay_type(String play_type) {
            this.play_type = play_type;
        }

        public String getNotes() {
            return notes;
        }

        public void setNotes(String notes) {
            this.notes = notes;
        }

        public String getMultiple() {
            return multiple;
        }

        public void setMultiple(String multiple) {
            this.multiple = multiple;
        }

        public String getPay_money() {
            return pay_money;
        }

        public void setPay_money(String pay_money) {
            this.pay_money = pay_money;
        }

        public String getPhase() {
            return phase;
        }

        public void setPhase(String phase) {
            this.phase = phase;
        }

        public String getTc_order_num() {
            return tc_order_num;
        }

        public void setTc_order_num(String tc_order_num) {
            this.tc_order_num = tc_order_num;
        }

        public String getMessage() {
            return message;
        }

        public void setMessage(String message) {
            this.message = message;
        }

        public String getIs_chasing() {
            return is_chasing;
        }

        public void setIs_chasing(String is_chasing) {
            this.is_chasing = is_chasing;
        }

        public String getCode() {
            return code;
        }

        public void setCode(String code) {
            this.code = code;
        }

        public String getOrder_time() {
            return order_time;
        }

        public void setOrder_time(String order_time) {
            this.order_time = order_time;
        }

        public String getCreated_at() {
            return created_at;
        }

        public void setCreated_at(String created_at) {
            this.created_at = created_at;
        }

        public String getMess() {
            return mess;
        }

        public void setMess(String mess) {
            this.mess = mess;
        }

        public String getCount() {
            return count;
        }

        public void setCount(String count) {
            this.count = count;
        }

        public String getLogo() {
            return logo;
        }

        public void setLogo(String logo) {
            this.logo = logo;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getWinning_money() {
            return winning_money;
        }

        public void setWinning_money(String winning_money) {
            this.winning_money = winning_money;
        }

        public List<ArrProduct> getArr() {
            return arr;
        }

        public void setArr(List<ArrProduct> arr) {
            this.arr = arr;
        }

        public static class ArrProduct implements Serializable{
            /**
             * week : 4
             * team_id : 014
             * team : 萨尔茨堡:马赛*1:1
             * selected : 平12.0*FT001*1:1*12.0*FT002*平12.0*FT001
             * result : 平
             * letpoint : 0
             */

            private String week;
            private String team_id;
            private String team;
            private String selected;
            private String result;
            private List<ChippedDetail.DataFootball.InfoBean> info;

            private int letpoint;

            public List<ChippedDetail.DataFootball.InfoBean> getInfo() {
                return info;
            }

            public void setInfo(List<ChippedDetail.DataFootball.InfoBean> info) {
                this.info = info;
            }


            public String getWeek() {
                return week;
            }

            public void setWeek(String week) {
                this.week = week;
            }

            public String getTeam_id() {
                return team_id;
            }

            public void setTeam_id(String team_id) {
                this.team_id = team_id;
            }

            public String getTeam() {
                return team;
            }

            public void setTeam(String team) {
                this.team = team;
            }

            public String getSelected() {
                return selected;
            }

            public void setSelected(String selected) {
                this.selected = selected;
            }

            public String getResult() {
                return result;
            }

            public void setResult(String result) {
                this.result = result;
            }

            public int getLetpoint() {
                return letpoint;
            }

            public void setLetpoint(int letpoint) {
                this.letpoint = letpoint;
            }
        }


    }


}
