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

    /** field extracted as trace id by {@link com.curtisnewbie.module.tracing.aop.TracingEntryAspect} */
    public static final String TRACE_ENTRY_FIELD = "id";
}
