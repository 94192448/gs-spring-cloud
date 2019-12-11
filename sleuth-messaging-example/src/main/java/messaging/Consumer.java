package messaging;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.cloud.stream.messaging.Processor;

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
@Slf4j
public class Consumer {
    public static void main(String[] args) {
        SpringApplication.run(Consumer.class,"--spring.application.name=consumer",
                "--server.port=0");
    }


    @StreamListener(Processor.INPUT)
    public void handle(String person) {
        log.info("mq received - {}",person);
    }
}
