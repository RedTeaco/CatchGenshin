package org.redtea.EasyExcel.write;

import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.ExcelWriter;
import com.alibaba.excel.util.ListUtils;
import com.alibaba.excel.write.metadata.WriteSheet;
import com.alibaba.excel.write.metadata.style.WriteCellStyle;
import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.redtea.EasyExcel.core.DemoData;
import org.redtea.EasyExcel.util.TestFileUtil;
import org.redtea.Genshin.ItemEntity;

import java.util.ArrayList;
import java.util.List;

public class WriteTest {
    //此方法用于保存用户所需的数据
    public static void writeToExcel(List<ItemEntity>... list) {
        //文件保存名为uid
        String uid = list[1].get(0).getUid();
        String fileName = TestFileUtil.getPath() + "/" + uid + ".xlsx";
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
            //如果是同一个sheet只要创建一次
            WriteSheet writeSheet100 = EasyExcel.writerSheet("新手池").build();
            WriteSheet writeSheet200 = EasyExcel.writerSheet("常驻池").build();
            WriteSheet writeSheet301 = EasyExcel.writerSheet("角色池").build();
            WriteSheet writeSheet302 = EasyExcel.writerSheet("武器池").build();
            List<WriteSheet> writeSheets = ListUtils.newArrayList(writeSheet100,writeSheet200, writeSheet301, writeSheet302);
            //调用写入
            int i = 0;
            //传入值为3个list，每个list保存入不同的sheet
            for (List<ItemEntity> arrayLists : list) {
                WriteSheet sheets = writeSheets.get(i);
                if (arrayLists.size() != 0) {
                    for (ItemEntity itemEntities : arrayLists) {
                        List<DemoData> data = data(itemEntities);
                        excelWriter.write(data, sheets);
                    }
                }
                i++;
            }
        }
    }

    //从ItemEntity类中提取出DemoData所需要的数据，例如times,names,types...
    public static List<DemoData> data(ItemEntity data_1) {
        List<DemoData> list = ListUtils.newArrayList();
        //List<ItemEntity> data_1 = new ArrayList<>(Arrays.asList(new ItemEntity("513020140", "", "武器", "1", "黎明神剑", "200", "2022-10-01 23:07:28", "1664636760000074940", "zh-cn", "3"), new ItemEntity("513020140", "", "武器", "1", "黑缨枪", "200", "2022-10-01 23:07:30", "1664636760000074940", "zh-cn", "3")));
        String pools = "";
        DemoData data = new DemoData();
        data.setUid(data_1.getUid());
        data.setTime(data_1.getTime());
        switch (data_1.getGacha_type()) {
            case "100" -> pools = "新手池";
            case "200" -> pools = "常驻池";
            case "301", "400" -> pools = "角色UP池";
            case "302" -> pools = "武器池";
        }
        data.setPools(pools);
        data.setTypes(data_1.getItem_type());
        data.setItems(data_1.getName());
        data.setRank(Integer.parseInt((data_1.getRank_type())));
        list.add(data);
        return list;
    }
}
