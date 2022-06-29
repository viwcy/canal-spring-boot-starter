package com.viwcy.canalspringbootstarter.config;

import com.alibaba.otter.canal.client.CanalConnector;
import com.alibaba.otter.canal.client.CanalConnectors;
import com.alibaba.otter.canal.client.impl.ClusterCanalConnector;
import com.alibaba.otter.canal.client.impl.ClusterNodeAccessStrategy;
import com.alibaba.otter.canal.common.zookeeper.ZkClientx;
import com.viwcy.canalspringbootstarter.constant.CanalModel;
import com.viwcy.canalspringbootstarter.event.CanalDataListener;
import com.viwcy.canalspringbootstarter.event.EventHandlerFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;

import java.net.InetSocketAddress;

/**
 * 可以封装自动装配加载，也可以自定义使用EnableXXX注解选择导入
 * <pre>
 *     自动装配加入下注注解即可
 *     @Configuration
 *     @EnableConfigurationProperties(CanalConfigProperties.class)
 *     @ConditionalOnClass(value = {CanalConnectors.class, EventHandlerFactory.class, CanalDataSync.class, CanalConnector.class})
 *     @ConditionalOnProperty(name = "canal.enabled", havingValue = "true", matchIfMissing = true)
 * </pre>
 */
@EnableConfigurationProperties(CanalConfigProperties.class)
public class CanalAutoConfiguration {

    private final Logger logger = LoggerFactory.getLogger(CanalAutoConfiguration.class);

    @Bean
    public EventHandlerFactory eventHandlerFactory() {
        return new EventHandlerFactory();
    }

    @Bean
    public CanalDataListener canalDataSync(CanalConnector connector, CanalConfigProperties properties,
                                           EventHandlerFactory factory) {
        return new CanalDataListener(connector, properties, factory);
    }

    @Bean
    public CanalConnector initConnector(CanalConfigProperties properties) throws Exception {

        //可以自定义增加部分配置，使用构造器创建对象
        CanalConnector connector;
        if (properties.getModel() == CanalModel.SINGLE) {
            CanalConfigProperties.Single single = properties.getSingle();
            connector = CanalConnectors.newSingleConnector(new InetSocketAddress(single.getHost(), single.getPort()),
                    properties.getDestination(), "", ""
            );
        } else if (properties.getModel() == CanalModel.CLUSTER) {

            CanalConfigProperties.Cluster cluster = properties.getCluster();
            ClusterCanalConnector canalConnector = new ClusterCanalConnector("", "",
                    properties.getDestination(),
                    new ClusterNodeAccessStrategy(properties.getDestination(), ZkClientx.getZkClient(cluster.getZkServers())));
            canalConnector.setSoTimeout(60 * 1000);
            canalConnector.setIdleTimeout(60 * 60 * 1000);
            canalConnector.setRetryTimes(cluster.getRetryTimes());
            canalConnector.setRetryInterval(cluster.getRetryInterval());
            connector = canalConnector;

        } else {
            throw new Exception("model not supported, connection failed");
        }
        connector.connect();
        connector.subscribe(properties.getFilter());
        connector.rollback();
        logger.info("canal connector init success");
        return connector;
    }
}
