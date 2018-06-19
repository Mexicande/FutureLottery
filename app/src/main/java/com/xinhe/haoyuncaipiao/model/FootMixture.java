package com.xinhe.haoyuncaipiao.model;

import com.bin.david.form.annotation.ColumnType;
import com.bin.david.form.annotation.SmartColumn;
import com.bin.david.form.annotation.SmartTable;

import java.util.List;

/**
 * Created by huang on 2018/2/6.
 */
@SmartTable(name = "课程表")
public class FootMixture {
    @SmartColumn(id = 1,name ="场次")
    private String name;
    @SmartColumn(id = 2,name ="主队vs客队")
    private String na;
    @SmartColumn(type = ColumnType.ArrayChild)
    private List<Lesson> lesson;

    public FootMixture(String name, String na, List<Lesson> lesson) {
        this.name = name;
        this.na = na;
        this.lesson = lesson;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAge() {
        return na;
    }

    public void setAge(String age) {
        this.na = age;
    }

    public List<Lesson> getWeeks() {
        return lesson;
    }

    public void setWeeks(List<Lesson> weeks) {
        this.lesson = weeks;
    }
}
