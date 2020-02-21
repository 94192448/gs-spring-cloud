package com.example;

import com.netflix.loadbalancer.DynamicServerListLoadBalancer;
import com.netflix.loadbalancer.ZoneAvoidanceRule;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author : yangzq80@gmail.com
 * @date: 2019-05-06
 */
@RestController
@Slf4j
public class TestController {
    @Resource
    PolyglotGoWebSidecarClient polyglotGoWebSidecarClient;

    @GetMapping("/user")
    public List<User> getUsers(){
        ZoneAvoidanceRule zoneAvoidanceRule;
        DynamicServerListLoadBalancer dynamicServerListLoadBalancer;
        log.info("get user--->");
        return polyglotGoWebSidecarClient.getUsers();
    }

    @GetMapping("/base")
    public String helloSleuth(String a) {
        if (a.equalsIgnoreCase("aa")){
            throw new RuntimeException("base error");
        }
        log.info("Hello Sleuth");
        return "success";
    }
}