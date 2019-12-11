# gs-spring-cloud
examples of spring-cloud-sleuth tracing for local logs.

| Release Train |  Boot Version |
| :--- | :---: | 
| Finchley.SR4 | 2.0.9.RELEASE | 

# getting started
启动后访问示例web端口 ```curl http://127.0.0.1:9002/user``` 则生成本地tracing日志

# guides
* 通过自定义 `zipkin2.reporter.Reporter`与`zipkin2.reporter.Sender` 将tracing日志Reporter到本地log

```
    @Bean("reporter")
    Reporter<Span> myReporter() {
        return AsyncReporter.create(mySender());
    }

    //@Bean(ZipkinAutoConfiguration.SENDER_BEAN_NAME)
    @Bean("sender")
    MySender mySender() {

        return new MySender();
    }
```
* logback-spring.xml中配置`reporter日志`到文件夹
```
    <logger name="com.example.ZipkinCustomConfig" level="${logging.level}" additivity="true">
        <appender-ref ref="appender-trace" />
    </logger>
```


# dependencies
```
        <dependency>
            <groupId>org.springframework.cloud</groupId>
            <artifactId>spring-cloud-starter-sleuth</artifactId>
        </dependency>
        <dependency>
            <groupId>org.springframework.cloud</groupId>
            <artifactId>spring-cloud-starter-zipkin</artifactId>
        </dependency>
```


# links
https://cloud.spring.io/spring-cloud-static/Finchley.SR4/single/spring-cloud.html