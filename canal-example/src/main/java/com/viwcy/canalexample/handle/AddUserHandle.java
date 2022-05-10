package com.viwcy.canalexample.handle;

import com.alibaba.fastjson.JSON;
import com.alibaba.otter.canal.protocol.CanalEntry;
import com.viwcy.canalexample.entity.User;
import com.viwcy.canalspringbootstarter.event.AbstractEventHandler;
import com.viwcy.canalspringbootstarter.event.TableEvent;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * TODO  Copyright (c) yun lu 2021 Fau (viwcy4611@gmail.com), ltd
 */
@Service
@TableEvent(database = "canal_example", table = "user", eventType = CanalEntry.EventType.INSERT)
public class AddUserHandle extends AbstractEventHandler<User> {

    @Override
    protected void doHandle(List<User> list) throws Exception {
        System.out.println(JSON.toJSONString(list));
    }

    @Override
    protected Boolean handleAfter() {
        return Boolean.TRUE;
    }
}
