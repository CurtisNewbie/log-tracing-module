package com.curtisnewbie.module.tracing.common;

/**
 * Constants used in log tracing
 *
 * @author yongjie.zhuang
 */
public final class TracingConstants {

    private TracingConstants() {
    }

    /** Id used for log tracing, especially in MDC */
    public static final String TRACE_ID = "traceId";

    /**
     * Field in {@link java.security.Principal} extracted and used as trace_id by {@link
     * com.curtisnewbie.module.tracing.filter.TracingHandlerInterceptorBase}
     */
    public static final String PRINCIPAL_TRACE_ID_FIELD = "username";
}
