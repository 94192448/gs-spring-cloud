package com.example;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;

/**
 * @author : yangzq80@gmail.com
 * @date: 2019-05-06
 */
@RestController
public class UserController {

    @RequestMapping("/user")
    public List<User> users(){
        return Arrays.asList(new User(1L,"Trump"),new User(2L,"Donal2d"));
    }

    @RequestMapping("/")
    public String hello(){
        return "hello service-a";
    }

    @PostMapping
    public User saveUser(@RequestBody User user){
        return user;
    }

    @Getter
    @Setter
    @AllArgsConstructor
    public class User {
        Long id;
        String name;
    }
}
