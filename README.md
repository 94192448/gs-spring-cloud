# gs-spring-cloud-base
验证zuul url rewrite

| Release Train |  Boot Version |
| :--- | :---: | 
| Finchley.SR4 | 2.0.9.RELEASE | 

## Modules

* [eureka](./eureka) - 服务注册发现
* [service-a](./service-a) - 注册到eureka的微服务
* [service-b](./service-b) - 通过feign-client调用 service-a
* [gateway-zuul](./gateway-zuul) - zuul网关

## Features

### I Zuul
- SimpleRouteLocator 会从 ZuulProperties 中加载routes信息
- DiscoveryClientRouteLocator extends SimpleRouteLocator  将SimpleRouteLocator配置的静态路由与来自DiscoveryClient的路由组合在一起
- ZuulProperties配置自定义route匹配path
``` 
zuul:
  routes:
    service-a:
      path: /system-a/domain1/**
      stripPrefix: true
    service-b:
      path: /system-b/domain1/**
      stripPrefix: true
```

### II Resttemplate  自定义CustomerRestTemplate重写doExecute方法中URI扩展为自定义映射关系
- resttemplate 中 InterceptingClientHttpRequest 执行拦截器列表中 LoadBalancerInterceptor implements ClientHttpRequestInterceptor（
client端http请求拦截器，注册到resttemplate中修改request路径或增强response） 
- 使用ribbon client端负载均衡 ZoneAwareLoadBalancer extends DynamicServerListLoadBalancer extends BaseLoadBalancer extends AbstractLoadBalancer implements ILoadBalancer
- 自定义拦截器使用

``` RestTemplate restTemplate = new RestTemplate();
    
            List<ClientHttpRequestInterceptor> interceptors
                    = restTemplate.getInterceptors();
            if (CollectionUtils.isEmpty(interceptors)) {
                interceptors = new ArrayList<>();
            }
            interceptors.add(0,new URLRewriteInterceptor());
            restTemplate.setInterceptors(interceptors);
    
            return restTemplate;
 ``` 

## Test 

```
# 后端service-a服务
curl -d '{"name":"Trump", "id":"11"}' -H "Content-Type: application/json" -X POST http://localhost:9001/user

- 
 curl localhost:9001/rewiret-resttemplate

# 通过zuul网关访问后端service-a服务
curl localhost/service-a/user
curl -d '{"name":"Trump", "id":"11"}' -H "Content-Type: application/json" -X POST http://localhost/service-a/user

# service-b 自定义路由匹配规则访问结果一致
curl localhost/service-b/test
curl localhost/system-a/main/test
```

## TODO
- DiscoveryClientRouteLocator 中 locateRoutes 读取 properties 中配置的rewrite 进行处理

``` 
服务路由

需求背景及要求
目前是按平台（project）来部署应用的，平台下的领域是以jar包（module project）被平台所依赖。 未来根据业务发展会按领域拆分独立部署，为了不影响调用方，所以设计了URL规范：/平台/领域/aa/bb， 网关和RestTemplet调用时解析出“平台”和“领域”并转换为注册中心的微服务名。
举例：url=/clear/trade/getUserList， 网关解析url根据clear和trade到配置中找到其映射的微服务名称cl-trade，并根据cl-trade路由请求

场景：
1、客户端调用网关时，网关解析url并路由到对应的微服务
2、统一复核（业务场景）通过RestTemplet调用时传入url，能路由到对应的微服务
```


## Links
https://cloud.spring.io/spring-cloud-static/spring-cloud-netflix/2.2.2.RELEASE/reference/html/#netflix-zuul-reverse-proxy 

