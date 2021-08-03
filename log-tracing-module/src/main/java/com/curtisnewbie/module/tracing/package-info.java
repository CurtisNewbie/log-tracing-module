/**
 * Module used for log tracing.
 * <p>
 * To set the traceId, you have following two options:
 * <ol>
 * <li>
 * Mark the rest endpoints (methods in controller) using {@link com.curtisnewbie.module.tracing.aop.TracingEntry}, it will uses
 * the value of a field named ({@link com.curtisnewbie.module.tracing.common.TracingConstants#PRINCIPAL_TRACE_ID_FIELD}
 * in the {@link java.security.Principal} object. If the user is not yet authenticated, this will have no effect. If you
 * have a Principal object with a field of this name, then you are all set.
 * </li>
 * <li>
 * You can also manually set the traceId in a servlet filter by calling {@link com.curtisnewbie.module.tracing.common.MdcUtil#setTraceId(java.lang.String)}
 * </li>
 * </ol>
 * <p>
 * After traceId is properly set, you will need to change your log-back setting in order to display the traceId. For example:
 * <pre>
 * {@code
 * <appender name="STDOUT" class="ch.qos.logback.core.ConsoleAppender">
 *      <encoder>
 *          <pattern>%d{HH:mm:ss.SSS} [%thread] %-5level %logger{36} traceId:'%X{traceId}' - %msg%n</pattern>
 *      </encoder>
 * </appender>
 * }
 * </pre>
 * </p>
 *
 * </p>
 */
package com.curtisnewbie.module.tracing;

