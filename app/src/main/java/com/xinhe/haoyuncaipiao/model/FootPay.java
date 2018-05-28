package com.xinhe.haoyuncaipiao.model;

import java.util.List;

/**
 * Created by apple on 2018/4/24.
 */

public class FootPay {

    /**
     * title : [{"team":"马拉加:皇马","type":"3,1,0","match":"20180414*6*016"},{"team":"马拉加:皇马","type":"3,1,0","match":"20180414*6*016"}]
     * message : {"strand":"2串1,3串1","play_rules":"FT001","number":12,"money":120,"multiple":2}
     */

    private MessageBean message;
    private List<TitleBean> title;

    public MessageBean getMessage() {
        return message;
    }

    public void setMessage(MessageBean message) {
        this.message = message;
    }

    public List<TitleBean> getTitle() {
        return title;
    }

    public void setTitle(List<TitleBean> title) {
        this.title = title;
    }

    public static class MessageBean {
        /**
         * strand : 2串1,3串1
         * play_rules : FT001
         * number : 12
         * money : 120
         * multiple : 2
         */

        private String strand;
        private String play_rules;
        private int number;
        private int money;
        private int multiple;

        public String getStrand() {
            return strand;
        }

        public void setStrand(String strand) {
            this.strand = strand;
        }

        public String getPlay_rules() {
            return play_rules;
        }

        public void setPlay_rules(String play_rules) {
            this.play_rules = play_rules;
        }

        public int getNumber() {
            return number;
        }

        public void setNumber(int number) {
            this.number = number;
        }

        public int getMoney() {
            return money;
        }

        public void setMoney(int money) {
            this.money = money;
        }

        public int getMultiple() {
            return multiple;
        }

        public void setMultiple(int multiple) {
            this.multiple = multiple;
        }
    }

    public static class TitleBean {
        /**
         * team : 马拉加:皇马
         * type : 3,1,0
         * match : 20180414*6*016
         */

        private String team;
        private String type;
        private String match;

        public String getTeam() {
            return team;
        }

        public void setTeam(String team) {
            this.team = team;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public String getMatch() {
            return match;
        }

        public void setMatch(String match) {
            this.match = match;
        }
    }
}
