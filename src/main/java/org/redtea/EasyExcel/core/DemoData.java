package org.redtea.EasyExcel.core;

import com.alibaba.excel.annotation.ExcelProperty;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class DemoData {
    @ExcelProperty(value = "uid", index = 0)
    private String uid;
    @ExcelProperty(value = "抽取时间", index = 1)
    private String time;
    @ExcelProperty(value = "卡池", index = 2)
    private String pools;
    @ExcelProperty(value = "类型", index = 3)
    private String types;
    @ExcelProperty(value = "物品", index = 4)
    private String items;
    @ExcelProperty(value = "品质", index = 5)
    private Integer rank;

    @Override
    public String toString() {
        return "DemoData{" +
                "uid='" + uid + '\'' +
                ", time='" + time + '\'' +
                ", pools='" + pools + '\'' +
                ", types='" + types + '\'' +
                ", items='" + items + '\'' +
                ", rank=" + rank +
                '}';
    }
}
