# gs-spring-cloud
examples of spring-cloud-sleuth tracing for RabbitMQ and kafka.

| Release Train |  Boot Version |
| :--- | :---: | 
| Finchley.SR4 | 2.0.9.RELEASE | 

# Tracing for Messaging

* We instrument the `RabbitTemplate` so that tracing headers get injected into the message.

To block this feature, set spring.sleuth.messaging.rabbit.enabled to false.

* We instrument the Spring Kafka’s `ProducerFactory` and `ConsumerFactory` so that tracing headers get injected into the created Spring Kafka’s Producer and Consumer.

To block this feature, set spring.sleuth.messaging.kafka.enabled to false

# Tracing for web
TracingClientHttpRequestInterceptor
```
@Override public ClientHttpResponse intercept(HttpRequest request, byte[] body,
      ClientHttpRequestExecution execution) throws IOException {
    Span span = handler.handleSend(injector, request.getHeaders(), request);
    ClientHttpResponse response = null;
    Throwable error = null;
    try (Tracer.SpanInScope ws = tracer.withSpanInScope(span)) {
      return response = execution.execute(request, body);
    } catch (IOException | RuntimeException | Error e) {
      error = e;
      throw e;
    } finally {
      handler.handleReceive(response, error, span);
    }
  }
```

# Tracer
Returns a new child span if there's a currentSpan() or a new trace if there isn't.
Prefer startScopedSpan(String) if you are tracing a synchronous function or code block.
```
public Span nextSpan() {
    TraceContext parent = currentTraceContext.get();
    return parent != null ? newChild(parent) : newTrace();
  }
```

# TracingFilter
You can also modify the behavior of the TracingFilter, which is the component that is responsible for processing the input HTTP request and adding tags basing on the HTTP response. You can customize the tags or modify the response headers by registering your own instance of the TracingFilter bean.

In the following example, we register the TracingFilter bean, add the ZIPKIN-TRACE-ID response header containing the current Span’s trace id, and add a tag with key custom and a value tag to the span.
```
@Component
@Order(TraceWebServletAutoConfiguration.TRACING_FILTER_ORDER + 1)
class MyFilter extends GenericFilterBean {

    private final Tracer tracer;

    MyFilter(Tracer tracer) {
        this.tracer = tracer;
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response,
            FilterChain chain) throws IOException, ServletException {
        Span currentSpan = this.tracer.currentSpan();
        if (currentSpan == null) {
            chain.doFilter(request, response);
            return;
        }
        // for readability we're returning trace id in a hex form
        ((HttpServletResponse) response).addHeader("ZIPKIN-TRACE-ID",
                currentSpan.context().traceIdString());
        // we can also add some custom tags
        currentSpan.tag("custom", "tag");
        chain.doFilter(request, response);
    }

}

```

# links
https://cloud.spring.io/spring-cloud-static/Finchley.SR4/single/spring-cloud.html