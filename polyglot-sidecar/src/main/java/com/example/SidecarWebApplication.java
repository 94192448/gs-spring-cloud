package com.example;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.sidecar.EnableSidecar;

/**
 * @author : yangzq80@gmail.com
 * @date: 2020-02-19
 */
@SpringBootApplication
@EnableSidecar
public class SidecarWebApplication {
    public static void main(String[] args) {
        SpringApplication.run(SidecarWebApplication.class,args);
    }
}
