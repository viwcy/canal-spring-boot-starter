package com.viwcy.canalspringbootstarter.event;

import org.springframework.stereotype.Component;

/**
 * TODO  Copyright (c) yun lu 2021 Fau (viwcy4611@gmail.com), ltd
 * <p>
 * 业务自行实现，获取更改前或后的列数据
 */
@Component
public interface DataParserInterface {

    /**
     * true为修改后的列, 如修改,新增
     * false为修改前的列, 如删除
     */
    Boolean after();
}
