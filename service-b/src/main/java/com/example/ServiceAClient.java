package com.example;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.List;

/**
 * @author : yangzq80@gmail.com
 * @date: 2019-05-06
 */
@FeignClient("system-a/domain1")
public interface ServiceAClient {

    @RequestMapping(method = RequestMethod.GET, value = "/user")
    List<User> getUsers();

    @RequestMapping(method = RequestMethod.POST, value = "/user/{userId}", consumes = "application/json")
    User update(@PathVariable("userId") Long userId, User user);


}
