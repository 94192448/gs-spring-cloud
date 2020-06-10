package com.example;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;

/**
 * @author : yangzq80@gmail.com
 * @date: 2019-05-06
 */
@RestController
@RequestMapping("/user")
public class UserResource {

    private final Logger log = LoggerFactory.getLogger(UserResource.class);

    @GetMapping
    public List<User> users(){

        log.info("request users...");
        return Arrays.asList(new User(1L,"Donald J. Trump"),new User(2L,"James Gosling"));
    }

    @Value("${eureka.instance.metadata-map.version}")
    String version;

    @GetMapping("/version")
    public String getVersion(){
        return version;
    }

    @PostMapping
    public User saveUser(@RequestBody User user){
        return user;
    }

    public class User {
        Long id;
        String name;

        public User(Long id, String name) {
            this.id = id;
            this.name = name;
        }

        public Long getId() {
            return id;
        }

        public void setId(Long id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }
    }
}
