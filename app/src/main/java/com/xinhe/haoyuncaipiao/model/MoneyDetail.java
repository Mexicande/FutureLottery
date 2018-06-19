package com.xinhe.haoyuncaipiao.model;

import java.io.Serializable;
import java.util.List;

/**
 * Created by apple on 2018/6/15.
 */

public class MoneyDetail implements Serializable {

    /**
     * money : 14.00
     * frost_cash : 180.00
     * info : [{"amount":"30.00","created_at":"2018-05-27 23:01:05","desc":"支付宝充值30.00元","type":1},{"amount":"30.00","created_at":"2018-05-27 23:01:05","desc":"支付宝充值30.00元","type":1},{"amount":"12.00","created_at":"2018-05-22 21:09:21","desc":"购买彩票消费12元"},{"amount":"12.00","created_at":"2018-05-22 21:11:22","desc":"购买彩票消费12元"},{"amount":"12.00","created_at":"2018-05-22 21:42:43","desc":"购买彩票消费12元"},{"amount":"3.00","created_at":"2018-05-29 14:23:02","desc":"购买彩票消费3元"},{"amount":"10.00","created_at":"2018-06-15 11:18:56","desc":"申请提现冻结10元"},{"amount":"10.00","created_at":"2018-06-15 11:19:25","desc":"申请提现冻结10元"}]
     */

    private String money;
    private String frost_cash;
    private List<InfoBean> info;

    public String getMoney() {
        return money;
    }

    public void setMoney(String money) {
        this.money = money;
    }

    public String getFrost_cash() {
        return frost_cash;
    }

    public void setFrost_cash(String frost_cash) {
        this.frost_cash = frost_cash;
    }

    public List<InfoBean> getInfo() {
        return info;
    }

    public void setInfo(List<InfoBean> info) {
        this.info = info;
    }

    public static class InfoBean {
        /**
         * amount : 30.00
         * created_at : 2018-05-27 23:01:05
         * desc : 支付宝充值30.00元
         * type : 1
         */

        private String amount;
        private String created_at;
        private String desc;
        private int type;

        public String getAmount() {
            return amount;
        }

        public void setAmount(String amount) {
            this.amount = amount;
        }

        public String getCreated_at() {
            return created_at;
        }

        public void setCreated_at(String created_at) {
            this.created_at = created_at;
        }

        public String getDesc() {
            return desc;
        }

        public void setDesc(String desc) {
            this.desc = desc;
        }

        public int getType() {
            return type;
        }

        public void setType(int type) {
            this.type = type;
        }
    }
}
