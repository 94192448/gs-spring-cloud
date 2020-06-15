# spring-cloud-gateway动态路由(金丝雀发布 / 灰度发布)

spring-cloud-gateway实现动态路由主要思路：
1. 使用自身endpoint动态修改router策略,即调用 GatewayControllerEndpoint 所提供rest api
2. 结合 DiscoveryClientRouteDefinitionLocator 中使用ribbon提供的lb策略，扩展默认 ZoneAvoidanceRule 规则

| Release Train |  Boot Version |
| :--- | :---: | 
| Finchley.SR4 | 2.0.9.RELEASE | 


## Modules

* [eureka](./eureka) - 服务注册发现
* [gateway](./gateway) - 基于spring-cloud-gateway扩展自定义(instance版本比较)路由规则
* [service-a](./service-a) - 注册到eureka的webmvc微服务
* [service-b-webflux](./service-b-webflux) - 注册到eureka的reactor服务模拟，通过feign-client调用service-a

## Features
* spring-cloud-gateway 通过 DiscoveryClientRouteDefinitionLocator 自动读取注册中心服务生成 RouteDefinitionLocator
 ``` localhost/actuator/gateway/routes ```
* 扩展 ZoneAwareLoadBalancer 中 ZoneAvoidanceRule 增加使用新版本自动选择功能
* 支持[加权轮训调度算法](./gateway/src/main/java/com/example/ribbon/WeightZoneAvoidanceRule.java) 实现动态路由策略

## Tests 

### I 同一服务多instances高版本优先策略分担流量

- spring-cloud-gateway与service-a均注册到eureka服务注册中心

- service-a启动两个不同版本的instance

```
java -jar -Dserver.port=9001 -Deureka.instance.metadata-map.version=v1.0.0  service-a/target/*.jar
java -jar -Dserver.port=9002 -Deureka.instance.metadata-map.version=v2.0.0  service-a/target/*.jar

```

- 通过gateway网关访问后端service-a服务, 多版本共存时候优先访问高版本instance

```curl http://localhost/SERVICE-A/user/version```

- Reactive模式微服务一样适合

``` 
java -jar -Dserver.port=9011 -Deureka.instance.metadata-map.version=v1.0.0  service-b-webflux/target/*.jar 
java -jar -Dserver.port=9012 -Deureka.instance.metadata-map.version=v2.0.0  service-b-webflux/target/*.jar 

curl http://localhost/SERVICE-B/books/version
```

### II 同一服务多instances权重优先策略分担流量

http://127.0.0.1:8761/metadata.html 修改instance weight值

通过Gateway访问流量按权重值分配
```curl http://localhost/SERVICE-A/user/version```

## Links
https://github.com/spring-cloud/spring-cloud-gateway/blob/v2.0.2.RELEASE/spring-cloud-gateway-core/src/main/java/org/springframework/cloud/gateway/discovery/DiscoveryClientRouteDefinitionLocator.java
