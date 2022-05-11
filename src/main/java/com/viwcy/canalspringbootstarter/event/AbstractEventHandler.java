package com.viwcy.canalspringbootstarter.event;

import com.alibaba.otter.canal.protocol.CanalEntry;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.Nullable;
import org.springframework.util.CollectionUtils;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * 事件处理模板
 */
public abstract class AbstractEventHandler<T> implements IEventHandle {

    static final Logger log = LoggerFactory.getLogger(AbstractEventHandler.class);

    @Autowired
    private DataParser dataParser;

    //流程框架
    @Override
    public void handle(List<CanalEntry.RowData> rowDataList) throws Exception {

        log.info("event handle start");
        try {
            check(rowDataList);
            List<T> list = analysis(rowDataList);
            if (CollectionUtils.isEmpty(list)) {
                return;
            }
            this.doHandle(list);
        } catch (Exception e) {
            log.error("canal handle has error, cause = " + e);
            throw new Exception("canal handle has error , e = " + e);
        } finally {
            log.info("event handle end");
        }
    }

    private void check(List<CanalEntry.RowData> rowDataList) throws Exception {

        if (CollectionUtils.isEmpty(rowDataList)) {
            throw new Exception("rowDataList is empty");
        }
    }

    protected abstract void doHandle(List<T> list) throws Exception;

    protected abstract Boolean handleAfter();

    private final Class<T> handleClass() {
        Class clazz = this.getClass();
        ParameterizedType parameterizedType = (ParameterizedType) clazz.getGenericSuperclass();
        Type[] types = parameterizedType.getActualTypeArguments();
        return (Class<T>) types[0];
    }

    private final List<T> analysis(@Nullable final List<CanalEntry.RowData> rowDataList) {
        ArrayList<T> list = new ArrayList<>(rowDataList.size());
        Class<T> clazz = this.handleClass();
        Iterator<CanalEntry.RowData> iterator = rowDataList.iterator();

        while (iterator.hasNext()) {
            CanalEntry.RowData rowData = iterator.next();
            T parse = this.dataParser.parse(clazz, rowData, this.handleAfter());
            list.add(parse);
        }
        return list;
    }
}
