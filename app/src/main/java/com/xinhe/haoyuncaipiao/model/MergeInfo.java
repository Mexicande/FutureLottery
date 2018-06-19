package com.xinhe.haoyuncaipiao.model;

import com.bin.david.form.annotation.SmartColumn;
import com.bin.david.form.annotation.SmartTable;

/**
 * Created by huang on 2017/11/1.
 */
@SmartTable(name="合并信息列表")
public class MergeInfo {
    @SmartColumn(id =1,name = "姓名",autoCount = true,autoMerge = true)
    private String name;
    @SmartColumn(id=2,name="年龄",autoCount = true,autoMerge = true)
    private int age;
    @SmartColumn(id =3,name="更新时间")
    private long time;
    private String url;

    public MergeInfo(String name, int age, long time) {
        this.name = name;
        this.age = age;
        this.time = time;
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public long getTime() {
        return time;
    }

    public void setTime(long time) {
        this.time = time;
    }


    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
