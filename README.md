# gs-spring-cloud
examples of spring-cloud-sleuth tracing for RabbitMQ and kafka.

| Release Train |  Boot Version |
| :--- | :---: | 
| Finchley.SR4 | 2.0.9.RELEASE | 

# Tracing for Messaging

* We instrument the `RabbitTemplate` so that tracing headers get injected into the message.

To block this feature, set spring.sleuth.messaging.rabbit.enabled to false.

* We instrument the Spring Kafka’s `ProducerFactory` and `ConsumerFactory` so that tracing headers get injected into the created Spring Kafka’s Producer and Consumer.

To block this feature, set spring.sleuth.messaging.kafka.enabled to false

# links
https://cloud.spring.io/spring-cloud-static/Finchley.SR4/single/spring-cloud.html