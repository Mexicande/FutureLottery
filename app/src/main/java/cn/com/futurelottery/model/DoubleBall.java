package cn.com.futurelottery.model;

import java.io.Serializable;

/**
 * Created by tantan on 2018/4/13.
 * 双色球
 */

public class DoubleBall implements Serializable{
    private String dan;
    private String red;
    private String blu;
    //类型，0代表单式1代表复式2代表胆拖
    private int type;
    private long zhushu;
    private long money;

    public String getDan() {
        return dan;
    }

    public void setDan(String dan) {
        this.dan = dan;
    }

    public String getRed() {
        return red;
    }

    public void setRed(String red) {
        this.red = red;
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
        return "DoubleBall{" +
                "dan='" + dan + '\'' +
                ", red='" + red + '\'' +
                ", blu='" + blu + '\'' +
                ", type='" + type + '\'' +
                ", zhushu='" + zhushu + '\'' +
                ", money='" + money + '\'' +
                '}';
    }
}
