package com.viwcy.canalspringbootstarter.config;

import com.viwcy.canalspringbootstarter.constant.CanalModel;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;

/**
 * canal配置信息
 */
@Data
@ConfigurationProperties(prefix = "canal")
public class CanalConfigProperties {

    private Single single = new Single();
    private Cluster cluster = new Cluster();
    /**
     * 监听实例，配置需要监听数据源，多个用逗号隔开
     * 一个canal server可以配置监听多个example（即多个数据源实例）
     */
    private String destination = "example";

    /**
     * 批处理数量
     */
    private Integer batchSize = 1000;

    /**
     * 规则，默认监听全部
     */
    private String filter = ".*\\\\..*";

    /**
     * 未拉取到数据变更，休眠的时间，单位ms
     */
    private Long sleepTime = 2000L;

    /**
     * canal监听模式（单机或集群）
     */
    private CanalModel model = CanalModel.SINGLE;

    /**
     * canal拉取数据，项目启动多久之后首次拉取，单位ms
     */
    public String initialDelay = "2000";

    /**
     * canal拉取数据，上次任务结束多久之后再去拉取，单位ms
     */
    private String fixedDelay = "2000";

    @Bean
    public String initialDelay () {
        return this.initialDelay;
    }

    @Bean
    public String fixedDelay () {
        return this.fixedDelay;
    }

    @Data
    public static class Single {
        /**
         * canal服务主机
         */
        private String host = "127.0.0.1";

        /**
         * canal内部端口
         */
        private Integer port = 11111;
    }

    /**
     * 尽量避免使用主从，因为会有master挂掉之后，batchId还没有同步至slave，会有重复消费问题，如果一定要用，做好数据幂等即可。
     */
    @Data
    public static class Cluster {
        /**
         * zk集群地址，多个使用逗号隔开
         */
        private String zkServers = "127.0.0.1:2181";

        /**
         * master canal server宕机之后，连接重试次数(适当偏大一点，因为master和slave切换会有耗时，否则一旦次数达到，则会抛出异常，程序终止)
         */
        private Integer retryTimes = 5;

        /**
         * master canal server宕机之后，连接重试的时间间隔，单位ms
         */
        private Integer retryInterval = 10000;
    }
}
