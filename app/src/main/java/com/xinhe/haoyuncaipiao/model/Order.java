package com.xinhe.haoyuncaipiao.model;

import java.io.Serializable;

/**
 * Created by tantan on 2018/4/25.
 * 订单
 */

public class Order implements Serializable{

    /**
     * openmatch : 5
     * pay_money_total : 20.00
     * name : 竞彩足球胜平负
     * status : 2
     * winning_money : 0
     * created_at : 05-19
     * order_id : 152671293588500776
     * type : 3
     * lotid : FT001
     */

    private String openmatch;
    private String pay_money_total;
    private String name;
    private String status;
    private String winning_money;
    private String created_at;
    private String order_id;
    private int type;
    private String lotid;

    public String getOpenmatch() {
        return openmatch;
    }

    public void setOpenmatch(String openmatch) {
        this.openmatch = openmatch;
    }

    public String getPay_money_total() {
        return pay_money_total;
    }

    public void setPay_money_total(String pay_money_total) {
        this.pay_money_total = pay_money_total;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getWinning_money() {
        return winning_money;
    }

    public void setWinning_money(String winning_money) {
        this.winning_money = winning_money;
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

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getLotid() {
        return lotid;
    }

    public void setLotid(String lotid) {
        this.lotid = lotid;
    }
}
