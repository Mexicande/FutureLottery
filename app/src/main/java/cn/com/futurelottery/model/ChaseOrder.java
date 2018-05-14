package cn.com.futurelottery.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by tantan on 2018/5/13.
 */

public class ChaseOrder implements Serializable{

    /**
     * code : 200
     * message : OK
     * data : {"logo":"http://or2eh71ll.bkt.clouddn.com/152489684445207.png","name":"排列3","total_periods":"2","already_periods":"0","remaining_periods":"2","pay_money_total":"8","is_stop":"1","stop_money":"0.00","winning_money":0,"info":[{"lotid":"dlt","created_at":"2018-05-09 14:28:43","pay_money_total":"2","order_id":"152584732369977819","chasing_id":"152584915664019048","start_periods":"18053","openmatch":5,"winning_money":0,"status":2}]}
     * error_code : 0
     * error_message :
     * time : 2018-05-13 18:35:22
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

    public static class DataProduct implements Serializable{
        /**
         * logo : http://or2eh71ll.bkt.clouddn.com/152489684445207.png
         * name : 排列3
         * total_periods : 2
         * already_periods : 0
         * remaining_periods : 2
         * pay_money_total : 8
         * is_stop : 1
         * stop_money : 0.00
         * winning_money : 0
         * info : [{"lotid":"dlt","created_at":"2018-05-09 14:28:43","pay_money_total":"2","order_id":"152584732369977819","chasing_id":"152584915664019048","start_periods":"18053","openmatch":5,"winning_money":0,"status":2}]
         */

        private String logo;
        private String name;
        private String total_periods;
        private String already_periods;
        private String remaining_periods;
        private String pay_money_total;
        private String is_stop;
        private String stop_money;
        private String winning_money;
        private ArrayList<InfoProduct> info;

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

        public String getTotal_periods() {
            return total_periods;
        }

        public void setTotal_periods(String total_periods) {
            this.total_periods = total_periods;
        }

        public String getAlready_periods() {
            return already_periods;
        }

        public void setAlready_periods(String already_periods) {
            this.already_periods = already_periods;
        }

        public String getRemaining_periods() {
            return remaining_periods;
        }

        public void setRemaining_periods(String remaining_periods) {
            this.remaining_periods = remaining_periods;
        }

        public String getPay_money_total() {
            return pay_money_total;
        }

        public void setPay_money_total(String pay_money_total) {
            this.pay_money_total = pay_money_total;
        }

        public String getIs_stop() {
            return is_stop;
        }

        public void setIs_stop(String is_stop) {
            this.is_stop = is_stop;
        }

        public String getStop_money() {
            return stop_money;
        }

        public void setStop_money(String stop_money) {
            this.stop_money = stop_money;
        }

        public String getWinning_money() {
            return winning_money;
        }

        public void setWinning_money(String winning_money) {
            this.winning_money = winning_money;
        }

        public ArrayList<InfoProduct> getInfo() {
            return info;
        }

        public void setInfo(ArrayList<InfoProduct> info) {
            this.info = info;
        }

        public static class InfoProduct implements Serializable{
            /**
             * lotid : dlt
             * created_at : 2018-05-09 14:28:43
             * pay_money_total : 2
             * order_id : 152584732369977819
             * chasing_id : 152584915664019048
             * start_periods : 18053
             * openmatch : 5
             * winning_money : 0
             * status : 2
             */

            private String lotid;
            private String created_at;
            private String pay_money_total;
            private String order_id;
            private String chasing_id;
            private String start_periods;
            private int openmatch;
            private int winning_money;
            private int status;

            public String getLotid() {
                return lotid;
            }

            public void setLotid(String lotid) {
                this.lotid = lotid;
            }

            public String getCreated_at() {
                return created_at;
            }

            public void setCreated_at(String created_at) {
                this.created_at = created_at;
            }

            public String getPay_money_total() {
                return pay_money_total;
            }

            public void setPay_money_total(String pay_money_total) {
                this.pay_money_total = pay_money_total;
            }

            public String getOrder_id() {
                return order_id;
            }

            public void setOrder_id(String order_id) {
                this.order_id = order_id;
            }

            public String getChasing_id() {
                return chasing_id;
            }

            public void setChasing_id(String chasing_id) {
                this.chasing_id = chasing_id;
            }

            public String getStart_periods() {
                return start_periods;
            }

            public void setStart_periods(String start_periods) {
                this.start_periods = start_periods;
            }

            public int getOpenmatch() {
                return openmatch;
            }

            public void setOpenmatch(int openmatch) {
                this.openmatch = openmatch;
            }

            public int getWinning_money() {
                return winning_money;
            }

            public void setWinning_money(int winning_money) {
                this.winning_money = winning_money;
            }

            public int getStatus() {
                return status;
            }

            public void setStatus(int status) {
                this.status = status;
            }
        }
    }
}
