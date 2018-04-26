package cn.com.futurelottery.model;

import java.io.Serializable;

/**
 * Created by tantan on 2018/4/24.
 */

public class BallInformation implements Serializable{

    /**
     * id : 7
     * end_time : 2018-04-24
     * lotid : ssq
     * phase : 2018032
     * bonuscode : 05,06,07,08,20,17,04
     * week : 周二
     */

    private int id;
    private String end_time;
    private String lotid;
    private String phase;
    private String bonuscode;
    private String week;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getEnd_time() {
        return end_time;
    }

    public void setEnd_time(String end_time) {
        this.end_time = end_time;
    }

    public String getLotid() {
        return lotid;
    }

    public void setLotid(String lotid) {
        this.lotid = lotid;
    }

    public String getPhase() {
        return phase;
    }

    public void setPhase(String phase) {
        this.phase = phase;
    }

    public String getBonuscode() {
        return bonuscode;
    }

    public void setBonuscode(String bonuscode) {
        this.bonuscode = bonuscode;
    }

    public String getWeek() {
        return week;
    }

    public void setWeek(String week) {
        this.week = week;
    }

    @Override
    public String toString() {
        return "BallInformation{" +
                "id=" + id +
                ", end_time='" + end_time + '\'' +
                ", lotid='" + lotid + '\'' +
                ", phase='" + phase + '\'' +
                ", bonuscode='" + bonuscode + '\'' +
                ", week='" + week + '\'' +
                '}';
    }
}
