package com.viwcy.canalspringbootstarter.event;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;

/**
 * TODO  Copyright (c) yun lu 2021 Fau (viwcy4611@gmail.com), ltd
 */
@RequiredArgsConstructor(onConstructor_ = {@Autowired})
public class CanalRunner implements ApplicationRunner {

    private final EventHandlerFactory eventHandlerFactory;

    @Override
    public void run(ApplicationArguments args) throws Exception {

        //注册事件执行器
        eventHandlerFactory.register();
    }
}
