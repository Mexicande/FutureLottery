package cn.com.futurelottery.model;

import java.io.Serializable;

/**
 * Created by apple on 2018/4/14.
 * Banner
 */

public class Banner implements Serializable{

    /**
     * title : 标题
     * img : http://or2eh71ll.bkt.clouddn.com/151618086714634.png
     * link_url : www.baidu.com
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

    @Override
    public String toString() {
        return "Banner{" +
                "title='" + title + '\'' +
                ", img='" + img + '\'' +
                ", link_url='" + link_url + '\'' +
                ", created_at='" + created_at + '\'' +
                '}';
    }
}
