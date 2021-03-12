# gs-spring-cloud
验证sleuth与sidecar
- sidecar支持sleuth链路追踪
- sidecar默认集成eureka服务注册发现
- @EnableSidecar包含了@EnableCircuitBreaker, @EnableDiscoveryClient, @EnableZuulProxy
- 异构系统通过zuul访问其他微服务,其他微服务通过feignclient直接访问到异构系统的IP+端口(sidecar将异构系统端口定义到了eureka-client中)

| Release Train |  Boot Version |
| :--- | :---: | 
| Finchley.SR4 | 2.0.9.RELEASE | 

# POC env
 - 启动sidecar java -jar polyglot-sidecar-1.0-SNAPSHOT.jar --eureka.client.serviceUrl.defaultZone=http://192.168.251.173:8761/eureka
 - 启动sidecar同主机的go应用 ./go-web-linux
 - 启动SC微服务 java -jar gs-consumer-1.0-SNAPSHOT.jar --eureka.client.serviceUrl.defaultZone=http://192.168.251.173:8761/eureka
 - 启动zipkin
 
# POC tests 
 - SC微服务访问 -> sidecar   curl localhost:3002/test/go
 - SC微服务被访问 <- sidecar  curl localhost:3000/test/java

# links
https://cloud.spring.io/spring-cloud-static/Finchley.SR4/single/spring-cloud.html

# zipkin下载
https://repo1.maven.org/maven2/io/zipkin/zipkin-server/2.23.2/zipkin-server-2.23.2-exec.jar