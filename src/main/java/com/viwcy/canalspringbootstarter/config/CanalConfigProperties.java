package com.viwcy.canalspringbootstarter.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * canal配置信息
 */
@Data
@ConfigurationProperties(prefix = "canal")
public class CanalConfigProperties {

    /**
     * canal服务主机
     */
    private String host = "127.0.0.1";

    /**
     * canal内部端口
     */
    private Integer port = 11111;

    /**
     * 节点
     */
    private String destination = "example";

    /**
     * 批处理数量
     */
    private Integer batchSize = 1000;

    /**
     * 规则
     */
    private String filter = ".*\\\\..*";

    /**
     * 未拉取到数据变更，休眠的时间，默认2000，单位ms
     */
    private Long sleepTime = 2000L;

}
