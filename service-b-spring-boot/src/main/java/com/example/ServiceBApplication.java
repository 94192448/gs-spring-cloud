package com.example;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

/**
 * @author yangzq80@gmail.com
 * @date 2020-07-23
 */
@SpringBootApplication
@RestController
public class ServiceBApplication {
    public static void main(String[] args) {
        SpringApplication.run(ServiceBApplication.class,args);
    }


    RestTemplate restTemplate = new RestTemplate();
    @GetMapping("/test")
    public String test(){
        return "ServiceB spring-boot -> test";
    }

    @GetMapping("/test/remote")
    public String testRemote(){

        ResponseEntity<String> responseEntity = restTemplate.exchange("http://localhost:9002/service-a/test", HttpMethod.GET,null
                ,String.class);

        return responseEntity.getBody();
    }

    @GetMapping("/health.json")
    public String health(){
        return "{\"status\": \"UP\"}";
    }



}
