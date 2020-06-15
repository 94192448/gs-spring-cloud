package com.example.definition;

import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.cloud.gateway.discovery.DiscoveryLocatorProperties;
import org.springframework.context.annotation.Bean;

/**
 * //TODO
 * @author yangzq80@gmail.com
 * @date 2020-06-11
 */
//@Configuration
public class TestConfig {
    @Bean(name = "discoveryClientRouteDefinitionLocator")
    @ConditionalOnBean(DiscoveryClient.class)
    @ConditionalOnProperty(name = "spring.cloud.gateway.discovery.locator.enabled")
    public EnhanceDiscoveryClientRouteDefinitionLocator discoveryClientRouteDefinitionLocator(
            DiscoveryClient discoveryClient, DiscoveryLocatorProperties properties) {
        return new EnhanceDiscoveryClientRouteDefinitionLocator(discoveryClient, properties);
    }
}
