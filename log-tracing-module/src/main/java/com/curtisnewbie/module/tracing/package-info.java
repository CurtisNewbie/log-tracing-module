/**
 * Module used for log tracing.
 * <p>
 * To set the traceId, you have following two options:
 * <ol>
 * <li>
 * You can add a HandlerInterceptor by extending {@link com.curtisnewbie.module.tracing.filter.TracingHandlerInterceptorBase},
 * all you have to do is to pass in the {@link java.security.Principal} object, and let the base class do the rest.
 * </li>
 * <li>
 * You can also manually set the traceId in a servlet filter by calling {@link com.curtisnewbie.module.tracing.common.MdcUtil#setTraceId(java.lang.String)}
 * </li>
 * </ol>
 * </p>
 * <p>
 * After traceId is properly set, you will need to change your log-back setting in order to display the traceId. For example:
 * <pre>
 * {@code
 * <appender name="STDOUT" class="ch.qos.logback.core.ConsoleAppender">
 *      <encoder>
 *          <pattern>%d{HH:mm:ss.SSS} [%thread] %-5level %logger{10} TRACE_ID:'%X{traceId}' - %msg%n</pattern>
 *      </encoder>
 * </appender>
 * }
 * </pre>
 * </p>
 */
package com.curtisnewbie.module.tracing;

