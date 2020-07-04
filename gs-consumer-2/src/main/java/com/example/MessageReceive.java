package com.example;

import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.cloud.stream.messaging.Sink;

/**
 * @author yangzq80@gmail.com
 * @date 2020-07-03
 */
@EnableBinding(Sink.class)
@Slf4j
public class MessageReceive {

    @StreamListener(value = Sink.INPUT,condition = "headers['eventType']=='type-0'")
    public void process(String data) {
        log.info("receive--->{}", data);
    }
}
