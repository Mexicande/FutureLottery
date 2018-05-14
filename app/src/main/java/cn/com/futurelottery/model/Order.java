package cn.com.futurelottery.model;

import java.io.Serializable;

/**
 * Created by tantan on 2018/4/25.
 * 订单
 */

public class Order implements Serializable{

    /**
     * lotid : FT005
     * created_at : 05-11
     * order_id : 152602509075355673
     * chasing_id :
     * openmatch : 5
     * pay_money_total : 9900
     * name : 竞彩足球混合串关玩法
     * status : 2
     * winning_money : 0
     */

    private String lotid;
    private String created_at;
    private String order_id;
    private String chasing_id;
    private String openmatch;
    private String pay_money_total;
    private String name;
    private String status;
    private int winning_money;

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

    public int getWinning_money() {
        return winning_money;
    }

    public void setWinning_money(int winning_money) {
        this.winning_money = winning_money;
    }

    @Override
    public String toString() {
        return "Order{" +
                "lotid='" + lotid + '\'' +
                ", created_at='" + created_at + '\'' +
                ", order_id='" + order_id + '\'' +
                ", chasing_id='" + chasing_id + '\'' +
                ", openmatch='" + openmatch + '\'' +
                ", pay_money_total='" + pay_money_total + '\'' +
                ", name='" + name + '\'' +
                ", status=" + status +
                ", winning_money=" + winning_money +
                '}';
    }
}
