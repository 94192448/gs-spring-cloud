package com.example.resttemplate;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.web.client.RequestCallback;
import org.springframework.web.client.ResponseExtractor;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.net.URI;

/**
 * @author yangzq80@gmail.com
 * @date 2020-07-06
 *
 * 自定义RestTemplate配置，支持根据配置文件动态映射serviceId
 */
@Slf4j
@Configuration
public class CustomerRestTemplateConfiguration {

    @LoadBalanced
    @Bean
    public RestTemplate loadbalancedRestTemplate() {

        return new CustomerRestTemplate();
    }

    @Autowired
    ServiceMappingProperties serviceMappingProperties;


    class CustomerRestTemplate extends RestTemplate {


        @Override
        protected <T> T doExecute(URI url, HttpMethod method, RequestCallback requestCallback, ResponseExtractor<T> responseExtractor) throws RestClientException {

            String[] urls = url.getPath().split("/");

            //默认规则自定义映射两级服务识别
            if (urls.length>=2){
                String backendHost = serviceMappingProperties.getMappingService(url.getHost() + urls[1]);

                if (backendHost != null) {
                    url = URI.create(url.toString().replace(url.getHost() + "/" + urls[1], backendHost));
                }
            }

            return super.doExecute(url, method, requestCallback, responseExtractor);
        }
    }
}
