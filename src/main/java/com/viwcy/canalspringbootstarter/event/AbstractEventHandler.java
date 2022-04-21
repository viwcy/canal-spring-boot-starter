package com.viwcy.canalspringbootstarter.event;

import com.alibaba.otter.canal.protocol.CanalEntry;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.CollectionUtils;

import java.util.List;

/**
 * 事件处理模板
 */
public abstract class AbstractEventHandler implements IEventHandle {

    static final Logger log = LoggerFactory.getLogger(AbstractEventHandler.class);

    //流程框架
    @Override
    public void handle(List<CanalEntry.RowData> rowDataList) throws Exception {
        log.info("event handle start");
        check(rowDataList);
        doHandle(rowDataList);
        log.info("event handle end");
    }

    //前置校验
    private void check(List<CanalEntry.RowData> rowDataList) throws Exception {
        if (CollectionUtils.isEmpty(rowDataList)) {
            throw new Exception("rowDataList is empty");
        }
    }

    //钩子，业务自行实现
    public abstract void doHandle(List<CanalEntry.RowData> rowDataList) throws Exception;

}
