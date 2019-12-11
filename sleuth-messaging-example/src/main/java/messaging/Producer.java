package messaging;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.messaging.Processor;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * The class description.
 *
 * @author yangzq80@gmail.com
 * @date 2019-12-09
 * @see
 * @since 1.0.0
 */
@SpringBootApplication
@EnableBinding(Processor.class)
@RestController
@Slf4j
@EnableScheduling
public class Producer {
    public static void main(String[] args) {
        SpringApplication.run(Producer.class,args);
    }

    @Autowired
    Processor processor;

    @GetMapping("/")
    public void test(){
        send();
        log.info("webmvc send - {}",i);
    }


    int i;
    //@Scheduled(fixedDelay = 1000)
    public void send() {
        i++;
        MessageBuilder messageBuilder = MessageBuilder.withPayload("msg"+i).
                setHeader("eventType", "event1");

        if (i%5==0){
            messageBuilder.setHeader("eventType","event2");
        }

        boolean isSend = processor.output().send(messageBuilder.build());

        //log.info("send:{} - {}",i,isSend);
    }

    public static class Person {
        private String name;
        public String getName() {
            return name;
        }
        public void setName(String name) {
            this.name = name;
        }
        public String toString() {
            return this.name;
        }
    }
}
