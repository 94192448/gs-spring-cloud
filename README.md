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
      serviceId: service-a
    service-a2:
      path: /system-a/domain2/**
      stripPrefix: true
      serviceId: service-a
    service-b:
      path: /system-b/domain1/**
      stripPrefix: true
      serviceId: service-b
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
 
 ### III feignclient
 - 启动阶段 内部类FeignClientsRegistrar 根据 annotationMetadata 构造 FeignClientSpecification 注册
 - bean注册 PostProcessorRegistrationDelegate ,刷新 RefreshAutoConfiguration  内部类 RefreshScopeBeanDefinitionEnhancer implements BeanDefinitionRegistryPostProcessor, EnvironmentAware
    
 - FeignAutoConfiguration将 List<FeignClientSpecification> 放到 FeignContext 中缓存
 - NamedContextFactory 获取 FeignLoadBalancer 缓存到CachingSpringLoadBalancerFactory中

 - openfeign中 LoadBalancerFeignClient 执行netflix.client中  AbstractLoadBalancerAwareClient.executeWithLoadBalancer(final S request, final IClientConfig requestConfig)

从 CachingSpringLoadBalancerFactory 中获取 lb 

- SynchronousMethodHandler 中组装请求响应

``` 
com.netflix.loadbalancer.reactive public class LoadBalancerCommand<T>
extends Object
A command that is used to produce the Observable from the load balancer execution. The load balancer is responsible for the following:
Choose a server
Invoke the call(com.netflix.loadbalancer.Server) method
Invoke the ExecutionListener if any
Retry on exception, controlled by RetryHandler
Provide feedback to the com.netflix.loadbalancer.LoadBalancerStats
```

- 配置文件读取 @Value 注解在BeanDefinitionRegistry之后执行

```Setting the value of fields annotated with @Value happens only after the post-processing of the BeanDefinitionRegistry, meaning they are not usable at this stage of the initialization process.

You can however explicitly scan the configuration environment and read the relevant properties' values from there, then use them in your dynamic bean definitions.

To gain access to the configuration environment, you can create your BeanDefinitionRegistryPostProcessor in a method annotated with @Bean, that takes the ConfigurableEnvironment as a parameter.
```

### IV ribbon
- SpringClientFactory 按cleint name为每个都创建 client，load balancer 和 client configure实例
A factory that creates client, load balancer and client configuration instances. It creates a Spring ApplicationContext per client name, and extracts the beans that it needs from there.
- LoadBalancerCommand 

## Test 

- 自定义映射 - feginclient

curl localhost:9002/user

- 自定义映射 - zuul

curl http://localhost/system-a/domain1/user
curl http://localhost/system-a/domain2/user
- 后端service-a服务

curl -d '{"name":"Trump", "id":"11"}' -H "Content-Type: application/json" -X POST http://localhost:9001/user

- 自定义服务映射 - resttemplate

curl localhost:9001/rewiret-resttemplate

- 通过zuul网关访问后端service-a服务

curl localhost/service-a/user
curl -d '{"name":"Trump", "id":"11"}' -H "Content-Type: application/json" -X POST http://localhost/service-a/user

- service-b 自定义路由匹配规则访问结果一致

curl localhost/service-b/test
curl localhost/system-a/main/test
curl localhost:9002/user

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

