# log-tracing-module

Module for log tracing between web endpoints and service layers (dubbo services)

## Implementation and Usage

This module relies on **MDC**. Log tracing usually starts from web endpoints (i.e., the controller methods), so you may decide to add a `HandlerInterceptor` such that we can set the trace_id in MDC before the request is handled. Set the trace_id using `MdcUtil.setTraceId(String)` method. 

A `HandlerInterceptor` for log tracing is already provided, it's the `TracingHandlerInterceptor`. This implementation requires a `TraceIdExtractor`, a simple implementation of this interface is also provided that extracts traceId by field name, it's the `FieldNameBasedExtractor`. Finally, to actually use this handler interceptor, you will need to register this bean through `WebMvcConfigurer`. 

Two filters are registered for Dubbo services, since Dubbo service calls are usually synchronous, the MDC can be easily copied and propagated throughout the method call, and get cleaned up once the RPC method calls return.

- com.curtisnewbie.module.tracing.filter.DubboProviderTracingFilter
- com.curtisnewbie.module.tracing.filter.DubboConsumerTracingFilter

## Modules and Dependencies

This project depends on the following modules that you must manually install (using `mvn clean install`).

- service-module
    - description: import dependencies for a Dubbo service
    - url: https://github.com/CurtisNewbie/service-module
    - branch: main

