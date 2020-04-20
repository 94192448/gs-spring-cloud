| Key |	Default Value | Description |
| :--- | :---: | :---: |
|yusp.security.headers.xss |X-XSS-Protection: 1; mode=block|**`X-XSS-Protection`** 响应头是Internet Explorer，Chrome和Safari的一个功能，当检测到跨站脚本攻击 ([XSS](https://developer.mozilla.org/en-US/docs/Glossary/XSS))时，浏览器将停止加载页面.|
|yusp.security.headers.hsts |max-age=31536000 ; includeSubDomains ; preload|`HTTP Strict Transport Security`是一个安全功能，它告诉浏览器只能通过HTTPS访问当前资源，而不是HTTP|
|yusp.security.headers.csrf ||是一种挟制用户在当前已登录的Web应用程序上执行非本意的操作的攻击方法.多个uri启用csrf则,分隔.如：/user,/test|
|yusp.security.headers.content-type-options|X-Content-Type-Options: nosniff|服务器用来提示客户端一定要遵循在 [`Content-Type`](https://developer.mozilla.org/zh-CN/docs/Web/HTTP/Headers/Content-Type) 首部中对 [MIME 类型](https://developer.mozilla.org/en-US/docs/Web/HTTP/Basics_of_HTTP/MIME_types) 的设定，而不能对其进行修改。这就禁用了客户端的 MIME 类型嗅探行为.|
|yusp.security.headers.csp|Content-Security-Policy:script-src 'self';report-uri /csp-report|内容安全策略  (CSP 是一个额外的安全层，用于检测并削弱某些特定类型的攻击，包括跨站脚本XSS 和数据注入攻击等|
|yusp.security.headers.feature-policy |Feature-Policy:vibrate 'self'; sync-xhr 'self'|`Feature-Policy`响应头提供了一种可以在本页面或包含的iframe上启用或禁止浏览器特性的机制|
|yusp.security.headers.frame-options |X-Frame-Options:SAMEORIGIN|`X-Frame-Options` HTTP 响应头是用来给浏览器 指示允许一个页面 可否在`<frame>, <iframe>, <embed> 或者 <object>` 中展现的标记。站点可以通过确保网站没有被嵌入到别人的站点里面，从而避免 `clickjacking`攻击。|
|yusp.security.headers.referrer-policy |Referrer-Policy:origin-when-cross-origin, strict-origin-when-cross-origin|`Referrer-Policy` 首部用来监管哪些访问来源信息——会在 `Referer`中发送——应该被包含在生成的请求当中。|
|yusp.security.headers.cache-control |Cache-Control:no-cache, no-store, max-age=0, must-revalidate|`Cache-Control` 通用消息头字段，被用于在http请求和响应中，通过指定指令来实现缓存机制。缓存指令是单向的，这意味着在请求中设置的指令，不一定被包含在响应中。|
|yusp.security.sql-injection.enabled |false| 默认不启用. |
|yusp.security.sql-injection.regex | ```(?:')|(?:--)|(/\\*(?:.|[\\n\\r])*?\\*/)|(\\b(select|update|union|and|or|delete|insert|trancate|char|into|substr|ascii|declare|exec|count|master|into|drop|execute)\\b)``` |需要拦截的SQL的正则表达式|
