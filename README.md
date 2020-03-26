# gs-spring-cloud-base
最基本customer与provider访问demo.

| Release Train |  Boot Version |
| :--- | :---: | 
| Finchley.SR4 | 2.0.9.RELEASE | 

## Modules

* [eureka](./eureka) - 服务注册发现
* [gs-provider](./gs-provider) - 注册到eureka的微服务
* [gs-consumer](./gs-consumer) - 通过feign-client调用gs-provider
* [spring-boot-demo](./spring-boot-demo) - 简单spring-boot服务,集成Security Headers

## Features
* 最基本customer与provider访问demo

## Test 

```
# 后端gs-provider服务
curl -d '{"name":"Trump", "id":"11"}' -H "Content-Type: application/json" -X POST http://localhost:9001/user

# 通过zuul网关访问后端gs-provider服务
curl localhost/gs-provider/user
curl -d '{"name":"Trump", "id":"11"}' -H "Content-Type: application/json" -X POST http://localhost/gs-provider/user

```

## Links
https://cloud.spring.io/spring-cloud-static/spring-cloud-netflix/2.2.2.RELEASE/reference/html/#netflix-zuul-reverse-proxy 

