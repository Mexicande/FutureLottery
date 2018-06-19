package com.xinhe.haoyuncaipiao.model;

import com.bin.david.form.annotation.ColumnType;
import com.bin.david.form.annotation.SmartColumn;

import java.util.List;

/**
 * Created by huang on 2018/2/6.
 */

public class DayTime {
    @SmartColumn(id=3,name ="玩法")
    private String time;
    @SmartColumn(type = ColumnType.ArrayChild)
    private Lesson lessons;

    public DayTime() {
    }

    public DayTime(String time, Lesson lessons) {
        this.time = time;
        this.lessons = lessons;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public Lesson getLessons() {
        return lessons;
    }

    public void setLessons(Lesson lessons) {
        this.lessons = lessons;
    }
}
