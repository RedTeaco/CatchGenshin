package org.redtea.EasyExcel.core;

import com.alibaba.excel.annotation.ExcelProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ItemEntityData {
    @ExcelProperty
    private String uid;
    @ExcelProperty
    private String item_id;
    @ExcelProperty
    private String item_type;
    @ExcelProperty
    private String count;
    @ExcelProperty
    private String name;
    @ExcelProperty
    private String gacha_type;
    @ExcelProperty
    private String time;
    @ExcelProperty
    private String id;
    @ExcelProperty
    private String lang;
    @ExcelProperty
    private String rank_type;

    @Override
    public String toString() {
        return "ItemEntityData{" +
                "uid='" + uid + '\'' +
                ", item_id='" + item_id + '\'' +
                ", item_type='" + item_type + '\'' +
                ", count='" + count + '\'' +
                ", name='" + name + '\'' +
                ", gacha_type='" + gacha_type + '\'' +
                ", time='" + time + '\'' +
                ", id='" + id + '\'' +
                ", lang='" + lang + '\'' +
                ", rank_type='" + rank_type + '\'' +
                '}';
    }
}
