package cn.com.futurelottery.model;

import java.io.Serializable;

/**
 * Created by tantan on 2018/5/18.
 */

public class ChippedDetail implements Serializable{
    public static class InfoProduct implements Serializable{
        /**
         * user_name : 幸运彩60571189
         * pay_money_total : 3.00
         * created_at : 05-16 04:59
         * winning_money: 0.00
         */

        private String user_name;
        private String pay_money_total;
        private String created_at;
        private String winning_money;

        public String getUser_name() {
            return user_name;
        }

        public void setUser_name(String user_name) {
            this.user_name = user_name;
        }

        public String getPay_money_total() {
            return pay_money_total;
        }

        public void setPay_money_total(String pay_money_total) {
            this.pay_money_total = pay_money_total;
        }

        public String getCreated_at() {
            return created_at;
        }

        public void setCreated_at(String created_at) {
            this.created_at = created_at;
        }

        public String getWinning_money() {
            return winning_money;
        }

        public void setWinning_money(String winning_money) {
            this.winning_money = winning_money;
        }
    }

    public static class DataProduct implements Serializable{
        /**
         * bouns : 01,02,03,04$07,08,09#01,02
         * play_type : 103
         * lotid : ssq
         * multiple : 1
         */

        private String bouns;
        private String play_type;
        private String lotid;
        private String multiple;
        private String type;

        public String getBouns() {
            return bouns;
        }

        public void setBouns(String bouns) {
            this.bouns = bouns;
        }

        public String getPlay_type() {
            return play_type;
        }

        public void setPlay_type(String play_type) {
            this.play_type = play_type;
        }

        public String getLotid() {
            return lotid;
        }

        public void setLotid(String lotid) {
            this.lotid = lotid;
        }

        public String getMultiple() {
            return multiple;
        }

        public void setMultiple(String multiple) {
            this.multiple = multiple;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }
    }
    public static class DataFootball implements Serializable{

        /**
         * week : 2
         * te : 001
         * na : 鹿岛鹿角:水原三星*VS
         * play : 胜平负
         * selected : 主胜,平,sp:--
         * result : 未开奖
         */

        private int week;
        private String te;
        private String na;
        private String play;
        private String selected;
        private String result;

        public int getWeek() {
            return week;
        }

        public void setWeek(int week) {
            this.week = week;
        }

        public String getTe() {
            return te;
        }

        public void setTe(String te) {
            this.te = te;
        }

        public String getNa() {
            return na;
        }

        public void setNa(String na) {
            this.na = na;
        }

        public String getPlay() {
            return play;
        }

        public void setPlay(String play) {
            this.play = play;
        }

        public String getSelected() {
            return selected;
        }

        public void setSelected(String selected) {
            this.selected = selected;
        }

        public String getResult() {
            return result;
        }

        public void setResult(String result) {
            this.result = result;
        }
    }
}
