package com.xinhe.haoyuncaipiao.model;

/**
 * Created by apple on 2018/4/14.
 * 首页通知
 */

public class Notification {
    /**
     * title : 消息
     * content :
     * created_at : 1970-01-02 00:00:00
     */

    private String title;
    private String content;
    private String created_at;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getCreated_at() {
        return created_at;
    }

    public void setCreated_at(String created_at) {
        this.created_at = created_at;
    }
}
