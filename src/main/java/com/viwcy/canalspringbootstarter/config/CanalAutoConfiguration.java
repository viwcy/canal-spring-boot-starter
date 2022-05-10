package com.viwcy.canalspringbootstarter.config;

import com.alibaba.otter.canal.client.CanalConnector;
import com.alibaba.otter.canal.client.CanalConnectors;
import com.viwcy.canalspringbootstarter.event.CanalDataSync;
import com.viwcy.canalspringbootstarter.event.EventHandlerFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
public class CanalAutoConfiguration {

    private final Logger logger = LoggerFactory.getLogger(CanalAutoConfiguration.class);

    @Bean
    public CanalConfigProperties properties() {
        return new CanalConfigProperties();
    }

    @Bean
    public EventHandlerFactory eventHandlerFactory() {
        return new EventHandlerFactory();
    }

    @Bean
    public CanalDataSync canalDataSync(CanalConnector connector, CanalConfigProperties properties,
                                       EventHandlerFactory factory) {
        return new CanalDataSync(connector, properties, factory);
    }

    @Bean
    public CanalConnector initConnector(CanalConfigProperties properties) {
        CanalConnector connector = CanalConnectors.newSingleConnector(new InetSocketAddress(properties.getHost(),
                properties.getPort()), properties.getDestination(), "", "");
        connector.connect();
        connector.subscribe(properties.getFilter());
        connector.rollback();
        logger.info("canal connector init success");
        return connector;
    }
}
