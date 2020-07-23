package com.example;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author : yangzq80@gmail.com
 * @date: 2019-05-06
 */
@SpringBootApplication
@RestController
@EnableFeignClients
public class ServiceAApplication {
    public static void main(String[] args) {
        SpringApplication.run(ServiceAApplication.class,args);
    }

    @GetMapping("/test")
    public String test(){
        return "Service-a -> test";
    }

    @Autowired
    ServiceBClient serviceBClient;

    @GetMapping("/test/remote")
    public String testRemote(){
        return serviceBClient.test();
    }
}
