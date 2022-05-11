package com.viwcy.canalspringbootstarter.event;

import com.alibaba.otter.canal.client.CanalConnector;
import com.alibaba.otter.canal.protocol.CanalEntry;
import com.alibaba.otter.canal.protocol.Message;
import com.google.protobuf.InvalidProtocolBufferException;
import com.viwcy.canalspringbootstarter.config.CanalConfigProperties;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.Objects;

/**
 * TODO  Copyright (c) yun lu 2021 Fau (viwcy4611@gmail.com), ltd
 */
@Slf4j
public class CanalDataSync {

    private final CanalConnector canalConnector;
    private final CanalConfigProperties canalConfigProperties;
    private final EventHandlerFactory factory;

    public CanalDataSync(CanalConnector canalConnector, CanalConfigProperties canalConfigProperties, EventHandlerFactory factory) {
        this.canalConnector = canalConnector;
        this.canalConfigProperties = canalConfigProperties;
        this.factory = factory;
    }

    public void execute() {

        while (true) {
            Message message = canalConnector.getWithoutAck(canalConfigProperties.getBatchSize());
            long batchId = message.getId();
            List<CanalEntry.Entry> entries = message.getEntries();
            if (batchId == -1 || CollectionUtils.isEmpty(entries)) {
                try {
                    Thread.sleep(canalConfigProperties.getSleepTime());
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            } else {
                try {
                    handle(entries);
                    canalConnector.ack(batchId);
                } catch (Exception e) {
                    canalConnector.rollback(batchId);
                }
            }
        }
    }

    private void handle(List<CanalEntry.Entry> entries) throws Exception {
        if (CollectionUtils.isEmpty(entries)) {
            return;
        }
        for (CanalEntry.Entry entry : entries) {
            if (entry.getEntryType() == CanalEntry.EntryType.TRANSACTIONBEGIN || entry.getEntryType() == CanalEntry.EntryType.TRANSACTIONEND) {
                continue;
            }
            CanalEntry.RowChange rowChange = null;
            try {
                rowChange = CanalEntry.RowChange.parseFrom(entry.getStoreValue());
            } catch (InvalidProtocolBufferException e) {
                log.error("解析获取CanalEntry.RowChange异常，原因 = " + e);
            }

            if (Objects.isNull(rowChange)) {
                continue;
            }
            CanalEntry.EventType eventType = rowChange.getEventType();
            String schemaName = entry.getHeader().getSchemaName();
            String tableName = entry.getHeader().getTableName();

            List<CanalEntry.RowData> rowDataList = rowChange.getRowDatasList();
            if (CollectionUtils.isEmpty(rowDataList)) {
                continue;
            }
            IEventHandle handler = factory.getHandler(EventHandlerFactory.createUnionKey(schemaName, tableName, eventType));
            log.info("query event handle = " + handler.getClass().getSimpleName());
            handler.handle(rowDataList);
        }
    }
}
