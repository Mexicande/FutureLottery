package cn.com.futurelottery.model;

import java.io.Serializable;
import java.util.List;

/**
 * Created by tantan on 2018/5/2.
 */

public class FootBallOrder implements Serializable{

    /**
     * id : 21
     * lotid : FT001
     * created_at : 1970-01-02 00:00:00
     * internal_id : 50
     * pay_money : 2
     * is_chasing : 0
     * openmatch : 3
     * phase : 20180426*4*001,20180426*4*002
     * notes : 1
     * tc_order_num : syc2018201804262037425509993
     * data : ["20180426*4*001*3^20180426*4*002*3"]
     * multiple : 1
     * play_type : ftb
     * bonuscode :
     * logo : http://or2eh71ll.bkt.clouddn.com/152463163583863.png
     * name : 竞彩足球胜平负
     * arr : [{"week":"4","team_id":"001","team":"利勒斯特罗姆:罗森博格*0:1","selected":"主胜12.0","result":"负","letpoint":0},{"week":"4","team_id":"002","team":"阿森纳:马德里竞技*1:1","selected":"主胜12.0","result":"平","letpoint":0}]
     * mess : 中奖
     * winmoney : 24.00
     * count : 2
     * strand : 0
     */

    private String id;
    private String lotid;
    private String created_at;
    private String internal_id;
    private String pay_money;
    private String is_chasing;
    private String openmatch;
    private String phase;
    private String notes;
    private String tc_order_num;
    private String data;
    private String multiple;
    private String play_type;
    private String bonuscode;
    private String logo;
    private String name;
    private String mess;
    private String winmoney;
    private String count;
    private String strand;
    private List<ArrProduct> arr;

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

    public String getPhase() {
        return phase;
    }

    public void setPhase(String phase) {
        this.phase = phase;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public String getTc_order_num() {
        return tc_order_num;
    }

    public void setTc_order_num(String tc_order_num) {
        this.tc_order_num = tc_order_num;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public String getMultiple() {
        return multiple;
    }

    public void setMultiple(String multiple) {
        this.multiple = multiple;
    }

    public String getPlay_type() {
        return play_type;
    }

    public void setPlay_type(String play_type) {
        this.play_type = play_type;
    }

    public String getBonuscode() {
        return bonuscode;
    }

    public void setBonuscode(String bonuscode) {
        this.bonuscode = bonuscode;
    }

    public String getLogo() {
        return logo;
    }

    public void setLogo(String logo) {
        this.logo = logo;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMess() {
        return mess;
    }

    public void setMess(String mess) {
        this.mess = mess;
    }

    public String getWinmoney() {
        return winmoney;
    }

    public void setWinmoney(String winmoney) {
        this.winmoney = winmoney;
    }

    public String getCount() {
        return count;
    }

    public void setCount(String count) {
        this.count = count;
    }

    public String getStrand() {
        return strand;
    }

    public void setStrand(String strand) {
        this.strand = strand;
    }

    public List<ArrProduct> getArr() {
        return arr;
    }

    public void setArr(List<ArrProduct> arr) {
        this.arr = arr;
    }

    public static class ArrProduct implements Serializable{
        /**
         * week : 4
         * team_id : 001
         * team : 利勒斯特罗姆:罗森博格*0:1
         * selected : 主胜12.0
         * result : 负
         * letpoint : 0
         */

        private String week;
        private String team_id;
        private String team;
        private String selected;
        private String result;
        private String letpoint;

        public String getWeek() {
            return week;
        }

        public void setWeek(String week) {
            this.week = week;
        }

        public String getTeam_id() {
            return team_id;
        }

        public void setTeam_id(String team_id) {
            this.team_id = team_id;
        }

        public String getTeam() {
            return team;
        }

        public void setTeam(String team) {
            this.team = team;
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

        public String getLetpoint() {
            return letpoint;
        }

        public void setLetpoint(String letpoint) {
            this.letpoint = letpoint;
        }
    }
}
