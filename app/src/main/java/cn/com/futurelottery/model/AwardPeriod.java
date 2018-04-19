package cn.com.futurelottery.model;

import com.chad.library.adapter.base.entity.MultiItemEntity;

import java.io.Serializable;

/**
 * Created by apple on 2018/4/16.
 */

public class AwardPeriod implements Serializable {

    /**
     * winning : 03,06,07,08,25,17,04
     * period : 2018032
     */

    private String winning;
    private String period;
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
}
