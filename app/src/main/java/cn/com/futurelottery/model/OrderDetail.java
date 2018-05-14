package cn.com.futurelottery.model;

import java.io.Serializable;
import java.util.List;

/**
 * Created by tantan on 2018/5/13.
 */

public class OrderDetail implements Serializable{

    /**
     * code : 200
     * message : OK
     * data : {"bonuscode":"","openmatch":5,"status":2,"info":[{"multiple":"1","play_type":"101","bouns":"01,04,10,11,16,31#11"}],"logo":"http://or2eh71ll.bkt.clouddn.com/152463165024708.png","name":"双色球","phase":"2018054","created_at":"2018-05-13 14:20:15","order_id":"152619241522035974","lotid":"ssq","winning_money":0,"pay_money_total":2}
     * error_code : 0
     * error_message :
     * time : 2018-05-13 15:35:23
     */

    private int code;
    private String message;
    private DataProduct data;
    private int error_code;
    private String error_message;
    private String time;

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

    public DataProduct getData() {
        return data;
    }

    public void setData(DataProduct data) {
        this.data = data;
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

    public static class DataProduct {
        /**
         * bonuscode :
         * openmatch : 5
         * status : 2
         * info : [{"multiple":"1","play_type":"101","bouns":"01,04,10,11,16,31#11"}]
         * logo : http://or2eh71ll.bkt.clouddn.com/152463165024708.png
         * name : 双色球
         * phase : 2018054
         * created_at : 2018-05-13 14:20:15
         * order_id : 152619241522035974
         * lotid : ssq
         * winning_money : 0
         * pay_money_total : 2
         */

        private String bonuscode;
        private String openmatch;
        private String status;
        private String logo;
        private String name;
        private String phase;
        private String created_at;
        private String order_id;
        private String lotid;
        private String winning_money;
        private String pay_money_total;
        private List<InfoProduct> info;

        public String getBonuscode() {
            return bonuscode;
        }

        public void setBonuscode(String bonuscode) {
            this.bonuscode = bonuscode;
        }

        public String getOpenmatch() {
            return openmatch;
        }

        public void setOpenmatch(String openmatch) {
            this.openmatch = openmatch;
        }

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
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

        public String getPhase() {
            return phase;
        }

        public void setPhase(String phase) {
            this.phase = phase;
        }

        public String getCreated_at() {
            return created_at;
        }

        public void setCreated_at(String created_at) {
            this.created_at = created_at;
        }

        public String getOrder_id() {
            return order_id;
        }

        public void setOrder_id(String order_id) {
            this.order_id = order_id;
        }

        public String getLotid() {
            return lotid;
        }

        public void setLotid(String lotid) {
            this.lotid = lotid;
        }

        public String getWinning_money() {
            return winning_money;
        }

        public void setWinning_money(String winning_money) {
            this.winning_money = winning_money;
        }

        public String getPay_money_total() {
            return pay_money_total;
        }

        public void setPay_money_total(String pay_money_total) {
            this.pay_money_total = pay_money_total;
        }

        public List<InfoProduct> getInfo() {
            return info;
        }

        public void setInfo(List<InfoProduct> info) {
            this.info = info;
        }

        public static class InfoProduct {
            /**
             * multiple : 1
             * play_type : 101
             * bouns : 01,04,10,11,16,31#11
             */

            private String multiple;
            private String play_type;
            private String bouns;

            public String getMultiple() {
                return multiple;
            }

            public void setMultiple(String multiple) {
                this.multiple = multiple;
            }

            public String getPlay_type() {
                return play_type;
            }

            public void setPlay_type(String play_type) {
                this.play_type = play_type;
            }

            public String getBouns() {
                return bouns;
            }

            public void setBouns(String bouns) {
                this.bouns = bouns;
            }
        }
    }
}
