package com.example;

import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;

/**
 * @author : yangzq80@gmail.com
 * @date: 2019-05-06
 */
@RestController
@RequestMapping("/user")
public class UserController {

    @RequestMapping(method = RequestMethod.GET)
    public List<User> users(){
        return Arrays.asList(new User(1L,"Trump"),new User(2L,"Donal2d"));
    }
}
