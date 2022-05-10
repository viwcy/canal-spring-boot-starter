package com.viwcy.canalspringbootstarter.event;

import com.alibaba.otter.canal.protocol.CanalEntry;

import java.util.List;

/**
 * TODO  Copyright (c) yun lu 2021 Fau (viwcy4611@gmail.com), ltd
 */
public interface IEventHandle<T> {

    void handle(List<CanalEntry.RowData> rowDataList) throws Exception;
}
