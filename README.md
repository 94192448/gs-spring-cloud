# spring-cloud-netflix-sidecar
遗留历史系统想切到java平台不完全重写过去的服务，可以以统一协议为代价来进行整合。non-JVM应用仅需实现一个health检测端口即可通过sidecar
上报到eureka在线或者down状态使用Eureka, Ribbon, and Config Server功能, 区别于istio代理组件Envoy sidecar流量劫持流程. 
sidecar提供可以获取既定服务所有实例的信息(例如host，端口等)的http api，通过一个嵌入的Zuul proxy从Eureka获取的相关路由节点，
Spring Cloud Config Server可以直接通过主机查找或通过代理Zuul进行访问。

| Release Train |  Boot Version |
| :--- | :---: | 
| Finchley.SR4 | 2.0.9.RELEASE | 

# Modules

examples of spring-cloud-netflix-sidecar.

* [eureka](./eureka) - 服务注册发现
* [gs-provider](./gs-provider) - 注册到eureka的微服务
* [polyglot-sidecar](./polyglot-sidecar) - 注册到eureka的sidecar
* [polyglot-go-web](./polyglot-go-web) - golang语言web服务,访问sidecar服务中zuul proxy的gs-provider微服务
* [gs-consumer](./gs-consumer) - 通过feign-client调用sidecar中代理的non-JVM的go-web服务

# Features

* @EnableSidecar默认支持@EnableCircuitBreaker, @EnableDiscoveryClient, @EnableZuulProxy功能
* 通过一个 HTTP API获取一个service所有instances
* non-JVM application需实现健康检测端口，便于sidecar报告给eureka该instance在线或者down
* 跟non-JVM application同一host运行sidecar
* 通过sidecar访问被proxy的non-JVM应用
* 异构系统无法通过ribbon访问其他service,通过sidecar提供的Zuul proxy访问其他微服务
* 一个sidecar仅能代理一个instance
* 其他微服务通过feign-client调用sidecar代理的non-JVM application

# Tests
* gs-proider提供用户接口 

```curl localhost:9001/user```
* 通过sidecar中zuul Proxy可将请求代理到eureka中注册的gs-proider微服务

```curl http://localhost:3001/gs-provider/user```
* non-JVM polyglot-go-web 访问sidecar代理的gs-proider微服务

```curl http://localhost:3000/test-remote```
* 微服务gs-consumer通过FeignClient访问non-JVM polyglot-go-web

```curl localhost:9002/user```

# Quick start
```
java -jar eureka/target/eureka-1.0-SNAPSHOT.jar
java -jar -Dserver.port=3002 polyglot-sidecar/target/polyglot-go-web-sidecar-1.0-SNAPSHOT.jar
java -jar -Dserver.port=3003 -Dsidecar.port=3002 -Dsidecar.health-uri=http://localhost:3002/health.json polyglot-sidecar/target/polyglot-sidecar-1.0-SNAPSHOT.jar

```

# Links
* https://cloud.spring.io/spring-cloud-static/spring-cloud-netflix/2.2.1.RELEASE/reference/html/#polyglot-support-with-sidecar
