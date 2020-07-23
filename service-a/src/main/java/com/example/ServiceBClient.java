package com.example;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * @author yangzq80@gmail.com
 * @date 2020-07-23
 */
@FeignClient("sidecar-service-b")
public interface ServiceBClient {
    @RequestMapping(method = RequestMethod.GET, value = "/test")
    String test();
}
