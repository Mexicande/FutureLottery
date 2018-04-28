package cn.com.futurelottery.model;

import java.io.Serializable;

/**
 * Created by tantan on 2018/4/25.
 * 订单
 */

public class Order implements Serializable{


    /**
     * id : 1
     * lotid : ssq
     * created_at : 04-25
     * internal_id : 2
     * pay_money : 2
     * is_chasing : 0
     * openmatch : 0
     * name : 双色球
     * winning_money :
     */

    private String id;
    private String lotid;
    private String created_at;
    private String internal_id;
    private String pay_money;
    private String is_chasing;
    private String openmatch;
    private String name;
    private String winning_money;
    private String status;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

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

    public String getInternal_id() {
        return internal_id;
    }

    public void setInternal_id(String internal_id) {
        this.internal_id = internal_id;
    }

    public String getPay_money() {
        return pay_money;
    }

    public void setPay_money(String pay_money) {
        this.pay_money = pay_money;
    }

    public String getIs_chasing() {
        return is_chasing;
    }

    public void setIs_chasing(String is_chasing) {
        this.is_chasing = is_chasing;
    }

    public String getOpenmatch() {
        return openmatch;
    }

    public void setOpenmatch(String openmatch) {
        this.openmatch = openmatch;
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

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }


    @Override
    public String toString() {
        return "Order{" +
                "id='" + id + '\'' +
                ", lotid='" + lotid + '\'' +
                ", created_at='" + created_at + '\'' +
                ", internal_id='" + internal_id + '\'' +
                ", pay_money='" + pay_money + '\'' +
                ", is_chasing='" + is_chasing + '\'' +
                ", openmatch='" + openmatch + '\'' +
                ", name='" + name + '\'' +
                ", winning_money='" + winning_money + '\'' +
                ", status='" + status + '\'' +
                '}';
    }
}
