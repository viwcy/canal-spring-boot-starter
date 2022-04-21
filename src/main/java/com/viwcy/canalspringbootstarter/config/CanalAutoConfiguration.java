package com.viwcy.canalspringbootstarter.config;

import com.alibaba.otter.canal.client.CanalConnector;
import com.viwcy.canalspringbootstarter.event.CanalDataSync;
import com.viwcy.canalspringbootstarter.event.DataParser;
import com.viwcy.canalspringbootstarter.event.EventHandlerFactory;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

/**
 * canal 自动装配配置
 */
@Configuration
@EnableConfigurationProperties(CanalConfigProperties.class)
@Import({CanalConnectorConfiguration.class})
public class CanalAutoConfiguration {

    @Bean
    public DataParser rowDataCustomParser() {
        return new DataParser();
    }

    @Bean
    @ConditionalOnProperty(name = "canal.enabled", havingValue = "true", matchIfMissing = true)
    public EventHandlerFactory eventHandlerFactory() {
        return new EventHandlerFactory();
    }

    @Bean
    @ConditionalOnProperty(name = "canal.enabled", havingValue = "true", matchIfMissing = true)
    public CanalDataSync canalDataSync(CanalConnector connector, CanalConfigProperties properties,
                                       EventHandlerFactory factory) {
        return new CanalDataSync(connector, properties, factory);
    }

}
