package cn.com.futurelottery.model;

import java.io.Serializable;

/**
 * Created by apple on 2018/4/11.
 * 彩票种类
 */

public class Product implements Serializable {
    private String img;
    private String name;
    private String desc;

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }
}
