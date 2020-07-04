package com.example;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.messaging.Source;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

/**
 * @author yangzq80@gmail.com
 * @date 2020-07-03
 */
@Service
@Slf4j
@EnableScheduling
@EnableBinding(Source.class)
public class MessgeSend {

    //private final EmitterProcessor<Message<?>> processor = EmitterProcessor.create();

    @Autowired
    Source source;

    int i=0;

    @Scheduled(fixedRate=1000)
    public void send(){
        i++;
        log.info("message....."+i);

        Message message = MessageBuilder.withPayload("message-hello-"+i).
                setHeader("eventType", "type-"+i%2).build();

        log.info(message.getHeaders().toString());

        source.output().send(message);
    }




//    //@Bean
//    public Supplier<Flux<Message<?>>> supplier() {
//        return () -> processor;
//    }
//
//    //Following sink is used as test consumer. It logs the data received through the consumer.
//    static class TestSink {
//
//        private final Log logger = LogFactory.getLog(getClass());
//
//        @Bean
//        public Consumer<String> receive1() {
//            return data -> logger.info("Data received from customer-1..." + data);
//        }
//
//        @Bean
//        public Consumer<String> receive2() {
//            return data -> logger.info("Data received from customer-2..." + data);
//        }
//    }

//    @StreamListener(value = CustomerChannels.CUSTOMER_INPUT, condition = "headers['eventType']=='CreditReserved'")
//    public void approvedOrder(DomainEventEntry eventEntry){
//
//        Order order = orderRepository.findById(eventEntry.getEventAggregateId()).get();
//
//        order.setState("approved");
//
//        orderRepository.save(order);
//    }

}
