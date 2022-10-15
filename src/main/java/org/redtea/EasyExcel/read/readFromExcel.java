package org.redtea.EasyExcel.read;

import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;
import org.junit.Test;
import org.redtea.EasyExcel.util.TestFileUtil;
import org.redtea.Genshin.ItemEntity;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class readFromExcel {
/*
    public Map<Integer,List<DemoData>> readExcel(String filename){
        List<DemoData> pool1 = new ArrayList<>();
        List<DemoData> pool2 = new ArrayList<>();
        List<DemoData> pool3 = new ArrayList<>();
        Map<Integer,List<DemoData>> map = new LinkedHashMap<>();
        //分别读取三个表中的数据并存入三个list中
        EasyExcel.read(filename, DemoData.class, new AnalysisEventListener<DemoData>() {
            @Override
            public void invoke(DemoData data, AnalysisContext analysisContext) {
                pool1.add(data);
            }
            @Override
            public void doAfterAllAnalysed(AnalysisContext analysisContext) {
            }
        }).sheet("常驻池").doRead();
        EasyExcel.read(filename, DemoData.class, new AnalysisEventListener<DemoData>() {
            @Override
            public void invoke(DemoData data, AnalysisContext analysisContext) {
                pool2.add(data);
            }
            @Override
            public void doAfterAllAnalysed(AnalysisContext analysisContext) {
            }
        }).sheet("角色池").doRead();
        EasyExcel.read(filename, DemoData.class, new AnalysisEventListener<DemoData>() {
            @Override
            public void invoke(DemoData data, AnalysisContext analysisContext) {
                pool3.add(data);
            }
            @Override
            public void doAfterAllAnalysed(AnalysisContext analysisContext) {
            }
        }).sheet("武器池").doRead();
        map.put(0,pool1);
        map.put(1,pool2);
        map.put(2,pool3);
        return map;

    }
*/

    public static Map<String, List<ItemEntity>> readData(String filename) {
        List<ItemEntity> pool1 = new ArrayList<>();
        List<ItemEntity> pool2 = new ArrayList<>();
        List<ItemEntity> pool3 = new ArrayList<>();
        Map<String, List<ItemEntity>> map = new LinkedHashMap<>();
        //分别读取三个表中的数据并存入三个list中
        EasyExcel.read(filename, ItemEntity.class, new AnalysisEventListener<ItemEntity>() {
            @Override
            public void invoke(ItemEntity data, AnalysisContext analysisContext) {
                pool1.add(data);
            }

            @Override
            public void doAfterAllAnalysed(AnalysisContext analysisContext) {
            }
        }).sheet("常驻池").doRead();
        EasyExcel.read(filename, ItemEntity.class, new AnalysisEventListener<ItemEntity>() {
            @Override
            public void invoke(ItemEntity data, AnalysisContext analysisContext) {
                pool2.add(data);
            }

            @Override
            public void doAfterAllAnalysed(AnalysisContext analysisContext) {
            }
        }).sheet("角色池").doRead();
        EasyExcel.read(filename, ItemEntity.class, new AnalysisEventListener<ItemEntity>() {
            @Override
            public void invoke(ItemEntity data, AnalysisContext analysisContext) {
                pool3.add(data);
            }

            @Override
            public void doAfterAllAnalysed(AnalysisContext analysisContext) {
            }
        }).sheet("武器池").doRead();
        map.put("200", pool1);
        map.put("301", pool2);
        map.put("302", pool3);
        return map;
    }

    @Test
    public void Test() {
        String filename = TestFileUtil.getPath() + "//data//" + "513020140.xlsx";
        Map<String, List<ItemEntity>> map = readData(filename);
        List<ItemEntity> list = map.get("200");
    }

}
