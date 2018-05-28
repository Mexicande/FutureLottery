package com.xinhe.haoyuncaipiao.model;

import java.io.Serializable;

/**
 * Created by tantan on 2018/4/18.
 * 大乐透
 */

public class SuperLotto implements Serializable{
    private String danRed;
    private String red;
    private String danBlu;
    private String blu;
    //类型，0代表单式1代表复式2代表胆拖
    private int type;
    private long zhushu;
    private long money;

    public String getDanRed() {
        return danRed;
    }

    public void setDanRed(String danRed) {
        this.danRed = danRed;
    }

    public String getRed() {
        return red;
    }

    public void setRed(String red) {
        this.red = red;
    }

    public String getDanBlu() {
        return danBlu;
    }

    public void setDanBlu(String danBlu) {
        this.danBlu = danBlu;
    }

    public String getBlu() {
        return blu;
    }

    public void setBlu(String blu) {
        this.blu = blu;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public long getZhushu() {
        return zhushu;
    }

    public void setZhushu(long zhushu) {
        this.zhushu = zhushu;
    }

    public long getMoney() {
        return money;
    }

    public void setMoney(long money) {
        this.money = money;
    }

    @Override
    public String toString() {
        return "SuperLotto{" +
                "danRed='" + danRed + '\'' +
                ", red='" + red + '\'' +
                ", danBlu='" + danBlu + '\'' +
                ", blu='" + blu + '\'' +
                ", type=" + type +
                ", zhushu=" + zhushu +
                ", money=" + money +
                '}';
    }
}
