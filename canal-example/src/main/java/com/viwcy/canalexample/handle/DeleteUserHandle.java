package com.viwcy.canalexample.handle;

import com.alibaba.fastjson.JSON;
import com.alibaba.otter.canal.protocol.CanalEntry;
import com.viwcy.canalexample.entity.User;
import com.viwcy.canalspringbootstarter.constant.ColumnEnum;
import com.viwcy.canalspringbootstarter.event.AbstractEventHandler;
import com.viwcy.canalspringbootstarter.event.TableEvent;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * TODO  Copyright (c) yun lu 2021 Fau (viwcy4611@gmail.com), ltd
 */
@Service
@TableEvent(database = "canal_example", table = "user", eventType = CanalEntry.EventType.DELETE)
public class DeleteUserHandle extends AbstractEventHandler<User> {

    @Override
    protected void doHandle(List<User> list) {
        System.out.println(JSON.toJSONString(list));
    }

    @Override
    protected ColumnEnum column() {
        return ColumnEnum.BEFORE_COLUMN;
    }

    @Override
    protected String resourceType() {
        return null;
    }

    @Override
    protected CanalEntry.EventType eventType() {
        return null;
    }
}
