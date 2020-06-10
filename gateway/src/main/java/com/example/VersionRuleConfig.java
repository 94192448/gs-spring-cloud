package com.example;

import com.netflix.client.config.IClientConfig;
import com.netflix.loadbalancer.IRule;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;

/**
 * @author yangzq80@gmail.com
 * @date 2020-06-09
 */

public class VersionRuleConfig {

    @Autowired
    IClientConfig config;

    @Bean
    public IRule ribbonRule() {
        VersionZoneAvoidanceRule rule = new VersionZoneAvoidanceRule();
        rule.initWithNiwsConfig(config);
        return rule;
    }
}
