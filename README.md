# gs-spring-cloud-sidecar-benchmarks
jvm-sidecar 与 go-sidecar 性能对比测试

| Release Train |  Boot Version |
| :--- | :---: | 
| Finchley.SR4 | 2.0.9.RELEASE | 

## Modules

* [eureka](./eureka) - 服务注册发现
* [service-a](./service-a) - 注册到eureka的微服务
* [service-b](./service-b) - 通过feign-client调用 service-a
* [gateway-zuul](./gateway-zuul) - zuul网关

## Features
* 通过gateway-zuul网关访问后端service-a,service-b
* service-b访问service-a

## Test 

```
# 后端service-a服务
curl -d '{"name":"Trump", "id":"11"}' -H "Content-Type: application/json" -X POST http://localhost:9001/user

# 通过zuul网关访问后端service-a服务
curl localhost/service-a/user
curl -d '{"name":"Trump", "id":"11"}' -H "Content-Type: application/json" -X POST http://localhost/service-a/user

```

## Links
https://cloud.spring.io/spring-cloud-static/spring-cloud-netflix/2.2.2.RELEASE/reference/html/#netflix-zuul-reverse-proxy 

