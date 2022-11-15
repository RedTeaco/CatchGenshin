package org.redtea.EasyExcel.read;

import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;
import org.redtea.Genshin.ItemEntity;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class readFromExcel {

    public static Map<String, List<ItemEntity>> readData(String filename) {
        List<ItemEntity> pool100 = new ArrayList<>();
        List<ItemEntity> pool200 = new ArrayList<>();
        List<ItemEntity> pool301 = new ArrayList<>();
        List<ItemEntity> pool302 = new ArrayList<>();
        Map<String, List<ItemEntity>> map = new LinkedHashMap<>();
        //分别读取三个表中的数据并存入三个list中
        EasyExcel.read(filename, ItemEntity.class, new AnalysisEventListener<ItemEntity>() {
            @Override
            public void invoke(ItemEntity data, AnalysisContext analysisContext) {
                pool100.add(data);
            }

            @Override
            public void doAfterAllAnalysed(AnalysisContext analysisContext) {
            }
        }).sheet("新手池").doRead();
        EasyExcel.read(filename, ItemEntity.class, new AnalysisEventListener<ItemEntity>() {
            @Override
            public void invoke(ItemEntity data, AnalysisContext analysisContext) {
                pool200.add(data);
            }

            @Override
            public void doAfterAllAnalysed(AnalysisContext analysisContext) {
            }
        }).sheet("常驻池").doRead();
        EasyExcel.read(filename, ItemEntity.class, new AnalysisEventListener<ItemEntity>() {
            @Override
            public void invoke(ItemEntity data, AnalysisContext analysisContext) {
                pool301.add(data);
            }

            @Override
            public void doAfterAllAnalysed(AnalysisContext analysisContext) {
            }
        }).sheet("角色池").doRead();
        EasyExcel.read(filename, ItemEntity.class, new AnalysisEventListener<ItemEntity>() {
            @Override
            public void invoke(ItemEntity data, AnalysisContext analysisContext) {
                pool302.add(data);
            }

            @Override
            public void doAfterAllAnalysed(AnalysisContext analysisContext) {
            }
        }).sheet("武器池").doRead();
        map.put("100", pool100);
        map.put("200", pool200);
        map.put("301", pool301);
        map.put("302", pool302);
        return map;
    }

}
