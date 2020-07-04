# gs-spring-cloud-stream-rabbitmq
Spring Cloud Concepts
- Binder— Depending upon the messaging system we will have to specify the messaging platform dependency, in this case, 
it's RabbitMQ
- Source— When a message is needed to be published it is done using  Source . The  Source  is an interface having a 
method annotated with  @Output . The  @Output annotation is used to identify output channels. The Source takes a POJO 
object, serializes it, and then publishes it to the output channel.

# modules

- [spring-rabbitmq](./spring-rabbitmq) 使用rabbitmq cliet实现header exchange ，自动产生临时queue
- [base-input-output-channels](./base-input-output-channels) 基本消息发送
