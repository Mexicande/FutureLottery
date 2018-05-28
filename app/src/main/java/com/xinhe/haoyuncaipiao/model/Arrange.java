package com.xinhe.haoyuncaipiao.model;

import java.io.Serializable;

/**
 * Created by tantan on 2018/5/7.
 */

public class Arrange implements Serializable{

    /**
     * individual : 12
     * ten : 1
     * hundred : 1
     * thousand : 1
     * absolutely : 1
     * notes : 2
     * money : 4
     * type : 1
     * zhi :
     * three_dan : 1,2
     * three_fu :
     * six :
     * periods : 1
     * multiple : 1
     */

    private String individual;
    private String ten;
    private String hundred;
    private String thousand;
    private String absolutely;
    private long notes;
    private long money;
    private int type;
    private String zhi;
    private String three_dan;
    private String three_fu;
    private String six;
    private int periods;
    private int multiple;

    public String getIndividual() {
        return individual;
    }

    public void setIndividual(String individual) {
        this.individual = individual;
    }

    public String getTen() {
        return ten;
    }

    public void setTen(String ten) {
        this.ten = ten;
    }

    public String getHundred() {
        return hundred;
    }

    public void setHundred(String hundred) {
        this.hundred = hundred;
    }

    public String getThousand() {
        return thousand;
    }

    public void setThousand(String thousand) {
        this.thousand = thousand;
    }

    public String getAbsolutely() {
        return absolutely;
    }

    public void setAbsolutely(String absolutely) {
        this.absolutely = absolutely;
    }

    public long getNotes() {
        return notes;
    }

    public void setNotes(long notes) {
        this.notes = notes;
    }

    public long getMoney() {
        return money;
    }

    public void setMoney(long money) {
        this.money = money;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getZhi() {
        return zhi;
    }

    public void setZhi(String zhi) {
        this.zhi = zhi;
    }

    public String getThree_dan() {
        return three_dan;
    }

    public void setThree_dan(String three_dan) {
        this.three_dan = three_dan;
    }

    public String getThree_fu() {
        return three_fu;
    }

    public void setThree_fu(String three_fu) {
        this.three_fu = three_fu;
    }

    public String getSix() {
        return six;
    }

    public void setSix(String six) {
        this.six = six;
    }

    public int getPeriods() {
        return periods;
    }

    public void setPeriods(int periods) {
        this.periods = periods;
    }

    public int getMultiple() {
        return multiple;
    }

    public void setMultiple(int multiple) {
        this.multiple = multiple;
    }
}
