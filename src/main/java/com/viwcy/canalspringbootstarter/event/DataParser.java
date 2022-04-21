package com.viwcy.canalspringbootstarter.event;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.otter.canal.protocol.CanalEntry;
import java.util.List;

/**
 * rowData解析成json
 */
public class DataParser {

    /**
     * rowData解析成json
     */
    public JSONObject parse(CanalEntry.RowData rowData, boolean after) {
        List<CanalEntry.Column> columnsList = after ? rowData.getAfterColumnsList() : rowData.getBeforeColumnsList();
        JSONObject dataJson = new JSONObject(columnsList.size());
        for (CanalEntry.Column column : columnsList) {
            dataJson.put(column.getName(), column.getValue());
        }
        return dataJson;
    }

    /**
     * 解析对应实体
     */
    public <T> T parse(Class<T> clazz, CanalEntry.RowData rowData, boolean after) {
        JSONObject parse = parse(rowData, after);
        return JSON.parseObject(parse.toString(), clazz);
    }
}
