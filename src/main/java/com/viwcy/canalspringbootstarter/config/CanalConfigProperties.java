package com.viwcy.canalspringbootstarter.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * canal配置信息
 */
@Data
@ConfigurationProperties(prefix = "canal")
public class CanalConfigProperties {

    //canal主机
    private String host;

    //端口
    private Integer port;

    //节点
    private String destination;

    //批处理数量
    private Integer batchSize;

    //规则
    private String filter;

}
