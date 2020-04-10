# yusp-security

web应用中安全策略通用实现,包含http headers安全xss,csp,cors,xss,crsf,以及sql injection等安全。
扩展spring-security默认安全策略，提供通用web安全策略默认配置满足大部分安全策略需求，扩展可通过配置文件灵活更改应用默认安全策略.

## Modules

* [yusp-security-autoconfigure](./yusp-security-autoconfigure) - 常规WEB应用安全策略自动配置
* [yusp-security-testing-tools](./yusp-security-testing-tools) - WEB安全检测工具

## Features
* HTTP Strict Transport Security (HSTS)  
* X-Frame-Options 
* CSRF attack prevention
配置一个或多个uri则启用csrf, 对post请求有效，get不受保护，访问uri则返回csrf-token到cookie中，前后端分离可使用axios的拦截器，为每个请求发起时，从cookie手动读取并放在请求头
* Cross-Site Scripting (XSS)
* sql注入 默认关闭，使用hibernate等orm框架已经将输入作为sql字符参数而不是sql片段避免了sql注入
* Content Security Policy			
* Cookies readonly
* Cross-origin Resource Sharing			
* HTTP Public Key Pinning			
* HTTP Strict Transport Security			
* Redirection		
* Referrer Policy		
* X-Content-Type-Options	
* X-Frame-Options
* X-XSS-Protection
* CSP安全上报

## Security properties
默认配置可满足大部分场景，无特殊需求则无需更改默认配置策略

| Key |	Default Value | Description |
| :--- | :---: | :---: |
|yusp.security.web.csrf.paths| ||
|yusp.security.headers.xss |X-XSS-Protection: 1; mode=block||
|yusp.security.headers.hsts |||
|yusp.security.headers.csrf ||多个uri启用csrf则,分隔.如：/user,/test|
|yusp.security.headers.content-type-options|||
|yusp.security.headers.csp|||
|yusp.security.headers.feature-policy |||
|yusp.security.headers.frame-options |||
|yusp.security.headers.referrer-policy |||
|yusp.security.headers.cache-control |||
|yusp.security.sql-injection.enabled |false| 默认不启用 |
|yusp.security.sql-injection.regex | ||


## Quick start 

- web安全自动配置引用

``` mvn install```

```
<dependency>
	<groupId>cn.com.yusys</groupId>
	<artifactId>yusp-security-autoconfigure</artifactId>
	<version>1.0.0.RELEASE</version>
</dependency>

```
- WEB安全检测工具

[Installation](./yusp-security-testing-tools/README.md)

测试: http://172.16.20.31:9292/ 
