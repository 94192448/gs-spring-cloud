package com.example;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

/**
 * @author yangzq80@gmail.com
 * @date 2020-07-04
 */
@RestController
public class RestTemplateService {
    @LoadBalanced
    @Bean
    public RestTemplate loadbalancedRestTemplate() {

        return new CustomerRestTemplate();
    }




    @Autowired
    RestTemplate restTemplate;

    public String getFirstProduct() {
        //return this.restTemplate.getForObject("http://service-b/test", String.class);
        return this.restTemplate.getForObject("http://system-b/domain1/test", String.class);
    }

    @GetMapping("/rewiret-resttemplate")
    public String test(){
        return getFirstProduct();
    }
}
