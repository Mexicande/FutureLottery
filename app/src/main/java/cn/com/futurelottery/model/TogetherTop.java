package cn.com.futurelottery.model;

import java.io.Serializable;

/**
 * Created by tantan on 2018/5/16.
 */

public class TogetherTop implements Serializable{

    /**
     * id : 17
     * title : 大乐透
     * logo : www.baidu.com
     * desc : 大乐透
     * created_at : 1970-01-02 00:00:00
     * status : 1
     * lotid : dlt
     */

    private int id;
    private String title;
    private String logo;
    private String desc;
    private String created_at;
    private int status;
    private String lotid;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getLogo() {
        return logo;
    }

    public void setLogo(String logo) {
        this.logo = logo;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getCreated_at() {
        return created_at;
    }

    public void setCreated_at(String created_at) {
        this.created_at = created_at;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getLotid() {
        return lotid;
    }

    public void setLotid(String lotid) {
        this.lotid = lotid;
    }
}
