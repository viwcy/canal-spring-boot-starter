package com.viwcy.canalspringbootstarter.config;

import com.alibaba.otter.canal.client.CanalConnector;
import com.alibaba.otter.canal.client.CanalConnectors;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.net.InetSocketAddress;

/**
 * canal 客户端连接配置
 */
@Configuration
@ConditionalOnClass(CanalConnectors.class)
@Slf4j
public class CanalConnectorConfiguration {

    private final CanalConfigProperties properties;

    public CanalConnectorConfiguration(CanalConfigProperties properties) {
        this.properties = properties;
    }

    @Bean
    public CanalConnector initConnector() {
        CanalConnector connector = CanalConnectors.newSingleConnector(new InetSocketAddress(properties.getHost(),
                properties.getPort()), properties.getDestination(), "", "");
        connector.connect();
        connector.subscribe(properties.getFilter());
        connector.rollback();
        log.info("CanalConnector init success");
        return connector;
    }
}
