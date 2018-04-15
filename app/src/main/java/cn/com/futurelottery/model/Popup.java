package cn.com.futurelottery.model;

import java.io.Serializable;

/**
 *
 * @author apple
 * @date 2018/4/14
 */

public class Popup implements Serializable{

    /**
     * title : 弹窗
     * img :
     * link_url :
     * created_at : 1970-01-02 00:00:00
     */

    private String title;
    private String img;
    private String link_url;
    private String created_at;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public String getLink_url() {
        return link_url;
    }

    public void setLink_url(String link_url) {
        this.link_url = link_url;
    }

    public String getCreated_at() {
        return created_at;
    }

    public void setCreated_at(String created_at) {
        this.created_at = created_at;
    }
}
