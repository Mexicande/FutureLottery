package com.xinhe.haoyuncaipiao.model;

import com.bin.david.form.annotation.ColumnType;
import com.bin.david.form.annotation.SmartColumn;

import java.util.List;

/**
 * Created by huang on 2018/2/1.
 */

public class Lesson {
    @SmartColumn(id = 3,name = "课程名称")
    private String name;
    @SmartColumn(id = 4,name="投注(出票赔率)")
    private  String selected;
    @SmartColumn(id = 5,name="彩果")
    private  String result;


    public Lesson(String name, String selected,String result) {
        this.name = name;
        this.selected = selected;
        this.result=result;
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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }



}
