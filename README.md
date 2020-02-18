# spring-cloud-netflix-sidecar
examples of spring-cloud-netflix-sidecar.

| Release Train |  Boot Version |
| :--- | :---: | 
| Finchley.SR4 | 2.0.9.RELEASE | 

# Features
* 通过一个 HTTP API获取一个service所有instances
* non-JVM application需实现健康检测端口，便于sidecar报告给eureka该instance在线或者down
* 跟non-JVM application同一host运行sidecar
* 通过sidecar访问被proxy的non-JVM应用
* 异构系统无法通过ribbon访问其他service
* 通过sidecar访问其他服务,Zuul proxy ,异构系统通过sidecar访问其他service
