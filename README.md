# config-apollo

携程apollo配置中心开放API访问测试

- Apollo管理员在 http://{portal_address}/open/manage.html 创建第三方应用

docker启动中需配置 EUREKA_INSTANCE_IP_ADDRESS

``` 
services:
  apollo-quick-start:
    image: nobodyiam/apollo-quick-start
    container_name: apollo-quick-start
    
    environment: 
      EUREKA_INSTANCE_IP_ADDRESS: '172.16.20.31'

    depends_on:
      - apollo-db
    ports:
      - "8080:8080"
      - "8070:8070"
    links:
      - apollo-db

```

``` 
<dependency>
     <groupId>com.ctrip.framework.apollo</groupId>
     <artifactId>apollo-client</artifactId>
     <version>1.1.0</version>
</dependency>

<dependency>
    <groupId>com.ctrip.framework.apollo</groupId>
    <artifactId>apollo-openapi</artifactId>
    <version>1.6.2</version>
</dependency>

```

| Release Train |  Boot Version |
| :--- | :---: | 
| Finchley.SR4 | 2.0.9.RELEASE | 

## Modules

* [eureka](./eureka) - 服务注册发现
* [service-b](./service-b) - apollo openapi使用

## Features
* 通过gateway-zuul网关访问后端service-a,service-b
* service-b访问service-a

## Test 


## Links
https://github.com/ctripcorp/apollo/wiki/Apollo%E5%BC%80%E6%94%BE%E5%B9%B3%E5%8F%B0 

