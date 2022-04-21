package com.viwcy.canalspringbootstarter.event;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

/**
 * TODO  Copyright (c) yun lu 2021 Fau (viwcy4611@gmail.com), ltd
 */
@Component
public class CanalRunner implements ApplicationRunner {

    @Autowired(required = false)
    private CanalDataSync canalDataSync;
    @Autowired(required = false)
    private EventHandlerFactory eventHandlerFactory;

    @Override
    public void run(ApplicationArguments args) throws Exception {

        if (canalDataSync != null && eventHandlerFactory != null) {
            //注册所有的事件执行器
            eventHandlerFactory.register();
            //监听
            canalDataSync.execute();
        }
    }
}
