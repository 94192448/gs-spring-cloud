# spring-cloud-gateway动态路由(金丝雀发布 / 灰度发布)

| Release Train |  Boot Version |
| :--- | :---: | 
| Finchley.SR4 | 2.0.9.RELEASE | 

## Modules

* [eureka](./eureka) - 服务注册发现
* [gateway](./gateway) - 基于spring-cloud-gateway扩展自定义(instance版本比较)路由规则
* [service-a](./service-a) - 注册到eureka的微服务
* [service-b-webflux](./service-b-webflux) - 通过feign-client调用service-a

## Features
* spring-cloud-gateway 通过 DiscoveryClientRouteDefinitionLocator 自动读取注册中心服务生成 RouteDefinitionLocator
 ``` localhost/actuator/gateway/routes ```
* 扩展 ZoneAwareLoadBalancer 中 ZoneAvoidanceRule 增加使用新版本自动选择功能

## Tests 

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


## Links
https://github.com/spring-cloud/spring-cloud-gateway/blob/v2.0.2.RELEASE/spring-cloud-gateway-core/src/main/java/org/springframework/cloud/gateway/discovery/DiscoveryClientRouteDefinitionLocator.java
