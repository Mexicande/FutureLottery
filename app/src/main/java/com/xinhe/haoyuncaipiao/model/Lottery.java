package com.xinhe.haoyuncaipiao.model;

import java.io.Serializable;

/**
 *
 * @author apple
 * @date 2018/4/11
 * 彩票种类
 */

public class Lottery implements Serializable {

    /**
     * id : 1
     * title : 双色球
     * logo :
     * desc : 这是双色球
     * created_at : 1970-01-02 00:00:00
     * status : 1
     * lotid : ssq
     */

    private String id;
    private String title;
    private String logo;
    private String desc;
    private String created_at;
    private String status;
    private String lotid;

    public String getId() {
        return id;
    }

    public void setId(String id) {
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

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getLotid() {
        return lotid;
    }

    public void setLotid(String lotid) {
        this.lotid = lotid;
    }

    @Override
    public String toString() {
        return "Lottery{" +
                "id='" + id + '\'' +
                ", title='" + title + '\'' +
                ", logo='" + logo + '\'' +
                ", desc='" + desc + '\'' +
                ", created_at='" + created_at + '\'' +
                ", status='" + status + '\'' +
                ", lotid='" + lotid + '\'' +
                '}';
    }
}
