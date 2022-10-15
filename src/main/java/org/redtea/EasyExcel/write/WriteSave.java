package org.redtea.EasyExcel.write;

import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.ExcelWriter;
import com.alibaba.excel.util.ListUtils;
import com.alibaba.excel.write.metadata.WriteSheet;
import org.redtea.EasyExcel.core.ItemEntityData;
import org.redtea.EasyExcel.util.TestFileUtil;
import org.redtea.Genshin.ItemEntity;

import java.io.File;
import java.util.List;

public class WriteSave {
    //此方法用于保存read所需的数据
    public static void writeToSave(List<ItemEntity>... list) {
        String uid = list[1].get(0).getUid();
        String filename = TestFileUtil.getPath() + "//data//" + uid + ".xlsx";
        File dir = new File(TestFileUtil.getPath() + "/data");
        if (dir.exists()) {
            System.out.println();
        } else {
            dir.mkdirs();
        }
        try (ExcelWriter writer = EasyExcel.write(filename, ItemEntityData.class).build()) {
            WriteSheet writeSheet200 = EasyExcel.writerSheet("常驻池").build();
            WriteSheet writeSheet301 = EasyExcel.writerSheet("角色池").build();
            WriteSheet writeSheet302 = EasyExcel.writerSheet("武器池").build();
            List<WriteSheet> writeSheets = ListUtils.newArrayList(writeSheet200, writeSheet301, writeSheet302);
            int i = 0;
            //传入值为3个list，每个list保存入不同的sheet
            for (List<ItemEntity> arrayLists : list) {
                WriteSheet sheets = writeSheets.get(i);
                if (arrayLists.size() != 0) {
                    writer.write(arrayLists, sheets);
                } else {
                    continue; //list.size() = 0,写入时会报错，所以直接跳过
                }
                i++;
            }
        }
    }
}
