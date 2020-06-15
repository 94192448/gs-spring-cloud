package com.example.ribbon;

import org.springframework.cloud.netflix.ribbon.RibbonClients;
import org.springframework.context.annotation.Configuration;

/**
 * @author yangzq80@gmail.com
 * @date 2020-06-09
 */
@Configuration
@RibbonClients(defaultConfiguration = WeightZoneAvoidanceRule.class)
public class RibbonClientConfig {
}
