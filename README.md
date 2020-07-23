# gs-spring-cloud-sidecar-benchmarks
jvm-sidecar 与 go-sidecar 性能对比测试

| Release Train |  Boot Version |
| :--- | :---: | 
| Finchley.SR4 | 2.0.9.RELEASE | 

## Modules

* [eureka](./eureka) - 服务注册发现
* [service-a](./service-a) - 注册到eureka的微服务,通过feign-client调用 service-b
* [service-b-spring-boot](./service-b-spring-boot) - 普通spring-boot单体应用，通过sidecar接入eureka
* [jvm-sidecar](./jvm-sidecar) - spring-cloud-netflix-sidecar
* [go-sidecar](./go-sidecar) - golang实现的sidecar
## Features

## Test 
jvm-sidecar与go-sidecar均使用port:9002

service-a 9000

service-b 9001

- service-b -> service-a 

curl http://localhost:9001/test/remote

- service-b <- service-a

curl http://localhost:9000/test/remote



### jvm-sidecar

### go-sidecar


## Links
https://cloud.spring.io/spring-cloud-static/spring-cloud-netflix/2.2.2.RELEASE/reference/html/#netflix-zuul-reverse-proxy 

