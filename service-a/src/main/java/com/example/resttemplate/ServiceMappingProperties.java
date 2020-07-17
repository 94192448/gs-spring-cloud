package com.example.resttemplate;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * @author yangzq80@gmail.com
 * @date 2020-07-06
 */
//@Component
@Configuration
@ConfigurationProperties(prefix = "zuul")
public class ServiceMappingProperties {
    /**
     * Map of route names to properties.
     */
    private Map<String, ZuulRoute> routes = new LinkedHashMap<>();

    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Map<String, ZuulRoute> getRoutes() {
        return routes;
    }

    public void setRoutes(Map<String, ZuulRoute> routes) {
        this.routes = routes;
    }

    public static class ZuulRoute {

        /**
         * The path (pattern) for the route, e.g. /foo/**.
         */
        private String path;


        private boolean stripPrefix = true;

        /**
         * The service ID (if any) to map to this route. You can specify a physical URL or
         * a service, but not both.
         */
        private String serviceId;


        public String getPath() {
            return path;
        }

        public void setPath(String path) {
            this.path = path;
        }

        public boolean isStripPrefix() {
            return stripPrefix;
        }

        public void setStripPrefix(boolean stripPrefix) {
            this.stripPrefix = stripPrefix;
        }

        public String getServiceId() {
            return serviceId;
        }

        public void setServiceId(String serviceId) {
            this.serviceId = serviceId;
        }
    }

    Map<String,String> mappings = new HashMap<>();

    public String getMappingService(String originaService){
        originaService = originaService.replaceAll("/","");

        if (mappings.size()==0){

            for(ZuulRoute zuulRoute:routes.values()){

                String value = zuulRoute.path;

                mappings.put(value.replaceAll("/","").replaceAll("\\*",""),zuulRoute.serviceId);

            }
        }
        return mappings.get(originaService);
    }
}
