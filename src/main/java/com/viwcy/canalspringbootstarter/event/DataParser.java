package com.viwcy.canalspringbootstarter.event;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.otter.canal.protocol.CanalEntry;
import com.viwcy.canalspringbootstarter.constant.ColumnEnum;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * rowData解析成json
 */
@Component
public class DataParser {

    /**
     * rowData解析成json
     */
    public JSONObject parse(CanalEntry.RowData rowData, ColumnEnum column) {
        List<CanalEntry.Column> columnsList = new ArrayList<>();
        if (column == ColumnEnum.AFTER_COLUMN) {
            columnsList = rowData.getAfterColumnsList();
        }
        if (column == ColumnEnum.BEFORE_COLUMN) {
            columnsList = rowData.getAfterColumnsList();
        }
        JSONObject dataJson = new JSONObject(columnsList.size());
        for (CanalEntry.Column var : columnsList) {
            dataJson.put(var.getName(), var.getValue());
        }
        return dataJson;
    }

    /**
     * 解析对应实体
     */
    public <T> T parse(Class<T> clazz, CanalEntry.RowData rowData, ColumnEnum column) {
        JSONObject parse = parse(rowData, column);
        return JSON.parseObject(parse.toString(), clazz);
    }
}
