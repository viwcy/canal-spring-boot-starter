package com.viwcy.canalspringbootstarter.event;

import com.viwcy.canalspringbootstarter.config.CanalAutoConfiguration;
import org.springframework.context.annotation.Import;

import java.lang.annotation.*;

/**
 * TODO  Copyright (c) yun lu 2021 Fau (viwcy4611@gmail.com), ltd
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@Documented
@Import({CanalAutoConfiguration.class, CanalRunner.class})
public @interface EnableCanal {
}
