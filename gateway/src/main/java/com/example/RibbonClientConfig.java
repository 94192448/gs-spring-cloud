package com.example;

import org.springframework.cloud.netflix.ribbon.RibbonClients;
import org.springframework.context.annotation.Configuration;

/**
 * @author yangzq80@gmail.com
 * @date 2020-06-09
 */
@Configuration
@RibbonClients(defaultConfiguration = VersionRuleConfig.class)
public class RibbonClientConfig {
}
