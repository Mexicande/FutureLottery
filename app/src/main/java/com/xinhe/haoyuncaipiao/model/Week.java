package com.xinhe.haoyuncaipiao.model;

import com.bin.david.form.annotation.ColumnType;
import com.bin.david.form.annotation.SmartColumn;

import java.util.List;

/**
 * Created by huang on 2018/2/6.
 */

public class Week {
    @SmartColumn(id=2,name ="场次")
    private String name;
    @SmartColumn(type = ColumnType.ArrayChild)
    private List<DayTime> times;

    public Week(String name, List<DayTime> times) {
        this.name = name;
        this.times = times;
    }
}
