package com.example;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * @author yangzq80@gmail.com
 * @date 2020-07-06
 */
@Component
@ConfigurationProperties(prefix = "customers")
public class RestRoutesProperties {
    Map<String,String> routes;

    public Map<String, String> getRoutes() {
        return routes;
    }

    public void setRoutes(Map<String, String> routes) {
        this.routes = routes;
    }
}
