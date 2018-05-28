package com.xinhe.haoyuncaipiao.model;

import java.io.Serializable;
import java.util.List;

/**
 * Created by tantan on 2018/5/21.
 */

public class RedPacket implements Serializable{

    /**
     * code : 200
     * message : OK
     * data : {"info":[{"id":"46","red_id":"1","title":"新手红包","amount":"10","end_time":"2018-08-19 23:59:59","type":"dlt,ssq","effective":90,"whole":2},{"id":"47","red_id":"1","title":"新手红包","amount":"10","end_time":"2018-08-19 23:59:59","type":"dlt,ssq","effective":90,"whole":2},{"id":"48","red_id":"1","title":"新手红包","amount":"10","end_time":"2018-08-19 23:59:59","type":"dlt,ssq","effective":90,"whole":2},{"id":"49","red_id":"1","title":"新手红包","amount":"10","end_time":"2018-08-19 23:59:59","type":"dlt,ssq","effective":90,"whole":2},{"id":"50","red_id":"1","title":"新手红包","amount":"10","end_time":"2018-08-19 23:59:59","type":"dlt,ssq","effective":90,"whole":2},{"id":"51","red_id":"1","title":"新手红包","amount":"10","end_time":"2018-08-19 23:59:59","type":"dlt,ssq","effective":90,"whole":2},{"id":"52","red_id":"1","title":"新手红包","amount":"10","end_time":"2018-08-19 23:59:59","type":"dlt,ssq","effective":90,"whole":2},{"id":"53","red_id":"1","title":"新手红包","amount":"10","end_time":"2018-08-19 23:59:59","type":"dlt,ssq","effective":90,"whole":2},{"id":"54","red_id":"1","title":"新手红包","amount":"10","end_time":"2018-08-19 23:59:59","type":"dlt,ssq","effective":90,"whole":2},{"id":"55","red_id":"1","title":"新手红包","amount":"10","end_time":"2018-08-19 23:59:59","type":"dlt,ssq","effective":90,"whole":2},{"id":"56","red_id":"1","title":"新手红包","amount":"10","end_time":"2018-08-19 23:59:59","type":"dlt,ssq","effective":90,"whole":2},{"id":"57","red_id":"1","title":"新手红包","amount":"10","end_time":"2018-08-19 23:59:59","type":"dlt,ssq","effective":90,"whole":2},{"id":"58","red_id":"1","title":"新手红包","amount":"10","end_time":"2018-08-19 23:59:59","type":"dlt,ssq","effective":90,"whole":2},{"id":"59","red_id":"1","title":"新手红包","amount":"10","end_time":"2018-08-19 23:59:59","type":"dlt,ssq","effective":90,"whole":2},{"id":"60","red_id":"1","title":"新手红包","amount":"10","end_time":"2018-08-19 23:59:59","type":"dlt,ssq","effective":90,"whole":2},{"id":"61","red_id":"1","title":"新手红包","amount":"10","end_time":"2018-08-19 23:59:59","type":"dlt,ssq","effective":90,"whole":2},{"id":"62","red_id":"1","title":"新手红包","amount":"10","end_time":"2018-08-19 23:59:59","type":"dlt,ssq","effective":90,"whole":2},{"id":"63","red_id":"1","title":"新手红包","amount":"10","end_time":"2018-08-19 23:59:59","type":"dlt,ssq","effective":90,"whole":2},{"id":"64","red_id":"1","title":"新手红包","amount":"10","end_time":"2018-08-19 23:59:59","type":"dlt,ssq","effective":90,"whole":2},{"id":"65","red_id":"1","title":"新手红包","amount":"10","end_time":"2018-08-19 23:59:59","type":"dlt,ssq","effective":90,"whole":2}],"limit_end":20}
     * error_code : 0
     * error_message :
     * time : 2018-05-21 15:29:51
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
         * info : [{"id":"46","red_id":"1","title":"新手红包","amount":"10","end_time":"2018-08-19 23:59:59","type":"dlt,ssq","effective":90,"whole":2},{"id":"47","red_id":"1","title":"新手红包","amount":"10","end_time":"2018-08-19 23:59:59","type":"dlt,ssq","effective":90,"whole":2},{"id":"48","red_id":"1","title":"新手红包","amount":"10","end_time":"2018-08-19 23:59:59","type":"dlt,ssq","effective":90,"whole":2},{"id":"49","red_id":"1","title":"新手红包","amount":"10","end_time":"2018-08-19 23:59:59","type":"dlt,ssq","effective":90,"whole":2},{"id":"50","red_id":"1","title":"新手红包","amount":"10","end_time":"2018-08-19 23:59:59","type":"dlt,ssq","effective":90,"whole":2},{"id":"51","red_id":"1","title":"新手红包","amount":"10","end_time":"2018-08-19 23:59:59","type":"dlt,ssq","effective":90,"whole":2},{"id":"52","red_id":"1","title":"新手红包","amount":"10","end_time":"2018-08-19 23:59:59","type":"dlt,ssq","effective":90,"whole":2},{"id":"53","red_id":"1","title":"新手红包","amount":"10","end_time":"2018-08-19 23:59:59","type":"dlt,ssq","effective":90,"whole":2},{"id":"54","red_id":"1","title":"新手红包","amount":"10","end_time":"2018-08-19 23:59:59","type":"dlt,ssq","effective":90,"whole":2},{"id":"55","red_id":"1","title":"新手红包","amount":"10","end_time":"2018-08-19 23:59:59","type":"dlt,ssq","effective":90,"whole":2},{"id":"56","red_id":"1","title":"新手红包","amount":"10","end_time":"2018-08-19 23:59:59","type":"dlt,ssq","effective":90,"whole":2},{"id":"57","red_id":"1","title":"新手红包","amount":"10","end_time":"2018-08-19 23:59:59","type":"dlt,ssq","effective":90,"whole":2},{"id":"58","red_id":"1","title":"新手红包","amount":"10","end_time":"2018-08-19 23:59:59","type":"dlt,ssq","effective":90,"whole":2},{"id":"59","red_id":"1","title":"新手红包","amount":"10","end_time":"2018-08-19 23:59:59","type":"dlt,ssq","effective":90,"whole":2},{"id":"60","red_id":"1","title":"新手红包","amount":"10","end_time":"2018-08-19 23:59:59","type":"dlt,ssq","effective":90,"whole":2},{"id":"61","red_id":"1","title":"新手红包","amount":"10","end_time":"2018-08-19 23:59:59","type":"dlt,ssq","effective":90,"whole":2},{"id":"62","red_id":"1","title":"新手红包","amount":"10","end_time":"2018-08-19 23:59:59","type":"dlt,ssq","effective":90,"whole":2},{"id":"63","red_id":"1","title":"新手红包","amount":"10","end_time":"2018-08-19 23:59:59","type":"dlt,ssq","effective":90,"whole":2},{"id":"64","red_id":"1","title":"新手红包","amount":"10","end_time":"2018-08-19 23:59:59","type":"dlt,ssq","effective":90,"whole":2},{"id":"65","red_id":"1","title":"新手红包","amount":"10","end_time":"2018-08-19 23:59:59","type":"dlt,ssq","effective":90,"whole":2}]
         * limit_end : 20
         */

        private int limit_end;
        private int count;
        private List<InfoProduct> info;

        public int getLimit_end() {
            return limit_end;
        }

        public void setLimit_end(int limit_end) {
            this.limit_end = limit_end;
        }

        public int getCount() {
            return count;
        }

        public void setCount(int count) {
            this.count = count;
        }

        public List<InfoProduct> getInfo() {
            return info;
        }

        public void setInfo(List<InfoProduct> info) {
            this.info = info;
        }

        public static class InfoProduct {
            /**
             * id : 46
             * red_id : 1
             * title : 新手红包
             * amount : 10
             * end_time : 2018-08-19 23:59:59
             * type : dlt,ssq
             * effective : 90
             * whole : 2
             */

            private String id;
            private String red_id;
            private String title;
            private String amount;
            private String end_time;
            private String type;
            private int effective;
            private int whole;

            public String getId() {
                return id;
            }

            public void setId(String id) {
                this.id = id;
            }

            public String getRed_id() {
                return red_id;
            }

            public void setRed_id(String red_id) {
                this.red_id = red_id;
            }

            public String getTitle() {
                return title;
            }

            public void setTitle(String title) {
                this.title = title;
            }

            public String getAmount() {
                return amount;
            }

            public void setAmount(String amount) {
                this.amount = amount;
            }

            public String getEnd_time() {
                return end_time;
            }

            public void setEnd_time(String end_time) {
                this.end_time = end_time;
            }

            public String getType() {
                return type;
            }

            public void setType(String type) {
                this.type = type;
            }

            public int getEffective() {
                return effective;
            }

            public void setEffective(int effective) {
                this.effective = effective;
            }

            public int getWhole() {
                return whole;
            }

            public void setWhole(int whole) {
                this.whole = whole;
            }
        }
    }
}
