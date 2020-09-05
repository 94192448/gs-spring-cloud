# gs-spring-cloud
验证sleuth与sidecar

| Release Train |  Boot Version |
| :--- | :---: | 
| Finchley.SR4 | 2.0.9.RELEASE | 

# Tests

- sidecar  -> 微服务
curl http://localhost:3001/gs-consumer

- 微服务FeignClient -> sidecar
curl http://localhost:9002/testRemote


# links
https://cloud.spring.io/spring-cloud-static/Finchley.SR4/single/spring-cloud.html