package org.redtea.EasyExcel.read;

import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.read.listener.ReadListener;
import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;
import org.redtea.EasyExcel.core.DemoData;

import java.util.List;

@Slf4j
public class DemoDataListener implements ReadListener<DemoData> {
    private static final int BATCH_COUNT = 100;
    /**
     * 缓存的数据
     */
    //private List<DemoData> cachedDataList = ListUtils.newArrayListWithExpectedSize(BATCH_COUNT);
    private final List<DemoData> cachedDataList;

    public DemoDataListener(List<DemoData> cachedDataList) {
        this.cachedDataList = cachedDataList;
    }

    /**
     * 每一条数据解析都会来调用
     */
    @Override
    public void invoke(DemoData data, AnalysisContext context) {
        log.info("解析到一条数据:{}", JSON.toJSONString(data));
        cachedDataList.add(data);
    }

    @Override
    public void doAfterAllAnalysed(AnalysisContext analysisContext) {

    }
}
