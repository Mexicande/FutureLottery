package com.xinhe.haoyuncaipiao.model;

import java.io.Serializable;

/**
 * Created by tantan on 2018/4/27.
 */

public class BankInformation implements Serializable{

    /**
     * id : 1
     * open_bank : 名胜
     * bank_id : 7271
     */

    private int id;
    private String open_bank;
    private String bank_id;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getOpen_bank() {
        return open_bank;
    }

    public void setOpen_bank(String open_bank) {
        this.open_bank = open_bank;
    }

    public String getBank_id() {
        return bank_id;
    }

    public void setBank_id(String bank_id) {
        this.bank_id = bank_id;
    }

    @Override
    public String toString() {
        return "BankInformation{" +
                "id=" + id +
                ", open_bank='" + open_bank + '\'' +
                ", bank_id='" + bank_id + '\'' +
                '}';
    }
}
