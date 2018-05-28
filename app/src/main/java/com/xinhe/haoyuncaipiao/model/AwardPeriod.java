package com.xinhe.haoyuncaipiao.model;


import java.io.Serializable;

/**
 *
 * @author apple
 * @date 2018/4/16
 */

public class AwardPeriod implements Serializable {

    /**
     * winning : 03,06,07,08,25,17,04
     * period : 2018032
     */

    private String winning;
    private String period;
    private String memo;
    private String commissioning;
    //type0双色球，1大乐透，2排5,3排3,4表示3D
    private int type;

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getWinning() {
        return winning;
    }

    public void setWinning(String winning) {
        this.winning = winning;
    }

    public String getPeriod() {
        return period;
    }

    public void setPeriod(String period) {
        this.period = period;
    }

    public String getMemo() {
        return memo;
    }

    public void setMemo(String memo) {
        this.memo = memo;
    }

    public String getCommissioning() {
        return commissioning;
    }

    public void setCommissioning(String commissioning) {
        this.commissioning = commissioning;
    }
}
