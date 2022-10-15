package org.redtea.Genshin;

import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.ExcelWriter;
import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;
import com.alibaba.excel.write.metadata.WriteSheet;
import com.alibaba.excel.write.metadata.style.WriteCellStyle;
import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.redtea.EasyExcel.core.DemoData;
import org.redtea.EasyExcel.write.HorizontalCellStyleStrategy;

import java.io.File;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import static org.redtea.EasyExcel.write.WriteTest.data;

public class addLogs {
    //从Excel中读取获得手动添加的数据
    //从GUI表单中获取itemEntityList
    //比对，写入excel；
    private ItemEntity itemEntity;
    private String dataFilename;
    private String filename;

    public addLogs(String filename, String dataFilename, ItemEntity itemEntity) {
        this.itemEntity = itemEntity;
        this.dataFilename = dataFilename;
        this.filename = filename;
    }

    public addLogs(ItemEntity itemEntity) {
        this.itemEntity = itemEntity;
        String file = new File("").getAbsolutePath();
        String uid = itemEntity.getUid();
        this.dataFilename = file + "\\data\\" + uid + "log.xlsx";
        this.filename = file + "\\" + uid + "log.xlsx";
    }

    public addLogs(String uid) {
        String file = new File("").getAbsolutePath();
        this.dataFilename = file + "\\data\\" + uid + "log.xlsx";
        this.filename = file + "\\" + uid + "log.xlsx";
    }

    public String getDataFilename() {
        return dataFilename;
    }

    public void setDataFilename(String dataFilename) {
        this.dataFilename = dataFilename;
    }

    public String getFilename() {
        return filename;
    }

    public void setFilename(String filename) {
        this.filename = filename;
    }

    public ItemEntity getItemEntity() {
        return itemEntity;
    }

    public void setItemEntity(ItemEntity itemEntity) {
        this.itemEntity = itemEntity;
    }

    //不带样式，原始数据
    public void writeSave(List<ItemEntity> itemEntityList) {
        String uid = itemEntityList.get(0).getUid();
        File dir = new File(dataFilename.split(uid)[0]);
        if (dir.exists()) {
        } else {
            dir.mkdirs();
        }
        try (ExcelWriter writer = EasyExcel.write(dataFilename, ItemEntity.class).build()) {
            WriteSheet writeSheetAddLog = EasyExcel.writerSheet("手动添加").build();
            if (itemEntityList.size() != 0) {
                writer.write(itemEntityList, writeSheetAddLog);
            } else {
                System.out.println("尚未添加记录");
            }

        }
    }

    //带样式
    public void writeToExcel(List<ItemEntity> itemEntityList) {
        String fileName = filename;
        //样式构造
        WriteCellStyle headWriteCellStyle = new WriteCellStyle();
        WriteCellStyle contentWriteCellStyle_gold = new WriteCellStyle();
        WriteCellStyle contentWriteCellStyle_violet = new WriteCellStyle();
        List<WriteCellStyle> contentWriteCellStyleList = new ArrayList<>();
        //金色样式
        contentWriteCellStyle_gold.setFillPatternType(FillPatternType.SOLID_FOREGROUND);
        contentWriteCellStyle_gold.setFillForegroundColor(IndexedColors.LIGHT_YELLOW.getIndex());
        //紫色样式
        contentWriteCellStyle_violet.setFillPatternType(FillPatternType.SOLID_FOREGROUND);
        contentWriteCellStyle_violet.setFillForegroundColor(IndexedColors.LAVENDER.getIndex());
        //加入样式列表中
        contentWriteCellStyleList.add(contentWriteCellStyle_violet);
        contentWriteCellStyleList.add(contentWriteCellStyle_gold);

        HorizontalCellStyleStrategy horizontalCellStyleStrategy = new HorizontalCellStyleStrategy(headWriteCellStyle, contentWriteCellStyleList);
        try (ExcelWriter excelWriter = EasyExcel.write(fileName, DemoData.class).registerWriteHandler(horizontalCellStyleStrategy).build()) {
            WriteSheet writeSheetAddLog = EasyExcel.writerSheet("手动添加").build();
            if (itemEntityList.size() != 0) {
                for (ItemEntity itemEntity : itemEntityList) {
                    List<DemoData> data = data(itemEntity);
                    excelWriter.write(data, writeSheetAddLog);
                }
            } else {
                System.out.println("???");
            }
        }
    }

    public Map<String, List<ItemEntity>> readExcel(String filename) {
        List<ItemEntity> poolAddLog = new ArrayList<>();
        Map<String, List<ItemEntity>> map = new LinkedHashMap<>();
        try {
            EasyExcel.read(filename, ItemEntity.class, new AnalysisEventListener<ItemEntity>() {
                @Override
                public void invoke(ItemEntity data, AnalysisContext analysisContext) {
                    poolAddLog.add(data);
                }

                @Override
                public void doAfterAllAnalysed(AnalysisContext analysisContext) {

                }
            }).sheet("手动添加").doRead();
            map.put("AddLog", poolAddLog);
            return map;
        } catch (Exception e) {
            System.out.println("Excel-Log文件不存在");
            return null;
        }
    }

    public void mainMethod() {
        List<ItemEntity> list_new = new ArrayList<>();
        list_new.add(itemEntity);
        List<ItemEntity> list;
        try {
            List<ItemEntity> list_old = readExcel(dataFilename).get("AddLog");
            miHoYoSoup miHoYoSoup = new miHoYoSoup();
            list = miHoYoSoup.get_collects(list_old, list_new);
        } catch (Exception e) {
            System.out.println("手动添加列表目前为空");
            list = list_new;
        }
        writeSave(list);
        writeToExcel(list);
    }

    public List<ItemEntity> getLogData() {
        List<ItemEntity> itemEntities = readExcel(dataFilename).get("AddLog");
        List<ItemEntity> resultList = new ArrayList<>();
        int i = 0;
        for (ItemEntity item : itemEntities) {
            if (item.getRank_type().equals("5")) {
                String pool = switch (item.getGacha_type()) {
                    case "100" -> "新手池";
                    case "200" -> "常驻池";
                    case "301", "400" -> "角色池";
                    case "302" -> "武器池";
                    default -> "";
                };
                item.setGacha_type(pool);
                resultList.add(item);
            } else {
                continue;
            }
            i++;
        }
        return resultList;
    }
}

