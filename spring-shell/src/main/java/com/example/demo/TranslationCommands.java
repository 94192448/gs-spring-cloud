package com.example.demo;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;

/**
 * @author yangzq80@gmail.com
 * @date 2020-04-10 14:28
 */
@ShellComponent
@SpringBootApplication
public class TranslationCommands {

    @ShellMethod("Add two integers together.")
    public int add(int a, int b) {
        System.out.printf("add {}",a);
        return a + b;
    }

    public static void main(String[] args) throws Exception {
        ConfigurableApplicationContext context = SpringApplication.run(TranslationCommands.class, args);
    }
}
