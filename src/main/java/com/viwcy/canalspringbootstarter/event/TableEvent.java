package com.viwcy.canalspringbootstarter.event;

import com.alibaba.otter.canal.protocol.CanalEntry;

import java.lang.annotation.*;

/**
 * 表事件
 */
@Documented
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface TableEvent {

    /**
     * 库名
     */
    String database();

    /**
     * 表名
     */
    String table();

    /**
     * 事件类型
     */
    CanalEntry.EventType eventType();

}
